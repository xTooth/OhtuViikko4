package ohtu.verkkokauppa;

import java.util.ArrayList;

public interface KirjanpitoI {


    void lisaaTapahtuma(String tapahtuma);

    ArrayList<String> getTapahtumat();
}
