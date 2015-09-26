import java.util.Scanner;
import java.awt.Point;
import java.util.ArrayList;

/*
 * Trabalho 1 - IA
 *
 * Prof. Solange Rezende
 *
 * Clayton de Oliveira - 7161591
 * Darlan Farias - 7987140
 * Dennis Ryudi - 8124354
 * Edesio Alcobaca - 8578872
 * Eric Freire - 7987115
 *
 * Ano - 2015
 */ 

class Programa {
	public static void main(String[] args) {

		Scanner ent = new Scanner(System.in);
		String sMapa = null;
		ArrayList<Point> rota = new ArrayList<Point>();
			
		/* dados iniciais requeridos */
		System.out.printf("Insira o nome do mapa inicial ........: ");
		sMapa = ent.nextLine();
		Rota r = new Rota(sMapa);
		System.out.printf("Insira o nome do mapa final ..........: ");
		sMapa = ent.nextLine();
		Rota r2 = new Rota(sMapa);

		//primeira rota, mapa sem escombros
		System.out.println("\n[BUSCA EM LARGURA]\n");
		rota = r.acharRota();
		r.imprimeRota(rota);

		System.out.println("\n[BUSCA COM HEURISTICA - A*]\n");
		rota = r.acharRotaH();
		r.imprimeRota(rota);

	
		System.out.println("\n\n[ESCOMBROS ESTAO CAINDO, EH PRECISO MUDAR A ROTA !!]\n\n");

		//segunda rota, mapa com escombros
		System.out.println("\n[BUSCA EM LARGURA]\n");
		rota = r2.acharRota();
		r2.imprimeRota(rota);

		System.out.println("\n[BUSCA COM HEURISTICA - A*]\n");
		rota = r2.acharRotaH();
		r2.imprimeRota(rota);


		//imprimir resultados finais 
		System.out.println("[ Mapa inicial ]");
		System.out.println("Numeros de estados visitados - B. Largura ..: "+r.getEstVis());
		System.out.println("Numeros de estados visitados - A* ..........: "+r.getEstVisH());
		System.out.println("[ Mapa com escombros ]");
		System.out.println("Numeros de estados visitados - B. Largura ..: "+r2.getEstVis());
		System.out.println("Numeros de estados visitados - A* ..........: "+r2.getEstVisH());
		System.out.println("............................................" );

		System.out.println("Numeros de estados visitados - B. Largura ..: "+(r.getEstVis()+r2.getEstVis()));
		System.out.println("Numeros de estados visitados - A* ..........: "+(r.getEstVisH()+r2.getEstVisH()));

		return;

	}
}

