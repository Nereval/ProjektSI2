package wat.learning.android.si.daoprojektsi.Fragments.Main;


import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import wat.learning.android.si.daoprojektsi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SkrzynkaOdbiorczaFragment extends ListFragment {


    public SkrzynkaOdbiorczaFragment() {
        // Required empty public constructor
    }

    public static interface SkrzynkaListListener{
        void itemClicked(long id);
    }

    private SkrzynkaListListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //TODO: Pobieranie listy wiadomosci

        String [] messages = new String[2 /*length*/];
        for(int i = 0; i < messages.length; i++){
            messages[i] = "Nadawca: " + i + "\nTemat: " + i + i;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.simple_list_item_1, messages);

        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.listener = (SkrzynkaListListener)activity;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if(listener != null)
            listener.itemClicked(id);
        //TODO: zmienić aby przekazywało ID wiadomosci
    }
}
