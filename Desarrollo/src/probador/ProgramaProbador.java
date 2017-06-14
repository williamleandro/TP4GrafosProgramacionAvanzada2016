package probador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import grafo.MatrizSimetrica;
import grafo.Nodo;

@SuppressWarnings("unused")
public class ProgramaProbador {
	private MatrizSimetrica matrizSimetrica;
	private ArrayList<Nodo> nodos;
	private int cantNodos;
	private int cantAristas;
	private int cantNodosColoreo;
	private int cantAristasColoreo;
	private int cantColores;
	
	public ProgramaProbador(String pathIn, String pathOut) {
		FileReader ent=null;
		FileReader sal = null;
		BufferedReader entrada = null;
		BufferedReader salida = null;
		String linea;
		String[] data;
		boolean bandera=false;
		int fila, columna, pos=0;
	
		try {
			ent = new FileReader(new File(pathIn));
			entrada = new BufferedReader(ent);
			
			while((linea=entrada.readLine())!=null) {
				data = linea.split(" ");
				if(!bandera) {
					this.cantNodos = Integer.parseInt(data[0]);
					this.cantAristas = Integer.parseInt(data[1]);
					
					this.matrizSimetrica = new MatrizSimetrica(this.cantNodos);
					bandera = true;
				} else {
					fila = Integer.parseInt(data[0]);
					columna = Integer.parseInt(data[1]);
					matrizSimetrica.setMatrizSimetrica(fila, columna);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				entrada.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
		bandera = false;
		
		try {
			sal = new FileReader(new File(pathOut));
			salida = new BufferedReader(sal);
			
			while((linea = salida.readLine())!=null) {
				data = linea.split(" ");
				if(!bandera) {
					this.cantNodosColoreo = Integer.parseInt(data[0]);
					this.cantColores = Integer.parseInt(data[1]);
					this.cantAristasColoreo = Integer.parseInt(data[2]);
					this.nodos = new ArrayList<>();
					bandera = true;
				} else {
					pos = Integer.parseInt(data[0]);
					int color = Integer.parseInt(data[1]);
					this.nodos.add(new Nodo(pos, color, 0));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				salida.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public boolean probador() {
		int color, numero;
		
		
		if(nodos.size() != matrizSimetrica.getPosiciones() || cantNodos != cantNodosColoreo || cantAristas != cantAristasColoreo)
			return false;
		
		for (int i = 0; i < nodos.size(); i++) {
			color = nodos.get(i).getColor();
			numero = nodos.get(i).getNumero();

			for (int j = numero+1; j < nodos.size(); j++) {
					if(matrizSimetrica.getMatrizSimetrica(numero, j)==true && color == nodos.get(j).getColor())
						return false;
			}
		}
		return true;
	}
}
