package logica;

import java.util.ArrayList;

public class No {

	private int id;
	private ArrayList<Integer> vizinhos;
	private String IP;
	private int porta;
	
	public No(int id) {
		this.id = id;
		vizinhos = new ArrayList<Integer>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Integer> getVizinhos() {
		return vizinhos;
	}

	public void setVizinhos(ArrayList<Integer> vizinhos) {
		this.vizinhos = vizinhos;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String ip) {
		IP = ip;
	}

	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}


}
