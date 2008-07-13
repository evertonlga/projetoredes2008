package threads;

import logica.No;
import controle.Controlador;

public class RoteadorThread extends Thread {

	private Controlador controlador;
	private int id;
	
	public RoteadorThread(Controlador controlador, int id) {
		this.controlador = controlador;
		this.id = id;
	}
	
	public void run(){
		controlador.setNoAtual(new No(id));
		
		controlador.configuraRoteador();
	}

}
