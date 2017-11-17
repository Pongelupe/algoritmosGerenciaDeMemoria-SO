package br.com.gerenciaMemoria.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import br.com.gerenciaMemoria.model.DadosEntradaAlgoritmo;
import br.com.gerenciaMemoria.model.NoSequencia;
import br.com.gerenciaMemoria.model.NomeAlgoritmo;

public class Fifo extends AlgoritmoDeGerencia {

	public Fifo(DadosEntradaAlgoritmo entrada) {
		super(entrada, NomeAlgoritmo.FIFO);
	}

	
	// substituição global
	public double taxaErroGlobal()
	{
		int totalErros=0;
		Queue<Integer> filaPaginas= new LinkedList<>();
		HashMap<String,ArrayList<Integer>> filaControl= new HashMap<>();
		Queue<String> filaProcessos= new LinkedList<>();
		
		
		for(int i=0;i<entrada.getProcessos().size();i++)
		{
			filaControl.put(entrada.getProcessos().get(i).getNome(), new ArrayList<Integer>());	
		}
		
		for(int i=0;i<entrada.getNumeroRequisicoes();i++)
		{
			String nomeProcesso = entrada.getSequencia().get(i).getProcesso();
			int pagAcessada = entrada.getSequencia().get(i).getPaginaAcessada();
			
			if(!filaControl.get(nomeProcesso).contains(pagAcessada))
			{
				totalErros++;
				
				if(filaProcessos.size()<entrada.getTamanhoQuadros())
				{
					filaPaginas.add(pagAcessada);
					filaControl.get(nomeProcesso).add(pagAcessada);
					filaProcessos.add(nomeProcesso);
				}
				else 
				{
					int pagRemovida=filaPaginas.remove();
					String processoRemovido= filaProcessos.remove();
					filaControl.get(processoRemovido).remove(filaControl.get(processoRemovido).indexOf(pagRemovida));
					
					filaPaginas.add(pagAcessada);
					filaControl.get(nomeProcesso).add(pagAcessada);
					filaProcessos.add(nomeProcesso);
				
				}
			
			}
		}
			
		return (double) totalErros/entrada.getNumeroRequisicoes();
		
		
		
	}
	
	// substituição local
	
	public double taxaErroIgual() {
		int totalErros = 0;
		int totalQuadros =0; //entrada.getTamanhoQuadros() / entrada.getProcessos().size();
		int totalPaginas=0;
		HashMap<String, Queue<Integer>> dadosFila = new HashMap<>();
		HashMap<String, Integer> tamanho= new HashMap<>();
		
		if(entrada.getAlocacao().equals("Proporcional"))
		{
			for(int i = 0; i < entrada.quantidadeProcessos(); i++)
			{
				totalPaginas+=entrada.getProcessos().get(i).getNumPaginas();
			}
		
			for (int i = 0; i < entrada.quantidadeProcessos(); i++) 
			{
				double p=(double)entrada.getProcessos().get(i).getNumPaginas()/totalPaginas*entrada.getTamanhoQuadros();
				totalQuadros=(int)p;
				tamanho.put(entrada.getProcessos().get(i).getNome(),totalQuadros);
				dadosFila.put(entrada.getProcessos().get(i).getNome(), new LinkedList<Integer>());
			}
		}
		else
		{
			totalQuadros=entrada.getTamanhoQuadros()/entrada.getProcessos().size();
			for (int i = 0; i < entrada.quantidadeProcessos(); i++) 
			{
				tamanho.put(entrada.getProcessos().get(i).getNome(),totalQuadros);
				dadosFila.put(entrada.getProcessos().get(i).getNome(), new LinkedList<Integer>());
			}
				
		}
		
		
		for (int i = 0; i < entrada.getSequencia().size(); i++) 
		{

			String nomeProcesso = entrada.getSequencia().get(i).getProcesso();
			int pagAcessada = entrada.getSequencia().get(i).getPaginaAcessada();
			
			if (dadosFila.get(nomeProcesso).contains(pagAcessada) == false) 
			{
				totalErros++;

				if (dadosFila.get(nomeProcesso).size()<tamanho.get(nomeProcesso)) 
				{
					dadosFila.get(nomeProcesso).add(pagAcessada);
				} 
				else 
				{
					dadosFila.get(nomeProcesso).remove();
					dadosFila.get(nomeProcesso).add(pagAcessada);
				}
			}

		}

		return (double) totalErros / entrada.getSequencia().size();
	}

	@Override
	public double getTaxaErros() {

		return this.taxaErroIgual();
	}

}
