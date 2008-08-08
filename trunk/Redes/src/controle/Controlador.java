package controle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import excecoes.RedesException;

import Util.Util;

import threads.RoteadorThread;
import logica.BellmanFord;
import logica.No;

/**
 * Classe que gerencia os roteadores
 * @author Everton Galdino
 * @author Vinicius Marques
 *
 */
public class Controlador {
	
	private No noAtual;
	private static Controlador controlador;
	private RoteadorThread roteadorThread;
	private BellmanFord bellmanFord; 
	
	/**
	 * retorna uma referencia para a classe que implementa as acoes do Bellman-Ford distribuido
	 * @return referencia para o objeto que implementa as acoes do Bellman-Ford
	 */
	public BellmanFord getBellmanFord() {
		return bellmanFord;
	}

	/**
	 * configura um novo objeto bellman-ford
	 * @param bellmanFord o novo objeto
	 */
	public void setBellmanFord(BellmanFord bellmanFord) {
		this.bellmanFord = bellmanFord;
	}
	
	/**
	 * retorna uma instancia da classe
	 * @return instancia da classe
	 */
	public static Controlador getInstance(){

		if (controlador == null){
			controlador = new Controlador();
		}

		return controlador;

	}
	
	/**
	 * Constroi um objeto que representa o no na rede.
	 * Este identificador tem que ser o mesmo usado para identificar o roteador no arquivo roteador.config
	 * @param id identificador do no
	 */
	public void criaNo(int id) {
		RoteadorThread roteador = new RoteadorThread(this, id);
		setRoteadorThread(roteador);
		roteadorThread.run();		
	}
	
	/**
	 * uma referencia para o objeto que representa o atual roteador
	 * @return um no que representa o roteador
	 */
	public No getNoAtual() {
		return noAtual;
	}

	/**
	 * altero a referencia para o roteador
	 * @param noAtual um nova configuracao para o roteador
	 */
	public void setNoAtual(No noAtual) {
		this.noAtual = noAtual;
	}
	
	/**
	 * retorna uma instancia da classe. 
	 * @return a instancia do objeto Controlador
	 */
	public static Controlador getControlador() {
		return controlador;
	}

	/**
	 * altera o controlador
	 * @param controlador o novo controlador
	 */
	public static void setControlador(Controlador controlador) {
		Controlador.controlador = controlador;
	}

	/**
	 * configura o roteador.
	 * carrega as configuracoes deste roteador que eh extraida do arquivo roteador.config que diz qual eh o ip do roteador e qual a porta que ele usa para se comunicar alem
	 * o metodo usa o id do roteador para saber quais configuracoes serao extraidas do arquivo  
	 * @throws RedesException caso ocorra algum problema com a leitura do arquivo de configuracao
	 */
	public void configuraRoteador() throws RedesException  {
		String file = Util.CONFIG_ROTEADOR;
		FileReader fr;
		try {
			fr = new FileReader(file);
			BufferedReader bfr = new BufferedReader(fr);
			
			String linha = bfr.readLine();
			while(linha != null){
				if (linha.startsWith(String.valueOf(noAtual.getId())))
					break;
				linha = bfr.readLine();
			}
			String [] dados = linha.split(" ");
			noAtual.setPorta(Integer.parseInt(dados[1]));
			noAtual.setIP(dados[2]);
			System.out.println("Dados do nó "+noAtual.getId()+" foram atualizados. --> Porta: "+getNoAtual().getPorta()+"  IP: "+getNoAtual().getIP());
			
		} catch (FileNotFoundException e) {
			throw new RedesException("Arquivo não encontrado!!");
		} catch (IOException e) {
			throw new RedesException("Erro na leitura do arquivo de configuração!!");
		}
		
		
	}
	/**
	 * carrega as configuracoes dos nos adjacentes ao roteador
	 * o metodo usa o id do roteador para saber quais configuracoes serao extraidas do arquivo enlaces.config  
	 */
	public void configuraVizinhos() {
		String file = Util.CONFIG_ENLACE;
		FileReader fr;
		try {
			fr = new FileReader(file);
			BufferedReader bfr = new BufferedReader(fr);
			
			String linha = bfr.readLine();
			String [] linhaArray = linha.split(" ");
			while(linha != null){
				if (linhaArray[0].equals(String.valueOf(noAtual.getId()))){
					noAtual.addVizinho(Integer.valueOf(linhaArray[1]));
				}else if (linhaArray[1].equals(String.valueOf(noAtual.getId()))){
					noAtual.addVizinho(Integer.valueOf(linhaArray[0]));
				}
				
				linha = bfr.readLine();
				if (linha!= null)
					linhaArray = linha.split(" ");
			}
			System.out.println(mostraVizinhos(getNoAtual().getVizinhos()));
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		noAtual.inicializaTabela();
		
	}

	/**
	 * mostra quais os nos adjacentes a este no
	 * @param vizinhos o array com os vizinhos neste no 
	 * @return um string com a informacao dos nos vizinhos
	 */
	private String mostraVizinhos(ArrayList<Integer> vizinhos) {
		String vizString = "";
		for (Integer i: vizinhos)
			vizString+=i+" ";
		return vizString;
	}
	
	/**
	 * retorna a referencia para a Thread que executa as acoes do roteador
	 * @return referencia para a Thread que executa as acoes do roteador
	 */
	public RoteadorThread getRoteadorThread() {
		return roteadorThread;
	}
	
	/**
	 * altera a Thread que executa acoes sobre o roteador
	 * @param roteadorThread a nova Thread
	 */
	public void setRoteadorThread(RoteadorThread roteadorThread) {
		this.roteadorThread = roteadorThread;
	}

	/**
	 * configura os enlaces
	 */
	public void configuraEnlaces() {
		System.out.println("Configurando enlaces");
		String file = Util.CONFIG_ENLACE;
		FileReader fr;
		try {
			fr = new FileReader(file);
			BufferedReader bfr = new BufferedReader(fr);
			
			String linha = bfr.readLine();
			String [] linhaArray = linha.split(" ");
			while(linha != null){
				if (linhaArray[0].equals(String.valueOf(noAtual.getId()))){
					noAtual.modificaTabela(noAtual.getId(), Integer.valueOf(linhaArray[1]), Integer.valueOf(linhaArray[2]));
				}else if (linhaArray[1].equals(String.valueOf(noAtual.getId()))){
					noAtual.modificaTabela(noAtual.getId(), Integer.valueOf(linhaArray[0]), Integer.valueOf(linhaArray[2]));
				}
				
				linha = bfr.readLine();
				if (linha!= null)
					linhaArray = linha.split(" ");
			}
		}catch (Exception e) {
			
		}
		
		//noAtual.mostraTabela();
		
	}

	/**
	 * reinicia o roteador
	 * @param noDesligado 
	 */
	public void reset(Integer noDesligado) {
		String file = Util.CONFIG_ENLACE;
		FileReader fr;
		try{
			fr = new FileReader(file);
			BufferedReader bfr = new BufferedReader(fr);
			String line = bfr.readLine();
			String[] enlace = line.split(" ");
			while (line != null){
				//TODO metodo reset
				//noAtual.reset(enlace, noDesligado);
				line = bfr.readLine();
				enlace = line.split(" ");
			}
				
			
		}catch (FileNotFoundException e){
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
