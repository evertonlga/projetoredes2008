package threads;

import logica.BellmanFord;

public class RepeteThread extends Thread {

	BellmanFord bf;
	int tempo;
	
	public RepeteThread(BellmanFord bellmanFord, int t) {
		this.bf = bellmanFord;
		this.tempo = t;
	}
	
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

	public BellmanFord getBf() {
		return bf;
	}

	public void setBf(BellmanFord bf) {
		this.bf = bf;
	}

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
	
	

}
