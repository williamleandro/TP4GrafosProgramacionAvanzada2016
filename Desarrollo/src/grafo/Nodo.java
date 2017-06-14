package grafo;

public class Nodo {
	private int color;
	private int numero;
	private int grado;
	
	public Nodo() {

		this.numero = 0;
		this.color = 0;
		this.grado = 0;
	}
	
	public Nodo(int numero, int color, int grado) {
		this.numero = numero;
		this.color = color;
		this.grado = grado;
	}
	
	public Nodo( Nodo nodo ) {
		this( nodo.numero, nodo.color, nodo.grado);
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getGrado() {
		return grado;
	}

	public void setGrado(int grado) {
		this.grado = grado;
	}
	
	
	public void copiarValores( Nodo nodo) {
		numero= nodo.numero;
		color= nodo.color;
		grado= nodo.grado;
	}
	
	public void intercambiar( Nodo ady) {
		Nodo aux = new Nodo(this);
		this.copiarValores(ady);
		ady.copiarValores(aux);
	}
	
	public int compararGrados(Nodo ady){
		if(this.grado > ady.grado)
			return 1;
		else 
			if(this.grado<ady.grado)
				return -1;
		else 
			return 0;
	}
}
