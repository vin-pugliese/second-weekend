package utils;

import bean.Prenotazione;
import bean.Tavolo;

import java.io.IOException;
import java.sql.SQLException;

public class Tavolo_CRUD extends DBUtils {

    public Tavolo_CRUD(){
        try {
            rp2.read("sql.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insert(Tavolo x) {
            try {
                conn = this.startConnection();

                ps = conn.prepareStatement(rp2.getProperties().getProperty("insertTavolo"));

                ps.setInt(1, x.getCapienza());

                if (ps.executeUpdate() != 0) L.info("Aggiunto " + x.getNumero());
                else L.info("non aggiunto");

                ps.clearParameters();

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            } finally {
                this.closeAll();
            }

    }

    public void update(Tavolo x){

        try {
            conn = this.startConnection();

            ps = conn.prepareStatement(rp.getProperties().getProperty("updatePrenotazione"));

            ps.setInt(5, x.getCapienza());

            if (ps.executeUpdate() != 0) L.info("Tavolo "+x.getNumero() +" aggiornato");
            else L.info("Tavolo "+x.getNumero() +" non aggiornato");

            ps.clearParameters();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            this.closeAll();
        }
    }


}
