import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainController {

    ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    Connection c = connectionFactory.connect();

    CommanderController commanderController = new CommanderController(c);
    MapController mapController = new MapController(c);
    PlayerController playerController = new PlayerController(c);
    GamesController gamesController = new GamesController(c);

    Map<Integer, String> tableNames = new HashMap<>();

    public void selectRegisterSpecific(Integer option) throws SQLException {

        Statement sts = c.createStatement();

        String showColumns = "SELECT column_name FROM information_schema.columns WHERE table_schema = 'public' AND table_name = '"+tableNames.get(option)+"'";

        ResultSet resultSet = sts.executeQuery(showColumns);

        StringBuilder resultStringBuilder = new StringBuilder();

        while (resultSet.next()) {
            // Suponiendo que el resultado sea una columna de tipo string
            String columnName = resultSet.getString(1);

            resultStringBuilder.append(columnName).append(",");
        }
        String resultString = resultStringBuilder.toString();
        System.out.println(" ");
        System.out.println("La tabla tiene las siguientes columnas: ");
        System.out.println(resultString);
        System.out.println("¿Que columna quieres mostrar? (nombre de la columna)");
        String column = read();
        System.out.println("");
        String[] columnToCompare = resultString.split(",");
        for (int i = 0; i < columnToCompare.length ; i++) {

            resultStringBuilder.setLength(0);
            if (column.equals(columnToCompare[i])){

                String selectColumns = "SELECT "+ column+" from " +tableNames.get(option)+"";
                resultSet = sts.executeQuery(selectColumns);

                while (resultSet.next()) {
                    String columnValue = resultSet.getString(1);

                    resultStringBuilder.append(columnValue).append(" | ");
                }

                resultString = resultStringBuilder.toString();
                System.out.println(resultString);
                break;
            }
        }

        if (resultStringBuilder.length()==0){
            System.out.println("No se a encontrado la columna proporcionada. Vuelve a intentarlo");
        }

    }

    public void selectRegisterSpecificText(Integer option) throws SQLException {

        Statement sts = c.createStatement();

        String showColumns = "SELECT column_name FROM information_schema.columns WHERE table_schema = 'public' AND table_name = '"+tableNames.get(option)+"'";

        ResultSet resultSet = sts.executeQuery(showColumns);

        StringBuilder resultStringBuilder = new StringBuilder();

        while (resultSet.next()) {
            String columnName = resultSet.getString(1);

            resultStringBuilder.append(columnName).append(",");
        }
        String resultString = resultStringBuilder.toString();
        System.out.println(" ");
        System.out.println("La tabla tiene las siguientes columnas: ");
        System.out.println(resultString +" *");
        System.out.println("¿Que columna quieres mostrar? (nombre de la columna)");
        String column = read();
        System.out.println("");
        System.out.println(resultString);
        System.out.println("Inserta la columna sobre la que quieres buscar: ");
        String filterColumn = read();
        System.out.println("");
        System.out.println("Inserta el texto que quieres buscar: ");
        String filter = read();
        String[] columnToCompare = resultString.split(",");
        for (int i = 0; i < columnToCompare.length ; i++) {

            resultStringBuilder.setLength(0);
            if (column.equals(columnToCompare[i]) || column.equals("*")){

                String selectColumns = "SELECT "+ column+" from " +tableNames.get(option)+" where "+filterColumn+" like '%"+filter+"%'";
                resultSet = sts.executeQuery(selectColumns);

                while (resultSet.next()) {
                    // Suponiendo que la columna seleccionada es de tipo cadena
                    String columnValue = resultSet.getString(1);

                    resultStringBuilder.append(columnValue).append(" | ");
                }

                resultString = resultStringBuilder.toString();
                System.out.println(resultString);
                break;
            }
        }

        if (resultStringBuilder.length()==0){
            System.out.println("No se a encontrado la columna proporcionada. Vuelve a intentarlo");
        }

    }

    public void deleteTable(Integer option) throws SQLException {
        if (option==5) {}
        else {

            if (tableExists(c, tableNames.get(option))) {

                Statement sts = c.createStatement();

                String sql = "DROP TABLE " + tableNames.get(option).toString() + " CASCADE";

                sts.executeUpdate(sql);
            } else System.out.println("La tabla no existe");
        }
    }

    public void createTable(int option) throws SQLException {
        switch (option){
            case 1:
                commanderController.createTable();
                break;
            case 2:
                mapController.createTable();
                break;
            case 3:
                playerController.createTable();
                break;
            case 4:
                gamesController.createTable();
                break;

        }
    }
    public void fillTables() throws SQLException, IOException {
        // Crear un if para cada uno de los read data para detectar si tienen o no contenido.
        commanderController.readDataFromCSV();
        playerController.readDataFromCSV();
        mapController.readDataFromCSV();
        gamesController.readDataFromCSV();
    }
    public boolean tableExists(Connection connection, String tableName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            // Use a ResultSet to check if the table exists
            String checkTableSQL = "select tablename from pg_catalog.pg_tables where schemaname='public' and tablename='" + tableName + "'";
            return statement.executeQuery(checkTableSQL).next();
        }
    }
    public void mapData(){
        tableNames.putIfAbsent(1,"commander");
        tableNames.putIfAbsent(2,"map");
        tableNames.putIfAbsent(3,"player");
        tableNames.putIfAbsent(4,"games");
    }

    public String read(){
        Scanner scanner = new Scanner(System.in);
        String data = scanner.nextLine();
        return data;
    }

    public MainController() {
        mapData();
    }
}
