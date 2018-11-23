package ohtu.verkkokauppa;

    public interface VarastoI{

        Tuote haeTuote(int id);
        int saldo(int id);
        void otaVarastosta(Tuote t);
        void palautaVarastoon(Tuote t);

    }