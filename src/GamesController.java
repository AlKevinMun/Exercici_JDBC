import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class GamesController {
    private Connection connection;

    public GamesController(Connection connection) {this.connection = connection;}

    public void createTable() throws SQLException {

        Statement sts = connection.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS games("+
                "game_name VARCHAR(50) PRIMARY KEY not null,\n" +
                "map_name VARCHAR(50),\n" +
                "player1 VARCHAR(50),\n" +
                "player2 VARCHAR(50),\n" +
                "FOREIGN KEY(map_name) REFERENCES map(map_name),\n" +
                "FOREIGN KEY (player1) REFERENCES player(player_name),\n" +
                "FOREIGN KEY (player1) REFERENCES player(player_name)\n)";

        sts.executeUpdate(sql);
    }

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
