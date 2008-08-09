package threads;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.util.ArrayList;

import Util.Util;

import controle.Controlador;

import logica.BellmanFord;
import logica.No;
import logica.RotCelula;

public class ThreadRecebeTabela extends Thread {
	
	private No no;
	private DatagramPacket pacote;
	private Controlador controlador;
	
	/**
	 * Construtor da classe
	 * @param no
	 * @param controlador
	 */
	public ThreadRecebeTabela(No no, Controlador controlador){
		super();
		setNo(no);
		setControlador(controlador);
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
	
	/**
	 * retorna o pacote recebido
	 * @return o pacote recebido
	 */
	public DatagramPacket getPacote() {
		return pacote;
	}
	
	/**
	 * altera o pacote que 
	 * @param pacote
	 */
	public void setPacote(DatagramPacket pacote) {
		this.pacote = pacote;
	}

	/**
	 * retorna referencia para o objeto que controla o no
	 * @return referencia para o objeto que controla o no
	 */
	public Controlador getControlador() {
		return controlador;
	}

	/**
	 * altera a referencia para o objeto que controla o no
	 * @param controlador o novo controlador do no
	 */
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	/**
	 * inicializa o socket que vai receber os pacotes
	 */
	public void inicializa() {
		byte[] receberDados = new byte[1024];
		try{
			DatagramPacket receberPacote = new DatagramPacket(receberDados,receberDados.length);
			
			while (true){
				getNo().getSocketServidor().receive(receberPacote);
				ThreadRecebeTabela rt = new ThreadRecebeTabela(getNo(), controlador);
				rt.setPacote(receberPacote);
				//coloca a nova thread para executar
				rt.run();
			}
		} catch (Exception e ){
			System.out.println(e.getMessage());
		}
		
	}

	/**
	 * inicia a Thread
	 */
	public void run() {

		String mensagem = new String(getPacote().getData());

		ArrayList<Integer> vizinhos = getNo().getVizinhos();

		RotCelula [][]  tabela = getNo().getTabela();

		//transforma em inteiros a string recebida
		System.out.println("ORIGINAL: "+mensagem);
		Integer[] info = Util.decodificaDadosPacote(mensagem);
		
		int linha = vizinhos.indexOf(info[0]);
		int posAtual = vizinhos.indexOf(getNo().getId());

		int coluna = 0;
		int tamanho = info.length;

		if(no.vizinhoFoiDesligado(info)){
			no.desligaNo(info[0]);
			controlador.reset(info[0]);

			try {
				controlador.getBellmanFord().avisaVizinhos();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			//getNo().mostraTabela();

		} // roteador.vizinho NAO FOI Desligado
		else if(no.roteadorNaRedeFoiDesligado(info)){

			int noDesligado = no.getNoDesligado(info);
			controlador.reset(noDesligado);
			try {
				controlador.getBellmanFord().avisaVizinhos();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			getNo().mostraTabela();
		}else{
			for (int i = 1; i < tamanho; i+= 3){
				if( ((i%3)==1) && (vizinhos.contains(info[i])) ){
					coluna = vizinhos.indexOf(info[i]);
					if ( info [i+1] < tabela[linha][coluna].getPeso() ){
						tabela[linha][coluna].setPeso(info[i+1]);
						tabela[linha][coluna].setSalto(info[i+2]);
					}
				}
			}

			getNo().mostraTabela();

			boolean hasModificacao = false;

			BellmanFord bf = new BellmanFord(getNo());
			hasModificacao = bf.aplicaBellmanFord(posAtual, linha);
			
			if(hasModificacao){
				getNo().mostraTabela();
				try {
					bf.avisaVizinhos();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
	
		}
		// Atualizando uma linha recebida pelo vizinho
					
	}
	
	/**
	 * 
	 * @param info
	 * @return
	 */
	private String show(Integer[] info) {
		String r = "[";
		for (Integer i: info){
			r+= i+" ";
		}
		return r.trim()+"]";
	}

	

}
