package jdbc;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Esta es la clase donde se realiza toda la lógica del programa.
 */
public class MainController {
    /**
     * Se instancian la clase para poder conectarnos a la base de datos pt1
     */
    ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    /**
     * Se instancian la clase para poder conectarnos a la base de datos pt2
     */
    Connection c = connectionFactory.connect();
    /**
     * Se instancia el controller del commander
     */
    CommanderController commanderController = new CommanderController(c);
    /**
     * Se instancia el controller del mapa
     */
    MapController mapController = new MapController(c);
    /**
     * Se instancia el controller del player
     */
    PlayerController playerController = new PlayerController(c);
    /**
     * Se instancia el controller de games
     */
    GamesController gamesController = new GamesController(c);
    /**
     * Se trata de un hashmap para poder relacionar la elección del menu de las tablas con la tabla seleccionada.
     */
    Map<Integer, String> tableNames = new HashMap<>();

    /**
     * Método para realizar un select en una columna concreta.
     * @param option Se le proporciona la opción del menu anterior para asi saber cual es el nombre de la columna
     * @throws SQLException Salta una exception si la sentencia SQL falla.
     */
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
        System.out.println();
        String[] columnToCompare = resultString.split(",");
        for (int i = 0; i < columnToCompare.length ; i++) {

            resultStringBuilder.setLength(0);
            if (column.equals(columnToCompare[i])){

                String selectColumns = "SELECT "+ column+" from " +tableNames.get(option);
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
    /**
     * Método para realizar un select en una columna concreta, junto a añadirle una condición.
     * @param option Se le proporciona la opción del menu anterior para asi saber cual es el nombre de la columna
     * @throws SQLException Salta una exception si la sentencia SQL falla.
     */
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
        System.out.println();
        System.out.println(resultString);
        System.out.println("Inserta la columna sobre la que quieres buscar: ");
        String filterColumn = read();
        System.out.println();
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
    /**
     * Método para realizar un select en una columna concreta junto a una condición.
     * @param option Se le proporciona la opción del menu anterior para asi saber cual es el nombre de la columna
     * @throws SQLException Salta una exception si la sentencia SQL falla.
     */
    public void selectRegisterWithCondition(Integer option) throws SQLException {

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
        System.out.println();
        System.out.println(resultString);
        System.out.println("Inserta la columna sobre la que quieres aplicar la condicion: ");
        String filterColumn = read();
        System.out.println();
        System.out.println("Inserta la condicion: ");
        String conditon = read();
        String[] columnToCompare = resultString.split(",");
        for (int i = 0; i < columnToCompare.length ; i++) {

            resultStringBuilder.setLength(0);
            if (column.equals(columnToCompare[i]) || column.equals("*")){

                String selectColumns = "SELECT "+ column+" from " +tableNames.get(option)+" where "+filterColumn+" "+conditon;
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
    /**
     * Método para realizar un update dentro de la base de datos.
     * @param option Se le proporciona la opción del menu anterior para asi saber cual es el nombre de la columna
     * @throws SQLException Salta una exception si la sentencia SQL falla.
     */
    public void updateRegisterSpecific(Integer option) throws SQLException {

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
        System.out.println("¿Que columna quieres editar? (nombre de la columna)");
        String column = read();
        System.out.println();
        System.out.println("Inserta por lo que lo quieres cambiar");
        String condicion = read();
        System.out.println();
        System.out.println("Inserta la condicion: ");
        String conditon = read();
        String[] columnToCompare = resultString.split(",");
        for (int i = 0; i < columnToCompare.length ; i++) {

            resultStringBuilder.setLength(0);
            if (column.equals(columnToCompare[i]) || column.equals("*")){

                String updateColumns = "UPDATE "+tableNames.get(option)+" SET "+column+" = '"+condicion+"' Where "+column+" = '"+conditon+"'" ;
                sts.executeUpdate(updateColumns);
                break;
            }
        }

        if (resultStringBuilder.length()==0){
            System.out.println("No se a encontrado la columna proporcionada. Vuelve a intentarlo");
        }
    }
    /**
     * Método para realizar un delete en una columna concreta, o de una condición, etc...
     * @param option Se le proporciona la opción del menu anterior para asi saber cual es el nombre de la columna
     * @throws SQLException Salta una exception si la sentencia SQL falla.
     */
    public void deleteRegister(Integer option) throws SQLException {

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
        System.out.println("¿Que columna quieres editar? (nombre de la columna)");
        String column = read();
        System.out.println();
        System.out.println("Inserta una condicion");
        String condicion = read();
        System.out.println();

        String[] columnToCompare = resultString.split(",");
        for (int i = 0; i < columnToCompare.length ; i++) {

            resultStringBuilder.setLength(0);
            if (column.equals(columnToCompare[i]) || column.equals("*")){

                String updateColumns = "DELETE FROM "+tableNames.get(option)+" where "+column+" = '"+condicion+"'" ;
                sts.executeUpdate(updateColumns);
                break;
            }
        }

        if (resultStringBuilder.length()==0){
            System.out.println("No se a encontrado la columna proporcionada. Vuelve a intentarlo");
        }
    }

    /**
     * Método para eliminar una tabla de la base de datos.
     * @param option Se le proporciona la opción del menu anterior para asi saber cual es el nombre de la columna
     * @throws SQLException Salta una exception si la sentencia SQL falla.
     */
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
    /**
     * Método para crear una tabla de la base de datos.
     * @param option Se le proporciona la opción del menu anterior para asi saber cual es el nombre de la columna
     * @throws SQLException Salta una exception si la sentencia SQL falla.
     */
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

    /**
     * método para poblar las tablas de información.
     * @throws SQLException Salta una exception si la sentencia SQL falla.
     * @throws IOException Salta la exception si no puede leer el fichero.
     */
    public void fillTables() throws SQLException, IOException {
        // Crear un if para cada uno de los read data para detectar si tienen o no contenido.
        commanderController.readDataFromCSV();
        playerController.readDataFromCSV();
        mapController.readDataFromCSV();
        gamesController.readDataFromCSV();
    }

    /**
     * Método para verificar si la tabla existe o no
     * @param connection La connexion con la base de datos
     * @param tableName El nombre de la tabla que se quiere comprobar si existe
     * @return True o False, dependiendo de si la tabla es existente o no
     * @throws SQLException Salta una exception si la sentencia SQL falla.
     */
    public boolean tableExists(Connection connection, String tableName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            // Use a ResultSet to check if the table exists
            String checkTableSQL = "select tablename from pg_catalog.pg_tables where schemaname='public' and tablename='" + tableName + "'";
            return statement.executeQuery(checkTableSQL).next();
        }
    }

    /**
     * En este método inserto los datos en el HashMap
     */
    public void mapData(){
        tableNames.putIfAbsent(1,"commander");
        tableNames.putIfAbsent(2,"map");
        tableNames.putIfAbsent(3,"player");
        tableNames.putIfAbsent(4,"games");
    }

    /**
     * Con este método invoco un scanner para leer una linea de texto
     * @return Devuelve el texto leido
     */
    public String read(){
        Scanner scanner = new Scanner(System.in);
        String data = scanner.nextLine();
        return data;
    }

    /**
     * Constructor
     */
    public MainController() {
        mapData();
    }
}
