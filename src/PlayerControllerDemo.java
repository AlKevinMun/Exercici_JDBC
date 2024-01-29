import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerControllerDemo {

    private Connection connection;

    public PlayerControllerDemo(Connection connection) {
        this.connection = connection;
    }

    public void addPlayer() throws SQLException, NumberFormatException, IOException, ParseException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Inserta federació: ");
        String federacio = br.readLine();
        System.out.println("Inserta nom: ");
        String nom = br.readLine();
        System.out.println("Inserta cognom: ");
        String cognom = br.readLine();
        System.out.println("Inserta data naixement: ");
        String data = br.readLine();
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        Date naixement = df.parse(data);
        java.sql.Date sDate = new java.sql.Date(naixement.getTime());

        System.out.println("Inserta sexe: ");
        String sexe = br.readLine();
        System.out.println("Inserta altura: ");
        int altura = Integer.parseInt(br.readLine());
        System.out.println("Inserta nom equip: ");
        String equip = br.readLine();
        System.out.println("Inserta quantitat mvp: ");
        int mvp = Integer.parseInt(br.readLine());

        String sql = "INSERT INTO player(federation_license_code, first_name, last_name,birth_date, gender, height, team_name, mvp_total) VALUES (?,?,?,?,?,?,?,?)";

        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setString(1, federacio);
        pst.setString(2, nom);
        pst.setString(3, cognom);
        pst.setDate(4, sDate);

        pst.setString(5, sexe);
        pst.setInt(6, altura);
        pst.setString(7, equip);
        pst.setInt(8, mvp);

        pst.executeUpdate();

    }

    public void signing() throws SQLException {

        ResultSet rs = null;
        Statement st = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        try {
            System.out.println("Inserti jugador a traspasar: ");
            String jugador = br.readLine();

            rs = st.executeQuery("SELECT * FROM player WHERE first_name LIKE '" + jugador + "'");

            if (rs == null) {
                System.out.println("No hi ha jugadors amb aquest nom. ");
            } else {

                while (rs.next()) {
                    System.out.println("Jugador: " + rs.getString(2) + " Equip actual: " + rs.getString(7));
                    System.out.println("Vol traspasar aquest jugador?");
                    String resposta = br.readLine();

                    if (resposta.equals("si")) {
                        System.out.println("Seleccioni l'equip pel que fitxa: ");
                        String equip = br.readLine();
                        rs.updateString("team_name", equip);
                        rs.updateRow();
                    }
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void rescission() throws SQLException, IOException {

        ResultSet rs = null;
        Statement st = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        System.out.println("Inserti jugador a rescindir contracte: ");
        String jugador = br.readLine();

        rs = st.executeQuery("SELECT * FROM player WHERE first_name LIKE '" + jugador + "'");

        if (rs == null) {
            System.out.println("No existeix el jugador. ");
        } else {
            while (rs.next()) {
                System.out.println("Jugador: " + rs.getString(2) + "Equip actual: " + rs.getString(7));
                System.out.println("Estar segur que vol rescindir el contracte? ");
                String resposta = br.readLine();
                if (resposta.equals("si")) {
                    rs.updateNull(7);
                    rs.updateRow();
                } else {
                    System.out.println("Operació cancelada. ");
                }
            }
        }
    }

}
