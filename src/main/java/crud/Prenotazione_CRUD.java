package crud;

import java.io.*;
import java.sql.ResultSet;
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

            ps = conn.prepareStatement(rp2.getProperties().getProperty("updatePrenotazione"));

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

            ps = conn.prepareStatement(rp2.getProperties().getProperty("deletePrenotazione"));
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

            rs = statement.executeQuery(rp2.getProperties().getProperty("selectPrenotazione"));

            this.printer(rs);
            rs.close();
        } catch (IOException | SQLException e) {
            e.getMessage();
        } finally {
            this.closeAll();
        }

    }


    private int disponibilitaTavolo(Prenotazione x){

        ArrayList<Tavolo> tavoli = getTables(x);
        try {
            conn = this.startConnection();
            java.sql.Date sqlDate = new java.sql.Date(x.getDate().getTime());
            ps = conn.prepareStatement("SELECT * FROM restaurant.prenotazione inner join restaurant.tavolo on prenotazione.numerotavolo = tavolo.numero WHERE tavolo.capienza = " +x.getNumPersone() + " AND prenotazione.datas = '" +sqlDate +"' ;");
            rs = ps.executeQuery();

            while(rs.next()) {
                for (int i = 0; i < tavoli.size(); i++) {
                    if (tavoli.get(i).getNumero() == rs.getInt(6)) {
                        tavoli.remove(i);
                        i -= 1;
                    }
                }
            }

            if (tavoli.isEmpty()) return 0;
            else {
               Tavolo res = tavoli.get(0);
               return res.getNumero();
            }

        } catch(IOException | SQLException e) {
            e.printStackTrace();
        }

       return 0;
    }



    private ArrayList<Tavolo> getTables(Prenotazione x){

        ResultSet rs3 = null;
        ArrayList<Tavolo> tavoli = new ArrayList<Tavolo>();
        try {

            conn = this.startConnection();
            ps = conn.prepareStatement("SELECT * FROM restaurant.tavolo WHERE tavolo.capienza =" + x.getNumPersone() + " ;");

            rs3 = ps.executeQuery();

            while (rs3.next()) {
                Tavolo tmp = new Tavolo();
                tmp.setNumero(rs3.getInt(1));
                tmp.setCapienza(rs3.getInt(2));
                tavoli.add(tmp);
            }
            for (Tavolo tav : tavoli)
                System.out.println(tav.toString());

            ps.clearParameters();
        } catch(IOException | SQLException e){
            e.printStackTrace();
        } finally {this.closeAll();}
        return tavoli;
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


    public void printOnFile(){
        try {
            File x = new File("src/main/resources/prenotazioni.txt");
            FileWriter fileWriter = new FileWriter(x, true);
            PrintWriter outputStream = new PrintWriter(fileWriter);
            outputStream.println(String.format("%-20s %-20s %-20s %-20s %-20s", "Cognome", "N. persone", "recapito", "data", "num. tavolo"));

            conn = this.startConnection();

            statement = conn.createStatement();

            rs = statement.executeQuery(rp2.getProperties().getProperty("selectPrenotazione"));

            while(rs.next()){
                outputStream.println(String.format("%-20s %-20s %-20s %-20s %-20s", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }

            fileWriter.flush();
            fileWriter.close();

            rs.close();
        } catch (IOException | SQLException e) {
            e.getMessage();
        } finally {
            this.closeAll();
        }





    }


}
