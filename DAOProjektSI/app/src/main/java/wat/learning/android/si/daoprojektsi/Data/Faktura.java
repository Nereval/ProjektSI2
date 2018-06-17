package wat.learning.android.si.daoprojektsi.Data;

public class Faktura {
    final int idFaktury;

    public int getIdFaktury() {
        return idFaktury;
    }

    public String getNrFaktury() {
        return nrFaktury;
    }

    public String getPodmiotWystawiajacy() {
        return podmiotWystawiajacy;
    }

    public String getTerminPlatnosci() {
        return terminPlatnosci;
    }

    public String getDataWystawienia() {
        return dataWystawienia;
    }

    public float getKwotaFaktury() {
        return kwotaFaktury;
    }

    public String getOpisFaktury() {
        return opisFaktury;
    }

    final String nrFaktury;
    final String podmiotWystawiajacy;
    final String terminPlatnosci;
    final String dataWystawienia;
    final float kwotaFaktury;
    final String opisFaktury;

    public Faktura(int idFaktury, String nrFaktury, String podmiotWystawiajacy, String terminPlatnosci, String dataWystawienia, float kwotaFaktury, String opisFaktury) {
        this.idFaktury = idFaktury;
        this.nrFaktury = nrFaktury;
        this.podmiotWystawiajacy = podmiotWystawiajacy;
        this.terminPlatnosci = terminPlatnosci;
        this.dataWystawienia = dataWystawienia;
        this.kwotaFaktury = kwotaFaktury;
        this.opisFaktury = opisFaktury;
    }
}
