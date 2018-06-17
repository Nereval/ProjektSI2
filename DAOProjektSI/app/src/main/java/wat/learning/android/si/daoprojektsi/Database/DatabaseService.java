package wat.learning.android.si.daoprojektsi.Database;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import wat.learning.android.si.daoprojektsi.ActionCode;
import wat.learning.android.si.daoprojektsi.Data.Faktura;
import wat.learning.android.si.daoprojektsi.Data.ListaSprzetu;
import wat.learning.android.si.daoprojektsi.Data.Media;
import wat.learning.android.si.daoprojektsi.Data.MiesieczneOplaty;
import wat.learning.android.si.daoprojektsi.Data.ObrotNaKoncie;

/**
 * Created by Piotr on 21.03.2018.
 */

public class DatabaseService extends IntentService implements Database  {

    private static final int LOGIN_SUCCESS = 0;
    private static final int LOGIN_FAILURE = 1;
    private static final int SEED_CONFIRMED = 2;
    private static final int PASSWORD_CHANGED = 3;
    private static final int PASSWORD_CHANGE_FAILED = 4;
    private static final int NEW_CONNECTION = 5;
    private static final int MEDIA_LIST = 6;
    private static final int MIESIĘCZNE_OPŁATY = 7;
    private static final int LISTA_SPRZĘTÓW = 8;
    private static final int OBROT_NA_KONCIE = 9;
    private static final int FAKTURY = 10;
    private static final int DODANO_ODCZYT = 11;

    private ResultReceiver resultReceiver;
    private Connection connection = null;
    private Statement stmt;
    private Bundle bundle;
    private ResultSet resultSet;
    private int lokatorId;

    public DatabaseService() {
        super("DatabaseService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        resultReceiver = intent.getParcelableExtra("receiverTag");
        ActionCode action_code = (ActionCode) intent.getSerializableExtra("code");

        if(action_code == ActionCode.CREATE_CONNECTION){
            createConnection();
        } else {
            String jsonConnection;
            jsonConnection = intent.getStringExtra("Connection");
            DatabaseConnection dbConn = new Gson().fromJson(jsonConnection, DatabaseConnection.class);
            connection = dbConn.getConnection();
        }

        String miesiac, rok;
        switch(action_code){
            case LOGIN:
                String email = intent.getStringExtra("email");
                String seed = intent.getStringExtra("seed");
                String hashPassword = intent.getStringExtra("hashPassword");
                UserLogin(email, seed, hashPassword);
                break;
            case PASSWORD_RESET:
                lokatorId = intent.getIntExtra("id", 0);
                String hashNewPassword = intent.getStringExtra("hashNewPassword");
                UserPasswordReset(lokatorId, hashNewPassword);
                break;
            case LOGOUT:
                lokatorId = intent.getIntExtra("id", 0);
                //UserLogout(lokatorId);
                break;
            case GET_MEDIA:
                lokatorId = intent.getIntExtra("id", 0);
                GetMediaFromDb();
                break;
            case MIESIĘCZNE_OPŁATY:
                lokatorId = intent.getIntExtra("id", 0);
                miesiac = intent.getStringExtra("miesiac");
                rok = intent.getStringExtra("rok");
                pobierzMiesięczneOpłaty(miesiac, rok);
                break;
            case LISTA_SPRZĘTÓW:
                pobierzListęSprzętów();
                break;
            case FAKTURY:
                pobierzFaktury();
                break;
            case OBROT_NA_KONCIE:
                pobierzObrotNaKoncie();
                break;
            case DODAJ_ODCZYT:
                lokatorId = intent.getIntExtra("id", 0);
                int media = intent.getIntExtra("media", 0);
                float odczyt = intent.getFloatExtra("odczyt", 0);
                String dataOdczytu = intent.getStringExtra("dataOdczytu");
                int idCeny = intent.getIntExtra("idCeny", 0);
                int nrMieszkania = intent.getIntExtra("nrMieszkania", 0);
                dodajOdczyt(media, odczyt, dataOdczytu,nrMieszkania, idCeny);

            default:
                break;
        }
    }

    private void createConnection() {
        String connectionURL = "jdbc:jtds:sqlserver://projektsi.database.windows.net:1433/projektSI;"
                + "user=pchorekLogin@projektsi;"
                + "password=1toJestSekretneHasloPchora2;"
                + "encrypt=true;"
                + "trustServerCertificate=false;"
                + "hostNameInCertificate=*.database.windows.net;"
                + "loginTimeout=30;";

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(connectionURL);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        bundle = new Bundle();
        bundle.putString("Connection", new Gson().toJson(new DatabaseConnection(connection)));
        resultReceiver.send(NEW_CONNECTION, bundle);
    }

    @Override
    public void UserLogin(String email, String seed, String hashPassword) {
        String userLoginQuery = "SELECT IdLokatora FROM Lokator WHERE Email=N'" + email + "' AND Haslo='" + hashPassword +"' AND Aktywny=1;";
        resultSet = getResultSet(userLoginQuery);
        try {
            if(resultSet.next()){
                int lokatorId = resultSet.getInt(1);
                if(lokatorId != 0){
                    //getResultSet("UPDATE Lokator SET Aktywny=True WHERE IdLokatora='"+lokatorId+"';");
                    bundle = new Bundle();
                    bundle.putInt("lokatorId", lokatorId);
                    resultReceiver.send(LOGIN_SUCCESS, bundle);
                    //clear();
                }
            } else {
                if(seed.length() == 16)
                    UserSeedCheck(email, seed);
                else {
                    bundle = new Bundle();
                    bundle.putString("Error", "Wrong email or password. Try again!");
                    resultReceiver.send(LOGIN_FAILURE, bundle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void UserSeedCheck(String email, String seed) {
        String userSeedQuery = "SELECT IdLokatora FROM Lokator WHERE Email=N'" + email + "' AND SeedResetowania='" + seed +"';";
        resultSet = getResultSet(userSeedQuery);
        try {
            if(resultSet.next()) {
                int lokatorId = resultSet.getInt(1);
                if (lokatorId != 0) {
                    bundle = new Bundle();
                    bundle.putInt("lokatorId", lokatorId);
                    resultReceiver.send(SEED_CONFIRMED, bundle);
                }
            } else {
                bundle = new Bundle();
                bundle.putString("Error", "Wrong email or password. Try again!");
                resultReceiver.send(LOGIN_FAILURE, bundle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //clear();
    }

    @Override
    public void UserPasswordReset(int lokatorId, String newPassword) {
        String userPasswordResetQuery = "UPDATE Lokator SET haslo='" + newPassword + "', SeedResetowania=NULL WHERE IdLokatora='"+ lokatorId +"';";
        resultSet = getResultSet(userPasswordResetQuery);
        String isUpdatedQuery = "Select haslo From Lokator WHERE IdLokatora='"+lokatorId+"';";
        resultSet = getResultSet(isUpdatedQuery);
        try {
            if (resultSet.next()){
                String changedPass = resultSet.getString(1);
                if(changedPass.equals(newPassword))
                    bundle = new Bundle();
                bundle.putString("msg", "Password changed!");
                resultReceiver.send(PASSWORD_CHANGED, bundle);
            } else {
                bundle = new Bundle();
                bundle.putString("Error", "Password change operation failed!");
                resultReceiver.send(PASSWORD_CHANGE_FAILED,bundle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void GetMediaFromDb() {

        List<Media> list = new ArrayList<>();
        String query = "SELECT S.IdMedia, S.NazwaMedia, max(S.IdCeny) as IdCeny, max(S.CenaJednostkowa) as cena, S.NrMieszkania as nr " +
                "FROM " +
                "(SELECT M.IdMedia, M.NazwaMedia, C.IdCeny, C.CenaJednostkowa, Z.NrMieszkania FROM Media M, Cena C, Zamieszkanie Z " +
                "WHERE C.IdMedia = M.IdMedia AND Z.IdLokatora = '"+lokatorId+"') as S " +
                "GROUP BY S.IdMedia, S.NazwaMedia, S.NrMieszkania";
        //String query = "SELECT DISTINCT M.IdMedia, M.NazwaMedia AS NazwaMedia, C.CenaJednostkowa as cena, Z.NrMieszkania AS nr From Media M, Cena C, Zamieszkanie Z  WHERE C.IdMedia = M.IdMedia AND Z.IdLokatora =" + lokatorId;
        resultSet = getResultSet(query);
        try {
            while (resultSet.next()){
                int idMedia = resultSet.getInt("IdMedia");
                String nazwaMedia = resultSet.getString("NazwaMedia");
                float cena = resultSet.getFloat("cena");
                int idCeny = resultSet.getInt("IdCeny");
                int nrMieszkania = resultSet.getInt("nr");

                Media media = new Media(idMedia, nazwaMedia, idCeny, cena, nrMieszkania);
                list.add(media);
            }
            bundle = new Bundle();
            bundle.putString("mediaList", new Gson().toJson(list));
            resultReceiver.send(MEDIA_LIST, bundle);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pobierzMiesięczneOpłaty(String miesiac, String rok) {
        List<MiesieczneOplaty> list = new ArrayList<>();

        String pobierzMiesięczneOpłątyQuery = "Select M.NazwaMedia AS Nazwa, C.CenaJednostkowa AS Cena, C.Jednostka AS Jednostka, O.WartoscOdczytu AS Odczyt, O.NrMieszkania AS Mieszkanie, DataOdczytu " +
                "FROM Odczyt O, Cena C, Media M " +
                "WHERE O.IdLokatora='" + lokatorId + "' AND O.IdCeny = C.IdCeny AND O.IdMedia = C.IdMedia AND C.IdMedia = M.IdMedia " +
                "AND DATEPART(mm,DataOdczytu)='" + miesiac +"' AND DATEPART(yyyy, O.DataOdczytu)='" + rok + "';";

        resultSet = getResultSet(pobierzMiesięczneOpłątyQuery);

        try {
            while (resultSet.next()){
                String nazwa = resultSet.getString("Nazwa");
                float cena = resultSet.getFloat("Cena");
                String jednostka = resultSet.getString("Jednostka");
                float odczyt = resultSet.getFloat("Odczyt");
                int mieszkanie = resultSet.getInt("Mieszkanie");
                String dataOdczytu = resultSet.getString("DataOdczytu");

                MiesieczneOplaty oplaty = new MiesieczneOplaty(nazwa, cena, jednostka, odczyt,mieszkanie,dataOdczytu);
                list.add(oplaty);
            }
            bundle = new Bundle();
            bundle.putString("miesięczneOpłaty", new Gson().toJson(list));
            resultReceiver.send(MIESIĘCZNE_OPŁATY, bundle);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void pobierzListęSprzętów() {

        List<ListaSprzetu> lista = new ArrayList<>();
        String query = "SELECT S.IdLokatora AS IdLokatora, IdSprzetu, NazwaSprzetu, OpisSprzetu, DataZakupu, KoniecGwarancji, DataWycofania, PrzyczynaWycofania, Nazwisko FROM Sprzet S " +
                "LEFT JOIN Lokator AS L ON S.IdLokatora = L.IdLokatora WHERE DataWycofania is not null AND PrzyczynaWycofania is not null";

        resultSet = getResultSet(query);

        try {
            while (resultSet.next()){

                int idLokatora = resultSet.getInt("IdLokatora");
                int idSprzetu = resultSet.getInt("IdSprzetu");
                String nazwaSprzetu = resultSet.getString("NazwaSprzetu");
                String nazwisko = resultSet.getString("Nazwisko");
                String opisSprzetu = resultSet.getString("OpisSprzetu");
                String dataZakupu = resultSet.getString("DataZakupu");
                String koniecGwarancji = resultSet.getString("KoniecGwarancji");
                String dataWycofania = resultSet.getString("DataWycofania");
                String przyczynaWycofania = resultSet.getString("PrzyczynaWycofania");

                ListaSprzetu listaSprzetu = new ListaSprzetu(idLokatora, idSprzetu, nazwaSprzetu, opisSprzetu, dataZakupu, koniecGwarancji, dataWycofania, przyczynaWycofania, nazwisko);
                lista.add(listaSprzetu);
            }
            bundle = new Bundle();
            bundle.putString("listaSprzetu", new Gson().toJson(lista));
            resultReceiver.send(LISTA_SPRZĘTÓW, bundle);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void pobierzObrotNaKoncie() {
        String query = "SELECT IdObrotu, IdFaktury, IdKateogriiObrotu AS IdKategoriiObrotu, DataDodania, DataZaksiegowania,TytulObrotu, OpisObortu AS OpisObrotu, Kwota " +
                "FROM ObrotNaKoncie";
        resultSet = getResultSet(query);
        List<ObrotNaKoncie> listaObrotu = new ArrayList<>();

        try {
            while (resultSet.next()){
                int idObrotu = resultSet.getInt("IdObrotu");
                int idFaktury = resultSet.getInt("IdFaktury");
                int idKategoriiObrotu= resultSet.getInt("IdKategoriiObrotu");
                String dataDodania = resultSet.getString("DataDodania");
                String dataZaksiegowania = resultSet.getString("DataZaksiegowania");
                String tytulObrotu = resultSet.getString("TytulObrotu");
                String opisObrotu = resultSet.getString("OpisObrotu");
                float kwota = resultSet.getFloat("Kwota");

                ObrotNaKoncie obrotNaKoncie = new ObrotNaKoncie(idObrotu, idFaktury, idKategoriiObrotu, dataDodania, dataZaksiegowania,tytulObrotu, opisObrotu, kwota);
                listaObrotu.add(obrotNaKoncie);
            }
            bundle = new Bundle();
            bundle.putString("obrotNaKoncie", new Gson().toJson(listaObrotu));
            resultReceiver.send(OBROT_NA_KONCIE, bundle);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void pobierzFaktury() {
        String query = "SELECT IdFaktury, NrFaktury, PodmiotWystawiajacy, TerminPlatnosci, DataWystawienia, KwotaFaktury, OpisFaktury " +
                "FROM FAKTURA";
        resultSet = getResultSet(query);
        List<Faktura> listaFaktur = new ArrayList<>();

        try {
            while (resultSet.next()){
                ArrayList<String> temp = new ArrayList<>();
                int IdFaktury = resultSet.getInt("IdFaktury");
                String NrFaktury = resultSet.getString("NrFaktury");
                String PodmiotWystawiajacy = resultSet.getString("PodmiotWystawiajacy");
                String TerminPlatnosci= resultSet.getString("TerminPlatnosci");
                String DataWystawienia = resultSet.getString("DataWystawienia");
                float KwotaFaktury = resultSet.getFloat("KwotaFaktury");
                String OpisFaktury = resultSet.getString("OpisFaktury");

                Faktura faktura = new Faktura(IdFaktury, NrFaktury, PodmiotWystawiajacy, TerminPlatnosci, DataWystawienia, KwotaFaktury, OpisFaktury);
                listaFaktur.add(faktura);
            }
            bundle = new Bundle();
            bundle.putString("faktury", new Gson().toJson(listaFaktur));
            resultReceiver.send(FAKTURY, bundle);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dodajOdczyt(int idMedia, float odczyt, String dataOdczytu, int nrMieszkania,int idCeny) {
        String query = "INSERT INTO Odczyt(IdMedia, IdLokatora, IdCeny, NrMieszkania, WartoscOdczytu, DataOdczytu)" +
                "VALUES('" + idMedia + "','" + lokatorId + "','" + idCeny + "','" + nrMieszkania + "','" + odczyt + "','" + dataOdczytu + "')";
        resultSet = getResultSet(query);

        String check = "SELECT * FROM Odczyt WHERE DataOdczytu ='" +dataOdczytu+"' AND WartoscOdczytu ='" + odczyt+"' AND IdMedia = '"+idMedia+"' AND IdLokatora ='"+lokatorId+"'";
        resultSet = getResultSet(check);

        try {
            bundle = new Bundle();
            if(resultSet.next()){

                bundle.putString("result", "INSERTED");

            } else{
                bundle.putString("result", "FAILED");
            }
            resultReceiver.send(DODANO_ODCZYT, bundle);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


//    @Override
//    public void UserLogout(int lokatorId) {
//        String userLogoutQuery = "UPDATE Lokator SET Aktywny=0 WHERE IdLokatora='"+lokatorId+"';";
//        getResultSet(userLogoutQuery);
//        bundle = new Bundle();
//        bundle.putString("msg","You are logged out!");
//        resultReceiver.send(5, bundle);
//    }

    private ResultSet getResultSet(String query){
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            //stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    private void clear(){
        try {
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
