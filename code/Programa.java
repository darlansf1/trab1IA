import java.util.Scanner;
import java.awt.Point;
import java.util.ArrayList;


class Programa {
	public static void main(String[] args) {

		Scanner ent = new Scanner(System.in);
		String s_mapa = null;
		ArrayList<Point> rota = new ArrayList<Point>();
			

		/* dados iniciais requeridos */
		System.out.printf("Insira o nome do mapa ...........: ");
		s_mapa = ent.nextLine();

		Rota r = new Rota(s_mapa);
		r.imprimeMapa();
		rota = r.achar_rota_h();
		r.imprimeRota(rota);

		return;

	}
}

