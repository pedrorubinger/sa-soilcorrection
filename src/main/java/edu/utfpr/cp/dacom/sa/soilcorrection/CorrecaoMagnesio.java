package edu.utfpr.cp.dacom.sa.soilcorrection;

public class CorrecaoMagnesio {
	public void calcularCorrecaoMagnesio(int fonteDeNutriente) {
		// TO DO: Implementar...
		if (fonteDeNutriente == 1) {
			// 18 * 0.0248 * 
		}
	}
	
	
	// N55 = SE(C6=1;"10 a 15";SE(C6=2;"8 a 12";""))
	public String getParticipacaoIdealNaCTCDoSolo(int texturaDoSolo) { 
		if (texturaDoSolo == 1) {
			return "10 a 15";
		}

		if (texturaDoSolo == 2) {
			return "8 a 12";
		}

		return "";
	}
}
