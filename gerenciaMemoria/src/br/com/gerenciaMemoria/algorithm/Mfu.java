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

	public boolean frequency(Queue<Integer> fila) {
		boolean teste = false;
		if (Collections.max(fila) == Collections.min(fila)) {
			System.out.println("Passei aqui");
			teste = true;

		}
		return teste;

	}

	public double taxaErroGlobal() {
		int totalErros = 0;
		/*
		 * HashMap<String, ArrayList<Integer>> dadosFila = new HashMap<>();
		 * HashMap<String, HashMap<Integer, Integer>> frequencia = new HashMap<>();
		 * HashMap<String, HashMap<Integer,Integer>> tempoEntrada = new HashMap<>();
		 */

		LinkedList<Integer> filaPaginas = new LinkedList<>();
		LinkedList<String> filaProcessos = new LinkedList<>();
		LinkedList<Integer> filaFrequencia = new LinkedList<>();

		/*
		 * for (int i = 0; i < entrada.getProcessos().size(); i++) {
		 * dadosFila.put(entrada.getProcessos().get(i).getNome(), new ArrayList<>());
		 * frequencia.put(entrada.getProcessos().get(i).getNome(), new HashMap<>());
		 * tempoEntrada.put(entrada.getProcessos().get(i).getNome(), new HashMap<>()); }
		 */
		for (int i = 0; i < entrada.getSequencia().size(); i++) {
			String nomeProcesso = entrada.getSequencia().get(i).getProcesso();
			int pagAcessada = entrada.getSequencia().get(i).getPaginaAcessada();

			if ((filaPaginas.contains(pagAcessada) == false)
					&& (filaProcessos.indexOf(filaPaginas.indexOf(pagAcessada)) == filaPaginas.indexOf(pagAcessada))) {
				totalErros++;

				if (filaPaginas.size() < entrada.getTamanhoQuadros()) {

					// dadosFila.get(nomeProcesso).add(pagAcessada);
					// frequencia.get(nomeProcesso).put(pagAcessada, 0);
					// tempoEntrada.get(nomeProcesso).put(pagAcessada, i);
					filaPaginas.add(pagAcessada);
					filaProcessos.add(nomeProcesso);
					filaFrequencia.add(0);

				} else {
					if (frequency(filaFrequencia) == true) {

						filaFrequencia.removeFirst();
						int pagRemovida = filaPaginas.removeFirst();
						String processo = filaProcessos.removeFirst();
						// frequencia.get(processo).remove(pagRemovida);
						// tempoEntrada.get(processo).remove(pagRemovida);
						// dadosFila.get(processo).remove(pagRemovida);

						// dadosFila.get(nomeProcesso).add(pagAcessada);
						// frequencia.get(nomeProcesso).put(pagAcessada, 0);
						// tempoEntrada.get(nomeProcesso).put(pagAcessada, i);
						filaPaginas.add(pagAcessada);
						filaProcessos.add(nomeProcesso);
						filaFrequencia.add(0);

					} else {
						int pos = filaFrequencia.indexOf(Collections.max(filaFrequencia));

						int pagRemovida = filaPaginas.remove(pos);
						String premovido = filaProcessos.remove(pos);
						filaFrequencia.remove(pos);

						// int pagRemovida=retorne(frequencia,tempoEntrada,nomeProcesso);
						// dadosFila.get(premovido).remove(pagRemovida);
						// frequencia.get(premovido).remove(pagRemovida);
						// tempoEntrada.get(premovido).remove(pagRemovida);

						// dadosFila.get(nomeProcesso).add(pagAcessada);
						// frequencia.get(nomeProcesso).put(pagAcessada, 0);
						// tempoEntrada.get(nomeProcesso).put(pagAcessada, i);
						filaPaginas.add(pagAcessada);
						filaProcessos.add(nomeProcesso);
						filaFrequencia.add(0);

					}
				}
			} else {

				int freq = filaFrequencia.get(filaPaginas.indexOf(pagAcessada));
				freq++;
				filaFrequencia.add(filaPaginas.indexOf(pagAcessada), freq);
			}

		}

		System.out.println(totalErros);

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

		} else {

			int totalPaginas = 0;
			for (int i = 0; i < entrada.getProcessos().size(); i++) {
				totalPaginas += entrada.getProcessos().get(i).getNumPaginas();

			}

			for (int i = 0; i < entrada.getProcessos().size(); i++) {
				totalQuadros = (entrada.getProcessos().get(i).getNumPaginas() / totalPaginas)
						* entrada.getTamanhoQuadros();
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

		Collections.sort(a);
		TreeMap<Integer, Integer> t = new TreeMap<>();
		for (Integer i : freq.keySet()) {
			if (freq.get(i) == a.get(a.size() - 1)) {
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
