package edu.utfpr.cp.dacom.sa.soilcorrection;

public class CorrecaoCalcioMagnesio {
	/**
	 * ---- NOTAS ----
	 * 
	 * CTC = Capacidade de Troca de Cátions
	 * TEOR DE CÁLCIO A ADICIONAR = (QTDE CÁLCIO NO SOLO * % DE PARTICIPAÇÃO DE CÁLCIO NA CTC DESEJADA / PARTICIP. ATUAL NA CTC DO SOLO) - QTDE CÁLCIO NO SOLO - QUANTIDADE ADICIONADA;
     */
	public double calculaTeorDeCaOASerAdicionado(
		double qtdeCalcioNoSolo,
		double participacaoNaCTCDesejada,
		double participacaoAtualNaCTCDoSolo,
		double qtdeCaOAdicionada
	) {
		return (qtdeCalcioNoSolo * participacaoNaCTCDesejada / participacaoAtualNaCTCDoSolo) - qtdeCalcioNoSolo - qtdeCaOAdicionada;
	}
	
	// E49 = N54 = SE(C6=1;"45 a 55";SE(C6=2;"35 a 40";""))
	public String getParticipacaoIdealNaCTCDoSoloCalcio(int texturaDoSoloId) {
		if (texturaDoSoloId == 1) {
			return "45 a 55";
		}

		if (texturaDoSoloId == 2) {
			return "35 a 40";
		}

		return "";
	}
	
	// N55 = SE(C6=1;"10 a 15";SE(C6=2;"8 a 12";""))
	public String getParticipacaoIdealNaCTCDoSoloMagnesio(int texturaDoSolo) { 
		if (texturaDoSolo == 1) {
			return "10 a 15";
		}

		if (texturaDoSolo == 2) {
			return "8 a 12";
		}

		return "";
	}
	
	// V52 = P10 = F11/R6*100
	// R6 = R5+L11
	// R5 = F11+H11+D11
	// F11 = calcio
	// H11 = magnesio
	// D11 = potássio
	// L11 = H + AL = DETERMINAÇÃO DE (H + Al) ou ACIDEZ POTENCIAL segundo laborsolo.com.br
	public double calculaParticipacaoAtualNaCTCDoSoloCalcio(
		double calcioNoSolo,
		double magnesioNoSolo,
		double potassioNoSolo,
		double acidezPotencialNoSolo
	) {
		return calcioNoSolo / (calcioNoSolo + magnesioNoSolo + potassioNoSolo + acidezPotencialNoSolo) * 100;
	}
	
	/* TO DO: Implementar método completo. */
	public void calculaQtdeASerAdicionada(
		double prnt,
		double teorDeCaO,
		double inputD54,
		int fonteId
	) {
		/*
		
		double value = 0;
		
		if (inputD54 > 0.01) {
			value = inputD54;
		} else {
			value = 1;
		}

		if (teorDeCaO / ((value * 0.01783) + (I105 * A105 / 1000))) {
			
		}

		*/
	}
	
	/* TO DO: Implementar método completo */
	public double calculaQtdeDeCaOAdicionadaAtravesDaFosfotagem(
		int fonteDeFosforoId
	) {
		/* if (fonteDeFosforoId == 1) {
			(H11*2*2.29*100/F15/100)*100/G20 * 2.42;
		} */
		return 0.0;
	}
}

/**
  -------------------------- RASCUNHOS E NOTAS ----------------------------------
 
  - CÁLCULO DE QUANTIDADE A APLICAR
	- VARIA COM A FONTE, PRNT, % de participação do CÁLCIO na CTC, desejada: MÍNIMO DE 44.8, 
	- noSolo (h13) / R6 * 100
	
	- QUANTIDADE DE CORRETIVO A INCORPORAR: =SE(P88>0,0001;P88;"0,0")
	P88 = SE(P91>0,001;P91;SE(P91<=13;"0,0"))
	
	- TEOR DE CÁLCIO A ADICIONAR = ('EQUILIBRIO E CORREÇÃO NA CTC'!F11*'EQUILIBRIO E CORREÇÃO NA CTC'!E51/'EQUILIBRIO E CORREÇÃO NA CTC'!E48)-'EQUILIBRIO E CORREÇÃO NA CTC'!F11-I105
	
	-> TEOR DE CÁLCIO A ADICIONAR = (QTDE CÁLCIO NO SOLO * % DE PARTICIPAÇÃO DE CÁLCIO NA CTC DESEJADA / PARTICIP. ATUAL NA CTC DO SOLO) - QTDE CÁLCIO NO SOLO - QUANTIDADE ADICIONADA;
	
	----------------------------------------------------------------------------------------------------------------------------------------------
	-> QUANTIDADE DE CaO ADCIONADA ATRAVES DA FOSFOTAGEM = 'EQUILIBRIO E CORREÇÃO NA CTC'!P129*A105/1000
	
	I105 = M22 / 2,42
	=SE(FONTE DE FOSFORO A UTILIZAR=1;'Memória de cálculo'!B24*0,28;SE(D23=2;'Memória de cálculo'!B24*0,2;SE(D23=3;'Memória de cálculo'!B24*0,09;SE(D23=4;'Memória de cálculo'!B24*0,16;SE(D23=5;'Memória de cálculo'!B24*0,28;SE(D23=6;'Memória de cálculo'!B24*0,52;SE(D23>=7;N28;""))))))) / 2,42
	
	1. EQUILIBRIO E CORREÇÃO NA CTC'!P129*A105/1000
	2. CTC P129 = M22/2,42
	3. M22 = N27
	4. N27 = SE(D23=1;'Memória de cálculo'!B24*0,28;SE(D23=2;'Memória de cálculo'!B24*0,2;SE(D23=3;'Memória de cálculo'!B24*0,09;SE(D23=4;'Memória de cálculo'!B24*0,16;SE(D23=5;'Memória de cálculo'!B24*0,28;SE(D23=6;'Memória de cálculo'!B24*0,52;SE(D23>=7;N28;"")))))))
	5. MEM B24 = B22*2,42
	6. MEM B22 = H16*100/G20
	7. MEM H16 = G14*100/F15/100
	8. G20 = EQUILIBRIO E CORREÇÃO NA CTC !AL40
	9. AL40 = SE(D23=1;"18";SE(D23=2;"41";SE(D23=3;"48";SE(D23=4;"45";SE(D23=5;"18";SE(D23=6;"33";SE(D23>=7;AL41;"")))))))
	10. AL41 = SE(D23=7;"29";SE(D23=8;"32";SE(D23=9;"24";SE(D23=10;"18,5";SE(D23=11;"52";SE(D23=12;"18";""))))))
	11. G14 = H12*2,29
	12. H12 = 'EQUILIBRIO E CORREÇÃO NA CTC'!N72
	13. N72 = N71 = 'Memória de cálculo'!H11*2
	14. H11 = C12 = 'EQUILIBRIO E CORREÇÃO NA CTC'!Q72
	15. CTC Q72 = SE('Memória de cálculo'!E10<0,01;"0,0";'Memória de cálculo'!E10)
	13. F15 = 'EQUILIBRIO E CORREÇÃO NA CTC'!D25
	----------------------------------------------------------------------------------------------------------------------------------------------
	
	-> QUANTIDADE A APLICAR
	1. T70*100/PRNT
	2. T70 = MEM F113
	3. MEM F113 = CTC O117
	4. CTC O117 = SE(P88>0,0001;P88;"0,0")
	5. P88 = SE(P91>0,001;P91;SE(P91<=13;"0,0"))
	6. P91 = 'Memória de cálculo'!F96/'Memória de cálculo'!G109
	7. F96 = Teor de CaO a adicionar
	8. g109 = I107+I105
	9. I107 = E101*0,01783
	10. E101 = G102
	11. G102 = SE(G101>0,01;G101;J102)
	12. G101 = EQUILIBRIO E CORREÇÃO NA CTC'!D54
	13. CTC D54 = INPUT DE DADOS
	13. J102 = EQUILIBRIO E CORREÇÃO NA CTC'!O102
	13. CTC O102 = SE(D52=1;"30,4";SE(D52=2;"56";SE(D52=3;"54";SE(D52=4;"29";SE(D52=5;"75,7";SE(D52=6;"35";""))))))
	
	14. I105 = EQUILIBRIO E CORREÇÃO NA CTC'!P129*A105/1000
	15. P129 = M22/2,42
	16. M22 = N27
	17. N27 = SE(D23=1;'Memória de cálculo'!B24*0,28;SE(D23=2;'Memória de cálculo'!B24*0,2;SE(D23=3;'Memória de cálculo'!B24*0,09;SE(D23=4;'Memória de cálculo'!B24*0,16;SE(D23=5;'Memória de cálculo'!B24*0,28;SE(D23=6;'Memória de cálculo'!B24*0,52;SE(D23>=7;N28;"")))))))
	18. 

*/