package wat.learning.android.si.daoprojektsi.Data;

public class ObrotNaKoncie {
    public int getIdObrotu() {
        return idObrotu;
    }

    public int getIdFaktury() {
        return idFaktury;
    }

    public int getIdKategoriiObrotu() {
        return idKategoriiObrotu;
    }

    public String getDataDodania() {
        return dataDodania;
    }

    public String getDataZaksiegowania() {
        return dataZaksiegowania;
    }

    public String getTytulObrotu() {
        return tytulObrotu;
    }

    public String getOpisObrotu() {
        return opisObrotu;
    }

    public float getKwota() {
        return kwota;
    }

    final int idObrotu;
    final int idFaktury;
    final int idKategoriiObrotu;
    final String dataDodania;
    final String dataZaksiegowania;
    final String tytulObrotu;
    final String opisObrotu;
    final float kwota;

    public ObrotNaKoncie(int idObrotu, int idFaktury, int idKategoriiObrotu, String dataDodania, String dataZaksiegowania, String tytulObrotu, String opisObrotu, float kwota) {
        this.idObrotu = idObrotu;
        this.idFaktury = idFaktury;
        this.idKategoriiObrotu = idKategoriiObrotu;
        this.dataDodania = dataDodania;
        this.dataZaksiegowania = dataZaksiegowania;
        this.tytulObrotu = tytulObrotu;
        this.opisObrotu = opisObrotu;
        this.kwota = kwota;
    }
}
