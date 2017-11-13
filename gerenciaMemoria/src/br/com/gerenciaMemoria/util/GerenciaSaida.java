package br.com.gerenciaMemoria.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class GerenciaSaida {

	private int requisicoes;
	// Taxas de erro
	private double fifo;
	private double opt;
	private double lru;
	private double lfu;
	private double mfu;
	private double my;

	private DecimalFormat formatter = new DecimalFormat("##.##");

	public GerenciaSaida(int requisicoes, double fifo, double opt, double lru, double lfu, double mfu, double my) {
		this.requisicoes = requisicoes;
		this.fifo = fifo;
		this.opt = opt;
		this.lru = lru;
		this.lfu = lfu;
		this.mfu = mfu;
		this.my = my;
	}

	public GerenciaSaida(int requisicoes) {
		this(requisicoes, 0, 0, 0, 0, 0, 0);
	}

	public void adicionarAlgoritmo(String nome, double taxa) {
		ArrayList<Field> fields = getFields();

		fields.forEach(att -> {
			att.setAccessible(true);
			if (att.getName().equals(nome))
				try {
					att.set(this, taxa);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
		});
	}

	private ArrayList<Field> getFields() {
		Class<?> clazz = this.getClass();
		return new ArrayList<Field>(Arrays.asList(clazz.getDeclaredFields()));
	}

	public void exportarSaida() throws FileNotFoundException, UnsupportedEncodingException {

		String saida = gerarSaida(new StringBuilder());
		System.out.println(saida + "\n-----------");

		PrintWriter writer = new PrintWriter(new File("saida.txt"), "UTF-8");
		writer.println(saida);
		writer.close();

	}

	private String gerarSaida(StringBuilder sb) {
		sb.append("Requisições=" + requisicoes);
		sb.append("\nTaxasDeErros:");
		sb.append("\nFIFO=" + formatter.format(fifo));
		sb.append("\nOPT=" + formatter.format(opt));
		sb.append("\nLRU=" + formatter.format(lru));
		sb.append("\nLFU=" + formatter.format(lfu));
		sb.append("\nMFU=" + formatter.format(mfu));
		sb.append("\nMY=" + formatter.format(my));

		return sb.toString().replaceAll("\\.", ",");
	}

}
