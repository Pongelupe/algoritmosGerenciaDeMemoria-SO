package br.com.gerenciaMemoria.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import br.com.gerenciaMemoria.algorithm.AlgoritmoDeGerencia;
import br.com.gerenciaMemoria.algorithm.Fifo;
import br.com.gerenciaMemoria.algorithm.Lfu;
import br.com.gerenciaMemoria.algorithm.Lru;
import br.com.gerenciaMemoria.algorithm.Mfu;
import br.com.gerenciaMemoria.algorithm.My;
import br.com.gerenciaMemoria.algorithm.Opt;
import br.com.gerenciaMemoria.model.DadosEntradaAlgoritmo;
import br.com.gerenciaMemoria.util.GerenciaEntradas;
import br.com.gerenciaMemoria.util.GerenciaSaida;

public class Main {

	public static void main(String[] args) {
		GerenciaEntradas gerenciaEntradas = new GerenciaEntradas("entradaSaida/entradaCarlos.txt");
		try {
			final DadosEntradaAlgoritmo dadosEntrada = gerenciaEntradas.getDadosEntrada();

			System.out.println("Dados inseridos:\n\n" + dadosEntrada + "\n\nSaida\n\n-----------");
			Opt opt = new Opt(dadosEntrada);
			Mfu mfu = new Mfu(dadosEntrada);
			Lfu lfu = new Lfu(dadosEntrada);
			Lru lru = new Lru(dadosEntrada);
			My my = new My(dadosEntrada);
			Fifo fifo = new Fifo(dadosEntrada);

			ArrayList<AlgoritmoDeGerencia> algoritmos = addAllAlgoritmos(opt, lru, my, fifo, mfu, lfu);
			GerenciaSaida gerenciaSaida = new GerenciaSaida(dadosEntrada.getNumeroRequisicoes());
			preencheSaida(algoritmos, gerenciaSaida);

			gerenciaSaida.exportarSaida();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static ArrayList<AlgoritmoDeGerencia> addAllAlgoritmos(AlgoritmoDeGerencia... algoritmo) {
		ArrayList<AlgoritmoDeGerencia> algoritmos = new ArrayList<AlgoritmoDeGerencia>();
		algoritmos.addAll(Arrays.asList(algoritmo));
		return algoritmos;
	}

	private static void preencheSaida(ArrayList<AlgoritmoDeGerencia> algoritmos, GerenciaSaida gerenciaSaida) {
		algoritmos.parallelStream().forEach(algoritmo -> {
			double taxaErros = algoritmo.getTaxaErros();
			String nomeAlgoritmo = algoritmo.getNomeAlgoritmo();
			gerenciaSaida.adicionarAlgoritmo(nomeAlgoritmo, taxaErros);
		});
	}

}
