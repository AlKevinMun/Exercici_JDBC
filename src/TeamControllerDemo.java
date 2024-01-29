
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class TeamControllerDemo {

    private Connection connection;

    public TeamControllerDemo(Connection connection) {
        this.connection = connection;
    }

    public void showTeams() throws SQLException, IOException {

        Statement st = connection.createStatement();
        ResultSet rs;

        rs = st.executeQuery("SELECT * FROM team");
        while (rs.next()) {
            System.out.println("Name: " + rs.getString("name") + " " +
                    "Type: " + rs.getString("type") + " " +
                    "Country: " + rs.getString("country") + " " +
                    "City: " + rs.getString("city") + " " +
                    "Court name: " + rs.getString("court_name"));
        }

        rs.close();
        st.close();
    }

    public void showTeamPlayers() throws SQLException, IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Statement st = connection.createStatement();
        ResultSet rs;

        rs = st.executeQuery("SELECT * FROM team");
        while (rs.next()) {
            System.out.println(rs.getString("type") + ": " + rs.getString("name") + " country: " + rs.getString("country") + " city:  " + rs.getString("city") + " Court: " + rs.getString("court_name"));
        }

        System.out.println("\nSeleccioni un dels equips llistats per veure la seva plantilla. ");
        String equip = br.readLine();

        rs = st.executeQuery("SELECT * FROM player WHERE team_name LIKE '" + equip + "'");

        while (rs.next()) {
            System.out.println("num: " + rs.getString(1) + " Player: " + rs.getString(2) + "  " + rs.getString(3) + " birthdate:  " + rs.getDate(4) + " sex: " + rs.getString(5) + " hight: " + rs.getInt(6) + " team: " + rs.getString(7) + " mvp_total: " + rs.getInt(8));
        }

        rs.close();
        st.close();
        br.close();
    }

    public void addTeam() throws SQLException, IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Inserta nom: ");
        String nom = br.readLine();
        System.out.println("Inserta tipus: ");
        String tipus = br.readLine();
        System.out.println("Inserta pais: ");
        String pais = br.readLine();
        System.out.println("Inserta ciutat: ");
        String ciutat = br.readLine();
        System.out.println("Inserta nom del camp: ");
        String nomCamp = br.readLine();

        String sql = "INSERT INTO team (name, type, country, city, court_name) VALUES (?,?,?,?,?)";

        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, nom);
        pst.setString(2, tipus);
        pst.setString(3, pais);
        pst.setString(4, ciutat);
        pst.setString(5, nomCamp);
        pst.executeUpdate();

    }
}
