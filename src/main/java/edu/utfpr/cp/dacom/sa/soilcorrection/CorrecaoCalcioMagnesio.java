package edu.utfpr.cp.dacom.sa.soilcorrection;

public class CorrecaoCalcioMagnesio {
	private EquilibrioCorrecaoCTC equilibrioCorrecaoCTC = new EquilibrioCorrecaoCTC();

	public double calculaQtdeCaOAdicionadaPorHa( // Cálculo da célula I105
		int idFonteDeFosforo,
		double magnesioNoSolo,
		double fosforoNoSolo,
		double eficienciaDeFosforo,
		double teorDeFosforoASerAtingido
	) {
		return (getN27(idFonteDeFosforo, magnesioNoSolo, eficienciaDeFosforo, teorDeFosforoASerAtingido, fosforoNoSolo) / 2.42) * getCTC_AM40(idFonteDeFosforo) / 1000;
	}

	public double calculaTeorDeCaOASerAdicionado(
		double calcioNoSolo,
		double magnesioNoSolo,
		double potassioNoSolo,
		double fosforoNoSolo,
		double acidezPotencial,
		double partDeCalcioNaCTCDesejada,
		double eficienciaDeFosforo,
		double teorDeFosforoASerAtingido,
		int idFonteDeFosforo
	) {
		double qtdeCaOAdicionadaPorHa = calculaQtdeCaOAdicionadaPorHa(idFonteDeFosforo, magnesioNoSolo, fosforoNoSolo, eficienciaDeFosforo, teorDeFosforoASerAtingido); // I105
		return (calcioNoSolo * partDeCalcioNaCTCDesejada / getPartAtualDeCalcioNaCTC(calcioNoSolo, magnesioNoSolo, potassioNoSolo, acidezPotencial)) - calcioNoSolo - qtdeCaOAdicionadaPorHa;
	}

	public String getParticipacaoIdealNaCTCDoSoloCalcio(int texturaDoSoloId) {
		if (texturaDoSoloId == 1) {
			return "45 a 55";
		}

		if (texturaDoSoloId == 2) {
			return "35 a 40";
		}

		return "";
	}

	public String getParticipacaoIdealNaCTCDoSoloMagnesio(int texturaDoSolo) { 
		if (texturaDoSolo == 1) {
			return "10 a 15";
		}

		if (texturaDoSolo == 2) {
			return "8 a 12";
		}

		return "";
	}

	public double calculaParticipacaoAtualNaCTCDoSoloCalcio(
		double calcioNoSolo,
		double magnesioNoSolo,
		double potassioNoSolo,
		double acidezPotencialNoSolo
	) {
		return calcioNoSolo / (equilibrioCorrecaoCTC.calculaSCmol(potassioNoSolo, calcioNoSolo, magnesioNoSolo) + acidezPotencialNoSolo) * 100;
	}

	public double calculaParticipacaoAtualNaCTCDoSoloMagnesio(
		double magnesioNoSolo,
		double calcioNoSolo,
		double potassioNoSolo,
		double acidezPotencial
	) {
		return magnesioNoSolo / (equilibrioCorrecaoCTC.calculaSCmol(potassioNoSolo, calcioNoSolo, magnesioNoSolo) + acidezPotencial) * 100;
	}

	// MEM CALC G109
	public double getQtdeTotalDeCalcioAdicionada(
		double teorDeCaODoCorretivo, // INPUT D54 = G101
		int idFonteDeCorretivo,
		int idFonteDeFosforo,
		double magnesioNoSolo,
		double eficienciaDeFosforo,
		double fosforoNoSolo,
		double teorDeFosforoASerAtingido,
		double qtdeCaOAdicionadaPorHa
	) {
		// G102 = SE(G101>0,01;G101;J102)
		double G102 = 0;

		if (teorDeCaODoCorretivo > 0.01) {
			G102 = teorDeCaODoCorretivo;
		} else {
			switch(idFonteDeCorretivo) {
				case 1:
					G102 = 30.4;
					break;
				case 2:
					G102 = 56;
					break;
				case 3:
					G102 = 54;
					break;
				case 4:
					G102 = 29;
					break;
				case 5:
					G102 = 75.7;
					break;
				case 6:
					G102 = 35;
					break;
				default:
					G102 = 0;
					break;
			}
		}

		double qtdeDeCalcioAdicionadaPorToneladaDeCorretivo = G102 * 0.01783; // I107

		return qtdeDeCalcioAdicionadaPorToneladaDeCorretivo + qtdeCaOAdicionadaPorHa; // I107 + I105
	}
	
	public double getQ72(double teorDeFosforoASerAtingido, double fosforoNoSolo) {
		double E10 = teorDeFosforoASerAtingido - fosforoNoSolo;

		if (E10 < 0.01) {
			return 0;
		} else {
			return E10;
		}
	}

	// =SE(D23=1;'Memória de cálculo'!B24*0,28;SE(D23=2;'Memória de cálculo'!B24*0,2;SE(D23=3;'Memória de cálculo'!B24*0,09;SE(D23=4;'Memória de cálculo'!B24*0,16;SE(D23=5;'Memória de cálculo'!B24*0,28;SE(D23=6;'Memória de cálculo'!B24*0,52;SE(D23>=7;N28;"")))))))
	// N28=SE(D23=7;'Memória de cálculo'!B24*0,52;SE(D23=8;'Memória de cálculo'!B24*0,45;SE(D23=9;'Memória de cálculo'!B24*0,28;SE(D23=10;'Memória de cálculo'!B24*0,44;SE(D23=11;'Memória de cálculo'!B24*0;SE(D23=12;'Memória de cálculo'!B24*0,18;""))))))
	public double getN27(
		int idFonteDeFosforo,
		double magnesioNoSolo,
		double eficienciaDeFosforo,
		double teorDeFosforoASerAtingido,
		double fosforoNoSolo
	) {
		// double G20 = 0;
		double teorDeP2O5DaFonte = 0;
		
		// G20 = =SE(D23=1;"18";SE(D23=2;"41";SE(D23=3;"48";SE(D23=4;"45";SE(D23=5;"18";SE(D23=6;"33";SE(D23>=7;AL41;"")))))))
		switch(idFonteDeFosforo) {
			case 1:
				teorDeP2O5DaFonte = 18;
				break;
			case 2:
				teorDeP2O5DaFonte = 41;
				break;
			case 3:
				teorDeP2O5DaFonte = 48;
				break;
			case 4:
				teorDeP2O5DaFonte = 45;
				break;
			case 5:
				teorDeP2O5DaFonte = 18;
				break;
			case 6:
				teorDeP2O5DaFonte = 33;
				break;
		}
		
		// =SE(D23=7;"29";SE(D23=8;"32";SE(D23=9;"24";SE(D23=10;"18,5";SE(D23=11;"52";SE(D23=12;"18";""))))))
		if (idFonteDeFosforo == 7) {
			switch(idFonteDeFosforo) {
				case 7:
					teorDeP2O5DaFonte = 29;
					break;
				case 8:
					teorDeP2O5DaFonte = 32;
					break;
				case 9:
					teorDeP2O5DaFonte = 24;
					break;
				case 10:
					teorDeP2O5DaFonte = 18.5;
					break;
				case 11:
					teorDeP2O5DaFonte = 52;
					break;
				case 12:
					teorDeP2O5DaFonte = 18;
					break;
				default:
					teorDeP2O5DaFonte = 0;
			}
		}
		
		// H11 = magnesio
		// H16 = magnesioNoSolo*2*2.29*100/eficienciaDeFosforo/100
		// B24 = B22*2,42
		// B22 = H16*100/G20
		// H16 = G14*100/F15/100
		// G14 = H12*2,29
		// b24 = ((((getQ72() *2) * 2.29) * 100 / eficienciaDeFosforo / 100) * 100 / G20) * 2.42;
		// MEM CALC B24 = ((magnesioNoSolo*2*2.29*100/eficienciaDeFosforo/100) * 100 / G20) * 2.42
		
		// double qtdeEmKgPorAlqueireParaAplicar = ((magnesioNoSolo * 2 * 2.29 * 100 / eficienciaDeFosforo / 100) * 100 / G20) * 2.42;
		double qtdeEmKgPorAlqueireParaAplicar = (((((getQ72(teorDeFosforoASerAtingido, fosforoNoSolo) *2) * 2.29) * 100 / eficienciaDeFosforo / 100) * 100 / teorDeP2O5DaFonte) * 2.42) * 100;

		if (idFonteDeFosforo >= 7) {
			switch(idFonteDeFosforo) {
				case 7:
					return qtdeEmKgPorAlqueireParaAplicar * 0.52;
				case 8:
					return qtdeEmKgPorAlqueireParaAplicar * 0.45;
				case 9:
					return qtdeEmKgPorAlqueireParaAplicar * 0.28;
				case 10:
					return qtdeEmKgPorAlqueireParaAplicar * 0.44;
				case 11:
					return 0;
				case 12:
					return qtdeEmKgPorAlqueireParaAplicar * 0.18;
				default:
					return 0;
			}
		}
		
		switch(idFonteDeFosforo) {
			case 1:
				return qtdeEmKgPorAlqueireParaAplicar * 0.28;
			case 2:
				return qtdeEmKgPorAlqueireParaAplicar * 0.2;
			case 3:
				return qtdeEmKgPorAlqueireParaAplicar * 0.09;
			case 4:
				return qtdeEmKgPorAlqueireParaAplicar * 0.16;
			case 5:
				return qtdeEmKgPorAlqueireParaAplicar * 0.28;
			case 6:
				return qtdeEmKgPorAlqueireParaAplicar * 0.52;
			default:
				return 0;
		}
	}

	public double getCTC_AM40(int idFonteDeFosforo) {
		// =SE(D23=7;"0,92716";SE(D23=8;"0,80235";SE(D23=9;"0,49924";SE(D23=10;"0,795218";SE(D23=11;"0,0";SE(D23=12;"0,0";""))))))
		if (idFonteDeFosforo >= 7) {
			switch(idFonteDeFosforo) {
				case 7:
					return 0.92716;
				case 8:
					return 0.80235;
				case 9:
					return 0.49924;
				case 10:
					return 0.795218;
				case 11:
				case 12:
					return 0;
			}
		}

		// AM40 = =SE(D23=1;"0,49924";SE(D23=2;"0,33877";SE(D23=3;"0,0";SE(D23=4;"0,0";SE(D23=5;"0,49924";SE(D23=6;"0,92716";SE(D23>=7;AM41;"")))))))
		switch(idFonteDeFosforo) {
			case 1:
				return 0.49924;
			case 2:
				return 0.33877;
			case 3:
			case 4:
				return 0;
			case 5:
				return 0.49924;
			case 6:
				return 0.92716;
			default:
				return 0;
		}
	}
	
	// G101 = TEOR DE CaO do corretivo
	// I107 = E101*0,01783
	// E101 = G102 = SE(G101>0,01;G101;J102)
	// J102 = CTC O102 = =SE(D52=1;"30,4";SE(D52=2;"56";SE(D52=3;"54";SE(D52=4;"29";SE(D52=5;"75,7";SE(D52=6;"35";""))))))
	
	// CÉLULA MEM CALC I107 = qtde de calcio adicionada por tonelada de corretivo
	public double getQtdeCalcioAdicionadaPorTonDeCorretivo(double teorDeCaOCorretivo, int idFonteDeCorretivo) {
		double teorDeCaoCorretivoEmPorcentagem = teorDeCaOCorretivo;
		
		if (teorDeCaOCorretivo > 0.01) {
			return teorDeCaoCorretivoEmPorcentagem * 0.01783;
		}

		switch(idFonteDeCorretivo) {
			case 1:
				teorDeCaoCorretivoEmPorcentagem = 30.4;
				break;
			case 2:
				teorDeCaoCorretivoEmPorcentagem = 56;
				break;
			case 3:
				teorDeCaoCorretivoEmPorcentagem = 54;
				break;
			case 4:
				teorDeCaoCorretivoEmPorcentagem = 29;
				break;
			case 5:
				teorDeCaoCorretivoEmPorcentagem = 75.7;
				break;
			case 6:
				teorDeCaoCorretivoEmPorcentagem = 35;
				break;
			default:
				teorDeCaoCorretivoEmPorcentagem = 0;
		}

		return teorDeCaoCorretivoEmPorcentagem * 0.01783;
	}
	
	public double calculaCustoEmReaisPorHectare(
		int idFonteDeCorretivo,
		double teorDeCaODoCorretivo,
		int idFonteDeFosforo,
		double magnesioNoSolo,
		double eficienciaDeFosforo,
		double calcioNoSolo,
		double potassioNoSolo,
		double acidezPotencial,
		double PRNT,
		double partDeCalcioNaCTCDesejada,
		double valorEmReaisPorTonelada,
		double fosforoNoSolo,
		double teorDeFosforoASerAtingido
	) {
		double teorDeCaOASerAdicionado = calculaTeorDeCaOASerAdicionado(
			calcioNoSolo,
			magnesioNoSolo,
			potassioNoSolo,
			fosforoNoSolo,
			acidezPotencial,
			partDeCalcioNaCTCDesejada,
			eficienciaDeFosforo,
			teorDeFosforoASerAtingido,
			idFonteDeFosforo
		);
		double qtdeCaOAdicionadaPorHa = calculaQtdeCaOAdicionadaPorHa(
			idFonteDeFosforo,
			magnesioNoSolo,
			fosforoNoSolo,
			eficienciaDeFosforo,
			teorDeFosforoASerAtingido
		);
		double qtdeTotalDeCalcioAdicionada = getQtdeTotalDeCalcioAdicionada(
			teorDeCaODoCorretivo,
			idFonteDeCorretivo,
			idFonteDeFosforo,
			magnesioNoSolo,
			eficienciaDeFosforo,
			fosforoNoSolo,
			teorDeFosforoASerAtingido,
			qtdeCaOAdicionadaPorHa
		);
		double P91 = teorDeCaOASerAdicionado / qtdeTotalDeCalcioAdicionada;
		double P104 = 0;
		double qtdeCorretivoAIncorporar = getQtdeCorretivoAIncorporar(
			calcioNoSolo,
			magnesioNoSolo,
			potassioNoSolo,
			acidezPotencial,
			partDeCalcioNaCTCDesejada,
			teorDeCaODoCorretivo,
			idFonteDeCorretivo,
			idFonteDeFosforo,
			eficienciaDeFosforo,
			fosforoNoSolo,
			teorDeFosforoASerAtingido
		);

		double qtdeEmToneladasPorAlqueire = (qtdeCorretivoAIncorporar * 100 / PRNT) * 2.42; // A116

		/*
		if (idFonteDeCorretivo == 1) {
			P104 = A116 / valorEmReaisPorTonelada // CalcarioDom;
		}

		if (idFonteDeCorretivo == 2) {
			P104 = A116 * valorEmReaisPorTonelada // CalcarioCalcitico;
		}

		if (idFonteDeCorretivo == 3) {
			P104 = A116 * valorEmReaisPorTonelada // CalcarioDeConcha;
		}

		if (idFonteDeCorretivo == 4) {
			P104 = A116 * valorEmReaisPorTonelada // GessoAgricola;
		}

		if (idFonteDeCorretivo == 5) {
			P104 = A116 * valorEmReaisPorTonelada // HidroxidoDeCalcio;
		}

		if (idFonteDeCorretivo == 6) {
			P104 = A116 * valorEmReaisPorTonelada // HidroxidoDeCalcarioMagnesiano;
		} */

		if (idFonteDeCorretivo == 7) {
			P104 = qtdeEmToneladasPorAlqueire * 44;
		} else if (idFonteDeCorretivo > 0) {
			P104 = qtdeEmToneladasPorAlqueire * valorEmReaisPorTonelada;
		}

		return P104 / 2.42;
	}
	
	public double getPartAtualDeCalcioNaCTC(double calcioNoSolo, double magnesioNoSolo, double potassioNoSolo, double acidezPotencial) {
		return calcioNoSolo / (calcioNoSolo + magnesioNoSolo + potassioNoSolo + acidezPotencial) * 100;
	}
	
	public double getQtdeCorretivoAIncorporar(
		double calcioNoSolo,
		double magnesioNoSolo,
		double potassioNoSolo,
		double acidezPotencial,
		double partDeCalcioNaCTCDesejada,
		double teorDeCaODoCorretivo,
		int idFonteDeCorretivo,
		int idFonteDeFosforo,
		double eficienciaDeFosforo,
		double fosforoNoSolo,
		double teorDeFosforoASerAtingido
	) {
		double qtdeCaOAdicionadaPorHa = calculaQtdeCaOAdicionadaPorHa(
			idFonteDeFosforo,
			magnesioNoSolo,
			fosforoNoSolo,
			eficienciaDeFosforo,
			teorDeFosforoASerAtingido
		); // I105
		double teorDeCaOASerAdicionado = calculaTeorDeCaOASerAdicionado(
			calcioNoSolo,
			magnesioNoSolo,
			potassioNoSolo,
			fosforoNoSolo,
			acidezPotencial,
			partDeCalcioNaCTCDesejada,
			eficienciaDeFosforo,
			teorDeFosforoASerAtingido,
			idFonteDeFosforo
		);
		double qtdeTotalDeCalcioAdicionada = getQtdeTotalDeCalcioAdicionada(
			teorDeCaODoCorretivo, // INPUT D54 = G101
			idFonteDeCorretivo,
			idFonteDeFosforo,
			magnesioNoSolo,
			eficienciaDeFosforo,
			fosforoNoSolo,
			teorDeFosforoASerAtingido,
			qtdeCaOAdicionadaPorHa // I105
		);
		double P91 = teorDeCaOASerAdicionado / qtdeTotalDeCalcioAdicionada;
		// F96 = =('EQUILIBRIO E CORREÇÃO NA CTC'!F11*'EQUILIBRIO E CORREÇÃO NA CTC'!E51/'EQUILIBRIO E CORREÇÃO NA CTC'!E48)-'EQUILIBRIO E CORREÇÃO NA CTC'!F11-I105
		// P91 = 'Memória de cálculo'!F96/'Memória de cálculo'!G109
		// P88 = =SE(P91>0,001;P91;SE(P91<=13;"0,0"))
		// =SE(P88>0,0001;P88;"0,0")
		double P88 = 0;
		
		if (P91 > 0.001) {
			P88 = P91;
		} else if (P91 <= 13) {
			P88 = 0;
		}
		
		if (P88 > 0.0001) {
			return P88;
		} else {
			return 0;
		}
	}

	public double calculaQuantidadeASerAplicada(
		double PRNT,
		double calcioNoSolo,
		double magnesioNoSolo,
		double potassioNoSolo,
		double acidezPotencial,
		double partDeCalcioNaCTCDesejada,
		double teorDeCaODoCorretivo,
		int idFonteDeCorretivo,
		int idFonteDeFosforo,
		double eficienciaDeFosforo,
		double fosforoNoSolo,
		double teorDeFosforoASerAtingido
	) {
		double qtdeCorretivoAIncorporar = getQtdeCorretivoAIncorporar(
			calcioNoSolo,
			magnesioNoSolo,
			potassioNoSolo,
			acidezPotencial,
			partDeCalcioNaCTCDesejada,
			teorDeCaODoCorretivo,
			idFonteDeCorretivo,
			idFonteDeFosforo,
			eficienciaDeFosforo,
			fosforoNoSolo,
			teorDeFosforoASerAtingido
		);

		return qtdeCorretivoAIncorporar * 100 / PRNT;
	}
}

// CALCULAR CUSTO EM REAIS POR HECTARE

// CALC MEM A116*K54
// A116 = F113*100/'EQUILIBRIO E CORREÇÃO NA CTC'!C53 *2,42
// F113 = SE(P88>0,0001;P88;"0,0")
// P88 = SE(P91>0,001;P91;SE(P91<=13;"0,0"))
// P91 = 'Memória de cálculo'!F96/'Memória de cálculo'!G109
// Mem CALC F96 = Teor de CaO a adicionar
// Mem CALC G109 = I107+I105
// I107 = E101*0,01783
// E101 = G102 = SE(G101>0,01;G101;J102)
// G101 = TEOR DE CaO do corretivo
// I105 = 'EQUILIBRIO E CORREÇÃO NA CTC'!P129*A105/1000

// N27 = =SE(D23=1;'Memória de cálculo'!B24*0,28;SE(D23=2;'Memória de cálculo'!B24*0,2;SE(D23=3;'Memória de cálculo'!B24*0,09;SE(D23=4;'Memória de cálculo'!B24*0,16;SE(D23=5;'Memória de cálculo'!B24*0,28;SE(D23=6;'Memória de cálculo'!B24*0,52;SE(D23>=7;N28;"")))))))
// D23 = idFonteDeFosforo
// AM40 = =SE(D23=1;"0,49924";SE(D23=2;"0,33877";SE(D23=3;"0,0";SE(D23=4;"0,0";SE(D23=5;"0,49924";SE(D23=6;"0,92716";SE(D23>=7;AM41;"")))))))
// I105 = N27/2,42 * AM40/1000