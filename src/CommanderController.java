import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase de los commandantes
 */
public class CommanderController {
    /**
     * Atributo necesario para conectarte a la base de datos
     */
    private Connection connection;
    /**
     * Constructor
     * @param connection Requiere la clase para conectarse a la base de datos.
     */

    public CommanderController(Connection connection) {this.connection = connection;}
    /**
     * método para crear la tabla en al base de datos.
     * @throws SQLException
     */
    public void createTable() throws SQLException {

        Statement sts = connection.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS commander("+
                "commander_name VARCHAR(20) PRIMARY KEY not null)";

        sts.executeUpdate(sql);
    }

    /**
     * método para leer los ficheros CSV y hacer un select basándose en los datos que a leido.
     * @throws IOException
     * @throws SQLException
     */
    public void readDataFromCSV() throws FileNotFoundException, SQLException {
        Scanner scanner= new Scanner(new File("resources/player.csv"));
        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> finalData = new ArrayList<>();
        int i = 0;
            while(scanner.hasNext()){
                data.add(scanner.nextLine());
                i++;
            }
            scanner.close();
        for (int j = 0; j < data.size(); j++) {
            System.out.println(data.get(j));
        }
        String[] separador1 = data.get(1).replaceAll(",","").split(" ");
        for (int j = 0; j < separador1.length; j++) {
            if (j%3==0){
                if (j==0) {
                    Pattern patron = Pattern.compile("\"\\[([^\"\\]]+)");
                    Matcher matcher = patron.matcher(separador1[j]);
                    if (matcher.find()) {
                        String resultado = matcher.group(1);
                        finalData.add(resultado);
                    }
                }
                else finalData.add(separador1[j]);
            }
        }
        Collections.sort(finalData);

        String sql = "INSERT INTO commander(commander_name) VALUES (?)";

        for (int j = 0; j < finalData.size(); j++) {
            PreparedStatement pst = connection.prepareStatement(sql);

            pst.setString(1, finalData.get(j));
            System.out.println(finalData.get(j));

            pst.executeUpdate();
        }

    }

}
