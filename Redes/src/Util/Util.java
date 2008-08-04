package Util;

import java.util.ArrayList;

import logica.No;
import logica.RotCelula;

public class Util {

	public static final String CONFIG_ROTEADOR = "." + System.getProperty("file.separator")+"configuracao"+
					System.getProperty("file.separator")+ "roteador.config";
	public static final String CONFIG_ENLACE = "." + System.getProperty("file.separator")+"configuracao"+
					System.getProperty("file.separator")+ "enlaces.config";
	
	public static String codificaDadosDoPacote(No no) {
		String dados = "";
		RotCelula [][] tabela = no.getTabela();
		ArrayList<Integer> vizinhos = no.getVizinhos();
		int pos = vizinhos.indexOf(no.getId());
		dados+= no.getId() + " ";
		
		for (int i = 0; i < tabela.length; i++){
			if (tabela[pos][i].getSalto() == 0){
				dados += vizinhos.get(i).toString() + " " + tabela[pos][i].getPeso() + " 0 ";				
			}else{
				dados += vizinhos.get(i).toString() + " " + tabela[pos][i].getPeso() + " " + tabela[pos][i].getSalto() + " ";	
			}
			
		}
		return dados.trim()+" .";
	}

	public static Integer[] decodificaDadosPacote(String mensagem) {
		String[] nums = mensagem.split(" ");
		Integer[] values = new Integer[nums.length-1];

		int i = 0;
		while (i < nums.length-1){
			if(	nums[i] == null){
				values[i] = 0;
			}
			else{
				values[i] = Integer.parseInt(nums[i]);
			}
			i++;	
		}
		
		return values;
	}

}
