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
	public  ArrayList<Point> achar_rota_h(){ return null; }



	public void ler_mapa(String s_mapa){
		
		try {
			FileReader arq = new FileReader("../test/"+s_mapa);
			Scanner lerArq = new Scanner(arq);

			this.lin = lerArq.nextInt();
			this.col = lerArq.nextInt();
			this.pessoa_p[0] =  lerArq.nextInt();
			this.pessoa_p[1] = lerArq.nextInt(); 
			this.ini_p[1] = lerArq.nextInt();
			this.ini_p[1] = lerArq.nextInt();

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

	public void imprime_mapa(){
		System.out.println("Coordenada da entrada.: X="+this.ini_p[0]+" Y="+this.ini_p[1]);
		System.out.println("Coordenada da pessoa..: X="+this.pessoa_p[0]+" Y="+this.pessoa_p[1]);
		for(int i=0; i<this.lin; i++){
			for(int j=0; j<this.col; j++)
				System.out.print(this.mapa[i][j]+" ");
			System.out.println();
		}

	}

}
