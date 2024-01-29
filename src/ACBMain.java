import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

public class ACBMain {

	public static void main(String[] args) throws IOException, SQLException, ParseException {
		ACBMenu menu = new ACBMenu();


		MainController mainController = new MainController();

		int option = menu.mainMenu();
		int optionTable;
		while (option > 0 && option < 12) {
			switch (option) {
			case 1:
				mainController.selectRegisterSpecific(menu.selectTable());
				break;

			case 2:
				mainController.selectRegisterSpecificText(menu.selectTable());
				break;

			case 3:

				break;

			case 4:

				break;

			case 5:
				break;

			case 6:
				break;

			case 7:
				//optionTable = menu.selectTable();
				mainController.deleteTable(menu.selectTable());

				break;

			case 8:

				break;

			case 9:

				break;

			case 10:
				//optionTable = menu.selectTable();
				mainController.createTable(menu.selectTable());
				break;

			case 11:
				mainController.fillTables();
				break;

			case 12:
				System.exit(0);
				break;

			default:
				System.out.println("Introdueixi una de les opcions anteriors");
				break;

			}
			option = menu.mainMenu();
		}

	}

}
