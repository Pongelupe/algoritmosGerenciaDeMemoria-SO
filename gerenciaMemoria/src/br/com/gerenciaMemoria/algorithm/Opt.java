package br.com.gerenciaMemoria.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.gerenciaMemoria.model.DadosEntradaAlgoritmo;
import br.com.gerenciaMemoria.model.NoSequencia;
import br.com.gerenciaMemoria.model.NomeAlgoritmo;
import br.com.gerenciaMemoria.model.Processo;

public class Opt extends AlgoritmoDeGerencia {

	private int totalErros = 0;
	private int tamanhoQuadros = entrada.getTamanhoQuadros();
	private int quantidadeProcessos = entrada.quantidadeProcessos();
	private List<Processo> processos = entrada.getProcessos();
	private int requisicoes = entrada.getSequencia().size();

	public Opt(DadosEntradaAlgoritmo entrada) {
		super(entrada, NomeAlgoritmo.OPT);
	}

	private double algoritmoSubstLocal() {

		HashMap<String, Integer> mapNumQuadros = new HashMap<>();
		int numQuadrosPorProcesso = tamanhoQuadros / quantidadeProcessos;

		HashMap<String, List<Integer>> mapProcessos = new HashMap<>();
		HashMap<String, List<Integer>> memoriaLocal = new HashMap<>();

		for (int i = 0; i < quantidadeProcessos; i++) {
			Processo processo = processos.get(i);
			String nomeProcesso = processo.getNome();

			LinkedList<Integer> queue = new LinkedList<Integer>();

			entrada.getSequencia().stream().filter(no -> no.getProcesso().equals(nomeProcesso))
					.collect(Collectors.toList()).forEach(no -> {
						queue.add(no.getPaginaAcessada());
					});

			numQuadrosPorProcesso = isAlocacaoIgual() ? numQuadrosPorProcesso
					: getNumPaginasProcessoProporcional(requisicoes, tamanhoQuadros, processo.getNumPaginas());
			mapProcessos.put(nomeProcesso, queue);
			memoriaLocal.put(nomeProcesso, new ArrayList<Integer>(numQuadrosPorProcesso));
			mapNumQuadros.put(nomeProcesso, numQuadrosPorProcesso);
		}

		for (int i = 0; i < requisicoes; i++) {
			String nomeProcesso = entrada.getSequencia().get(i).getProcesso();
			int pagAcessada = entrada.getSequencia().get(i).getPaginaAcessada();

			List<Integer> filaTotal = mapProcessos.get(nomeProcesso);
			List<Integer> memoriaProcesso = memoriaLocal.get(nomeProcesso);
			numQuadrosPorProcesso = isAlocacaoIgual() ? numQuadrosPorProcesso : mapNumQuadros.get(nomeProcesso);

			filaTotal.remove(0);

			if (memoriaProcesso.size() == numQuadrosPorProcesso) {
				if (!memoriaProcesso.contains(pagAcessada)) {
					totalErros++;
					int paginaRemovida = decideQualTirar(filaTotal, pagAcessada, memoriaProcesso);
					memoriaProcesso.remove(paginaRemovida);
					memoriaProcesso.add(pagAcessada);
				}

			} else {
				if (!memoriaProcesso.contains(pagAcessada)) {
					totalErros++;
					memoriaProcesso.add(pagAcessada);
				}
			}

		}

		return (double) totalErros / requisicoes;

	}

	private double algoritmoSubstGlobal() {
		tamanhoQuadros = getTamMemoriaGlobal();
		ArrayList<NoSequencia> memoria = new ArrayList<NoSequencia>(tamanhoQuadros);
		ArrayList<Integer> fila = new ArrayList<Integer>();
		entrada.getSequencia().forEach(no -> fila.add(no.getPaginaAcessada()));

		for (int i = 0; i < requisicoes; i++) {
			NoSequencia pagAcessada = entrada.getSequencia().get(i);

			fila.remove(0);

			if (memoria.size() == tamanhoQuadros) {
				if (!isEmMemoria(memoria, pagAcessada)) {
					totalErros++;
					int paginaRemovida = decideQualTirar(fila, pagAcessada, memoria);
					memoria.remove(paginaRemovida);
					memoria.add(pagAcessada);
				}
			} else {
				if (!isEmMemoria(memoria, pagAcessada)) {
					totalErros++;
					memoria.add(pagAcessada);
				}
			}
		}

		return (double) totalErros / requisicoes;
	}

	private <T> int decideQualTirar(List<Integer> fila, T pagAcessada, List<T> memoria) {
		HashMap<Integer, T> distanciasProcessos = new HashMap<Integer, T>();
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

	@Override
	public double getTaxaErros() {
		return isSubstituicaoGlobal() ? algoritmoSubstGlobal() : algoritmoSubstLocal();
	}

}