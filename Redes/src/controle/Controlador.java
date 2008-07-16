package controle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import Util.Util;

import threads.RoteadorThread;
import logica.No;

public class Controlador {
	
	private No noAtual;
	private static Controlador controlador;
	
	public static Controlador getInstance(){

		if (controlador == null){
			controlador = new Controlador();
		}

		return controlador;

	}

	public void criaNo(int id) {
		RoteadorThread roteador = new RoteadorThread(this, id);
		roteador.run();		
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

	public void configuraRoteador()  {
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
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void configuraEnlaces() {
		// TODO Auto-generated method stub
		
	}


}
