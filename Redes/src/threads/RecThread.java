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

public class RecThread extends Thread {
	
	private No no;
	private DatagramPacket pacote;
	private Controlador controlador;
	
	public RecThread(No no, Controlador controlador){
		super();
		setNo(no);
		setControlador(controlador);
	}
	
	public No getNo() {
		return no;
	}
	public void setNo(No no) {
		this.no = no;
	}
	public DatagramPacket getPacote() {
		return pacote;
	}
	public void setPacote(DatagramPacket pacote) {
		this.pacote = pacote;
	}

	public Controlador getControlador() {
		return controlador;
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	public void inicializa() {
		byte[] receberDados = new byte[1024];
		try{
			DatagramPacket receberPacote = new DatagramPacket(receberDados,receberDados.length);
			
			while (true){
				getNo().getSocketServidor().receive(receberPacote);
				RecThread rt = new RecThread(getNo(), controlador);
				rt.setPacote(receberPacote);
				//coloca a nova thread para executar
				rt.run();
			}
		} catch (Exception e ){
			System.out.println(e.getMessage());
		}
		
	}

	
	public void run() {

		String mensagem = new String(getPacote().getData());

		ArrayList<Integer> vizinhos = getNo().getVizinhos();

		RotCelula [][]  tabela = getNo().getTabela();

		//transforma em inteiros a string recebida
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

			getNo().mostraTabela();

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

	

}
