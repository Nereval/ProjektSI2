package wat.learning.android.si.daoprojektsi.Fragments.Main;


import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wat.learning.android.si.daoprojektsi.Activities.MainActivity;
import wat.learning.android.si.daoprojektsi.Data.ListaSprzetu;
import wat.learning.android.si.daoprojektsi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SprzetyListFragment extends ListFragment {

    private List<ListaSprzetu> lista = new ArrayList<>();

    public static interface SprzetyListener{
        void itemClickedS(long id);
    }

    public SprzetyListFragment() {
        // Required empty public constructor
    }

    private SprzetyListener listener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        lista = ((MainActivity)getActivity()).getListaSprzetow();

        String [] sprzety = new String[lista.size()];
        for(int i = 0; i < lista.size(); i++){

            ListaSprzetu sprzet = lista.get(i);
            sprzety[i] = "Sprzet: " + sprzet.getNazwaSprzetu() + "\nNazwisko lokatora: " + sprzet.getNazwisko();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.simple_list_item_1, sprzety);

        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (SprzetyListener)activity;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if(listener != null)
            listener.itemClickedS(id);
    }

}
