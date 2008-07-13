package main;

import java.util.Scanner;

import controle.Controlador;

public class Main {

	
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
