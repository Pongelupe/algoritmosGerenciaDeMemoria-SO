package br.com.gerenciaMemoria.model;

public class NoSequencia {

	private String processo;
	private int paginaAcessada;

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public int getPaginaAcessada() {
		return paginaAcessada;
	}

	public void setPaginaAcessada(int paginaAcessada) {
		this.paginaAcessada = paginaAcessada;
	}

	public NoSequencia(String processo, int paginaAcessada) {
		this.processo = processo;
		this.paginaAcessada = paginaAcessada;
	}

	public NoSequencia() {
		this(null, 0);
	}

	@Override
	public String toString() {
		return "NoSequencia [processo=" + processo + ", paginaAcessada=" + paginaAcessada + "]";
	}

}
