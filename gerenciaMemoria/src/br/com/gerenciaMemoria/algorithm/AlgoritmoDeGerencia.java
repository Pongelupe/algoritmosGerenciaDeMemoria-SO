package br.com.gerenciaMemoria.algorithm;

import br.com.gerenciaMemoria.model.DadosEntradaAlgoritmo;

public abstract class AlgoritmoDeGerencia {

	protected final DadosEntradaAlgoritmo entrada;
	protected int hits;
	protected final int requisicoes;

	public AlgoritmoDeGerencia(DadosEntradaAlgoritmo entrada) {
		this.entrada = entrada;
		this.requisicoes = entrada.getNumeroRequisicoes();
	}

	abstract double getTaxaErros();

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public DadosEntradaAlgoritmo getEntrada() {
		return entrada;
	}

	public int getRequisicoes() {
		return requisicoes;
	}
}
