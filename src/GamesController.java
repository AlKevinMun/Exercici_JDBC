import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase de las partidas
 */
public class GamesController {
    /**
     * Atributo necesario para conectarte a la base de datos
     */
    private Connection connection;

    /**
     * Constructor
     * @param connection Requiere la clase para conectarse a la base de datos.
     */

    public GamesController(Connection connection) {this.connection = connection;}

    /**
     * Método para crear la tabla en al base de datos.
     * @throws SQLException Salta una exception si la sentencia SQL falla.
     */
    public void createTable() throws SQLException {

        Statement sts = connection.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS games("+
                "game_name VARCHAR(50) PRIMARY KEY not null,\n" +
                "map_name VARCHAR(50),\n" +
                "player1 VARCHAR(50),\n" +
                "player2 VARCHAR(50),\n" +
                "FOREIGN KEY(map_name) REFERENCES map(map_name) ON DELETE CASCADE,\n" +
                "FOREIGN KEY (player1) REFERENCES player(player_name) ON DELETE CASCADE,\n" +
                "FOREIGN KEY (player1) REFERENCES player(player_name) ON DELETE CASCADE\n)";

        sts.executeUpdate(sql);
    }

    /**
     * método para leer los ficheros CSV y hacer un select basándose en los datos que a leido.
     * @throws IOException Salta la exception si no puede leer el fichero.
     * @throws SQLException Salta una exception si la sentencia SQL falla.
     */
    public void readDataFromCSV() throws IOException, SQLException {
        CSVReader reader = new CSVReader(new FileReader("resources/games.csv"));
        String[] data = null;
        reader.readNext();
        while ((data = reader.readNext()) !=null){
            String game_name = data[0];
            String map_name = data[1];
            String players[] = data[2].split(",");
            String player1 = players[0];
            String player2 = players[1];

            String sql = "INSERT INTO games(game_name, map_name, player1, player2) VALUES (?,?,?,?)";

            PreparedStatement pst = connection.prepareStatement(sql);

            pst.setString(1, game_name);
            pst.setString(2, map_name);
            pst.setString(3, player1);
            pst.setString(4, player2);

            pst.executeUpdate();

        }

    }

}
