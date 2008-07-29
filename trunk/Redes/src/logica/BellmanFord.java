package logica;

import threads.ThreadAvisaVizinho;

public class BellmanFord {

	private No no;
	
	public BellmanFord(No noAtual) {
		this.no = no;
	}

	public void avisaVizinhos() {
		ThreadAvisaVizinho avisa;
		for (int vizinho: no.getVizinhos())
			if (vizinho != no.getId()){
				avisa = new ThreadAvisaVizinho(no, vizinho);
				avisa.run();
			}
	}

}
