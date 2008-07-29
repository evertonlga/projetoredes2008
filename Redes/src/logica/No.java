package logica;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;


public class No {

	private int id;
	private ArrayList<Integer> vizinhos;
	private String IP;
	private int porta;
	private int[][] tabela;
	private DatagramSocket socketServidor;
	
	public No(int id) {
		this.id = id;
		vizinhos = new ArrayList<Integer>();
		vizinhos.add(id);
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
		try {
			setSocketServidor(new DatagramSocket(getPorta()));
		} catch (SocketException e) {
			System.out.println("Erro Socket");
			e.printStackTrace();
		}
	}
	
	public void setSocketServidor(DatagramSocket socketServidor) {
		this.socketServidor = socketServidor;
	}
	
	public void addVizinho(int idVizinho){
		this.vizinhos.add(idVizinho);
	}

	public void modificaTabela(int origem, Integer destino, Integer peso) {
		int ori = vizinhos.indexOf(origem);
		int des = vizinhos.indexOf(destino);
		
		if (peso < tabela[ori][des]) {
			tabela[ori][des] = peso;
		}
		
	}

	public void inicializaTabela() {
		tabela = new int[vizinhos.size()][vizinhos.size()];
		for (int i = 0; i < vizinhos.size(); i++){
			for (int j = 0; j < vizinhos.size(); j++){
				if ((vizinhos.indexOf(this.id)==i)&&(vizinhos.indexOf(this.id) == j))
					tabela[i][j] = 0;
				else 
					tabela[i][j] = Integer.MAX_VALUE;


			}
		}
		System.out.println(mostraTabela());

	}

	public String mostraTabela() {
		String tabString = "\nConfiguração atual da tabela do no "+id+"\n";
		for (int i = 0; i < tabela.length; i++){
			tabString+=(vizinhos.get(i)+ "  |  ");
			for (int j = 0; j < tabela.length; j++) {
				if (tabela[i][j] != Integer.MAX_VALUE) {	
					tabString+=tabela[i][j] + "   ";
				} else {
					tabString+="INF  ";
				}
			}
			tabString+= "\n";
		}
		return tabString;
	}

	public int[][] getTabela() {
		return tabela;
	}

	public void setTabela(int[][] tabela) {
		this.tabela = tabela;
	}

	public DatagramSocket getSocketServidor() {
		return socketServidor;
	}

	


}
