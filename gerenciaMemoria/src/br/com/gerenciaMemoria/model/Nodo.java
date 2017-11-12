package br.com.gerenciaMemoria.model;

public class Nodo {

	private Integer elemento;
	private Nodo prox;
	private Nodo anterior;

	public Nodo getAnterior() {
		return anterior;
	}

	public void setAnterior(Nodo anterior) {
		this.anterior = anterior;
	}

	public Nodo(Integer elemento) {
		this.elemento = elemento;
	}

	public Integer getElemento() {
		return elemento;
	}

	public void setElemento(Integer elemento) {
		this.elemento = elemento;
	}

	public Nodo getProx() {
		return prox;
	}

	public void setProx(Nodo prox) {
		this.prox = prox;
	}

}
