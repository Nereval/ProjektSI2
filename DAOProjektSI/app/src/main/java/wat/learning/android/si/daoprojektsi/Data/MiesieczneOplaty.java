package wat.learning.android.si.daoprojektsi.Data;

public class MiesieczneOplaty {

    final String nazwaMedia;
    final float cena;
    final String jednostka;
    final float wartoscOdczytu;
    final int nrMieszkania;
    final String dataOdczytu;
    final float wartosc;


    public MiesieczneOplaty(String nazwaMedia, float cena, String jednostka, float wartoscOdczytu, int nrMieszkania, String dataOdczytu) {
        this.nazwaMedia = nazwaMedia;
        this.cena = cena;
        this.jednostka = jednostka;
        this.wartoscOdczytu = wartoscOdczytu;
        this.nrMieszkania = nrMieszkania;
        this.dataOdczytu = dataOdczytu;
        wartosc = cena * wartoscOdczytu;
    }


    public String getNazwaMedia() {
        return nazwaMedia;
    }

    public float getCena() {
        return cena;
    }

    public String getJednostka() {
        return jednostka;
    }

    public float getWartoscOdczytu() {
        return wartoscOdczytu;
    }

    public int getNrMieszkania() {
        return nrMieszkania;
    }

    public String getDataOdczytu() {
        return dataOdczytu;
    }

    public float getWartosc() {
        return wartosc;
    }

}
