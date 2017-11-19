package br.com.gerenciaMemoria.algorithm;

import java.util.ArrayList;

import br.com.gerenciaMemoria.model.DadosEntradaAlgoritmo;
import br.com.gerenciaMemoria.model.NoSequencia;
import br.com.gerenciaMemoria.model.NomeAlgoritmo;
import br.com.gerenciaMemoria.model.Processo;

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

	public boolean isAlocacaoIgual() {
		return entrada.getAlocacao().equals("Igual");
	}

	public boolean isSubstituicaoGlobal() {
		return entrada.getSubstituicao().equals("Global");
	}

	public int getNumPaginasProcessoProporcional(int requisicoes, int tamanhoQuadros, int numPaginasProcesso) {
		double coefiente = (double) numPaginasProcesso / requisicoes;
		int numPags = (int) (coefiente * tamanhoQuadros);
		return numPags <= 0 ? 1 : numPags;
	}

	public boolean isEmMemoria(ArrayList<NoSequencia> memoria, NoSequencia noAcessado) {
		boolean flagEmMemoria = true;
		boolean isEmMemoria = false;
		int i = 0;

		while (i < memoria.size() && flagEmMemoria) {
			NoSequencia noSequencia = memoria.get(i);
			isEmMemoria = noSequencia.getProcesso().equals(noAcessado.getProcesso())
					&& noSequencia.getPaginaAcessada() == noAcessado.getPaginaAcessada();

			if (isEmMemoria)
				flagEmMemoria = false;
			i++;
		}

		return isEmMemoria;
	}

	public int getMemoria() {
		int tamMemoria = 0;
		int tamanhoQuadros = entrada.getTamanhoQuadros();
		if (isAlocacaoIgual()) {
			int divisao = tamanhoQuadros / entrada.quantidadeProcessos();
			tamMemoria = divisao * (tamanhoQuadros % entrada.quantidadeProcessos());

		} else {
			for (Processo p : entrada.getProcessos())
				tamMemoria += getNumPaginasProcessoProporcional(entrada.getSequencia().size(), tamanhoQuadros,
						p.getNumPaginas());

		}

		return tamMemoria;
	}

}
