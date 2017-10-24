package br.com.gerenciaMemoria.main;

import java.io.IOException;

import br.com.gerenciaMemoria.model.DadosEntradaAlgoritmo;
import br.com.gerenciaMemoria.util.GerenciaEntradas;

public class Main {

	public static void main(String[] args) {
		GerenciaEntradas gerenciaEntradas = new GerenciaEntradas("entrada1.txt");
		try {
			DadosEntradaAlgoritmo dadosEntrada = gerenciaEntradas.getDadosEntrada();
			System.out.println(dadosEntrada);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
