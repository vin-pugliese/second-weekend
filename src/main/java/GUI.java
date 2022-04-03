import bean.Prenotazione;
import bean.Tavolo;
import crud.Prenotazione_CRUD;
import crud.Tavolo_CRUD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class GUI {

    Scanner sc = new Scanner(System.in);

    public void execute() {

        Prenotazione p = new Prenotazione();
        Tavolo t = new Tavolo();


        Prenotazione_CRUD pc = new Prenotazione_CRUD();
        Tavolo_CRUD tc = new Tavolo_CRUD();

        int x;
        do {
            System.out.println("\nScegli l'operazione: \n" +
                    "1 - Mostra tutti i tavoli\n" +
                    "2 - Mostra tutte le prenotazioni\n" +
                    "3 - Modifica tavolo\n" +
                    "4 - Modifica Prenotazione\n" +
                    "5 - Inserisci tavolo\n" +
                    "6 - Inserisci Prenotazione\n" +
                    "7 - Elimina tavolo\n" +
                    "8 - Elimina Prenotazione\n" +
                    "-1 - esci\n");
            x = sc.nextInt();
            switch (x) {
                case 1:
                    tc.showAll();
                    break;
                case 2:
                    pc.showAll();
                    break;
                case 3:
                    System.out.println("Inserisci il numero del tavolo da modificare");
                    t.setNumero(sc.nextInt());
                    sc.nextLine();      //workaround that prevents scanner from skipping inputs
                    System.out.println("Inserisci capienza");
                    t.setCapienza(sc.nextInt());
                    tc.update(t);
                    break;
                case 4:
                    System.out.println("Inserisci il cognome della prenotazione da modificare");
                    p.setCognome(sc.nextLine());
                    p = this.prenotazioneQuestions(p);
                    pc.update(p);
                    break;
                case 5:
                    sc.nextLine();      //workaround that prevents scanner from skipping inputs
                    System.out.println("Inserisci capienza");
                    t.setCapienza(sc.nextInt());
                    tc.insert(t);
                    break;
                case 6:
                    System.out.println("Inserisci il cognome della prenotazione da modificare");
                    p.setCognome(sc.nextLine());
                    p = this.prenotazioneQuestions(p);
                    pc.insert(p);
                    break;
                case 7:
                    System.out.println("Inserisci il numero del tavolo da eliminare");
                    tc.delete(sc.nextInt());
                    break;
                case 8:
                    System.out.println("Inserisci il cognome della prenotazione da eliminare:");
                    pc.delete(sc.nextLine());
                    break;
                case -1:
                    System.exit(0);
            }
        } while (x != -1);

    }


    private Prenotazione prenotazioneQuestions(Prenotazione x) {
        System.out.println("inserisci numero persone");
        x.setNumPersone(sc.nextInt());

        System.out.println("Inserisci cellulare");
        x.setCell(sc.nextLine());

        System.out.println("Inserisci data");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");        /////////////
        try {
            String s = sc.nextLine();
            Date date = sdf.parse(s);
            x.setDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("Inserisci numero tavolo");
        x.setNumTavolo(sc.nextInt());

        return x;
    }
}


