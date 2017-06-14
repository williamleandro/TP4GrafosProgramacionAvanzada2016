package grafo;

public class MatrizSimetrica {
	private boolean[] matrizEnVector;
	private int posiciones;
	
	public MatrizSimetrica(int cantidadDeNodos) {
		this.posiciones = cantidadDeNodos;
		int cantidadDePosiciones = (cantidadDeNodos * (cantidadDeNodos - 1)) / 2;
		this.matrizEnVector = new boolean[cantidadDePosiciones];
	}
	
	public void setMatrizSimetrica(int fila, int columna) {
		if(columna < fila){
			int aux=fila;
			fila = columna;
			columna = aux;
		}
		int indice = (int)(fila * this.posiciones + columna- (Math.pow(fila, 2) + 3 * fila + 2)/2);
		this.matrizEnVector[indice] = true;
	}
	
	public boolean getMatrizSimetrica(int fila, int columna) {
		if(columna < fila){
			int aux=fila;
			fila = columna;
			columna = aux;
		}
		int indice = (int)(fila * this.posiciones + columna- (Math.pow(fila, 2) + 3 * fila + 2)/2);
		return this.matrizEnVector[indice];
	}	
	
	public int getCantAristas(){
		return matrizEnVector.length;
	}
	
	public int getPosiciones() {
		return this.posiciones;
	}
	
	public boolean getValorMatrizSimetrica(int pos) {
		return this.matrizEnVector[pos];
	}
	
	public void setValorMatrizSimetrica(int pos) {
		this.matrizEnVector[pos] = true;
	}
}