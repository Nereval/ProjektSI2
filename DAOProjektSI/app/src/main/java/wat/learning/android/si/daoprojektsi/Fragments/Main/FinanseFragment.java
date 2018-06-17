package wat.learning.android.si.daoprojektsi.Fragments.Main;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import wat.learning.android.si.daoprojektsi.Activities.MainActivity;
import wat.learning.android.si.daoprojektsi.Data.ObrotNaKoncie;
import wat.learning.android.si.daoprojektsi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinanseFragment extends Fragment {


    public FinanseFragment() {
        // Required empty public constructor
    }

    public ArrayList<String> finanse = new ArrayList<String>();
    public View fragView;
    public List<ObrotNaKoncie> listaObrot = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragView = inflater.inflate(R.layout.fragment_finanse, container, false);

        //TODO: Pobrać wszystkie id obrotu (zakładam że są dwa rodzaje obrotów - przychody i obciążenia)
        //TODO: Poza tym pobrać id faktury i kwotę
        listaObrot = ((MainActivity)getActivity()).getObrotNaKoncie();
        finanse.clear();
        for(ObrotNaKoncie obrot : listaObrot){
            if(obrot.getKwota() > 0)
                finanse.add("Przychód: +" + obrot.getKwota() + "\nTytuł: " + obrot.getTytulObrotu());
            else
                finanse.add("Obciążenie: -" + obrot.getKwota() + "\nTytuł: " + obrot.getTytulObrotu());
        }

        Fragment fragment = new FakturyListFragment();
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();

        return fragView;
    }

}
