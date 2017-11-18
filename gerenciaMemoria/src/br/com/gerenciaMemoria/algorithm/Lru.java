package br.com.gerenciaMemoria.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.gerenciaMemoria.model.DadosEntradaAlgoritmo;
import br.com.gerenciaMemoria.model.NoSequencia;
import br.com.gerenciaMemoria.model.NomeAlgoritmo;
import br.com.gerenciaMemoria.model.Processo;

public class Lru extends AlgoritmoDeGerencia {

	private int totalErros = 0;
	private int tamanhoQuadros = entrada.getTamanhoQuadros();
	private int quantidadeProcessos = entrada.quantidadeProcessos();
	private List<Processo> processos = entrada.getProcessos();
	private int requisicoes = entrada.getSequencia().size();

	public Lru(DadosEntradaAlgoritmo entrada) {
		super(entrada, NomeAlgoritmo.LRU);
	}

	private double algoritmoSubstLocal() {
		HashMap<String, Integer> mapNumQuadros = new HashMap<>();
		int numQuadrosPorProcesso = tamanhoQuadros / quantidadeProcessos;

		HashMap<String, List<Integer>> memoriaLocal = new HashMap<>();
		HashMap<String, Map<Integer, Integer>> mapFreqProcessos = new HashMap<>();

		for (int i = 0; i < quantidadeProcessos; i++) {
			Processo processo = processos.get(i);
			String nomeProcesso = processo.getNome();

			numQuadrosPorProcesso = isAlocacaoIgual() ? numQuadrosPorProcesso
					: getNumPaginasProcessoProporcional(requisicoes, tamanhoQuadros, processo.getNumPaginas());
			memoriaLocal.put(nomeProcesso, new ArrayList<Integer>(numQuadrosPorProcesso));
			mapNumQuadros.put(nomeProcesso, numQuadrosPorProcesso);
			mapFreqProcessos.put(nomeProcesso, new HashMap<>());
		}

		for (int i = 0; i < requisicoes; i++) {
			NoSequencia noSequencia = entrada.getSequencia().get(i);
			String nomeProcesso = noSequencia.getProcesso();
			int pagAcessada = noSequencia.getPaginaAcessada();

			List<Integer> memoriaProcesso = memoriaLocal.get(nomeProcesso);
			Map<Integer, Integer> mapFrequenciaAcesso = mapFreqProcessos.get(nomeProcesso);
			numQuadrosPorProcesso = isAlocacaoIgual() ? numQuadrosPorProcesso : mapNumQuadros.get(nomeProcesso);

			envelheceMap(mapFrequenciaAcesso);

			if (memoriaProcesso.size() == numQuadrosPorProcesso) {
				if (!memoriaProcesso.contains(pagAcessada)) {
					totalErros++;
					Integer key = mapFrequenciaAcesso.entrySet().stream()
							.max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();

					memoriaProcesso.remove(key);
					mapFrequenciaAcesso.remove(key);

					memoriaProcesso.add(pagAcessada);

				}

				mapFrequenciaAcesso.put(pagAcessada, 0);

			} else {
				if (!memoriaProcesso.contains(pagAcessada)) {
					totalErros++;
					memoriaProcesso.add(pagAcessada);
				}

				mapFrequenciaAcesso.put(pagAcessada, 0);
			}

		}

		return (double) totalErros / requisicoes;
	}

	private <T> void envelheceMap(Map<T, Integer> map) {
		map.forEach((k, v) -> map.put(k, ++v));
	}

	private double algoritmoSubstGlobal() {
		tamanhoQuadros = getMemoria();
		ArrayList<NoSequencia> memoria = new ArrayList<NoSequencia>(tamanhoQuadros);
		HashMap<NoSequencia, Integer> mapFrequenciaAcesso = new HashMap<>();

		for (int i = 0; i < requisicoes; i++) {
			NoSequencia noAcessado = entrada.getSequencia().get(i);

			envelheceMap(mapFrequenciaAcesso);

			if (memoria.size() == tamanhoQuadros) {
				if (!isEmMemoria(memoria, noAcessado)) {
					totalErros++;
					NoSequencia key = mapFrequenciaAcesso.entrySet().stream()
							.max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
					memoria.remove(key);
					mapFrequenciaAcesso.remove(key);

					memoria.add(noAcessado);
					mapFrequenciaAcesso.put(noAcessado, 0);
				} else {
					mapFrequenciaAcesso.put(noAcessado, 0);
				}

			} else {
				if (!isEmMemoria(memoria, noAcessado)) {
					totalErros++;
					memoria.add(noAcessado);
				}
				mapFrequenciaAcesso.put(noAcessado, 0);
			}
		}

		return (double) totalErros / requisicoes;
	}

	@Override
	public double getTaxaErros() {
		return isSubstituicaoGlobal() ? algoritmoSubstGlobal() : algoritmoSubstLocal();
	}
}
