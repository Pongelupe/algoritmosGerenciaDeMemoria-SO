package br.com.gerenciaMemoria.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import br.com.gerenciaMemoria.model.DadosEntradaAlgoritmo;
import br.com.gerenciaMemoria.model.NoSequencia;
import br.com.gerenciaMemoria.model.Processo;

public class GerenciaEntradas {
	private final String path;
	private final File entrada;
	private DadosEntradaAlgoritmo dadosEntrada;

	public String getPath() {
		return path;
	}

	public DadosEntradaAlgoritmo getDadosEntrada() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(entrada));
		int tamanhoQuadros = Integer.parseInt(lerLinha(br.readLine()));
		dadosEntrada.setTamanhoQuadros(tamanhoQuadros);

		leProcessos(br);

		String alocacao = lerLinha(br.readLine());
		dadosEntrada.setAlocacao(alocacao);

		String substituicao = lerLinha(br.readLine());
		dadosEntrada.setSubstituicao(substituicao);

		leSequencia(br);

		br.close();
		return dadosEntrada;
	}

	private String lerLinha(String line) {
		String[] splitted = line.split("=");
		return splitted[1];
	}

	private void leSequencia(BufferedReader br) throws IOException {
		String line = br.readLine();
		boolean flagSequencia = true;
		while ((line != null) && flagSequencia) {
			line = br.readLine();
			NoSequencia noSequencia = new NoSequencia();
			String[] splitted = line.split(",");
			String processoName = splitted[0];
			noSequencia.setProcesso(processoName);

			String paginas = splitted[1];
			flagSequencia = !paginas.contains(";");

			if (!flagSequencia)
				paginas = paginas.replace(';', ' ').trim();

			noSequencia.setPaginaAcessada(Integer.parseInt(paginas));
			dadosEntrada.adicionaSequencia(noSequencia);
		}

	}

	private void leProcessos(BufferedReader br) throws IOException {
		String line = br.readLine();
		boolean flagProcessos = true;
		while ((line != null) && flagProcessos) {
			line = br.readLine();
			Processo processo = new Processo();
			String[] splitted = line.split("=");
			String processoName = splitted[0];
			processo.setNome(processoName);

			String paginas = splitted[1];
			flagProcessos = !paginas.contains(";");

			if (!flagProcessos)
				paginas = paginas.replace(';', ' ').trim();

			processo.setNumPaginas(Integer.parseInt(paginas));
			dadosEntrada.adicionaProcesso(processo);
		}
	}

	public GerenciaEntradas(String path) {
		this.path = path;
		this.entrada = new File(path);
		this.dadosEntrada = new DadosEntradaAlgoritmo();
	}

}
