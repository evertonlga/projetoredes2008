package threads;

import logica.BellmanFord;
import logica.No;
import controle.Controlador;
import excecoes.RedesException;

/**
 * 
 * @author Everton Galdino
 * @author Vinicius Marques
 *
 */
public class RoteadorThread extends Thread {

	private Controlador controlador;
	private int id;
	
	/**
	 * Construtor da classe
	 * @param controlador controlador do roteador
	 * @param id identificador do roteador
	 */
	public RoteadorThread(Controlador controlador, int id) {
		this.controlador = controlador;
		this.id = id;
	}
	
	/**
	 * inicializa a thread
	 */
	public void run() {
		controlador.setNoAtual(new No(id));
		controlador.setBellmanFord(new BellmanFord (controlador.getNoAtual()));
		System.out.println("Nó "+ id+ " criado!!");
		//TODO 
		
		try {
			controlador.configuraRoteador();
			controlador.configuraVizinhos();
			controlador.configuraEnlaces();
			controlador.getBellmanFord().avisaVizinhos();
		} catch (RedesException e) {
			System.out.println(e.getMessage());
		}
		
		RepeteThread repete = new RepeteThread(controlador.getBellmanFord(), 200);
		repete.start();
		
		ThreadRecebeTabela rec = new ThreadRecebeTabela(controlador.getNoAtual(), controlador);
		rec.inicializa();
		
		
		
	}

}
