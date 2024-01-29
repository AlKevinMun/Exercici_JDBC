import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ACBMenu {
	private int option;

	public ACBMenu() {
		super();
	}

	public int mainMenu() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		do {

			System.out.println(" \nMENU PRINCIPAL \n");

			System.out.println("1. Mostrar datos"); //Elemento concreto
			System.out.println("2. Mostrar datos que tengan texto concreto"); // Texto concreto
			System.out.println("3. Mostrar datos que cumplan una condition"); // Condicion
			System.out.println("4. Crear datos para una tabla");
			System.out.println("5. Modificar dato concreto"); // Concreto
			System.out.println("6. Modificar multiples datos "); // Diferentes registros
			System.out.println("7. Borrar tablas");
			System.out.println("8. Borrar dato concreto"); // Concreto
			System.out.println("9. Eliminar multiples datos"); // Diferentes registros
			System.out.println("10. Crear tablas");
			System.out.println("11. Poblar las tablas");
			System.out.println("12. Sortir");
			System.out.println("Esculli opció: ");
			try {
				option = Integer.parseInt(br.readLine());
			} catch (NumberFormatException | IOException e) {
				System.out.println("valor no vàlid");
				e.printStackTrace();

			}

		} while (option != 1 && option != 2 && option != 3 && option != 4 && option != 5 && option != 6 && option != 7
				&& option != 8 && option != 9 && option != 10 && option != 11);

		return option;
	}

	public int selectTable() throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println(" \nSELECCIONA UNA TABLA \n");

			System.out.println("1. Commander");
			System.out.println("2. Map");
			System.out.println("3. Player");
			System.out.println("4. Games");
			System.out.println("5. Sortir");
			System.out.println("Esculli opció: ");
			option = Integer.parseInt(br.readLine());

		return option;
	}

}
