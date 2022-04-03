import utils.DBUtils;

import java.io.IOException;
import java.sql.SQLException;

public class DBCreator extends DBUtils {

    public void execute() {
        try {
            //creating schema market
            conn = this.newSchemaConnection();
            statement = conn.createStatement();

            rp2.read("sql.properties");
            String sql = rp2.getProperties().getProperty("newSchema");

            statement.executeUpdate(sql);
            L.info("Database restaurant creato");

            conn.close();
            statement.close();


            //creating table tavolo
            conn = this.startConnection();

            String sql2 = rp2.getProperties().getProperty("createTavolo");

            statement = conn.createStatement();
            statement.executeUpdate(sql2);
            L.info("tabella tavolo creata");
            statement.close();

            //creating table prenotazioni
            String sql3 = rp2.getProperties().getProperty("createPrenotazione");

            statement = conn.createStatement();
            statement.executeUpdate(sql3);
            L.info("tabella prenotazione creata");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            this.closeAll();
        }
    }
}
