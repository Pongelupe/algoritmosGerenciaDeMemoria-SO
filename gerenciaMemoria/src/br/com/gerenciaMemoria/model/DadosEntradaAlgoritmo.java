package br.com.gerenciaMemoria.model;

import java.util.ArrayList;
import java.util.List;

public class DadosEntradaAlgoritmo {

	private int tamanhoQuadros;
	private List<Processo> processos;
	private String alocacao;
	private String substituicao;
	private List<NoSequencia> sequencia;

	public int getTamanhoQuadros() {
		return tamanhoQuadros;
	}

	public void setTamanhoQuadros(int tamanhoQuadros) {
		this.tamanhoQuadros = tamanhoQuadros;
	}

	public List<Processo> getProcessos() {
		return processos;
	}

	public void adicionaProcesso(Processo processo) {
		processos.add(processo);
	}

	public String getAlocacao() {
		return alocacao;
	}

	public void setAlocacao(String alocacao) {
		this.alocacao = alocacao;
	}

	public String getSubstituicao() {
		return substituicao;
	}

	public void setSubstituicao(String substituicao) {
		this.substituicao = substituicao;
	}

	public List<NoSequencia> getSequencia() {
		return sequencia;
	}

	public void adicionaSequencia(NoSequencia no) {
		this.sequencia.add(no);
	}

	public DadosEntradaAlgoritmo(int tamanhoQuadros, List<Processo> processos, String alocacao, String substituicao,
			List<NoSequencia> sequencia) {
		this.tamanhoQuadros = tamanhoQuadros;
		this.processos = processos;
		this.alocacao = alocacao;
		this.substituicao = substituicao;
		this.sequencia = sequencia;
	}

	public DadosEntradaAlgoritmo() {
		this(0, new ArrayList<Processo>(), "", "", new ArrayList<NoSequencia>());
	}

	@Override
	public String toString() {
		return "DadosEntradaAlgoritmo [tamanhoQuadros=" + tamanhoQuadros + ", processos=" + processos + ", alocacao="
				+ alocacao + ", substituicao=" + substituicao + ", sequencia=" + sequencia + "]";
	}

}
