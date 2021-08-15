package edu.utfpr.cp.dacom.sa.soilcorrection.fonte;

import java.util.Set;

import edu.utfpr.cp.dacom.sa.soilcorrection.nutriente.NomeNutrienteAdicional;
import edu.utfpr.cp.dacom.sa.soilcorrection.nutriente.NutrienteAdicional;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FontePotassio implements IFonteNutriente {

	CLORETO_POTASSIO(0.58, Set.of()),
    SULFATO_POTASSIO(0.52, Set.of(NutrienteAdicional.builder().nome(NomeNutrienteAdicional.ENXOFRE).teorNutriente(0.17).build())),
    SULFATO_POTASSIO_MAGNESIO(
    	0.22,
    	Set.of(
    		NutrienteAdicional.builder().nome(NomeNutrienteAdicional.ENXOFRE).teorNutriente(0.22).build(),
    		NutrienteAdicional.builder().nome(NomeNutrienteAdicional.MAGNESIO).teorNutriente(0.18).build())
    );

    private final double teorFonte;
    @NonNull private final Set<NutrienteAdicional> nutrientesAdicionais;
}
