package br.com.gerenciaMemoria.algorithm;

import java.util.HashMap;
import java.util.Stack;

import br.com.gerenciaMemoria.model.DadosEntradaAlgoritmo;
import br.com.gerenciaMemoria.model.NomeAlgoritmo;

public class My extends AlgoritmoDeGerencia {

	public My(DadosEntradaAlgoritmo entrada) {
		super(entrada, NomeAlgoritmo.MY);
	}


	public double taxaErroGlobal() {
		int totalErros = 0;
		Stack<Integer> pilhaPagina = new Stack<>();
		HashMap<String, Stack<Integer>> pilhaControl = new HashMap<>();
		Stack<String> pilhaProcesso = new Stack<>();
		int totalQuadros=0;
	
		if (entrada.getAlocacao().equals("Igual")) 
		{
			totalQuadros=entrada.getTamanhoQuadros()/entrada.getProcessos().size();
			totalQuadros=totalQuadros*entrada.getProcessos().size();
		} 
		
		
		else if(entrada.getAlocacao().equals("Proporcional"))
		{
			
			int totalPaginas=0;
			for(int i=0;i<entrada.getProcessos().size();i++)
			{
				totalPaginas+=entrada.getProcessos().get(i).getNumPaginas();
			}
		
			int soma=0;
			for(int i=0;i<entrada.getProcessos().size();i++)
			{
				int totalPaginasPr=entrada.getProcessos().get(i).getNumPaginas();
				totalQuadros=(int) ((double)totalPaginasPr/totalPaginas*totalQuadros);
				soma+=totalQuadros;
			}
			
			totalQuadros=soma;
			
		}
			
		
		
		for (int i = 0; i < entrada.getProcessos().size(); i++) 
		{
			pilhaControl.put(entrada.getProcessos().get(i).getNome(), new Stack<Integer>());
		}

		for (int i = 0; i < entrada.getNumeroRequisicoes(); i++) {
			String nomeProcesso = entrada.getSequencia().get(i).getProcesso();
			int pagAcessada = entrada.getSequencia().get(i).getPaginaAcessada();

			if (pilhaControl.get(nomeProcesso).contains(pagAcessada) == false) {
				totalErros++;

				if (pilhaPagina.size() < entrada.getTamanhoQuadros()) {
					pilhaPagina.push(pagAcessada);
					pilhaControl.get(nomeProcesso).push(pagAcessada);
					pilhaProcesso.push(nomeProcesso);
				} else {
					int pagRemovida = pilhaPagina.pop();
					String processo = pilhaProcesso.pop();
					pilhaControl.get(processo).remove(pilhaControl.get(processo).indexOf(pagRemovida));

					pilhaControl.get(nomeProcesso).push(pagAcessada);
					pilhaProcesso.push(nomeProcesso);
					pilhaPagina.push(pagAcessada);

				}

			}
		}

		return (double) totalErros / entrada.getNumeroRequisicoes();

	}

	/*
	 * substitui��o local
	 * 
	 * 
	 */

	public double taxaErroIgual() {
		int totalErros = 0;
		int totalQuadros = 0;
		int totalPaginas = 0;
		HashMap<String, Stack<Integer>> dadosPilha = new HashMap<>();
		HashMap<String, Integer> tamanho = new HashMap<>();

		if (entrada.getAlocacao().equals("Proporcional")) {

			for (int i = 0; i < entrada.quantidadeProcessos(); i++) {
				totalPaginas += entrada.getProcessos().get(i).getNumPaginas();
			}

			for (int i = 0; i < entrada.quantidadeProcessos(); i++) {
				double p = (double) entrada.getProcessos().get(i).getNumPaginas() / totalPaginas
						* entrada.getTamanhoQuadros();
				totalQuadros = (int) p;
				tamanho.put(entrada.getProcessos().get(i).getNome(), totalQuadros);
				dadosPilha.put(entrada.getProcessos().get(i).getNome(), new Stack<Integer>());
			}
		} else {
			totalQuadros = entrada.getTamanhoQuadros() / entrada.getProcessos().size();
			for (int i = 0; i < entrada.quantidadeProcessos(); i++) {
				tamanho.put(entrada.getProcessos().get(i).getNome(), totalQuadros);
				dadosPilha.put(entrada.getProcessos().get(i).getNome(), new Stack<Integer>());
			}
		}

		for (int i = 0; i < entrada.getSequencia().size(); i++) {

			String nomeProcesso = entrada.getSequencia().get(i).getProcesso();
			int pagAcessada = entrada.getSequencia().get(i).getPaginaAcessada();

			if (dadosPilha.get(nomeProcesso).contains(pagAcessada) == false) {
				totalErros++;

				if (dadosPilha.get(nomeProcesso).size() < tamanho.get(nomeProcesso)) {
					dadosPilha.get(nomeProcesso).push(pagAcessada);
				} else {
					dadosPilha.get(nomeProcesso).pop();
					dadosPilha.get(nomeProcesso).push(pagAcessada);
				}

			}

		}

		return (double) totalErros / entrada.getSequencia().size();
	}

	@Override
	public double getTaxaErros() {

		return isSubstituicaoGlobal() ? this.taxaErroGlobal() : this.taxaErroIgual();
	}

}
