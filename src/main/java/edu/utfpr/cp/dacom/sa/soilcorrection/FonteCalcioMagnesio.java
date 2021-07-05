package edu.utfpr.cp.dacom.sa.soilcorrection;

import java.util.Set;

public enum FonteCalcioMagnesio implements IFonteNutriente {
	/* TO DO: Implementar os valores */
	CALCARIO_DOLOMITICO(0.28, Set.of()),
	CALCARIO_CALCITICO(0.20, Set.of()),
	CALCARIO_DE_CONCHA(0.09, Set.of()),
	GESSO_AGRICOLA(0.16, Set.of()),
	HIDROXIDO_DE_CALCIO(0.28, Set.of()),
	CALCARIO_MAGNESIANO(0.52, Set.of());

    private final double teorFonte;
    private final Set<NutrienteAdicional> nutrientesAdicionais;

	FonteCalcioMagnesio(double teorFonte, final Set<NutrienteAdicional> nutrientesAdicionais) {
		this.teorFonte = teorFonte;
		this.nutrientesAdicionais = nutrientesAdicionais;
	}
	
	public int getFonteDeCalcioId(String name) {
		switch(name) {
			case "CALCARIO_DOLOMITICO":
				return 1;
			case "CALCARIO_CALCITICO":
				return 2;
			case "CALCARIO_DE_CONCHA":
				return 3;
			case "GESSO_AGRICOLA":
				return 4;
			case "HIDROXIDO_DE_CALCIO":
				return 5;
			case "CALCARIO_MAGNESIANO":
				return 6;
			default:
				return 0;
		}
	}

    @Override
    public double getTeorFonte() {
        return this.teorFonte;
    }

    @Override
    public Set<NutrienteAdicional> getNutrientesAdicionais() {
        return this.nutrientesAdicionais;
    }
}
