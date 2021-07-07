package edu.utfpr.cp.dacom.sa.soilcorrection;

import java.util.Set;

public enum FonteCalcioMagnesio implements IFonteNutriente {
	/* TO DO: Confirmar se estes valores estão corretos... */
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

    @Override
    public double getTeorFonte() {
        return this.teorFonte;
    }

    @Override
    public Set<NutrienteAdicional> getNutrientesAdicionais() {
        return this.nutrientesAdicionais;
    }
}
