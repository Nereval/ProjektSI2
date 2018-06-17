package wat.learning.android.si.daoprojektsi.Data;

public class Media {
    final String nazwaMedia;
    final int idMedia;
    final int idCeny;

    public int getIdCeny() {
        return idCeny;
    }

    final float cena;

    public int getNrMieszkania() {
        return nrMieszkania;
    }

    final int nrMieszkania;

    public String getNazwaMedia() {
        return nazwaMedia;
    }

    public int getIdMedia() {
        return idMedia;
    }

    public Media(int idMedia, String nazwaMedia, int idCeny, float cena, int nrMieszkania) {
        this.idMedia = idMedia;
        this.nazwaMedia = nazwaMedia;
        this.idCeny = idCeny;
        this.cena = cena;
        this.nrMieszkania = nrMieszkania;
    }
}
