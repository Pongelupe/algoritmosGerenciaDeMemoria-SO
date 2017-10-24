package br.com.gerenciaMemoria.model;

public class Processo {

	private String nome;
	private int numPaginas;

	public String getNome() {
		return nome;
	}

	public int getNumPaginas() {
		return numPaginas;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setNumPaginas(int numPaginas) {
		this.numPaginas = numPaginas;
	}

	Processo(String nome, int numPaginas) {
		this.nome = nome;
		this.numPaginas = numPaginas;
	}

	public Processo() {
		this(null, 0);
	}

	@Override
	public String toString() {
		return "Processo [nome=" + nome + ", numPaginas=" + numPaginas + "]";
	}

}
