package br.com.gerenciaMemoria.algorithm;

import java.util.ArrayList;

public class Fila {

	private Nodo cabeca;
	private Nodo cauda;
	private int totalPaginas=0;
	private int spaceMemory;
	
	public boolean isFull()
	{
		return this.totalPaginas==this.spaceMemory;
		
	}
	
	public Fila(int space){
		this.spaceMemory=space;
	}
	
	public Fila() {}

	public int getTotalPaginas() 
	{
		return totalPaginas;
	}


	public void setTotalPaginas(int totalPaginas) 
	{
		this.totalPaginas = totalPaginas;
	}


	public int getSpaceMemory() 
	{
		return spaceMemory;
	}

	//tá cheio
	public void setSpaceMemory(int spaceMemory) 
	{
		this.spaceMemory = spaceMemory;
	}


	public void add(Integer pagina)
	{
		if(cabeca==null && cauda==null)
		{
			cauda=new Nodo(pagina);
			cabeca=cauda;	
			totalPaginas++;
			
		}else {
		
			Nodo pag= new Nodo(pagina);	
			pag.setAnterior(cauda);
			cauda.setProx(pag);
			cauda=pag;
			totalPaginas++;
		}
	}

	
	public boolean contains(int pagina)
	{
		boolean chave=false;
		Nodo inicio=cabeca;
		while(inicio!=null && chave==false)
		{
			
			if(inicio.getElemento()==pagina) chave=true;
			inicio=inicio.getProx();
			
		}	
		
		return chave;	
	}
	

	public int remove()
	{
		int pageRemoved=cabeca.getElemento();
		cabeca=cabeca.getProx();
		return pageRemoved;
	}
	
	
	public int remove(ArrayList<Integer> paginas){
		int paginaRemovida=0;
		boolean substituicao=false;
		if(paginas.size()==1)
		{
			paginaRemovida=this.remove(paginas.get(0));
			
		}else 
		{
			Nodo inicio=cabeca;
			while(inicio!=null &&substituicao==false)
			{
				
				if(paginas.contains(inicio.getElemento()))
				{
					substituicao=true;
					paginaRemovida=this.remove();
				}
				
				inicio=inicio.getProx();
			}	
			
		}
		
		return paginaRemovida;
		
	}
	
	private int remove(int pagina)
	{
		
		int paginaRemovida=0;
		
		Nodo proximo=cabeca;
		Nodo elementoAlvo=cabeca;
		
		while(elementoAlvo.getElemento()!=pagina)
		{
			elementoAlvo=proximo;
			proximo=proximo.getProx();
			
		}
		
		
		if(elementoAlvo.equals(cabeca))
		{
			paginaRemovida=this.remove();
		}
		
		
		else if (elementoAlvo.equals(cauda))
		{
			paginaRemovida=cauda.getElemento();
			cauda=cauda.getAnterior();
			cauda.setProx(null);
		}
		else 
		{
			paginaRemovida=elementoAlvo.getElemento();
			Nodo anterior=elementoAlvo.getAnterior();
			anterior.setProx(proximo);
			proximo.setAnterior(anterior);
		}
		
		return paginaRemovida;
	
	}
	
	
}
