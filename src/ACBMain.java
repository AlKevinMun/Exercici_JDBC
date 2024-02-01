import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Clase donde se ejecuta todo el codigo.
 */
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
				mainController.selectRegisterWithCondition(menu.selectTable());
				break;
			case 4:
				mainController.updateRegisterSpecific(menu.selectTable());
				break;
			case 5:
				mainController.updateRegisterSpecific(menu.selectTable());
				break;
			case 6:
				//optionTable = menu.selectTable();
				mainController.deleteTable(menu.selectTable());
				break;
			case 7:
				mainController.deleteRegister(menu.selectTable());
				break;
			case 8:
				mainController.deleteRegister(menu.selectTable());
				break;
			case 9:
				mainController.createTable(menu.selectTable());
				break;
			case 10:
				mainController.fillTables();
				break;
			case 11:
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
