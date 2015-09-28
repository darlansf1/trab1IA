import java.util.*;
import java.io.*;
import java.awt.Point;


public class Rota{

	private int mapa[][];
	private int lin;
	private int col;
	private int destino[]; //ponto de destino
	private int partida[];  // ponto de partida
	private Point dest; //ponto de destino
	private Point part; //ponto de partida
	private int estVisH; //contador de nos visitados para busca informada
	private int estVis; //contador de nos visitados para busca cega

	public Rota(String s_mapa){
		this.mapa = null;
		this.lin = 0;
		this.col = 0;
		this.destino = new int[2];
		this.partida = new int[2];
		this.ler_mapa(s_mapa);
		this.estVisH = 0;
		this.estVis = 0;

	}

	public void setPart( Point part) { this.part = part; }
	public void setDest( Point part) { this.dest = dest; }

	public Point getPart() { return this.part; }
	public Point getdest() { return this.dest; }
	public int getEstVisH() { return this.estVisH; }
	public int getEstVis() { return this.estVis; }
	/*
	* metodo acharRotaH
	* Funcao: Encontrar uma rota utilizando busca informada
	* Implementacao atual utiliza:
	*	Estrategia: A*  
	*	Heuristica: Distancia de Manhattan, desconsiderando obstaculos
	*/
	public  ArrayList<Point> acharRotaH(){ 

		/*Encontrar a estimativa (heuristica) de todos os poontos do mapa em relacao ao destino*/
		int matrizH[][] = this.heuristica(this.destino);
		/* 
		* Utilizando nomes intuitivos para o problema, considerando a primeira etapa da solucao: encontrar rota partindo da entrada e chegando em pessoa 
		* 	variavel pessoa representa o estado destino
		*	variavel entrada representa o estado de partida
		*/
		Estado pessoa = new Estado (new Point(destino[0],destino[1]),matrizH[destino[0]][destino[1]],0,null);
		Estado entrada = new Estado (new Point(partida[0], partida[1]), matrizH[partida[0]][partida[1]],0,null);	
		
		ArrayList<Estado> pQ = new ArrayList<Estado>(); //lista de processos abertos
		ArrayList<Estado> saida = new ArrayList<Estado>(); //lista de processos visitados
		ArrayList<Estado> cam = new ArrayList<Estado>(); // estrutura que armazenara os nos para que possa ser calculado o caminho ao final do algoritmo.
		/*
		* insere-se o no inicial na lista de processos abertos e na lista de processos visitados
		*/
		pQ.add(entrada); 
		saida.add(entrada);

		while(pQ.size() != 0){
			/*
			* busca-se o processo com a menor funcao heuristica (menor soma passo + distancia manhattan)
			*/
			Estado e = pQ.get(0);
			for(Estado menor : pQ){
				if(e.compareTo(menor) > 0) e = menor;
			}
			/*
			* Adiciona-se o processo na estrutura elaborara de maneira que seja possivel calcular o caminho ao final do algoritmo
			* tal no e tambem removido da lita de processos abertos
			*/
			cam.add(pQ.get(pQ.indexOf(e)));
			pQ.remove(e);	

			this.estVisH++;

			if(pessoa.equals(e)) break; //se encontrou o estado final, entao termina o algortimo.
			ArrayList<Estado> viz = proxEst(matrizH,e);// encontra os estados vizinhos 


			for(Estado v : viz){//para cada estado vizinho
				//Estado aux;
				if(!saida.contains(v)){ // se o estado vizinho nao estiver na lista de visitados
					pQ.add(v); //abre-se um processo com o estado vizinho
					saida.add(v); // adiciona tal processo na lista de visitados
				}
				/*else if(v.getPasso() < (aux = pQ.get(pQ.indexOf(v))).getPasso() ) {
					aux.setPasso(v.getPasso());
					aux.setAnt(e);
					System.out.println("asdasdasdasdasdasd");
				}*/
			}


		}
		/*
		* A estrutura cam nao contem o caminho em si. Portanto uma rotina que calcuula efetivamente o caminho,.
		* Da maneira em q esta estruturado, e preciso somente o ultimo no de cam para calcular  o caminho
		*/
		return caminho(cam.remove(cam.size()-1));
	}
	/*
	* metodo acharRota
	* Funcao: Encontrar uma rota utilizando busca cega
	* Implementacao atual utiliza:
	*	Estrategia: Busca em largura
	*/

	public  ArrayList<Point> acharRota(){

		ArrayList<Point> fila = new ArrayList<Point>(); // estrutura de fila para armazenar os proximos nos a serem visitados
		int visitados[][] = new int[lin][col]; // estrutura mara indicar quais estados ja foram visitados, evitando loops
		HashMap<Point, Point> antecessores = new HashMap<Point, Point>();

		/*
		* Inicializa o algoritmo, tomando o ponto de partida como raiz da arvore
		* Para isso, adiciona-se o ponto de partida na fila, marca-o como visitado e inicializa seu sucessor como nulo
		*/
		fila.add(new Point(partida[0], partida[1]));
		Point p = fila.get(0);
		antecessores.put(p, null);
		visitados[(int)p.getX()][(int)p.getY()] = 1;

		while(p.getX() != destino[0] || p.getY()!= destino[1] || fila.isEmpty()){ // enquanto nao encontrar no destino e enquanto a fila estiver cheia
			//remove pai da fila
			fila.remove(p);
			this.estVis++;               

			int x = (int) p.getX();
			int y = (int) p.getY();
			Point filho1 = null; 
			Point filho2 = null; 
			Point filho3 = null; 
			Point filho4 = null;

			/*
			* Procuram-se as transicoes validas, checando se as posicoes vizinhas ainda se encontram dentro do mapa (maior que 0 e menor col ou lin) e 
			* checando se e uma posicao livre (mapa == 0).
			* Alem disso, checa-se se o vizinho esta marcado como visitado, evitando loops
			*/
			if(p.getX() > 0 && mapa[x-1][y] == 0 && visitados[x-1][y] == 0){//se e uma transicao valida
				filho1 = new Point(x-1, y); //cria-se um filho
				visitados[x-1][y] = 1; //marca como visitado
				fila.add(filho1); // adiciona filho na fila para que seja visitado analisado posteriormente
			}if(p.getY() > 0 && mapa[x][y-1] == 0 && visitados[x][y-1] == 0){
				filho2 = new Point(x, y-1);
				visitados[x][y-1] = 1;
				fila.add(filho2);
			}if(p.getX()+1 < lin && mapa[x+1][y] == 0 && visitados[x+1][y] == 0){
				filho3 = new Point(x+1, y);
				visitados[x+1][y] = 1;
				fila.add(filho3);
			}if(p.getY()+1 < col && mapa[x][y+1] == 0 && visitados[x][y+1] == 0){
				filho4 = new Point(x, y+1);
				visitados[x][y+1] = 1;
				fila.add(filho4);
			}

			//adiciona pai na lista de antecessores dos filhos
			if(filho1 != null)
				antecessores.put(filho1, p);
			if(filho2 != null)
				antecessores.put(filho2, p);
			if(filho3 != null)
				antecessores.put(filho3, p);
			if(filho4 != null)
				antecessores.put(filho4, p);

			if(!fila.isEmpty()) // pega proximo estado
				p = fila.get(0);
		}
		/*
		* Encontrar caminho baseado na lista de antecessores dos nos, retornando o caminho invertido
		*/
		ArrayList<Point> caminho = new ArrayList<Point>();
		caminho.add(p);// adiciona-se o no destino no caminho
		while(antecessores.get(p) != null){
			p = antecessores.get(p);// pega antecessor
			caminho.add(p); //adiciona antecessor ao caminho
		}
		//desinverte o caminho
		ArrayList<Point> caminho_final = new ArrayList<Point>();
		for(int i = caminho.size()-1; i >= 0; i--){
			caminho_final.add(caminho.get(i));
		}

		return caminho_final;
	}
	/*
	* metodo caminho
	* Funcao: Criar uma lista de Pontos que representara o caminho efetivo escolhido pelo algoritmo, baseado no atributo "ant" que representa
	* 	o antecessor do estado.
	*/
	private ArrayList<Point> caminho(Estado e){

		ArrayList<Point> cam = new ArrayList<Point>();

		cam.add(e.getPonto()); //adiciona-se o etado e na lista de pontos que compoem o caminho
		while(e.getAnt() != null){// enquanto nao chega na raiz
			e = e.getAnt(); 
			cam.add(e.getPonto()); //adiciona-se o ponto antecessor no caminho
		}

		return cam;
	}

	/*
	* metodo proxEst
	* Funcao: Retornar uma lista de estados sucessores, baseado nas restricoes do problma.
	* 	Ou seja, retornar todas as transicoes possiveis para um determinado estado
	* Parametros:
	* 	matrizH 	 matriz contendo valor aproximado da distancia de todos os pontos ate o destino.
	*	e 	 estado cujos sucessores serao avaliados 
	*/
	private ArrayList<Estado> proxEst(int matrizH [][], Estado e){

		int lin= (int)e.getPonto().getX();
		int col= (int)e.getPonto().getY();
		ArrayList<Estado> arr = new ArrayList<Estado>();//lista de novos processos para cada transicao possivel
		/*
		* Para cada uma das direcoes, verrificar se e uma transicao possivel.
		* 	Para fazer verificacao, checar se as posicoes vizinhas correspondem a obstaculos (valor na matriz igual a 1) 
		* Para cada transicao possivel cria-se um novo processo, inicilizando com a seguinte logica:
		* 	o valor da aproximacao utilizado e retirado da matriz matrizH, que contem todas as estimativas
		* 	o valor do custo real eh calculado somando o valor do custo ate o estado atual, aqui chamado de passo (e.getPasso()) com o valor do custo da transicao(+1)
		* OBS: todas as transicoes possuem custo 1
		*/
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
	/*
	* metodo heuristica
	* Funcao: Retornar uma matriz contendo uma estimativa de distancia de todos os ontos do mapa ate um determinado ponto especifico
	* Parametros:
	* 	ini 	 coordenadas do ponto a partir do qual serao calculadas as distancias
	*/
	private int [][] heuristica(int ini[]){

		int [][] m = new int[this.lin][this.col];
		for(int i=0; i<this.lin; i++){
			for(int j=0; j<this.col; j++){
				m[i][j] = Math.abs(i- ini[0]) + Math.abs(j- ini[1]); //calculo da distanca de Manhattan, desconsiderando obstaculos
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

			this.part = new Point (lerArq.nextInt(), lerArq.nextInt());
			this.dest = new Point (lerArq.nextInt(), lerArq.nextInt());

			this.partida[0] = (int)this.part.getX();
			this.partida[1] = (int)this.part.getY();
			this.destino[0] =  (int)this.dest.getX();
			this.destino[1] = (int)this.dest.getY();

			this.mapa = new int[lin][col];

			for(int i=0; i<this.lin && lerArq.hasNext(); i++)
				for(int j=0; j<this.col && lerArq.hasNext(); j++)
					this.mapa[i][j] = lerArq.nextInt();
			arq.close();

		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n",
					e.getMessage());
			System.exit(1);
		}
	}

	public void imprimeMapa(){

		this.imprimeMapa(this.desenhaMapa());

		return;	
	}

	private void imprimeMapa(char mapa[][]){

		for(int i=0; i<this.lin; i++){
			for(int j=0; j<this.col; j++)
				System.out.print(mapa[i][j]+" ");
			System.out.println();
		}

		return;
	}


	private char[][] desenhaMapa(){

		char mapa[][] = new char[this.lin][this.col];

		for(int i=0; i < this.lin; i++){
			for(int j=0; j< this.col; j++){
				if (this.mapa[i][j] == 0) mapa[i][j] = '0';
				else if (this.mapa[i][j] == 1) mapa[i][j] = '1';
			}
		}
		return mapa;
	}

	public void imprimeRota(ArrayList<Point> rota){

		char mapa[][] = this.desenhaMapa();
		int partX = (int)this.part.getX();
		int partY = (int)this.part.getY();
		int destX = (int)this.dest.getX();
		int destY = (int)this.dest.getY();

		System.out.println("Coordenada de partida.: X="+partX+" Y="+partY);
		System.out.println("Coordenada de destino.: X="+destX+" Y="+destY+"\n");

		for(Point p : rota)
			mapa[(int)p.getX()][(int)p.getY()] = '*';

		mapa[destX][destY] = '#';
		mapa[partX][partY] = '$';

		imprimeMapa(mapa);

		System.out.println("\nLegenda: ");
		System.out.println("     [1] - Indica parte bloqueada");
		System.out.println("     [0] - Indica parte nao bloqueada");
		System.out.println("     [#] - Indica ponto de destino");
		System.out.println("     [$] - Indica ponto de partida\n");

		return;
	}

}
