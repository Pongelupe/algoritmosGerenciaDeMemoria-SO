package br.com.gerenciaMemoria.algorithm;

import br.com.gerenciaMemoria.model.DadosEntradaAlgoritmo;
import br.com.gerenciaMemoria.model.NomeAlgoritmo;

public class Lru extends AlgoritmoDeGerencia {

	public Lru(DadosEntradaAlgoritmo entrada) {
		super(entrada, NomeAlgoritmo.LRU);
	}

	private double algoritmoSubstLocal() {
		// TODO Auto-generated method stub
		return 0;
	}

	private double algoritmoSubstGlobal() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getTaxaErros() {
		return isSubstituicaoGlobal() ? algoritmoSubstGlobal() : algoritmoSubstLocal();
	}
}
