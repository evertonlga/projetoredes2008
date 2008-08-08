package logica;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * classe que representa um no do simulador
 * @author Everton Galdino
 * @author Vinicius Marques
 *
 */
public class No {

	//Atributos
	private int id;
	private ArrayList<Integer> vizinhos;
	private InetAddress IP;
	private int porta;
	private RotCelula[][] tabela;
	private DatagramSocket socketServidor;
	
	/**
	 * Constroi um no
	 * @param id identificador do roteador
	 */
	public No(int id) {
		this.id = id;
		vizinhos = new ArrayList<Integer>();
		vizinhos.add(id);
	}

	/**
	 * retorna o identificador do no
	 * @return o identificador do no
	 */
	public int getId() {
		return id;
	}

	/**
	 * altera o valor do identificador do no
	 * @param id o novo identificador do no
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * retorna a lista com os nos adjacentes a este no
	 * @return
	 */
	public ArrayList<Integer> getVizinhos() {
		return vizinhos;
	}

	/**
	 * altera a lista com os nos adjacentes a este no
	 * @param vizinhos a nova lista que armazenara os nos vizinhos
	 */
	public void setVizinhos(ArrayList<Integer> vizinhos) {
		this.vizinhos = vizinhos;
	}

	/**
	 * retorna informacoes sobre o IP do roteador
	 * @return informacoes sobre o IP do roteador
	 */
	public InetAddress getIP() {
		return IP;
	}

	/**
	 * converte o ip passado como parametro para o tipo InetAddress
	 * @param ipS ip a ser convencido
	 * @return um IPAdrress do ip passado como parametro
	 */
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

	/**
	 * seta o ip do roteador
	 * @param ip
	 */
	public void setIP(String ip) {
		IP = configureIP(ip);
	}

	/**
	 * retorna a porta de comunicacao do roteador
	 * @return a porta de comunicacao do roteador
	 */
	public int getPorta() {
		return porta;
	}

	/**
	 * altera a porta de comunicacao do roteador
	 * @param porta a nova porta de comunicacao do roteador
	 */
	public void setPorta(int porta) {
		this.porta = porta;
		try {
			setSocketServidor(new DatagramSocket(getPorta()));
		} catch (SocketException e) {
			System.out.println("Erro Socket");
			e.printStackTrace();
		}
	}
	
	/**
	 * altera o socket UDP
	 * @param socketServidor o novo socket UDP
	 */
	public void setSocketServidor(DatagramSocket socketServidor) {
		this.socketServidor = socketServidor;
	}
	
	
	/**
	 * adiciona um no vizinho a lista de nos adjacentes ao no
	 * @param idVizinho o no a ser adicionado como vizinho
	 */
	public void addVizinho(int idVizinho){
		this.vizinhos.add(idVizinho);
	}

	/**
	 * modifica os pesos de um enlace
	 * @param origem identificador do enlace de origem
	 * @param destino identificador da outra ponta do enlace
	 * @param peso o novo peso valor do enlace
	 */
	public void modificaTabela(int origem, Integer destino, Integer peso) {
		int ori = vizinhos.indexOf(origem);
		int des = vizinhos.indexOf(destino);
		
		if (peso < tabela[ori][des].getPeso()) {
			tabela[ori][des].setPeso(peso);
			tabela[ori][des].setSalto(destino);
			mostraTabela();
		}
		
	}
	
	/**
	 * configura os pesos dos nos atingiveis pelo no e imprime esses valores 
	 */
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
	
	/**
	 * imprime a tabela de roteamento com os enlaces atingivieis pelo no e os respectivos pesos
	 */
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

	/** 
	 * retorna a tabela de roteamento
	 * @return
	 */
	public RotCelula[][] getTabela() {
		return tabela;
	}
	
	/**
	 * altera a tabela de roteamento
	 * @param tabela a nova tabela de roteamento
	 */
	public void setTabela(RotCelula[][] tabela) {
		this.tabela = tabela;
	}
	
	/** 
	 * retorna referencia ao socket UDP usado pelo no
	 * @return referencia ao socket UDP usado pelo no
	 */
	public DatagramSocket getSocketServidor() {
		return socketServidor;
	}

	/**
	 * checa se o vizinho foi desligado
	 * @param info o identificador do vizinho
	 * @return true se o vizinho foi desligado e false caso contrario
	 */
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
	
	
	/**
	 * seta o marcador do no vizinho como desligado
	 * @param id no que sera marcado como desligado
	 */
	public void desligaNo(Integer id) {
		int posicao = vizinhos.indexOf(id);
		for (int i =0; i<vizinhos.size(); i++){
			tabela[posicao][i].setPeso(Integer.MAX_VALUE);
			tabela[posicao][i].setSalto(0);
		}
		
	}

//	public void reset(String[] enlace, Integer noDesligado) {
//		// TODO Auto-generated method stub
//		
//	}

	/**
	 * checa se o roteador foi desligado
	 * @return se o roteador foi desligado retorna true caso contrario retorna false
	 */
	public boolean roteadorNaRedeFoiDesligado(Integer[] info) {
		boolean retorno = false;
		for (int i=2; i<info.length; i+=3)
			if((info[i] == Integer.MAX_VALUE) && (info[i+1]==0)){
				retorno = true;
				break;
			}
		return retorno;
	}

	/**
	 * ao encontrar o primeiro roteador da lista  cujo peso é infinito retorna a referencia 
	 * @param info lista com informacos sobre os nos
	 * @return o primeiro no que esta desligado na lista passada como parametro
	 */
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
