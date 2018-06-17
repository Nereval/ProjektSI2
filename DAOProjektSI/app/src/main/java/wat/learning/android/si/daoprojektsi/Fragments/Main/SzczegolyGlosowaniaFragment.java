package wat.learning.android.si.daoprojektsi.Fragments.Main;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import wat.learning.android.si.daoprojektsi.Activities.MainActivity;
import wat.learning.android.si.daoprojektsi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SzczegolyGlosowaniaFragment extends Fragment {


    public SzczegolyGlosowaniaFragment() {
        // Required empty public constructor
    }

    public View fragView;
    public long id;

    public void setId(long i){
        id = i;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragView = inflater.inflate(R.layout.fragment_szczegoly_glosowania, container, false);

        TextView temat = (TextView) fragView.findViewById(R.id.temat_glosowania);
        TextView opis = (TextView) fragView.findViewById(R.id.opis_glosowania);
        TextView dataR = (TextView) fragView.findViewById(R.id.data_rozpoczecia);
        TextView dataZ = (TextView) fragView.findViewById(R.id.data_zakonczenia);
        final Spinner opcje = (Spinner) fragView.findViewById(R.id.spinner);
        final Button glosuj = (Button) fragView.findViewById(R.id.glosowanie);
        Button pokazProjekt = (Button) fragView.findViewById(R.id.projekt);

        glosuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String glos = opcje.getSelectedItem().toString();
                glosuj.setEnabled(false);
                //TODO: Dodaj rekord glosu do bazy
            }
        });

        pokazProjekt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: tutaj będzie IdProjektu
                int id = 2;
                ((MainActivity)getActivity()).pokazProjekt(id);
            }
        });

        //TODO: Wczytanie opcji głosowania do spinnera, wczytanie danych z tabeli, idProjektu!, sprawdzenie czy osoba już glosowala na ten projekt

        //To tylko mój test
        String test = "11.06.2018";
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = format.parse(test);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //System.out.println(date);
        Date today = Calendar.getInstance().getTime();

        if(date.before(today)){
            glosuj.setEnabled(false);
        }

        return fragView;
    }

}
