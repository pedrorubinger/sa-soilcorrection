package edu.utfpr.cp.dacom.sa.soilcorrection;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestaCorrecaoCalcioMagnesio {	
	@Test
	void testaTeorDeCaOASerAdicionado() {
		var correcaoCalcio = new CorrecaoCalcioMagnesio();
		int idFonteDeFosforo = 1;
		double magnesioNoSolo = 1.63;
		double calcioNoSolo = 5.76;
		double potassioNoSolo = 0.15;
		double acidezPotencial = 5.35;
		double eficienciaDeFosforo = 70;
		double partDeCalcioNaCTCDesejada = 55;
		double fosforoNoSolo = 8.59;
		double teorDeFosforoASerAtingido = 12;

		double teorASerAdicionado = correcaoCalcio.calculaTeorDeCaOASerAdicionado(
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

		assertEquals(1.312, teorASerAdicionado, 0.01);
	}
	
	@Test
	void testaParticipacaoAtualNaCTCDoSoloCalcio() {
		var correcaoCalcioMagnesio = new CorrecaoCalcioMagnesio();
		double calcio = 5.76;
		double magnesio = 1.63;
		double potassio = 0.15;
		double acidezPotencial = 5.35;
		double partAtual = correcaoCalcioMagnesio.calculaParticipacaoAtualNaCTCDoSoloCalcio(
			calcio,
			magnesio,
			potassio,
			acidezPotencial
		);

		assertEquals(44.685802948, partAtual, 0.001);
	}
	
	// Planilha de Exemplo 03
	@Test
	void testaParticipacaoAtualNaCTCDoSoloMagnesio() {
		var correcaoCalcioMagnesio = new CorrecaoCalcioMagnesio();
		double calcio = 12.45;
		double magnesio = 3.47;
		double potassio = 0.82;
		double acidezPotencial = 5.15;
		double partAtual = correcaoCalcioMagnesio.calculaParticipacaoAtualNaCTCDoSoloMagnesio(
			magnesio,
			calcio,
			potassio,
			acidezPotencial
		);

		assertEquals(15.9, partAtual, 0.1);
	}
	
	// Planilha de Exemplo 01
	@Test
	void testaQuantidadeASerAplicada() {
		var correcaoCalcioMagnesio = new CorrecaoCalcioMagnesio();
		
		double PRNT = 70;
		double calcioNoSolo = 5.76;
		double magnesioNoSolo = 1.63;
		double potassioNoSolo = 0.15;
		double acidezPotencial = 5.35;
		double partDeCalcioNaCTCDesejada = 55;
		double teorDeCaODoCorretivo = 0;
		int idFonteDeCorretivo = 2;
		int idFonteDeFosforo = 1;
		double eficienciaDeFosforo = 70;
		double fosforoNoSolo = 8.59;
		double teorDeFosforoASerAtingido = 12;
		
		double qtde = correcaoCalcioMagnesio.calculaQuantidadeASerAplicada(
			PRNT,
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
		assertEquals(1.85, qtde, 0.01);
	}
	
	// Planilha de Exemplo 01
	@Test
	void testaCalculoDoCustoEmReaisPorHectare() {
		var correcaoCalcioMagnesio = new CorrecaoCalcioMagnesio();
		int idFonteDeCorretivo = 2;
		double teorDeCaODoCorretivo = 0;
		int idFonteDeFosforo = 1;
		double magnesioNoSolo = 1.63;
		double eficienciaDeFosforo = 70;
		double calcioNoSolo = 5.76;
		double potassioNoSolo = 0.15;
		double acidezPotencial = 5.35;
		double PRNT = 70;
		double partDeCalcioNaCTCDesejada = 55;
		double valorEmReaisPorTonelada = 500;
		double fosforoNoSolo = 8.59;
		double teorDeFosforoASerAtingido = 12;

		double custo = correcaoCalcioMagnesio.calculaCustoEmReaisPorHectare(
			idFonteDeCorretivo,
			teorDeCaODoCorretivo,
			idFonteDeFosforo,
			magnesioNoSolo,
			eficienciaDeFosforo,
			calcioNoSolo,
			potassioNoSolo,
			acidezPotencial,
			PRNT,
			partDeCalcioNaCTCDesejada,
			valorEmReaisPorTonelada,
			fosforoNoSolo,
			teorDeFosforoASerAtingido
		);
	
		assertEquals(922.68, custo, 0.01);
	}
}
