package br.com.gerenciaMemoria.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import br.com.gerenciaMemoria.model.DadosEntradaAlgoritmo;
import br.com.gerenciaMemoria.model.Fila;
import br.com.gerenciaMemoria.model.NomeAlgoritmo;

public class Mfu extends AlgoritmoDeGerencia {

	public Mfu(DadosEntradaAlgoritmo entrada) {
		super(entrada, NomeAlgoritmo.MFU);
	}

	private double taxaErroIgual() {
		int totalErros = 0;
		int totalQuadros = entrada.getTamanhoQuadros() / entrada.getProcessos().size();
		HashMap<String, Fila> hash = new HashMap<>();
		HashMap<String, HashMap<Integer, Integer>> frequencia = new HashMap<>();

		for (int i = 0; i < entrada.getProcessos().size(); i++) {
			hash.put(entrada.getProcessos().get(i).getNome(), new Fila(totalQuadros));
			frequencia.put(entrada.getProcessos().get(i).getNome(), new HashMap<>());
		}

		for (int i = 0; i < entrada.getSequencia().size(); i++) {
			String nomeProcesso = entrada.getSequencia().get(i).getProcesso();
			int pagAcessada = entrada.getSequencia().get(i).getPaginaAcessada();

			if (hash.get(nomeProcesso).contains(pagAcessada) == false) {
				totalErros++;

				if (hash.get(nomeProcesso).isFull() == false) {

					hash.get(nomeProcesso).add(pagAcessada);
					frequencia.get(nomeProcesso).put(pagAcessada, 0);

				} else if (hash.get(nomeProcesso).isFull() == true) {
					if (frequency(frequencia, nomeProcesso)) {

						int pagRemovida = hash.get(nomeProcesso).remove();
						frequencia.get(nomeProcesso).remove(pagRemovida);
						hash.get(nomeProcesso).add(pagAcessada);
						frequencia.get(nomeProcesso).put(pagAcessada, 0);

					} else {

						int pagRemovida = hash.get(nomeProcesso).remove(retorne(frequencia, nomeProcesso));
						frequencia.get(nomeProcesso).remove(pagRemovida);
						hash.get(nomeProcesso).add(pagAcessada);
						frequencia.get(nomeProcesso).put(pagAcessada, 0);
					}
				}

			} else {

				int freq = frequencia.get(nomeProcesso).get(pagAcessada);
				frequencia.get(nomeProcesso).put(pagAcessada, freq++);
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

	private ArrayList<Integer> retorne(HashMap<String, HashMap<Integer, Integer>> map, String process) {

		HashMap<Integer, Integer> freq = map.get(process);
		ArrayList<Integer> a = new ArrayList<>();

		for (Integer i : freq.keySet()) {
			a.add(freq.get(i));
		}

		Collections.sort(a);
		ArrayList<Integer> paginas = new ArrayList<>();
		for (Integer i : freq.keySet()) {
			if (freq.get(i) == a.get(a.size() - 1)) {
				paginas.add(i);
			}

		}

		return paginas;
	}

	@Override
	public double getTaxaErros() {
		return taxaErroIgual();
	}

}
