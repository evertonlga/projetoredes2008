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

public class Controlador {
	
	private No noAtual;
	private static Controlador controlador;
	private RoteadorThread roteadorThread;
	private BellmanFord bellmanFord; 
	
	public BellmanFord getBellmanFord() {
		return bellmanFord;
	}

	public void setBellmanFord(BellmanFord bellmanFord) {
		this.bellmanFord = bellmanFord;
	}
	
	public static Controlador getInstance(){

		if (controlador == null){
			controlador = new Controlador();
		}

		return controlador;

	}

	public void criaNo(int id) {
		RoteadorThread roteador = new RoteadorThread(this, id);
		setRoteadorThread(roteador);
		roteadorThread.run();		
	}

	public No getNoAtual() {
		return noAtual;
	}

	public void setNoAtual(No noAtual) {
		this.noAtual = noAtual;
	}

	public static Controlador getControlador() {
		return controlador;
	}

	public static void setControlador(Controlador controlador) {
		Controlador.controlador = controlador;
	}

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

	private String mostraVizinhos(ArrayList<Integer> vizinhos) {
		String vizString = "";
		for (Integer i: vizinhos)
			vizString+=i+" ";
		return vizString;
	}

	public RoteadorThread getRoteadorThread() {
		return roteadorThread;
	}

	public void setRoteadorThread(RoteadorThread roteadorThread) {
		this.roteadorThread = roteadorThread;
	}

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
				noAtual.reset(enlace, noDesligado);
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
