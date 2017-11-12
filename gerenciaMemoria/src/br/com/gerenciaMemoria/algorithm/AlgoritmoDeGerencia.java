package br.com.gerenciaMemoria.algorithm;

import br.com.gerenciaMemoria.model.DadosEntradaAlgoritmo;
import br.com.gerenciaMemoria.model.NomeAlgoritmo;

public abstract class AlgoritmoDeGerencia {

	protected final DadosEntradaAlgoritmo entrada;
	protected final NomeAlgoritmo nome;

	public AlgoritmoDeGerencia(DadosEntradaAlgoritmo entrada, NomeAlgoritmo nome) {
		this.entrada = entrada;
		this.nome = nome;
	}

	public abstract double getTaxaErros();

	public DadosEntradaAlgoritmo getEntrada() {
		return entrada;
	}

	public String getNomeAlgoritmo() {
		return nome.name().toLowerCase();
	}

}
