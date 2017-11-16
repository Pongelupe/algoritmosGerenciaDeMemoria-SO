package br.com.gerenciaMemoria.algorithm;

import br.com.gerenciaMemoria.model.DadosEntradaAlgoritmo;
import br.com.gerenciaMemoria.model.NomeAlgoritmo;

public class My extends AlgoritmoDeGerencia {

	public My(DadosEntradaAlgoritmo entrada) {
		super(entrada, NomeAlgoritmo.MY);
	}

	@Override
	public double getTaxaErros() {
		// TODO Auto-generated method stub
		return 0;
	}

}
