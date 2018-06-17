package wat.learning.android.si.daoprojektsi.Data;

public class ListaSprzetu {
    final int IdSprzetu;
    final int IdLokatora;
    final String NazwaSprzetu;
    final String OpisSprzetu;
    final String DataZakupu;
    final String KoniecGwarancji;
    final String DataWycofania;
    final String PrzyczynaWycofania;
    final String Nazwisko;

    public ListaSprzetu(int IdSprzetu, int IdLokatora, String nazwaSprzetu, String opisSprzetu, String dataZakupu, String koniecGwarancji, String dataWycofania, String przyczynaWycofania, String nazwisko){
        this.IdSprzetu = IdSprzetu;
        this.IdLokatora = IdLokatora;
        this.NazwaSprzetu = nazwaSprzetu;
        this.OpisSprzetu = opisSprzetu;
        this.DataZakupu = dataZakupu;
        this.KoniecGwarancji = koniecGwarancji;
        this.DataWycofania = dataWycofania;
        this.PrzyczynaWycofania = przyczynaWycofania;
        this.Nazwisko = nazwisko;
    }


    public int getIdSprzetu() {
        return IdSprzetu;
    }

    public int getIdLokatora() {
        return IdLokatora;
    }

    public String getNazwaSprzetu() {
        return NazwaSprzetu;
    }

    public String getOpisSprzetu() {
        return OpisSprzetu;
    }

    public String getDataZakupu() {
        return DataZakupu;
    }

    public String getKoniecGwarancji() {
        return KoniecGwarancji;
    }

    public String getDataWycofania() {
        return DataWycofania;
    }

    public String getPrzyczynaWycofania() {
        return PrzyczynaWycofania;
    }

    public String getNazwisko() {
        return Nazwisko;
    }
}
