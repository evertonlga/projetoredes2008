package logica;

public class RotCelula {

	private int peso;
	private int salto;
	
	public RotCelula(int peso, int salto){
		this.peso = peso;
		this.salto = salto;
	}

	public RotCelula() {

	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public int getSalto() {
		return salto;
	}

	public void setSalto(int salto) {
		this.salto = salto;
	}
	
	public String toString(){
		return "(" + this.peso +", " + this.salto + ")";
	}
}
