import java.util.*;
import java.io.*;
import java.awt.Point;


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



	public  ArrayList<Point> achar_rota(int[] destino){
            ArrayList<Point> fila = new ArrayList<Point>();
            int visitados[][] = new int[lin][col];
            HashMap<Point, Point> antecessores = new HashMap<Point, Point>();
            
            fila.add(new Point(ini_p[0], ini_p[1]));
            Point p = fila.get(0);
            antecessores.put(p, null);
            visitados[(int)p.getX()][(int)p.getY()] = 1;
            
            while(p.getX() != destino[0] || p.getY()!= destino[1] || fila.isEmpty()){
                //remove pai da fila
                fila.remove(p);
                
                //adiciona filhos
                int x = (int) p.getX();
                int y = (int) p.getY();
                Point filho1 = null; 
                Point filho2 = null; 
                Point filho3 = null; 
                Point filho4 = null;
                
                //System.out.println("p: "+x+", "+y);
                if(p.getX() > 0 && mapa[x-1][y] == 0 && visitados[x-1][y] == 0){
                    filho1 = new Point(x-1, y);
                    visitados[x-1][y] = 1;
                    fila.add(filho1);
                    //System.out.println("f1: "+(x-1)+", "+y);
                }if(p.getY() > 0 && mapa[x][y-1] == 0 && visitados[x][y-1] == 0){
                    filho2 = new Point(x, y-1);
                    visitados[x][y-1] = 1;
                    fila.add(filho2);
                    //System.out.println("f2: "+x+", "+(y-1));
                }if(p.getX()+1 < lin && mapa[x+1][y] == 0 && visitados[x+1][y] == 0){
                    filho3 = new Point(x+1, y);
                    visitados[x+1][y] = 1;
                    fila.add(filho3);
                    //System.out.println("f3: "+(x+1)+", "+y);
                }if(p.getY()+1 < col && mapa[x][y+1] == 0 && visitados[x][y+1] == 0){
                    filho4 = new Point(x, y+1);
                    visitados[x][y+1] = 1;
                    fila.add(filho4);
                    //System.out.println("f4: "+x+", "+(y+1));
                }
               
               //new Scanner(System.in).nextLine();
                
                //adiciona pai a lista de sucessores dos filhos
                if(filho1 != null)
                    antecessores.put(filho1, p);
                if(filho2 != null)
                    antecessores.put(filho2, p);
                if(filho3 != null)
                    antecessores.put(filho3, p);
                if(filho4 != null)
                    antecessores.put(filho4, p);
                
                if(!fila.isEmpty())
                    p = fila.get(0);
            }
            
            ArrayList<Point> caminho = new ArrayList<Point>();
            caminho.add(p);
            while(antecessores.get(p) != null){
                p = antecessores.get(p);
                caminho.add(p);
            }
            //System.out.println("Caminho encontrado:");
            ArrayList<Point> caminho_final = new ArrayList<Point>();
            for(int i = caminho.size()-1; i >= 0; i--){
                caminho_final.add(caminho.get(i));
                //System.out.println("linha: "+caminho.get(i).getX()+" coluna: "+caminho.get(i).getY());
            }
            
            return caminho_final;
        }
	public  ArrayList<Point> achar_rota_h(){ return null; }



	public void ler_mapa(String s_mapa){
		
		try {
			FileReader arq = new FileReader("test/"+s_mapa);
			Scanner lerArq = new Scanner(arq);

			this.lin = lerArq.nextInt();
			this.col = lerArq.nextInt();
			this.ini_p[0] =  lerArq.nextInt();
			this.ini_p[1] = lerArq.nextInt(); 
			this.pessoa_p[0] = lerArq.nextInt();
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

	public void imprime_mapa(){
		System.out.println("Coordenada da entrada.: X="+this.ini_p[0]+" Y="+this.ini_p[1]);
		System.out.println("Coordenada da pessoa..: X="+this.pessoa_p[0]+" Y="+this.pessoa_p[1]);
		for(int i=0; i<this.lin; i++){
			for(int j=0; j<this.col; j++)
				System.out.print(this.mapa[i][j]+" ");
			System.out.println();
		}

                achar_rota(pessoa_p);
	}

}
