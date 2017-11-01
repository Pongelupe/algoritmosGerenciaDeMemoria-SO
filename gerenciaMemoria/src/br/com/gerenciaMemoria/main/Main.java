package br.com.gerenciaMemoria.main;

import java.io.IOException;

import br.com.gerenciaMemoria.model.DadosEntradaAlgoritmo;
import br.com.gerenciaMemoria.util.GerenciaEntradas;
import br.com.gerenciaMemoria.util.GerenciaSaida;

public class Main {

	public static void main(String[] args) {
		GerenciaEntradas gerenciaEntradas = new GerenciaEntradas("entrada1.txt");
		try {
			DadosEntradaAlgoritmo dadosEntrada = gerenciaEntradas.getDadosEntrada();
			System.out.println("Dados inseridos:\n\n" + dadosEntrada + "\n\nSaida\n\n\n");
			new GerenciaSaida(4, 22122.321312311, 1, 3.58, 3.1444, 7, 9).exportarSaida();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
