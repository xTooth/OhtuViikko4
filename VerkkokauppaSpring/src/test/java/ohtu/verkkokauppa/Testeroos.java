package ohtu.verkkokauppa;

import org.junit.*;
import static org.mockito.Mockito.*;

public class Testeroos {

    private Pankki p;
    private Viitegeneraattori v;
    private Varasto var;

    @Before
    public void setUp() {
        // luodaan ensin mock-oliot
        p = mock(Pankki.class);
        v = mock(Viitegeneraattori.class);
        var = mock(Varasto.class);
    }

    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {

        // määritellään että viitegeneraattori palauttaa viitten 42
        when(v.uusi()).thenReturn(42);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(var.saldo(1)).thenReturn(10);
        when(var.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        // sitten testattava kauppa 
        Kauppa k = new Kauppa(var, p, v);
        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(p).tilisiirto("pekka", 42, "12345", "33333-44455", 5);
        // toistaiseksi ei välitetty kutsussa käytetyistä parametreista
    }

    @Test
    public void kahdenErituotteenOstoToimii() {

        when(v.uusi()).thenReturn(43);
        when(var.saldo(1)).thenReturn(10);
        when(var.saldo(2)).thenReturn(20);
        when(var.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(var.haeTuote(2)).thenReturn(new Tuote(2, "mehu", 10));
        Kauppa k = new Kauppa(var, p, v);
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("jussi", "54321");
        verify(p).tilisiirto("jussi", 43, "54321", "33333-44455", 15);
    }

    @Test
    public void kahdenSamanTuotteenOstoToimii() {
        when(v.uusi()).thenReturn(43);
        when(var.saldo(1)).thenReturn(10);
        when(var.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        Kauppa k = new Kauppa(var, p, v);
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(1);
        k.tilimaksu("jussi", "54321");
        verify(p).tilisiirto("jussi", 43, "54321", "33333-44455", 10);
    }

    @Test
    public void kahdenTuotteenOstoJoistaToinenLoppu() {

        when(v.uusi()).thenReturn(43);
        when(var.saldo(1)).thenReturn(0);
        when(var.saldo(2)).thenReturn(20);
        when(var.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(var.haeTuote(2)).thenReturn(new Tuote(2, "mehu", 10));
        Kauppa k = new Kauppa(var, p, v);
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("jussi", "54321");
        verify(p).tilisiirto("jussi", 43, "54321", "33333-44455", 10);

    }

}
