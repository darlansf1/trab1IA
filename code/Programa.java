import java.util.Scanner;

class Programa {
	public static void main(String[] args) {

		Scanner ent = new Scanner(System.in);
		String s_mapa = null;

		/* dados iniciais requeridos */
		System.out.printf("Insira o nome do mapa ...........: ");
		s_mapa = ent.nextLine();

		Rota r = new Rota(s_mapa);
		r.imprime_mapa();

		return;

	}
}

