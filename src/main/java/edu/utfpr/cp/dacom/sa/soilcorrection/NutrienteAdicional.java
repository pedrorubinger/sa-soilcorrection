package edu.utfpr.cp.dacom.sa.soilcorrection;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class NutrienteAdicional {

    @NonNull private final NomeNutrienteAdicional nome;
	private final double teorNutriente;
    @Setter private double correcaoAdicional;
}
