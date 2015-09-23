import java.util.Scanner;
import java.io.*;
import java.awt.Point;
import java.util.ArrayList;


public class Rota{

	private int mapa[][];
	private int lin;
	private int col;
	private int pessoa_p[];
	private int ini_p[];

	public Rota(String s_mapa){
		this.mapa = null;
		this.lin = 0;
		this.col = 0;
		this.pessoa_p = new int[2];
		this.ini_p = new int[2];
		this.ler_mapa(s_mapa);
	}


	public  ArrayList<Point> achar_rota(){ return null; }
	public  ArrayList<Point> achar_rota_h(){ 

		/* distancia em relacao a saida*/
		int matrizH[][] = this.heuristica(this.pessoa_p);
	
		for(int i=0; i< this.lin;i++){
			for(int j=0; j<this.col; j++){
				System.out.print(matrizH[i][j]);
			}
			System.out.println();
		}

		Estado pessoa = new Estado (new Point(pessoa_p[0],pessoa_p[1]),matrizH[pessoa_p[0]][pessoa_p[1]],0,null);
		Estado entrada = new Estado (new Point(ini_p[0], ini_p[1]), matrizH[ini_p[0]][ini_p[1]],0,null);	

		ArrayList<Estado> pQ = new ArrayList<Estado>();
		ArrayList<Estado> saida = new ArrayList<Estado>();
		pQ.add(entrada);

		while(pQ.size() != 0){

			Estado e = pQ.get(0);
			for(Estado menor : pQ){
				if(e.compareTo(menor) > 0) e = menor;
			}
			saida.add(pQ.get(pQ.indexOf(e)));
			pQ.remove(e);	

			System.out.println("X="+e.getPonto().getX()+" Y="+e.getPonto().getY());

			if(pessoa.equals(e)) break; 
			ArrayList<Estado> viz = proxEst(matrizH,e);


			for(Estado v : viz){
				Estado aux;
				if(!pQ.contains(v)){
					pQ.add(v);
				}
				else if(v.getPasso() < (aux = pQ.get(pQ.indexOf(v))).getPasso() ) {
					aux.setPasso(v.getPasso());
					aux.setAnt(e);
				}
			}
				

		}

		return caminho(saida.remove(saida.size()-1));
	}

	private ArrayList<Point> caminho(Estado e){

		ArrayList<Point> cam = new ArrayList<Point>();

		cam.add(e.getPonto());
		while(e.getAnt() != null){
			e = e.getAnt();
			cam.add(e.getPonto());
		}
		
		return cam;
	}

	public void imprimeRota(ArrayList<Point> rota){
		
		char mapa[][] = new char[this.lin][this.col];

		for(int i=0; i < this.lin; i++){
			for(int j=0; j< this.col; j++){
				if (this.mapa[i][j] == 0) mapa[i][j] = '0';
				else if (this.mapa[i][j] == 1) mapa[i][j] = '+';
			}
		}

		for(Point p : rota){
			mapa[(int)p.getX()][(int)p.getY()] = '*';
		}

		imprimeMapa(mapa);

		return;
	}

	private ArrayList<Estado> proxEst(int matrizH [][], Estado e){
		int lin= (int)e.getPonto().getX();
		int col= (int)e.getPonto().getY();
		ArrayList<Estado> arr = new ArrayList<Estado>();

		if(lin+1 < this.lin) 
			if(this.mapa[lin+1][col] != 1)
				arr.add( new Estado( new Point(lin+1,col), matrizH[lin+1][col], e.getPasso()+1,e));

		if(lin-1 >= 0) 
			if(this.mapa[lin-1][col] != 1)
				arr.add( new Estado( new Point(lin-1,col), matrizH[lin-1][col], e.getPasso()+1,e));

		if(col+1 < this.col) 
			if(this.mapa[lin][col+1] != 1)
				arr.add( new Estado( new Point(lin, col+1), matrizH[lin][col+1], e.getPasso()+1,e));

		if(col-1 >= 0) 
			if(this.mapa[lin][col-1] != 1)
				arr.add( new Estado( new Point(lin,col-1), matrizH[lin][col-1], e.getPasso()+1,e));
	
		return arr;
	}

	private int [][] heuristica(int ini[]){
		int [][] m = new int[this.lin][this.col];
		for(int i=0; i<this.lin; i++){
			for(int j=0; j<this.col; j++){
				m[i][j] = Math.abs(i- ini[0]) + Math.abs(j- ini[1]);
			}
		}

		return m;
	}

	public void ler_mapa(String s_mapa){
		
		try {
			FileReader arq = new FileReader("test/"+s_mapa);
			Scanner lerArq = new Scanner(arq);

			this.lin = lerArq.nextInt();
			this.col = lerArq.nextInt();
			this.ini_p[0] = lerArq.nextInt();
			this.ini_p[1] = lerArq.nextInt();
			this.pessoa_p[0] =  lerArq.nextInt();
			this.pessoa_p[1] = lerArq.nextInt(); 


			this.mapa = new int[lin][col];

			for(int i=0; i<this.lin && lerArq.hasNext(); i++)
				for(int j=0; j<this.col && lerArq.hasNext(); j++)
					this.mapa[i][j] = lerArq.nextInt();

			arq.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n",
					e.getMessage());
		}
	}

	public void imprimeMapa(){
		System.out.println("Coordenada da entrada.: X="+this.ini_p[0]+" Y="+this.ini_p[1]);
		System.out.println("Coordenada da pessoa..: X="+this.pessoa_p[0]+" Y="+this.pessoa_p[1]);
		for(int i=0; i<this.lin; i++){
			for(int j=0; j<this.col; j++)
				System.out.print(this.mapa[i][j]+" ");
			System.out.println();
		}

	}

	private void imprimeMapa(char mapa[][]){
		for(int i=0; i<this.lin; i++){
			for(int j=0; j<this.col; j++)
				System.out.print(mapa[i][j]+" ");
			System.out.println();
		}

	}


}
