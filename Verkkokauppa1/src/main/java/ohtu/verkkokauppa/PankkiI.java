package ohtu.verkkokauppa;

    public interface PankkiI{

        boolean tilisiirto(String nimi, int viitenumero, String tililta, String tilille, int summa);


    }