package Util;

import java.util.ArrayList;

import logica.No;
import logica.RotCelula;

/**
 * 
 * @author Everton Galdino
 * @author Vinicius Marques
 *
 */
public class Util {

	public static final String CONFIG_ROTEADOR = "." + System.getProperty("file.separator")+"configuracao"+
					System.getProperty("file.separator")+ "roteador.config";
	public static final String CONFIG_ENLACE = "." + System.getProperty("file.separator")+"configuracao"+
					System.getProperty("file.separator")+ "enlaces.config";
	public static final int RAIO_DA_REDE = 26;
	
	/**
	 * prepara a mensagem para ser enviada 
	 * @param no destino da mensagem 
	 * @return mensagem pronta para ser enviada
	 */
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

	/**
	 * extrai o dado do pacote
	 * @param mensagem mensagem codificada
	 * @return a mensagem que estava no pacote
	 */
	public static Integer[] decodificaDadosPacote(String mensagem) {
		String[] nums = mensagem.split(" ");
		Integer[] values = new Integer[limit(nums)];

		int i = 0;
		while (i<values.length){
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
	
	/**
	 * conta
	 * @param nums
	 * @return
	 */
	private static int limit(String[] nums) {
		for (int i =0; i< nums.length; i++)
			if (nums[i].startsWith("."))
				return i;
		return 0;
	}

}
