package wat.learning.android.si.daoprojektsi.Activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import wat.learning.android.si.daoprojektsi.ActionCode;
import wat.learning.android.si.daoprojektsi.Data.Faktura;
import wat.learning.android.si.daoprojektsi.Data.ListaSprzetu;
import wat.learning.android.si.daoprojektsi.Data.Media;
import wat.learning.android.si.daoprojektsi.Data.MiesieczneOplaty;
import wat.learning.android.si.daoprojektsi.Data.ObrotNaKoncie;
import wat.learning.android.si.daoprojektsi.Database.DatabaseConnection;
import wat.learning.android.si.daoprojektsi.Database.DatabaseService;
import wat.learning.android.si.daoprojektsi.Database.MyResultReceiver;
import wat.learning.android.si.daoprojektsi.Fragments.Login.ProgressFragment;
import wat.learning.android.si.daoprojektsi.Fragments.Main.ButtonsFragment;
import wat.learning.android.si.daoprojektsi.Fragments.Main.FakturyListFragment;
import wat.learning.android.si.daoprojektsi.Fragments.Main.FinanseFragment;
import wat.learning.android.si.daoprojektsi.Fragments.Main.GlosowanieFragment;
import wat.learning.android.si.daoprojektsi.Fragments.Main.GlosowanieFragment.GlosowanieListener;
import wat.learning.android.si.daoprojektsi.Fragments.Main.MiesieczneOplatyListFragment;
import wat.learning.android.si.daoprojektsi.Fragments.Main.MiesieczneOplatyV2Fragment;
import wat.learning.android.si.daoprojektsi.Fragments.Main.NowaWiadomoscFragment;
import wat.learning.android.si.daoprojektsi.Fragments.Main.OdbiorWiadomosciFragment;
import wat.learning.android.si.daoprojektsi.Fragments.Main.OdczytLicznikowFragment;
import wat.learning.android.si.daoprojektsi.Fragments.Main.ProjektFragment;
import wat.learning.android.si.daoprojektsi.Fragments.Main.SkrzynkaOdbiorczaFragment;
import wat.learning.android.si.daoprojektsi.Fragments.Main.SprzetyListFragment;
import wat.learning.android.si.daoprojektsi.Fragments.Main.SzczegolyFakturyFragment;
import wat.learning.android.si.daoprojektsi.Fragments.Main.SzczegolyGlosowaniaFragment;
import wat.learning.android.si.daoprojektsi.Fragments.Main.SzczegolySprzetuFragment;
import wat.learning.android.si.daoprojektsi.R;

import static wat.learning.android.si.daoprojektsi.Activities.LoginActivity.hideKeyboard;

public class MainActivity extends AppCompatActivity implements MyResultReceiver.Receiver,
        SkrzynkaOdbiorczaFragment.SkrzynkaListListener, GlosowanieListener, FakturyListFragment.FakturyListener,
        SprzetyListFragment.SprzetyListener{

    private static final int LISTA_MEDIÓW = 6;
    private static final int MIESIĘCZNE_OPŁATY = 7;
    private static final int LISTA_SPRZĘTÓW = 8;
    private static final int OBROT_NA_KONCIE = 9;
    private static final int FAKTURY = 10;
    private static final int DODANO_ODCZYT = 11;

    private int lokatorId;
    private DatabaseConnection dbConn;
    private MyResultReceiver mReceiver;
    private List<Media> mediaList = new ArrayList<>();
    private List<MiesieczneOplaty> miesięczneOpłaty = new ArrayList<>();
    private List<ListaSprzetu> listaSprzetow = new ArrayList<>();
    private List<ObrotNaKoncie> obrotNaKoncie = new ArrayList<>();
    private List<Faktura> listaFaktur = new ArrayList<>();

    public List<ObrotNaKoncie> getObrotNaKoncie() {
        return obrotNaKoncie;
    }

    public List<Faktura> getListaFaktur() {
        return listaFaktur;
    }

//    private String[] titles;
//    private ListView drawerList;
//    private DrawerLayout drawerLayout;
//    private ActionBarDrawerToggle drawerToggle;
//
//    private class DrawerItemClickListener implements ListView.OnItemClickListener{
//
//        @Override
//        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//            selectItem(i);
//        }
//    }
//
//    private void selectItem(int position){
//        Fragment fragment;
//        switch (position){
//            case 0:
//                fragment = new OdczytLicznikowFragment();
//                break;
//            case 1:
//                fragment = new MiesieczneOplatyV2Fragment();
//                break;
//            case 2:
//                fragment = new NowaWiadomoscFragment();
//                break;
//            default:
//                fragment = new OdczytLicznikowFragment();
//        }
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.replace(R.id.frameMain, fragment);
//        ft.addToBackStack(null);
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        ft.commit();
//
//        drawerLayout.closeDrawer(drawerList);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String jsonConnection = getIntent().getExtras().getString("Connection");
        dbConn = new Gson().fromJson(jsonConnection, DatabaseConnection.class);

        mReceiver = new MyResultReceiver(new Handler());
        mReceiver.setReceiver(this);

        lokatorId = getIntent().getIntExtra("id", 0);
//        titles = getResources().getStringArray(R.array.menu);
//        drawerList = (ListView)findViewById(R.id.drawer);
//        drawerList.setAdapter(new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_activated_1, titles));
//        drawerList.setOnItemClickListener(new DrawerItemClickListener());
//
//        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if(savedInstanceState == null){
//            selectItem(0);
//        }
//
//        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
//                R.string.open_drawer, R.string.close_drawer){
//            @Override
//            public void onDrawerClosed(View view){
//                super.onDrawerClosed(view);
//                invalidateOptionsMenu();
//            }
//
//            @Override
//            public void onDrawerOpened(View view){
//                super.onDrawerOpened(view);
//                invalidateOptionsMenu();
//            }
//        };
//        drawerLayout.setDrawerListener(drawerToggle);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setHomeButtonEnabled(true)
        showButtons();
    }

    public void showNowaWiadomosc(){
        NowaWiadomoscFragment nowaWiadomoscFragment = new NowaWiadomoscFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frameMain, nowaWiadomoscFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        drawerToggle.syncState();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        drawerToggle.onConfigurationChanged(newConfig);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        if(drawerToggle.onOptionsItemSelected(item))
//            return true;
//        return false;
//    }

    public void showButtons(){
        ButtonsFragment buttonsFragment = new ButtonsFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frameMain, buttonsFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    public void showOdczyt(){
        OdczytLicznikowFragment odczytLicznikowFragment = new OdczytLicznikowFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frameMain, odczytLicznikowFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    public void showSkrzynka(){
        SkrzynkaOdbiorczaFragment skrzynkaOdbiorczaFragment = new SkrzynkaOdbiorczaFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frameMain, skrzynkaOdbiorczaFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    public void showGlosowanie(){
        GlosowanieFragment glosowanieFragment = new GlosowanieFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frameMain, glosowanieFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    public void showOplaty(){
        MiesieczneOplatyV2Fragment miesieczneOplatyFragment = new MiesieczneOplatyV2Fragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frameMain, miesieczneOplatyFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    public void nowaWiadomosc(String odbiorca, String temat, String wiadomosc){
        //TODO: wysylanie wiadomosci (narazie bez zalacznikow)
    }

    public void odpowiedz(String odbiorca, String temat){
        NowaWiadomoscFragment nowaWiadomoscFragment = new NowaWiadomoscFragment();
        nowaWiadomoscFragment.odbiorcaO = odbiorca;
        nowaWiadomoscFragment.tematO = temat;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frameMain, nowaWiadomoscFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    public void usun(long id){
        //TODO: Usun wiadomosc ze skrzynki odbiorczej
    }

    public void pokazProjekt(long id)
    {
        ProjektFragment projektFragment = new ProjektFragment();
        projektFragment.setId(id);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frameMain, projektFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    public void showFinanse(){
        FinanseFragment finanseFragment = new FinanseFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frameMain, finanseFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    public void showSprzety(){
        SprzetyListFragment sprzetyFragment = new SprzetyListFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frameMain, sprzetyFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    public void miesięczneOpłaty(String miesiacS, String rokS) {
        showProgress();

        Intent intent = new Intent(this, DatabaseService.class);
        intent.putExtra("receiverTag", mReceiver);
        intent.putExtra("code", ActionCode.MIESIĘCZNE_OPŁATY);
        intent.putExtra("id", lokatorId);
        intent.putExtra("Connection", new Gson().toJson(dbConn));
        intent.putExtra("miesiac", miesiacS);
        intent.putExtra("rok", rokS);
        startService(intent);
    }

    public void listaSprzętów() {
        showProgress();

        Intent intent = new Intent(this, DatabaseService.class);
        intent.putExtra("receiverTag", mReceiver);
        intent.putExtra("code", ActionCode.LISTA_SPRZĘTÓW);
        intent.putExtra("id", lokatorId);
        intent.putExtra("Connection", new Gson().toJson(dbConn));
        startService(intent);
    }

    public void obrotNaKoncie(){
        showProgress();

        Intent intent = new Intent(this, DatabaseService.class);
        intent.putExtra("receiverTag", mReceiver);
        intent.putExtra("code", ActionCode.OBROT_NA_KONCIE);
        intent.putExtra("id", lokatorId);
        intent.putExtra("Connection", new Gson().toJson(dbConn));
        startService(intent);
    }

    public void faktury(){
        Intent intent = new Intent(this, DatabaseService.class);
        intent.putExtra("receiverTag", mReceiver);
        intent.putExtra("code", ActionCode.FAKTURY);
        intent.putExtra("id", lokatorId);
        intent.putExtra("Connection", new Gson().toJson(dbConn));
        startService(intent);
    }

    public void showProgress() {
        hideKeyboard(this);
        ProgressFragment progressFragment = new ProgressFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frameMain, progressFragment);
        ft.commit();
    }

    public void dodajOdczyt(int media, float odczyt, String dataOdczytu, int nrMieszkania, int idCeny){
        Intent intent = new Intent(this, DatabaseService.class);
        intent.putExtra("receiverTag", mReceiver);
        intent.putExtra("code", ActionCode.DODAJ_ODCZYT);
        intent.putExtra("id", lokatorId);
        intent.putExtra("Connection", new Gson().toJson(dbConn));
        intent.putExtra("media", media);
        intent.putExtra("odczyt", odczyt);
        intent.putExtra("idCeny", idCeny);
        intent.putExtra("dataOdczytu", dataOdczytu);
        intent.putExtra("nrMieszkania", nrMieszkania);
        startService(intent);
    }

//    public void onBackPressed() {
////        if(lokatorId != 0) {
////            Intent logoutIntent = new Intent(this, DatabaseService.class);
////            logoutIntent.putExtra("code", ActionCode.LOGOUT);
////            logoutIntent.putExtra("id", lokatorId);
////            startService(logoutIntent);
////        }
//        //TODO: FIX! Logout from db - dziala ale wywala apke
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        finish();
//        System.exit(0);
//    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {

        switch (resultCode) {
            case LISTA_MEDIÓW:
                String jsonMedia = resultData.getString("mediaList");
                mediaList = new Gson().fromJson(jsonMedia, new TypeToken<List<Media>>() {
                }.getType());
                showOdczyt();
                break;
            case MIESIĘCZNE_OPŁATY:
                String json = resultData.getString("miesięczneOpłaty");
                miesięczneOpłaty = new Gson().fromJson(json, new TypeToken<List<MiesieczneOplaty>>() {
                }.getType());
                showOplaty();
                break;
            case LISTA_SPRZĘTÓW:
                String jsonSprzet = resultData.getString("listaSprzetu");
                listaSprzetow = new Gson().fromJson(jsonSprzet, new TypeToken<List<ListaSprzetu>>() {
                }.getType());
                showSprzety();
                break;
            case OBROT_NA_KONCIE:
                String jsonObrot = resultData.getString("obrotNaKoncie");
                obrotNaKoncie = new Gson().fromJson(jsonObrot, new TypeToken<List<ObrotNaKoncie>>() {
                }.getType());
                faktury();
                break;
            case FAKTURY:
                String jsonFaktury = resultData.getString("faktury");
                listaFaktur = new Gson().fromJson(jsonFaktury, new TypeToken<List<Faktura>>() {
                }.getType());
                showFinanse();
                break;
            case DODANO_ODCZYT:
                String result = resultData.getString("result");
                if(result == "INSERTED"){
                    Toast.makeText(this, "Odczyt dodano!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Błąd! Spróbuj ponownie!", Toast.LENGTH_SHORT).show();
                }

            default:
                break;

        }

    }

    public void mediaList(){
        showProgress();
        Intent intent = new Intent(this, DatabaseService.class);
        intent.putExtra("receiverTag", mReceiver);
        intent.putExtra("id", lokatorId);
        intent.putExtra("code", ActionCode.GET_MEDIA);
        intent.putExtra("Connection", new Gson().toJson(dbConn));
        startService(intent);
    }

    public List<Media> getMediaList(){
        return mediaList;
    }

    public List<MiesieczneOplaty> getMiesięczneOpłaty() {
        return miesięczneOpłaty;
    }
    public List<ListaSprzetu> getListaSprzetow() {
        return listaSprzetow;
    }



    @Override
    public void itemClicked(long id) {
        //TODO: przekazanie ID wiadomosci do fragmentu odbioru wiadomosci
        OdbiorWiadomosciFragment odbiorWiadomosciFragment = new OdbiorWiadomosciFragment();
        odbiorWiadomosciFragment.setId(id);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frameMain, odbiorWiadomosciFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    @Override
    public void itemClickedG(long id) {
        //TODO: przekazanie ID glosowania do fragmentu szegółów głosowania
        SzczegolyGlosowaniaFragment szczegolyGlosowaniaFragment = new SzczegolyGlosowaniaFragment();
        szczegolyGlosowaniaFragment.setId(id);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frameMain, szczegolyGlosowaniaFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }


    @Override
    public void itemClickedF(long id) {
        //TODO: przekazanie ID faktury do fragmentu szczegółów fakury
        SzczegolyFakturyFragment szczegolyFakturyFragment = new SzczegolyFakturyFragment();
        szczegolyFakturyFragment.setId(id);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frameMain, szczegolyFakturyFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    @Override
    public void itemClickedS(long id) {
        //TODO: przekazanie ID sprzetu do fragmentu szczegółów sprzetu
        SzczegolySprzetuFragment szczegolySprzetuFragment = new SzczegolySprzetuFragment();
        szczegolySprzetuFragment.setId(id);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frameMain, szczegolySprzetuFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}
