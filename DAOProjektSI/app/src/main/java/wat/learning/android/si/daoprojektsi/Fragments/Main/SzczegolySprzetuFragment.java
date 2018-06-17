package wat.learning.android.si.daoprojektsi.Fragments.Main;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wat.learning.android.si.daoprojektsi.Activities.MainActivity;
import wat.learning.android.si.daoprojektsi.Data.ListaSprzetu;
import wat.learning.android.si.daoprojektsi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SzczegolySprzetuFragment extends Fragment {


    public SzczegolySprzetuFragment() {
        // Required empty public constructor
    }

    public long id;

    public void setId(long i){
        id = i;
    }

    public View fragView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragView = inflater.inflate(R.layout.fragment_szczegoly_sprzetu, container, false);

        TextView nazwaSprzetu = (TextView) fragView.findViewById(R.id.nazwa_sprzetu);
        TextView opisSprzetu = (TextView) fragView.findViewById(R.id.opis_sprzetu);
        TextView nazwiskoLokatora = (TextView) fragView.findViewById(R.id.nazwisko_lokatora);
        TextView numerMieszkania = (TextView) fragView.findViewById(R.id.numer_mieszkania);
        TextView dataZakupu = (TextView) fragView.findViewById(R.id.data_zakupu);
        TextView koniecGwarancji = (TextView) fragView.findViewById(R.id.koniec_gwarancji);
        TextView dataWycofania = (TextView) fragView.findViewById(R.id.data_wycofania);
        TextView przyczynaWycofania = (TextView) fragView.findViewById(R.id.przyczyna_wycofania);

        List<ListaSprzetu> listaSprzetu = new ArrayList<>();
        listaSprzetu = ((MainActivity)getActivity()).getListaSprzetow();
        ListaSprzetu sprzet = listaSprzetu.get((int)id);
        int idSprzetu = sprzet.getIdSprzetu();
        for(ListaSprzetu sprzety : listaSprzetu){
            if(sprzety.getIdSprzetu() == idSprzetu){
                nazwaSprzetu.setText(sprzety.getNazwaSprzetu());
                opisSprzetu.setText(sprzety.getOpisSprzetu());
                nazwiskoLokatora.setText(sprzety.getNazwisko());
                //numerMieszkania.setText(sprzety.getNumerMieszkania());
                dataZakupu.setText(sprzety.getDataZakupu());
                koniecGwarancji.setText(sprzety.getKoniecGwarancji());
                dataWycofania.setText(sprzety.getDataWycofania());
                przyczynaWycofania.setText(sprzety.getPrzyczynaWycofania());
            }
        }
        return fragView;
    }

}
