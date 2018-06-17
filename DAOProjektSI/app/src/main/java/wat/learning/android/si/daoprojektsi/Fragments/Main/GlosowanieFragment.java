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

import wat.learning.android.si.daoprojektsi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GlosowanieFragment extends ListFragment {

    public static interface GlosowanieListener{
        void itemClickedG(long id);
    }

    public GlosowanieFragment() {
        // Required empty public constructor
    }

    private GlosowanieListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //TODO: Pobieranie listy glosowan

        String [] glosowania = new String[20 /*length*/];
        for(int i = 0; i < glosowania.length; i++){
            glosowania[i] = "Temat: " + i + "\nData zakonczenia: 2018-06-2" + i;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.simple_list_item_1, glosowania);

        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (GlosowanieListener)activity;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if(listener != null)
            listener.itemClickedG(id);
        //TODO: zmienić aby przekazywało ID wiadomosci
    }

}
