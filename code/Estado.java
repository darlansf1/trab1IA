import java.awt.Point;

public class Estado implements Comparable<Estado>{

	private Point p; //posicao no mapa
	private int passo; // custo real, somando os pesos das transicoes do ponto de partido ate o atual
	private int h; // estimativa de custo do estado atual ate o final
	private Estado ant; //estado anterior no caminho

	public Estado(){
		this.p = null;
		this.h = 0;
		this.passo = 0;
	}

	public Estado(Point p, int h, int passo, Estado ant){
		this.p = p;
		this.h = h;
		this.passo = passo;
		this.ant = ant;
	}

	public Point getPonto(){ return this.p; }

	//definicao de peso como a funcao heuristica: soma do custo real com a estimativa
	public int getPeso(){
		return this.h + this.passo; 
	}
	
	public int getPasso(){return this.passo;}
	public int getH(){return this.h;}
	public Estado getAnt(){return this.ant;}

	public void setPonto(Point p) { this.p = p; }
	public void setPasso(int passo) {this.passo = passo;}
	public void setH(int h){this.h = h;}
	public void setAnt(Estado ant){this.ant = ant;}
	/*
	* metodo compareTo
	* Funcao: realizar comparacao de dois estados baseados em seu peso (ver definicao de peso acima)
	* Retorno conforme especificacao da implementaca da interface Comparable do Java 
	*	
	*/
	public int compareTo(Estado e){
		
		if(this.getPeso() > e.getPeso()) return 1;
		else if(this.getPeso() < e.getPeso()) return -1;
		else return 0;
	}
	
	public boolean equals(Object o){
		Estado est = (Estado) o;
		if(this.p.getX() == est.getPonto().getX() && 
			this.p.getY() == est.getPonto().getY()) return true;
		else return false;
	} 
	
}

