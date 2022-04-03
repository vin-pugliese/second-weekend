import crud.DBCreator;

public class Test {


    public static void main(String[] args) {
        DBCreator dbc = new DBCreator();
        GUI g = new GUI();

        dbc.execute();
        g.execute();

    }
}
