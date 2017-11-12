package br.com.gerenciaMemoria.algorithm;

import java.util.ArrayList;

import br.com.gerenciaMemoria.model.DadosEntradaAlgoritmo;
import br.com.gerenciaMemoria.model.NomeAlgoritmo;
import br.com.gerenciaMemoria.model.Processo;

public class Opt extends AlgoritmoDeGerencia {

	private ArrayList<Processo> memoria;

	public Opt(DadosEntradaAlgoritmo entrada) {
		super(entrada, NomeAlgoritmo.OPT);
		memoria = new ArrayList<Processo>(entrada.getTamanhoQuadros());
	}

	public void aplicaAlgoritmo() {
		if (entrada.getSubstituicao().equals("Global"))
			algoritmoSubstGlobal();
		else
			algoritmoSubstLocal();

	}

	private void algoritmoSubstLocal() {
		final int tamanhoQuadros = entrada.getTamanhoQuadros();
		int numQuadrosPorProcesso = -1;

		if (tamanhoQuadros % 2 == 0)
			numQuadrosPorProcesso = tamanhoQuadros / entrada.quantidadeProcessos();

	}

	private void algoritmoSubstGlobal() {
		if (isMemoriaCheia()) {
		}
	}

	private boolean isMemoriaCheia() {
		return memoria.size() == entrada.getTamanhoQuadros();
	}

	@Override
	public double getTaxaErros() {
		// TODO Auto-generated method stub
		return 0;
	}

}
