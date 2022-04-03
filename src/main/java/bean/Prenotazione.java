package bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Prenotazione {

    private String cognome;
    private int numPersone;
    private String cell;
    private Date date;
    private int numTavolo;
}
