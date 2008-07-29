package threads;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.DatagramSocket;

import Util.Util;
import logica.No;

public class ThreadAvisaVizinho extends Thread {

	private No no;
	private int idVizinho;
	
	public ThreadAvisaVizinho(No no, int vizinho) {
		super();
		this.no = no;
		this.idVizinho = vizinho;
	}
	
	public void run(){
		String IPDestino = procuraIPVizinho();
		int portaDestino = procuraPortaVizinho();
		envia(idVizinho, portaDestino);
	}

	private String procuraIPVizinho() {
		String file = Util.CONFIG_ROTEADOR;
		FileReader fr;
		try {
			fr = new FileReader(file);
			BufferedReader bfr = new BufferedReader(fr);
			
			String linha = bfr.readLine();
			while(linha != null){
				if (linha.startsWith(String.valueOf(idVizinho)))
					break;
				linha = bfr.readLine();
			}
			String[] info = linha.split(" ");

			return info[2]; 
		}catch (Exception e) {
			
		}
		return null;
	}
	
	private int procuraPortaVizinho() {
		String file = Util.CONFIG_ROTEADOR;
		FileReader fr;
		try {
			fr = new FileReader(file);
			BufferedReader bfr = new BufferedReader(fr);
			
			String linha = bfr.readLine();
			while(linha != null){
				if (linha.startsWith(String.valueOf(idVizinho)))
					break;
				linha = bfr.readLine();
			}
			String[] info = linha.split(" ");

			return Integer.parseInt(info[1]); 
		}catch (Exception e) {
			
		}
		return -1;
	}
	
	public void envia(int IPVizinho, int portaDestino){
		DatagramSocket datagrama = no.getSocketServidor();
	}
	
}