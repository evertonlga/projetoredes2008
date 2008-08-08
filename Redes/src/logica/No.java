package logica;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;


public class No {

	private int id;
	private ArrayList<Integer> vizinhos;
	private InetAddress IP;
	private int porta;
	private RotCelula[][] tabela;
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

	public InetAddress getIP() {
		return IP;
	}

	public static InetAddress configureIP(String ipS) {
		String[] ipFrag = ipS.split("\\.");
		byte[] ipByte = new byte[4];
		InetAddress ip;		
		try {
			for (int i = 0; i < ipFrag.length; i++){
				Integer num = Integer.parseInt(ipFrag[i]);
				ipByte[i] = num.byteValue();
			}
			
			ip = InetAddress.getByAddress(ipByte);
			
			return ip;
			
		} catch (Exception  e){
			System.out.println(e.getMessage());
		}
		return null;
	}

	public void setIP(String ip) {
		IP = configureIP(ip);
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
		
		if (peso < tabela[ori][des].getPeso()) {
			tabela[ori][des].setPeso(peso);
			tabela[ori][des].setSalto(destino);
			mostraTabela();
		}
		
	}

	public void inicializaTabela() {
		tabela = new RotCelula[vizinhos.size()][vizinhos.size()];
		for (int i = 0; i < vizinhos.size(); i++){
			for (int j = 0; j < vizinhos.size(); j++){
				this.tabela[i][j] = new RotCelula();
				this.tabela[i][j].setPeso(Integer.MAX_VALUE);
				
				if ((vizinhos.indexOf(this.id)==i)&&(vizinhos.indexOf(this.id) == j)){
					this.tabela[i][j].setPeso(0);
					this.tabela[i][j].setSalto(this.id);
				}				
			}
		}
		
		mostraTabela();

	}

	public void mostraTabela() {
		String tabString = "\nConfiguração atual da tabela do no "+id+"\n";
		for (int i = 0; i < tabela.length; i++){
			tabString+=(vizinhos.get(i)+ "  |  ");
			for (int j = 0; j < tabela.length; j++) {
				if (tabela[i][j].getPeso() != Integer.MAX_VALUE) {	
					tabString+=tabela[i][j] + "   ";
				} else {
					tabString+="INF  ";
				}
			}
			tabString+= "\n";
		}
		Calendar c = Calendar.getInstance();
		tabString += "\n Timestamp--->"+ c.get(Calendar.HOUR_OF_DAY)+"h" + ":" + c.get(Calendar.MINUTE)+"min" + ":" + c.get(Calendar.SECOND)+"s";
		System.out.println(tabString);
	}

	public RotCelula[][] getTabela() {
		return tabela;
	}

	public void setTabela(RotCelula[][] tabela) {
		this.tabela = tabela;
	}

	public DatagramSocket getSocketServidor() {
		return socketServidor;
	}

	public boolean vizinhoFoiDesligado(Integer[] info) {
		boolean retorno = false;
		try{

			for (int i=2; i<info.length; i+=3){
				if((info[i] == Integer.MAX_VALUE) && (info[i+1]== 0)){
					retorno = true;
				}else{
					retorno = false;
				}
			}

			
		}
		catch(NullPointerException e){
			System.out.println(e);	
		}
		return retorno;
		
	}

	public void desligaNo(Integer id) {
		int posicao = vizinhos.indexOf(id);
		for (int i =0; i<vizinhos.size(); i++){
			tabela[posicao][i].setPeso(Integer.MAX_VALUE);
			tabela[posicao][i].setSalto(0);
		}
		
	}

	public void reset(String[] enlace, Integer noDesligado) {
		// TODO Auto-generated method stub
		
	}

	public boolean roteadorNaRedeFoiDesligado(Integer[] info) {
		boolean retorno = false;
		for (int i=2; i<info.length; i+=3)
			if((info[i] == Integer.MAX_VALUE) && (info[i+1]==0)){
				retorno = true;
				break;
			}
		return retorno;
	}

	public int getNoDesligado(Integer[] info) {
		int no = 0;
		for (int i=2; i<info.length; i+=3){
			if((info[i] == Integer.MAX_VALUE) && (info[i+1]==0)){
				no = info[i];
				break;
			}
		}
		return no;
	}

	


}
