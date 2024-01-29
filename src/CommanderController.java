import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommanderController {

    private Connection connection;

    public CommanderController(Connection connection) {this.connection = connection;}

    public void createTable() throws SQLException {

        Statement sts = connection.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS commander("+
                "commander_name VARCHAR(20) PRIMARY KEY not null)";

        sts.executeUpdate(sql);
    }


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
/*
    public boolean tableExists(Connection connection, String tableName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            // Use a ResultSet to check if the table exists
            String checkTableSQL = "select tablename from pg_catalog.pg_tables where schemaname='public' and tablename='" + tableName + "'";
            return statement.executeQuery(checkTableSQL).next();
        }
    }

 */


}
