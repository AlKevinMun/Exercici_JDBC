package jdbc;
import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase de los mapas
 */
public class MapController {
    /**
     * Atributo necesario para conectarte a la base de datos
     */
    private Connection connection;
    /**
     * Constructor
     * @param connection Requiere la clase para conectarse a la base de datos.
     */

    public MapController(Connection connection) {this.connection = connection;}
    /**
     * método para crear la tabla en al base de datos.
     * @throws SQLException Salta una exception si la sentencia SQL falla.
     */
    public void createTable() throws SQLException {

        Statement sts = connection.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS map("+
                "map_name VARCHAR(50) PRIMARY KEY not null,\n" +
                "creator VARCHAR(50) not null,\n" +
                "max_players INTEGER not null,\n" +
                "size VARCHAR(50) not null)";

        sts.executeUpdate(sql);
    }
    /**
     * método para leer los ficheros CSV y hacer un select basándose en los datos que a leido.
     * @throws IOException Salta la exception si no puede leer el fichero.
     * @throws SQLException Salta una exception si la sentencia SQL falla.
     */
    public void readDataFromCSV() throws IOException, SQLException {
        CSVReader reader = new CSVReader(new FileReader("resources/map.csv"));
        String[] data = null;
        reader.readNext();
        while ((data = reader.readNext()) !=null){
            String map_name = data[0];
            String creator = data[1];
            int max_player = Integer.parseInt(data[2]);
            String size = data[3];

            String sql = "INSERT INTO map(map_name, creator, max_players, size) VALUES (?,?,?,?)";

            PreparedStatement pst = connection.prepareStatement(sql);

            pst.setString(1, map_name);
            pst.setString(2, creator);
            pst.setInt(3, max_player);
            pst.setString(4, size);

            pst.executeUpdate();

        }

    }
}
