import java.io.IOException;
import java.sql.SQLException;

import bean.Prenotazione;
import bean.Tavolo;
import utils.DBUtils;

import java.util.ArrayList;
import java.util.Date;

public class Prenotazione_CRUD extends DBUtils {

    public Prenotazione_CRUD() {
        try {
            rp2.read("sql.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void insert(Prenotazione x) {

        int y = disponibilitaTavolo(x);
        if (y!=0) {
            try {
                conn = this.startConnection();

                ps = conn.prepareStatement(rp2.getProperties().getProperty("insertPrenotazione"));

                ps.setString(1, x.getCognome());
                ps.setInt(2, x.getNumPersone());
                ps.setString(3, x.getCell());
                ps.setDate(4, getSQLDate(x.getDate()));
                ps.setInt(5, y);

                if (ps.executeUpdate() != 0) L.info("Aggiunto " + x.getCognome());
                else L.info("non aggiunto");

                ps.clearParameters();

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            } finally {
                this.closeAll();
            }
        } else System.out.println("Impossibile aggiungere prenotazione");
    }

    public void update(Prenotazione x){

        try {
            conn = this.startConnection();

            ps = conn.prepareStatement(rp.getProperties().getProperty("updatePrenotazione"));

            ps.setString(1, x.getCognome());
            ps.setInt(2, x.getNumPersone());
            ps.setString(3, x.getCell());
            ps.setDate(4, getSQLDate(x.getDate()));
            ps.setInt(5, x.getNumTavolo());

            if (ps.executeUpdate() != 0) L.info("Prenotazione:" + x.getCognome() + " modificato");
            else L.info("prenotazione:" + x.getCognome() + " non modificato");

            ps.clearParameters();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            this.closeAll();
        }
    }

    public void delete(String cognome){
        try {
            conn = this.startConnection();

            ps = conn.prepareStatement(rp.getProperties().getProperty("deletePrenotazione"));
            ps.setString(1, cognome);

            if (ps.executeUpdate() != 0) L.info("Eliminato " +cognome);
            else L.info(cognome +"non Eliminato");

            ps.clearParameters();


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            this.closeAll();
        }
    }

    public void showAll(){
        try {
            conn = this.startConnection();

            statement = conn.createStatement();

            rs = statement.executeQuery(rp.getProperties().getProperty("selectPrenotazione"));

            this.printer(rs);
            rs.close();
        } catch (IOException | SQLException e) {
            e.getMessage();
        } finally {
            this.closeAll();
        }

    }


    private int disponibilitaTavolo(Prenotazione x){

        ArrayList<Tavolo> tavoli = new ArrayList<Tavolo>();
        try {
            conn = this.startConnection();
            ps = conn.prepareStatement("SELECT * FROM tavolo WHERE capienza >=" + x.getNumPersone());

            rs = ps.executeQuery();

            while(rs.next()) {
                Tavolo tmp = new Tavolo(rs.getInt(1), rs.getInt(2));
                tavoli.add(tmp);
            }
            rs.close();

            ps.clearParameters();
            ps = conn.prepareStatement("SELECT * FROM restaurant.prenotazione inner join restaurant.tavolo on prenotazione.numerotavolo = tavolo.numero WHERE tavolo.capienza >=" +x.getNumPersone() + "AND prenotazione.data = " + x.getDate());
            rs = ps.executeQuery();

            for(int i=0; rs.next(); i++){
                if(tavoli.get(i).getNumero() == rs.getInt(5))
                    tavoli.remove(i);
            }

            if (tavoli.isEmpty()) return 0;
            else {
               Tavolo res = tavoli.get(tavoli.size());
               return res.getNumero();
            }


        } catch(IOException | SQLException e) {
            e.printStackTrace();
        }

       return 0;
    }



    /**
     * @param date
     * @return
     * @Method getSQLDate
     * casts a java.util.date into a SQL.date in order to execute CRUD operations
     */
    private java.sql.Date getSQLDate(Date date) {
        long timeInMilliSeconds = date.getTime();
        java.sql.Date date1 = new java.sql.Date(timeInMilliSeconds);
        return date1;
    }
}
