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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + paginaAcessada;
		result = prime * result + ((processo == null) ? 0 : processo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NoSequencia other = (NoSequencia) obj;
		if (paginaAcessada != other.paginaAcessada)
			return false;
		if (processo == null) {
			if (other.processo != null)
				return false;
		} else if (!processo.equals(other.processo))
			return false;
		return true;
	}

	public NoSequencia clone() {
		return new NoSequencia(processo, paginaAcessada);
	}

}
