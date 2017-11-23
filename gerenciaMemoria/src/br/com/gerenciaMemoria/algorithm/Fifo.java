package br.com.gerenciaMemoria.algorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import br.com.gerenciaMemoria.model.DadosEntradaAlgoritmo;
import br.com.gerenciaMemoria.model.NoSequencia;
import br.com.gerenciaMemoria.model.NomeAlgoritmo;

public class Fifo extends AlgoritmoDeGerencia {

	public Fifo(DadosEntradaAlgoritmo entrada) {
		super(entrada, NomeAlgoritmo.FIFO);
	}

	public double taxaErroGlobal() {
		int totalErros = 0;
		int requisicoes = entrada.getSequencia().size();
		int tamanhoQuadros = getTamMemoriaGlobal();
		List<NoSequencia> memoria = new LinkedList<NoSequencia>();

		for (int i = 0; i < requisicoes; i++) {
			NoSequencia noAcessado = entrada.getSequencia().get(i);
			if (memoria.size() == tamanhoQuadros) {
				if (!isEmMemoria(memoria, noAcessado)) {
					totalErros++;
					memoria.remove(0);
					memoria.add(noAcessado);
				}
			} else {
				if (!isEmMemoria(memoria, noAcessado)) {
					totalErros++;
					memoria.add(noAcessado);
				}
			}
		}

		return (double) totalErros / requisicoes;
	}

	// substitui��o local

	public double taxaErroLocal() {
		int totalErros = 0;
		int totalQuadros = 0;
		int totalPaginas = 0;
		HashMap<String, Queue<Integer>> dadosFila = new HashMap<>();
		HashMap<String, Integer> tamanho = new HashMap<>();

		if (entrada.getAlocacao().equals("Proporcional")) {
			for (int i = 0; i < entrada.quantidadeProcessos(); i++) {
				totalPaginas += entrada.getProcessos().get(i).getNumPaginas();
			}

			for (int i = 0; i < entrada.quantidadeProcessos(); i++) {
				double p = (double) entrada.getProcessos().get(i).getNumPaginas() / totalPaginas
						* entrada.getTamanhoQuadros();
				totalQuadros = (int) p;
				tamanho.put(entrada.getProcessos().get(i).getNome(), totalQuadros);
				dadosFila.put(entrada.getProcessos().get(i).getNome(), new LinkedList<Integer>());
			}
		} else {
			totalQuadros = entrada.getTamanhoQuadros() / entrada.getProcessos().size();
			for (int i = 0; i < entrada.quantidadeProcessos(); i++) {
				tamanho.put(entrada.getProcessos().get(i).getNome(), totalQuadros);
				dadosFila.put(entrada.getProcessos().get(i).getNome(), new LinkedList<Integer>());
			}

		}

		for (int i = 0; i < entrada.getSequencia().size(); i++) {

			String nomeProcesso = entrada.getSequencia().get(i).getProcesso();
			int pagAcessada = entrada.getSequencia().get(i).getPaginaAcessada();

			if (dadosFila.get(nomeProcesso).contains(pagAcessada) == false) {
				totalErros++;

				if (dadosFila.get(nomeProcesso).size() < tamanho.get(nomeProcesso)) {
					dadosFila.get(nomeProcesso).add(pagAcessada);
				} else {
					dadosFila.get(nomeProcesso).remove();
					dadosFila.get(nomeProcesso).add(pagAcessada);
				}
			}

		}

		return (double) totalErros / entrada.getSequencia().size();
	}

	@Override
	public double getTaxaErros() {

		return isSubstituicaoGlobal() ? taxaErroGlobal() : taxaErroLocal();
	}

}
