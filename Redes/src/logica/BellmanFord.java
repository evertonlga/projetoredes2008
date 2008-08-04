package logica;

import threads.ThreadAvisaVizinho;


public class BellmanFord {

	private No no;
	
	public BellmanFord(No noAtual) {
		this.no = noAtual;
	}

	public void avisaVizinhos() {
		ThreadAvisaVizinho avisa;
		for (int vizinho: no.getVizinhos())
			if (vizinho != no.getId()){
				avisa = new ThreadAvisaVizinho(no, vizinho);
				avisa.run();
			}
	}

	public boolean aplicaBellmanFord(int posAtual, int posicao) {
		RotCelula [][] tabela = getNo().getTabela();
		int custo = tabela[posAtual][posicao].getPeso(); 
		boolean hasModificacao = false;

		// Equação de Bellman-Ford
		for (int i = 0; i < tabela.length; i++){
			if ((tabela[posAtual][i].getPeso() != Integer.MAX_VALUE)&&(tabela[posicao][i].getPeso() != Integer.MAX_VALUE)&&
					(custo != Integer.MAX_VALUE )){
				if ((custo + tabela[posicao][i].getPeso()) < tabela[posAtual][i].getPeso()){
					tabela[posAtual][i].setPeso(custo + tabela[posicao][i].getPeso());
					tabela[posAtual][i].setSalto(this.getNo().getVizinhos().get(posicao));
					hasModificacao = true;
				}
				
			}
		}

		return hasModificacao;
	}

	public No getNo() {
		return no;
	}

	public void setNo(No no) {
		this.no = no;
	}

}
