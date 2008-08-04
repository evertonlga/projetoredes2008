package threads;

import logica.BellmanFord;
import logica.No;
import controle.Controlador;
import excecoes.RedesException;

public class RoteadorThread extends Thread {

	private Controlador controlador;
	private int id;
	
	public RoteadorThread(Controlador controlador, int id) {
		this.controlador = controlador;
		this.id = id;
	}
	
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
		
		RecThread rec = new RecThread(controlador.getNoAtual(), controlador);
		rec.inicializa();
		
	}

}
