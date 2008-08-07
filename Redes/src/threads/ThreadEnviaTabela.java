package threads;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

import Util.Util;
import logica.No;
import logica.RotCelula;

public class ThreadEnviaTabela extends Thread {

	private No no;
	private int idVizinho;
	
	public ThreadEnviaTabela(No no, int vizinho) {
		super();
		this.no = no;
		this.idVizinho = vizinho;
	}
	
	public void run(){
		InetAddress IPDestino = procuraIPVizinho();
		int portaDestino = procuraPortaVizinho();
		envia(IPDestino, portaDestino);
	}

	private InetAddress procuraIPVizinho() {
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

			return No.configureIP(info[2]); 
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
	
	public void envia(InetAddress IPDestino, int portaDestino){
		try {
			DatagramSocket datagrama = no.getSocketServidor();
			String pack = Util.codificaDadosDoPacote(getNo());
			byte[] tabelaByte = new byte[1024];
			tabelaByte = pack.getBytes();

			DatagramPacket dp = new DatagramPacket(tabelaByte,tabelaByte.length,IPDestino, portaDestino);
			datagrama.send(dp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public No getNo() {
		return no;
	}

	public void setNo(No no) {
		this.no = no;
	}
	
}
