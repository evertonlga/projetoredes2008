package excecoes;
/**
 * Excecao generica
 * @author Everton Galdino
 * @author Vinicius Marques
 *
 */
public class RedesException extends Exception {
	
	/**
	 * Construtor da classe
	 * @param msg mensagem que sera exibida quando a excecao for lancada
	 */
	public RedesException(String msg){
		super(msg);
	}
}
