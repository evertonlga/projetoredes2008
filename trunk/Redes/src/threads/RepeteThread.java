package threads;

import logica.BellmanFord;

/**
 * classe que fica reexecutando uma Thread
 * @author Everton Galdino
 * @author Vinicius Marques
 *
 */
public class RepeteThread extends Thread {

	BellmanFord bf;
	int tempo;
	
	/**
	 * Construtor da classe
	 * @param bellmanFord objeto que ficara sendo reexecutado
	 * @param t frequencia de execucao da Thread
	 */
	public RepeteThread(BellmanFord bellmanFord, int t) {
		this.bf = bellmanFord;
		this.tempo = t;
	}
	
	/**
	 * inicia a Thread
	 */
	public void run(){
    	while (true){
	    	try{
	    		sleep(tempo*1000);
	    		try {
					bf.avisaVizinhos();
				} catch (Exception e){
					System.out.println(e.getMessage());
					System.exit(1);
				}
	    	}catch (InterruptedException ie) {
				
			}
    	}
    }

	/**
	 * retorna a referencia ao objeto que tah executando o Bellman-Ford
	 * @return a referencia ao objeto que tah executando o Bellman-Ford
	 */
	public BellmanFord getBf() {
		return bf;
	}

	/**
	 * altera o objeto que esta sendo reexecutado pela Thread
	 * @param bf o novo objeto 
	 */
	public void setBf(BellmanFord bf) {
		this.bf = bf;
	}
	
	/**
	 * retorna o tempo de repeticao da thread
	 * @return a frequencia da thread
	 */
	public int getTempo() {
		return tempo;
	}

	/**
	 * altera o tempo de repeticao da thread
	 * @param tempo a nova frequencia para a Thread
	 */
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
	
	

}
