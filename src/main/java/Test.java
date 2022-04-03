import bean.Prenotazione;
import bean.Tavolo;
import crud.Prenotazione_CRUD;
import crud.Tavolo_CRUD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Test {


    public static void main(String[] args) {
        DBCreator dbc = new DBCreator();
        GUI g = new GUI();

        dbc.execute();
        g.execute();

    }
}
