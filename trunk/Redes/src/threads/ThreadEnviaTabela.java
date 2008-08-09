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

/**
 * 
 * @author Everton Galdino
 * @author Vinicius Marques
 *
 */
public class ThreadEnviaTabela extends Thread {

	private No no;
	private int idVizinho;
	
	/**
	 * Construtor da classe
	 * @param no no que enviara um pacote
	 * @param vizinho identificador do no que recebera o pacote
	 */
	public ThreadEnviaTabela(No no, int vizinho) {
		super();
		this.no = no;
		this.idVizinho = vizinho;
	}
	
	/**
	 * inicializa a Thread
	 */
	public void run(){
		InetAddress IPDestino = procuraIPVizinho();
		int portaDestino = procuraPortaVizinho();
		envia(IPDestino, portaDestino);
	}
	
	/**
	 * procura o ip de um roteador vizinho
	 * @return
	 */
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
	/**
	 * procura no arquivo de configuracao do roteador qual eh a porta do no vizinho
	 * @return
	 */
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
	
	/**
	 * envia o pacote para a porta especificada do destino passado como parametro
	 * @param IPDestino ip do roteador que ira receber o pacote
	 * @param portaDestino porta usada pelo roteador que ira receber o pacote
	 */
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

	/**
	 * retorna o no
	 * @return o no
	 */
	public No getNo() {
		return no;
	}

	/**
	 * altera o no
	 * @param no o novo no
	 */
	public void setNo(No no) {
		this.no = no;
	}
	
}
