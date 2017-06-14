package grafo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class GrafoNDNP {
	private MatrizSimetrica matriz;
	private int cantidadDeNodos;
	private int cantidadDeAristas;
	private double porcentajeAdyacencia;
	private int gradoMaximo;
	private int gradoMinimo;
	private Nodo[] nodos;
	private int cantidadDeColores;
	
	public GrafoNDNP(String path) {
		BufferedReader bf = null;
		FileReader entrada = null;
		String linea;
		String[] data;
		boolean bandera = false;
		int fila, columna;
		
		try {
			entrada = new FileReader(new File(path));
			bf = new BufferedReader(entrada);
			
			while((linea = bf.readLine())!=null) {
				data = linea.split(" ");
				if(!bandera) {
					this.cantidadDeNodos = Integer.parseInt(data[0]);
					this.cantidadDeAristas = Integer.parseInt(data[1]);
					this.porcentajeAdyacencia = Double.parseDouble(data[2]);
					this.gradoMinimo = Integer.parseInt(data[3]);
					this.gradoMaximo = Integer.parseInt(data[4]);
					
					this.nodos = new Nodo[this.cantidadDeNodos];
					
					for (int i = 0; i < this.cantidadDeNodos; i++) {
						this.nodos[i] = new Nodo(i, 0, 0);
					}
					
					this.matriz = new MatrizSimetrica(this.cantidadDeNodos);		//Crea la matriz Simetrica
					bandera = true;
				} else {
					fila = Integer.parseInt(data[0]);
					columna = Integer.parseInt(data[1]);
					
					matriz.setMatrizSimetrica(fila, columna);
					this.nodos[fila].setGrado(this.nodos[fila].getGrado() + 1);
					this.nodos[columna].setGrado(this.nodos[columna].getGrado() + 1);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public GrafoNDNP(MatrizSimetrica matriz, int cantNodos, int cantAristas, double PorcAdyacencia, int gMin, int gMax) {
		this.matriz=matriz;
		this.cantidadDeNodos=cantNodos;
		this.cantidadDeAristas=cantAristas;
		this.porcentajeAdyacencia=PorcAdyacencia;
		this.gradoMinimo = gMin;
		this.gradoMaximo = gMax;
	}
	
	public MatrizSimetrica getMatrizSimetrica() {
		return this.matriz;
	}
	
	
	
	public void grabarEntradaGrafo(String pathIn) {
		PrintWriter entrada = null;
		
		try {
			entrada = new PrintWriter(new File(pathIn));
			
			entrada.println(this.cantidadDeNodos + " " + this.cantidadDeAristas + " " 
					+ this.porcentajeAdyacencia + " " + this.gradoMaximo + " " + this.gradoMinimo);
			
			for (int i = 0; i < cantidadDeNodos; i++) {
				for(int j = i+1; j < cantidadDeNodos; j++) {
					if(this.matriz.getMatrizSimetrica(i, j))
						entrada.println(i + " " + j);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			entrada.close();
		}
	}
	
	public void colorearPowell(){
		ordenarGradoMayorAMenor(nodos,0,nodos.length-1);
		colorear();
	}
	

	public void colorearMatula(){
		
		ordenarGradoMenorAMayor(nodos,0,nodos.length-1);
		colorear();
	}
	
	public void colorearSecuencialAleatorio() {
		colorear();
	}
	
	private void colorear() {
		int i, color;
		this.cantidadDeColores = 0;
		
		for(i=0; i<cantidadDeNodos; i++) {
			color = 1;
			while(!sePuedeColorear(i, color))
				color++;
			
			nodos[i].setColor(color);
			if(color > this.cantidadDeColores)
				this.cantidadDeColores = color;
		}
	}
	
	private boolean sePuedeColorear(int pos, int color) {
		int i=0; 
		if(nodos[pos].getColor() != 0) //si el nodo fue coloreado 
			return false;
		//no puedo colorear
		while(i < this.cantidadDeNodos) {
			if(nodos[i].getColor() == color) {  //si el nodo a colorear es adyacente a un nodo ya pintado de ese
				if(esAdyacente(nodos[i], nodos[pos]))  //color, no lo podré colorear
					return false;
			}
			i++;
		}
		return true;
	}
	
	private boolean esAdyacente(Nodo nodoUno, Nodo nodoDos) {
		return matriz.getMatrizSimetrica(nodoUno.getNumero(), nodoDos.getNumero()) == true;
	}
	
	private void ordenarGradoMenorAMayor(Nodo[] nodo,int izq,int der) {
		Nodo pivote = new Nodo( nodo[(izq+der)/2] );  	        
		int i=izq, d=der;	
		
		do {	            
			while((nodo[i].compararGrados(pivote)<0)) 
				i++; 	            
			while((nodo[d].compararGrados(pivote)>0)) 
				d--;	            
			if(i<=d) {	                
				nodo[i].intercambiar(nodo[d]);	            	
				i++;	            	
				d--;	            
			}	        
		} while (i<=d); 
		
		if(izq<d) 
			ordenarGradoMenorAMayor(nodo,izq,d);	            
		if(i<der) 
			ordenarGradoMenorAMayor(nodo,i,der);	        
	}
	
	
	private void ordenarGradoMayorAMenor(Nodo nodo[],int izq,int der) {
		Nodo pivote = new Nodo( nodo[(izq+der)/2] );  	        
		int i=izq, d=der;
		
		do {	            
			while((nodo[i].compararGrados(pivote)>0)) 
				i++; 	            
			while((nodo[d].compararGrados(pivote)<0)) 
				d--;	
			
			if(i<=d) {	                
				nodo[i].intercambiar(nodo[d]);	            	
				i++;	            	
				d--;	            
			}	        
		} while (i<=d); 
		
		if(izq<d) 
			ordenarGradoMayorAMenor(nodo,izq,d);	            
		if(i<der) 
			ordenarGradoMayorAMenor(nodo,i,der);	        
	}
	
	public void alterarOrdenNodos() {
		cantidadDeColores=0;  //vuelvo a setearlo en 0 por si se ejecuta varias veces el coloreo
		Nodo vectorAux[]= new Nodo[cantidadDeNodos];  //creo vector auxiliar donde voy a alterar posiciones 
	    int[] numeros= new int[cantidadDeNodos];
	    Random rnd=new Random();
	    int aux=cantidadDeNodos, pos;
	    
	    //se rellena una matriz ordenada del 1 al..n
	    for(int i=0; i<cantidadDeNodos; i++) 
	    	numeros[i]=i;
	   
	    for(int i=0; i<cantidadDeNodos; i++) {
	        pos= rnd.nextInt(aux);  
	        vectorAux[ numeros[pos] ] = 
				new Nodo( nodos[i].getNumero() ,0,nodos[i].getGrado() ); //cambio la pos del nodo, en un vector de nodos aux
	        numeros[pos]=numeros[aux-1]; aux--; 
	    }
		nodos = vectorAux;
		vectorAux=null;
	}
	
	public void selectorColoreoGrafo(int algoritmo) {
		if(algoritmo == 1)
			colorearSecuencialAleatorio();
		
		if(algoritmo==2)
			colorearPowell();
		
		if(algoritmo==3)
			colorearMatula();
	}
	

	@SuppressWarnings("unused")
	public void ejecutarCaso (int cod_algoritmo, int cantCorridas, String path) {
		int cantColor[] = new int[cantidadDeNodos];
		int nroCromatico= cantidadDeNodos;
		Nodo[] grafoColoreado=null;
		
		for(int i=0; i<cantCorridas; i++) {
			selectorColoreoGrafo(cod_algoritmo);
			cantColor[cantidadDeColores]+=1 ;
			if(cantidadDeColores < nroCromatico) //me quedo con la menor cantidad de colores obtenidos hasta el
			{                             //momento.
				nroCromatico= cantidadDeColores;
				grafoColoreado = nodos;
			}
			alterarOrdenNodos();
		}
	
		grabarResumenCaso(cod_algoritmo,cantColor, nroCromatico, path);
	}
	
	public void grabarResumenCaso(int cod_algoritmo, int[] cantColor, int nroCromatico, String path) {
		FileWriter fw = null;
		PrintWriter pw= null;
		try {
			fw = new FileWriter(path);
			pw = new PrintWriter(fw);
			pw.println("Algoritmo: " + cod_algoritmo);
			pw.println("NRO CROMATICO: " + nroCromatico);
			pw.println("CantColores  CantRepeticiones");
			for(int i=0; i<cantColor.length; i++)
			{
				if(cantColor[i]>0)
					pw.println(i + " " + cantColor[i]);
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
				try {
					if(fw!=null)
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			} 
	}
	
	public void grabarSalidaGrafo(String pathOut, Nodo[] coloreo) {
		PrintWriter salida = null;
		
		algSeleccion(coloreo);
		
		try {
			salida = new PrintWriter(new File(pathOut));
			
			salida.print(this.cantidadDeNodos + " " + this.cantidadDeColores + " ");
			salida.print(this.cantidadDeAristas + " " + this.porcentajeAdyacencia + " ");
			salida.println(this.gradoMaximo + " " + this.gradoMinimo);
			
			for (int i = 0; i < this.cantidadDeNodos; i++) {
				salida.println(coloreo[i].getNumero() + " " + coloreo[i].getColor());
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			salida.close();
		}
	}
	
	private void algSeleccion(Nodo[] vector)
	{
		int minValor;
		Nodo aux;
		
		for(int i=0;i<(vector.length-1);i++) {
			minValor = i;
			
			for(int j=i+1;j<vector.length;j++) {
				if(vector[j].getNumero()<vector[minValor].getNumero())
					minValor = j;
			}
			
			aux = vector[i];
			vector[i] = vector[minValor];
			vector[minValor] = aux;
		}
	}
	
	public Nodo[] getVectorNodos() {
		return this.nodos;
	}
}
