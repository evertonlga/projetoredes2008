package main;

import java.util.Scanner;

import controle.Controlador;


/**
 * Classe a ser instanciada para executar o simulador
 * @author Everton Galdino
 * @author Vinicius Marques
 *
 */
public class Main {

	/**
	 * para poder instanciar o roteador precisamos passar o identificador para o roteador. As configuracoes serao
	 * mapeadas a partir dos arquivos de configuracao do simulador.
	 * o simulador vai carregar apenas as configuracoes referentes ao identificador do roteador  
	 * Caso nao seja passado nenhum parametro o simulador perguntara qual o roteador sera criado
	 * @param args identificador do roteador
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int id = 0;
		
		if (args.length==0) {
			System.out.println("Insira o ID do roteador atual: ");
			id = input.nextInt();										
		} else {
			id = Integer.parseInt(args[0]);
			System.out.println("ID lido: " + id);
		}
		
		Controlador controlador = Controlador.getInstance();
		controlador.criaNo(id);

	}

}
