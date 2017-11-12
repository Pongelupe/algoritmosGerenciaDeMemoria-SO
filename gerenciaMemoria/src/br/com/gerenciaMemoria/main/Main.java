package br.com.gerenciaMemoria.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import br.com.gerenciaMemoria.algorithm.AlgoritmoDeGerencia;
import br.com.gerenciaMemoria.algorithm.Mfu;
import br.com.gerenciaMemoria.model.DadosEntradaAlgoritmo;
import br.com.gerenciaMemoria.util.GerenciaEntradas;
import br.com.gerenciaMemoria.util.GerenciaSaida;

public class Main {

	public static void main(String[] args) {
		GerenciaEntradas gerenciaEntradas = new GerenciaEntradas("entrada2.txt");
		try {
			final DadosEntradaAlgoritmo dadosEntrada = gerenciaEntradas.getDadosEntrada();

			System.out.println("Dados inseridos:\n\n" + dadosEntrada + "\n\nSaida\n\n\n-----------");
			// Opt opt = new Opt(dadosEntrada);
			Mfu mfu = new Mfu(dadosEntrada);

			ArrayList<AlgoritmoDeGerencia> algoritmos = addAllAlgoritmos(mfu);
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
		algoritmos.forEach(algoritmo -> {
			double taxaErros = algoritmo.getTaxaErros();
			String nomeAlgoritmo = algoritmo.getNomeAlgoritmo();
			gerenciaSaida.adicionarAlgoritmo(nomeAlgoritmo, taxaErros);
		});
	}

}
