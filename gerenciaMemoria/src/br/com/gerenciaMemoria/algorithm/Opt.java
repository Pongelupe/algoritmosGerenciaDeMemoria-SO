package br.com.gerenciaMemoria.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.gerenciaMemoria.model.DadosEntradaAlgoritmo;
import br.com.gerenciaMemoria.model.NomeAlgoritmo;
import br.com.gerenciaMemoria.model.Processo;

public class Opt extends AlgoritmoDeGerencia {

	public Opt(DadosEntradaAlgoritmo entrada) {
		super(entrada, NomeAlgoritmo.OPT);
	}

	private double algoritmoSubstLocal() {
		int totalErros = 0;
		final int tamanhoQuadros = entrada.getTamanhoQuadros();
		int quantidadeProcessos = entrada.quantidadeProcessos();
		List<Processo> processos = entrada.getProcessos();

		int numQuadrosPorProcesso = tamanhoQuadros / quantidadeProcessos;

		HashMap<String, List<Integer>> mapProcessos = new HashMap<>();
		HashMap<String, List<Integer>> memoriaLocal = new HashMap<>();

		for (int i = 0; i < quantidadeProcessos; i++) {
			String nomeProcesso = processos.get(i).getNome();

			LinkedList<Integer> queue = new LinkedList<Integer>();

			entrada.getSequencia().stream().filter(no -> no.getProcesso().equals(nomeProcesso))
					.collect(Collectors.toList()).forEach(no -> {
						queue.add(no.getPaginaAcessada());
					});

			mapProcessos.put(nomeProcesso, queue);
			memoriaLocal.put(nomeProcesso, new ArrayList<Integer>(numQuadrosPorProcesso));
		}

		for (int i = 0; i < entrada.getSequencia().size(); i++) {
			String nomeProcesso = entrada.getSequencia().get(i).getProcesso();
			int pagAcessada = entrada.getSequencia().get(i).getPaginaAcessada();

			List<Integer> filaTotal = mapProcessos.get(nomeProcesso);
			List<Integer> memoriaProcesso = memoriaLocal.get(nomeProcesso);

			if (memoriaProcesso.size() == numQuadrosPorProcesso) {
				if (!memoriaProcesso.contains(pagAcessada)) {
					totalErros++;
					int paginaRemovida = decideQualTirar(filaTotal, pagAcessada, memoriaProcesso);
					memoriaProcesso.remove(paginaRemovida);
					memoriaProcesso.add(pagAcessada);
				}

			} else {
				totalErros++;
				memoriaProcesso.add(pagAcessada);
			}

			filaTotal.remove(0);
		}

		return (double) totalErros / entrada.getSequencia().size();

	}

	private int decideQualTirar(List<Integer> fila, int pagAcessada, List<Integer> memoria) {
		HashMap<Integer, Integer> distanciasProcessos = new HashMap<Integer, Integer>();
		ArrayList<Integer> distancias = new ArrayList<Integer>();
		memoria.forEach(p -> {
			if (fila.contains(p)) {
				int distancia = fila.indexOf(p);
				distancias.add(distancia);
				distanciasProcessos.put(distancia, p);
			} else {
				distancias.add(Integer.MAX_VALUE);
				distanciasProcessos.put(Integer.MAX_VALUE, p);
			}
		});

		Collections.sort(distancias);
		Integer indexOnMap = distancias.get(distancias.size() - 1);

		return memoria.indexOf(distanciasProcessos.get(indexOnMap));
	}

	private double algoritmoSubstGlobal() {
		return 0;
	}

	@Override
	public double getTaxaErros() {
		return entrada.getSubstituicao().equals("Global") ? algoritmoSubstGlobal() : algoritmoSubstLocal();
	}

}