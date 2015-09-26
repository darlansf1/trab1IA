import java.util.Scanner;
import java.awt.Point;
import java.util.ArrayList;


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

		System.out.println("\n[BUSCA EM LARGURA]\n");
		rota = r.acharRota();
		r.imprimeRota(rota);

		System.out.println("\n[BUSCA COM HEURISTICA - A*]\n");
		rota = r.acharRotaH();
		r.imprimeRota(rota);

	
		System.out.println("\n\n[ESCOMBROS ESTAO CAINDO, EH PRECISO MUDAR A ROTA !!]\n\n");


		System.out.println("\n[BUSCA EM LARGURA]\n");
		rota = r2.acharRota();
		r2.imprimeRota(rota);

		System.out.println("\n[BUSCA COM HEURISTICA - A*]\n");
		rota = r2.acharRotaH();
		r2.imprimeRota(rota);

		return;

	}
}

