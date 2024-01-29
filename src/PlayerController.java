import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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


public class PlayerController {

    private Connection connection;

    public PlayerController(Connection connection) {
        this.connection = connection;
    }

    public void createTable() throws SQLException {

        Statement sts = connection.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS player("+
                "player_name   VARCHAR(50) PRIMARY KEY not null,\n" +
                "last_activity VARCHAR(50) not null,\n" +
                "official_ratting  VARCHAR(20) not null,\n" +
                "wld   VARCHAR(50) not null,\n" +
                "commander VARCHAR(20),\n" +
                "winrate INTEGER not null,\n" +
                "FOREIGN KEY(commander) REFERENCES commander(commander_name)\n)";

        sts.executeUpdate(sql);
    }

    public void readDataFromCSV() throws IOException, SQLException {
        CSVReader reader = new CSVReader(new FileReader("resources/player.csv"));
        String[] data = null;
        reader.readNext();
        while ((data = reader.readNext()) != null) {
            String player_name = data[0];
            String last_activity = data[1];
            String official_ratting = data[2];
            String WLD = data[3];
            String[] temporal = data[4].replaceAll(",", "").split(" ");
            String commander = temporal[0].replaceFirst("\\[", "").replaceFirst("]","");
            int winrate = 0;
            Pattern patron = Pattern.compile("\\d+");
            Matcher matcher = patron.matcher(temporal[2]);

            if (matcher.find()) {
                String temp = matcher.group();
                winrate = Integer.parseInt(temp);
            }

            System.out.println(commander);

            String sql = "INSERT INTO player(player_name, last_activity, official_ratting, wld, commander, winrate) VALUES (?,?,?,?,?,?)";

            PreparedStatement pst = connection.prepareStatement(sql);

            pst.setString(1, player_name);
            pst.setString(2, last_activity);
            pst.setString(3, official_ratting);
            pst.setString(4, WLD);
            pst.setString(5, commander);
            pst.setInt(6, winrate);

            pst.executeUpdate();

        }


    }

}
