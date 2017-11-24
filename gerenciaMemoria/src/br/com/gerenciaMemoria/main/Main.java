package br.com.gerenciaMemoria.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

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

		Scanner sc = new Scanner(System.in);

		System.out.print("Entre o caminho do arquivo de texto (.txt) de entrada: ");
		String path = sc.nextLine();
		path += path.endsWith(".txt") ? "" : ".txt";

		try {
			GerenciaEntradas gerenciaEntradas = new GerenciaEntradas(path);
			final DadosEntradaAlgoritmo dadosEntrada = gerenciaEntradas.getDadosEntrada();

			System.out.println("\nDados inseridos:\n\n" + dadosEntrada + "\n\nSaida\n\n-----------");
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
			System.err
					.println("Nobre usuário, seu arquivo não existe ou está em formato errado!\nTente mais uma vez!\n");
			main(args);
		}

		sc.close();

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
