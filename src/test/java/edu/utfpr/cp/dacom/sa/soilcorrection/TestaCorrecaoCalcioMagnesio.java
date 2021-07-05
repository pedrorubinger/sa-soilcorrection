package edu.utfpr.cp.dacom.sa.soilcorrection;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestaCorrecaoCalcioMagnesio {

	/**
	 * Varia com a fonte, PRNT, % de participação do CÁLCIO na CTC desejada (aparentemente no mínimo 44.8)
	 */
	@Test
	void testaQuantidadeAplicar() {
		var correcaoCalcio = new CorrecaoCalcio();
		double prnt = 70.0; // PRNT = Poder Relativo Neutralizante Total
		double inputD54 = 40.00; // TO DO: Descobrir o que é exatamente este input da célula D54
		int fonteId = 1;

		double qtdeCalcioNoSolo = 5.76;
		double participacaoNaCTCDesejada = 56.9;
		double participacaoAtualNaCTCDoSolo = 44.7;
		double qtdeCaOAdicionada = 0.00;
		double teorDeCaO = correcaoCalcio.calculaTeorDeCaOASerAdicionado(
			qtdeCalcioNoSolo,
			participacaoNaCTCDesejada,
			participacaoAtualNaCTCDoSolo,
			qtdeCaOAdicionada
		);
		
		/* TO DO: Terminar implementação do método. */
		correcaoCalcio.calculaQtdeASerAdicionada(prnt, teorDeCaO, inputD54, fonteId);
	}
	
	@Test
	void testaQtdeDeCaOAdicionadaAtravesDaFosfotagem() {
		var correcaoCalcio = new CorrecaoCalcio();
		int fonteDeFosforoId = 1;
		
		/* TO DO: Terminar implementação do método. */
		correcaoCalcio.calculaQtdeDeCaOAdicionadaAtravesDaFosfotagem(fonteDeFosforoId);
		// FonteCalcio.CALCARIO_DOLOMITICO.getTeorFonte()
	}

	@Test
	void testaTeorDeCaOASerAdicionado() {
		var correcaoCalcio = new CorrecaoCalcio();
		double qtdeCalcioNoSolo = 5.76;
		double participacaoNaCTCDesejada = 56.9;
		double participacaoAtualNaCTCDoSolo = 44.7;
		double qtdeCaOAdicionada = 0.00; // calculaQtdeDeCaOAdicionadaAtravesDaFosfotagem(...);

		double teorASerAdicionado = correcaoCalcio.calculaTeorDeCaOASerAdicionado(
			qtdeCalcioNoSolo,
			participacaoNaCTCDesejada,
			participacaoAtualNaCTCDoSolo,
			qtdeCaOAdicionada
		);

		assertEquals(1.57208053691, teorASerAdicionado, 0.001);
	}
}
