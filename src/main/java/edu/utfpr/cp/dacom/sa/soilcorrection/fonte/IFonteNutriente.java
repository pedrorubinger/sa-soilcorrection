package edu.utfpr.cp.dacom.sa.soilcorrection.fonte;

import java.util.Set;

import edu.utfpr.cp.dacom.sa.soilcorrection.nutriente.NutrienteAdicional;

public interface IFonteNutriente {

    public double getTeorFonte();
    public Set<NutrienteAdicional> getNutrientesAdicionais();
}
