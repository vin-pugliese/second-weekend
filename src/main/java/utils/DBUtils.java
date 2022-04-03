package utils;

import java.io.IOException;
import java.sql.*;

public class DBUtils {

    public static Log L = Log.getInstance();
    public ReadProperties rp = new ReadProperties();
    public ReadProperties rp2 = new ReadProperties();
    public Statement statement = null;
    public PreparedStatement ps = null;
    public ResultSet rs = null;
    public Connection conn = null;

    /**
     * Constructor
     */
    public DBUtils(){
        try {
            rp.read("login.properties");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Inizializza una nuova connessione prendendo l'url del database dal file passato come parametro

     * @return
     * @throws IOException
     */
    public Connection startConnection() throws IOException {
        Connection conn = null;
        try {
            Class.forName(rp.getDbdriver()).newInstance();
            conn = DriverManager.getConnection(rp2.getDburl(), rp.getUser(), rp.getPsw());
            L.info("Connection with database established");
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * creates connection for a new schema
     * @return
     * @throws IOException
     */
    public Connection newSchemaConnection() throws IOException {
        Connection conn = null;
        try {
            Class.forName(rp.getDbdriver()).newInstance();
            conn = DriverManager.getConnection(rp.getProperties().getProperty("newschemaUrl"), rp.getUser(), rp.getPsw());
            L.info("Connection with database established");
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * @method printer
     * stampa tutti i dati contenuti nel ResulSet passato come parametro
     * @param rs
     */
    public void printer(ResultSet rs) throws SQLException {
        try {
            ResultSetMetaData md = rs.getMetaData();

            while (rs.next()) {
                for (int i = 1; i <= md.getColumnCount(); i++)
                    System.out.print(rs.getString(i) +"\t\t");

                System.out.println("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally { if(rs!=null) rs.close(); }
    }

    /**
     * @method closeAll
     * closes all connections - statements - preparedStaments
     */
    public void closeAll() {
        try {
            if(statement != null) statement.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
