package logica;
/**
 * 
 * @author Everton Galdino
 * @author Vinicius Marques
 *
 */
public class RotCelula {

	private int peso;
	private int salto;
	
	/**
	 * Construtor da classe
	 * @param peso peso do enlace
	 * @param salto a outra ponta do enlace
	 */
	public RotCelula(int peso, int salto){
		this.peso = peso;
		this.salto = salto;
	}
	
	/**
	 * Construtor vazio
	 */
	public RotCelula() {

	}

	/**
	 * o peso do salto
	 * @return
	 */
	public int getPeso() {
		return peso;
	}

	/**
	 * altera o peso do salto
	 * @param peso
	 */
	public void setPeso(int peso) {
		this.peso = peso;
	}

	/**
	 * retorna o identificador do salto
	 * @return o identificador do salto
	 */
	public int getSalto() {
		return salto;
	}

	/**
	 * altera o identificador do salto
	 * @param salto o novo identificador do salto
	 */
	public void setSalto(int salto) {
		this.salto = salto;
	}
	
	/**
	 * @return o toString para a classe
	 */
	public String toString(){
		return "(" + this.peso +", " + this.salto + ")";
	}
}
