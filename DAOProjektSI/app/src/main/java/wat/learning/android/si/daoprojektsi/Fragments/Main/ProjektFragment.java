package wat.learning.android.si.daoprojektsi.Fragments.Main;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import wat.learning.android.si.daoprojektsi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjektFragment extends Fragment {


    public ProjektFragment() {
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

        fragView = inflater.inflate(R.layout.fragment_projekt, container, false);

        TextView temat = (TextView) fragView.findViewById(R.id.temat_projektu);
        TextView dataD = (TextView) fragView.findViewById(R.id.data_dodania);
        TextView dataR = (TextView) fragView.findViewById(R.id.data_rozpoczecia_realizacji);
        TextView dataZ = (TextView) fragView.findViewById(R.id.data_zakonczenia);
        TextView koszt = (TextView) fragView.findViewById(R.id.przewidywany_koszt);
        TextView opis = (TextView) fragView.findViewById(R.id.opis_projektu);
        TextView status = (TextView) fragView.findViewById(R.id.status);

        //TODO: Wczytać z bady dane i wyświetlić wartości we fragmencie

        return fragView;
    }

}
