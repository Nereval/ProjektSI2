package wat.learning.android.si.daoprojektsi.Fragments.Main;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import wat.learning.android.si.daoprojektsi.Activities.MainActivity;
import wat.learning.android.si.daoprojektsi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OdbiorWiadomosciFragment extends Fragment {


    public OdbiorWiadomosciFragment() {
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

        fragView = inflater.inflate(R.layout.fragment_odbior_wiadomosci, container, false);

        TextView odbiorca = (TextView) fragView.findViewById(R.id.odbiorca);
        TextView temat = (TextView) fragView.findViewById(R.id.temat);
        TextView tresc = (TextView) fragView.findViewById(R.id.tresc_wiadomosci);

        //TODO: Zczytanie z ID wszystkich danych i przedstawienie ich

        odbiorca.setText("Nadawca: " + String.valueOf(id));
        temat.setText("Temat: " + String.valueOf(id));
        tresc.setText(String.valueOf(id));

        Button odpowiedz = (Button)fragView.findViewById(R.id.odpowiedz);
        odpowiedz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView odbiorca = (TextView) fragView.findViewById(R.id.odbiorca);
                TextView temat = (TextView) fragView.findViewById(R.id.temat);

                ((MainActivity)getActivity()).odpowiedz(odbiorca.getText().toString(), temat.getText().toString());
            }
        });
        Button usun = (Button)fragView.findViewById(R.id.usun);
        usun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MainActivity)getActivity()).usun(id);
            }
        });

        return fragView;
    }
}
