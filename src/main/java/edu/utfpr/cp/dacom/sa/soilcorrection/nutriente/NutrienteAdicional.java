package edu.utfpr.cp.dacom.sa.soilcorrection.nutriente;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Getter
public class NutrienteAdicional {

    @NonNull private final NomeNutrienteAdicional nome;
	private final double teorNutriente;
    @Setter private double correcaoAdicional;
}
