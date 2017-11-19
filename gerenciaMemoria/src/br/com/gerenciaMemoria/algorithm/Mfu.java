package br.com.gerenciaMemoria.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;

import br.com.gerenciaMemoria.model.DadosEntradaAlgoritmo;
import br.com.gerenciaMemoria.model.NomeAlgoritmo;

public class Mfu extends AlgoritmoDeGerencia {

	public Mfu(DadosEntradaAlgoritmo entrada) {
		super(entrada, NomeAlgoritmo.MFU);
	}

	public boolean frequency(HashMap<String, TreeMap<Integer, Integer>> frequencia) {

		ArrayList<Integer> freq = new ArrayList<>();
		TreeMap<Integer, Integer> aux = new TreeMap<>();
		for (String process : frequencia.keySet()) {
			aux = frequencia.get(process);
			freq.addAll(aux.values());

		}
		return Collections.max(freq).equals(Collections.min(freq));

	}

	public int retorne(HashMap<String, TreeMap<Integer, Integer>> frequencia,
			HashMap<String, TreeMap<Integer, Integer>> tempoEntrada) {

		TreeMap<Integer, Integer> dados = new TreeMap<>();
		TreeMap<Integer, Integer> t = new TreeMap<>();

		for (String process : frequencia.keySet()) {

			for (Integer i : tempoEntrada.get(process).keySet()) {
				t.put(tempoEntrada.get(process).get(i), i);
			}

			dados.put(t.lastKey(), tempoEntrada.get(process).get(t.lastKey()));

		}

		System.out.println(dados.lastKey());

		return dados.lastKey();

	}

	public double taxaErroGlobal() {
		int totalErros = 0;

		LinkedList<Integer> filaPaginas = new LinkedList<>();
		LinkedList<String> filaProcessos = new LinkedList<>();
		HashMap<String, TreeMap<Integer, Integer>> frequencia = new HashMap<>();
		HashMap<String, TreeMap<Integer, Integer>> tempoEntrada = new HashMap<>();

		for (int i = 0; i < entrada.getProcessos().size(); i++) {
			frequencia.put(entrada.getProcessos().get(i).getNome(), new TreeMap<>());
			tempoEntrada.put(entrada.getProcessos().get(i).getNome(), new TreeMap<>());
		}

		for (int i = 0; i < entrada.getSequencia().size(); i++) {
			String nomeProcesso = entrada.getSequencia().get(i).getProcesso();
			int pagAcessada = entrada.getSequencia().get(i).getPaginaAcessada();

			if (tempoEntrada.get(nomeProcesso).containsKey(pagAcessada) == false) {

				if (filaPaginas.size() < entrada.getTamanhoQuadros()) {
					frequencia.get(nomeProcesso).put(pagAcessada, 0);
					filaPaginas.add(pagAcessada);
					tempoEntrada.get(nomeProcesso).put(pagAcessada, i);
					filaProcessos.add(nomeProcesso);
				}

				else {

					if (frequency(frequencia) == true) {

						int pagRemovida = filaPaginas.removeFirst();
						String processoRemovido = filaProcessos.removeFirst();
						frequencia.get(processoRemovido).remove(pagRemovida);
						tempoEntrada.get(processoRemovido).remove(pagRemovida);

						frequencia.get(nomeProcesso).put(pagAcessada, 0);
						filaPaginas.add(pagAcessada);
						tempoEntrada.get(nomeProcesso).put(pagAcessada, i);
						filaProcessos.add(nomeProcesso);

					} else {
						int pagRemovida = retorne(frequencia, tempoEntrada);
						filaPaginas.remove(pagRemovida);

						String processoRemovido = filaProcessos.remove(filaPaginas.lastIndexOf(pagRemovida));
						frequencia.get(processoRemovido).remove(pagRemovida);
						tempoEntrada.get(processoRemovido).remove(pagRemovida);

						frequencia.get(nomeProcesso).put(pagAcessada, 0);
						filaPaginas.add(pagAcessada);
						tempoEntrada.get(nomeProcesso).put(pagAcessada, i);
						filaProcessos.add(nomeProcesso);

					}

				}

			}

			else {
				int freq = frequencia.get(nomeProcesso).get(pagAcessada);
				freq++;
				frequencia.get(nomeProcesso).put(pagAcessada, freq);

			}

		}

		return (double) totalErros / entrada.getSequencia().size();

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

		int maiorFrequencia = Collections.max(a);
		TreeMap<Integer, Integer> t = new TreeMap<>();
		for (Integer i : freq.keySet()) {
			if (freq.get(i) == maiorFrequencia) {
				t.put(timeEntry.get(i), i);
			}

		}

		return t.get(t.lastKey());
	}

	@Override
	public double getTaxaErros() {
		return isSubstituicaoGlobal() ? this.taxaErroGlobal() : this.taxaErroIgual();
	}

}
