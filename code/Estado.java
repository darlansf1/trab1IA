import java.awt.Point;

public class Estado implements Comparable<Estado>{

	private Point p;
	private int passo;
	private int h;
	private Estado ant;

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
	public int getPeso(){ return this.h + this.passo; }
	public int getPasso(){return this.passo;}
	public int getH(){return this.h;}
	public Estado getAnt(){return this.ant;}

	public void setPonto(Point p) { this.p = p; }
	public void setPasso(int passo) {this.passo = passo;}
	public void setH(int h){this.h = h;}
	public void setAnt(Estado ant){this.ant = ant;}

	public int compareTo(Estado e){
		
		if(this.getPeso() > e.getPeso()) return 1;
		else if(this.getPeso() < e.getPeso()) return -1;
		else return 0;
	}

	public boolean equals(Object o){
		Estado est = (Estado) o;
		if(this.p.getX() == est.getPonto().getX() && this.p.getY() == est.getPonto().getY() ) return true;
		else return false;
	} 
	
}

