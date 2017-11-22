package br.com.gerenciaMemoria.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Queue;
import java.util.TreeMap;

import br.com.gerenciaMemoria.model.DadosEntradaAlgoritmo;
import br.com.gerenciaMemoria.model.NoSequencia;
import br.com.gerenciaMemoria.model.NomeAlgoritmo;

public class Lfu extends AlgoritmoDeGerencia {

	private int totalErros = 0;
	private int tamanhoQuadros = entrada.getTamanhoQuadros();
	private int requisicoes = entrada.getSequencia().size();

	public Lfu(DadosEntradaAlgoritmo entrada) {
		super(entrada, NomeAlgoritmo.LFU);
	}

	private double taxaErroGlobal() {
		tamanhoQuadros = getTamMemoriaGlobal();
		ArrayList<NoSequencia> memoria = new ArrayList<NoSequencia>(tamanhoQuadros);
		HashMap<NoSequencia, Integer> mapFrequencia = new HashMap<>();
		HashMap<NoSequencia, Integer> mapHit = new HashMap<>();

		for (int i = 0; i < requisicoes; i++) {
			NoSequencia noAcessado = entrada.getSequencia().get(i);

			if (memoria.size() == tamanhoQuadros) {
				if (!isEmMemoria(memoria, noAcessado)) {
					totalErros++;
					NoSequencia key = getKeyRemovida(mapFrequencia, mapHit);

					mapHit.remove(key);
					mapHit.put(noAcessado, 0);

					mapFrequencia.remove(key);
					mapFrequencia.put(noAcessado, i);

					memoria.remove(key);
					memoria.add(noAcessado);
				} else {
					contabilizaHit(mapHit, noAcessado);
				}

			} else {
				if (!isEmMemoria(memoria, noAcessado)) {
					totalErros++;
					memoria.add(noAcessado);
					mapHit.put(noAcessado, 0);
					mapFrequencia.put(noAcessado, i);
				} else {
					contabilizaHit(mapHit, noAcessado);
				}
			}

		}

		return (double) totalErros / requisicoes;
	}

	private NoSequencia getKeyRemovida(HashMap<NoSequencia, Integer> mapFrequencia,
			HashMap<NoSequencia, Integer> mapHit) {

		NoSequencia key = null;

		Entry<NoSequencia, Integer> entryMenorValor = mapHit.entrySet().stream()
				.max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get();

		List<Entry<NoSequencia, Integer>> entriesCandidatos = mapHit.entrySet().stream()
				.filter(entry -> entry.getValue().equals(entryMenorValor.getValue())).collect(Collectors.toList());

		boolean precisaDesempate = entriesCandidatos.size() > 1;

		if (precisaDesempate) {

			ArrayList<NoSequencia> nosCandidatos = new ArrayList<NoSequencia>();
			entriesCandidatos.stream().forEach(no -> nosCandidatos.add(no.getKey()));
			key = mapFrequencia.entrySet().stream().filter(entry -> nosCandidatos.contains(entry.getKey()))
					.min((entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue())).get().getKey();
		} else
			key = entryMenorValor.getKey();

		return key;
	}

	// Alocacao igual substitui��o local

	public double taxaErroIgual() {
		int totalErros = 0;
		int totalQuadros = 0;
		HashMap<String, Queue<Integer>> filaPaginas = new HashMap<>();
		HashMap<String, HashMap<Integer, Integer>> frequencia = new HashMap<>();
		HashMap<String, HashMap<Integer, Integer>> tempoEntrada = new HashMap<>();
		HashMap<String, Integer> tamanho = new HashMap<>();

		if (entrada.getAlocacao().equals("Igual")) {
			totalQuadros = entrada.getTamanhoQuadros() / entrada.getProcessos().size();

			for (int i = 0; i < entrada.getProcessos().size(); i++) {
				tamanho.put(entrada.getProcessos().get(i).getNome(), totalQuadros);

			}

		} else if (entrada.getAlocacao().equals("Proporcional")) {

			int totalPaginas = 0;
			for (int i = 0; i < entrada.getProcessos().size(); i++) {
				totalPaginas += entrada.getProcessos().get(i).getNumPaginas();

			}

			for (int i = 0; i < entrada.getProcessos().size(); i++) {

				int totalPaginasPr = entrada.getProcessos().get(i).getNumPaginas();
				totalQuadros = entrada.getTamanhoQuadros();
				totalQuadros = (int) ((double) totalPaginasPr / totalPaginas * totalQuadros);
				tamanho.put(entrada.getProcessos().get(i).getNome(), totalQuadros);

			}

		}

		for (int i = 0; i < entrada.getProcessos().size(); i++) {
			filaPaginas.put(entrada.getProcessos().get(i).getNome(), new LinkedList<>());
			frequencia.put(entrada.getProcessos().get(i).getNome(), new HashMap<>());
			tempoEntrada.put(entrada.getProcessos().get(i).getNome(), new HashMap<>());
		}

		for (int i = 0; i < entrada.getSequencia().size(); i++) {
			String nomeProcesso = entrada.getSequencia().get(i).getProcesso();
			int pagAcessada = entrada.getSequencia().get(i).getPaginaAcessada();

			if (filaPaginas.get(nomeProcesso).contains(pagAcessada) == false) {
				totalErros++;

				if (filaPaginas.get(nomeProcesso).size() < tamanho.get(nomeProcesso)) {

					filaPaginas.get(nomeProcesso).add(pagAcessada);
					frequencia.get(nomeProcesso).put(pagAcessada, 0);
					tempoEntrada.get(nomeProcesso).put(pagAcessada, i);

				} else {
					if (frequency(frequencia, nomeProcesso) == false) {

						int pagRemovida = filaPaginas.get(nomeProcesso).remove();
						frequencia.get(nomeProcesso).remove(pagRemovida);
						tempoEntrada.get(nomeProcesso).remove(pagRemovida);
						filaPaginas.get(nomeProcesso).add(pagAcessada);
						frequencia.get(nomeProcesso).put(pagAcessada, 0);
						tempoEntrada.get(nomeProcesso).put(pagAcessada, i);

					} else {

						int pagRemovida = retorne(frequencia, tempoEntrada, nomeProcesso);
						filaPaginas.get(nomeProcesso).remove(pagRemovida);
						frequencia.get(nomeProcesso).remove(pagRemovida);
						tempoEntrada.get(nomeProcesso).remove(pagRemovida);
						filaPaginas.get(nomeProcesso).add(pagAcessada);
						frequencia.get(nomeProcesso).put(pagAcessada, 0);
						tempoEntrada.get(nomeProcesso).put(pagAcessada, i);

					}
				}
			} else {

				int freq = frequencia.get(nomeProcesso).get(pagAcessada);
				freq++;
				frequencia.get(nomeProcesso).put(pagAcessada, freq);
			}

		}

		return (double) totalErros / entrada.getSequencia().size();

	}

	private boolean frequency(HashMap<String, HashMap<Integer, Integer>> map, String process) {
		boolean chave = false;
		HashMap<Integer, Integer> freq = map.get(process);
		ArrayList<Integer> a = new ArrayList<>();

		for (Integer i : freq.keySet()) {
			a.add(freq.get(i));
		}

		int primeiro = a.get(0);
		for (Integer i : a) {
			if (i != primeiro) {
				chave = true;
			}

		}

		return chave;
	}

	private int retorne(HashMap<String, HashMap<Integer, Integer>> map, HashMap<String, HashMap<Integer, Integer>> time,
			String process) {

		HashMap<Integer, Integer> freq = map.get(process);
		ArrayList<Integer> a = new ArrayList<>();
		HashMap<Integer, Integer> timeEntry = time.get(process);

		for (Integer i : freq.keySet()) {
			a.add(freq.get(i));
		}

		int menorFrequencia = Collections.min(a);
		TreeMap<Integer, Integer> t = new TreeMap<>();
		for (Integer i : freq.keySet()) {
			if (freq.get(i) == menorFrequencia) {
				t.put(timeEntry.get(i), i);
			}

		}

		return t.get(t.lastKey());
	}

	private void contabilizaHit(HashMap<NoSequencia, Integer> map, NoSequencia noAcessado) {
		map.put(noAcessado, map.get(noAcessado) - 1);
	}

	@Override
	public double getTaxaErros() {
		return isSubstituicaoGlobal() ? this.taxaErroGlobal() : this.taxaErroIgual();
	}

}
