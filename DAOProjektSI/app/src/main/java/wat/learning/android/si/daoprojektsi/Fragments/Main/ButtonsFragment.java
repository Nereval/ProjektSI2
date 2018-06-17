package wat.learning.android.si.daoprojektsi.Fragments.Main;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import wat.learning.android.si.daoprojektsi.Activities.MainActivity;
import wat.learning.android.si.daoprojektsi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ButtonsFragment extends Fragment {

    private View fragView;

    public ButtonsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragView = inflater.inflate(R.layout.fragment_buttons, container, false);

        Button buttonOdczyt = fragView.findViewById(R.id.button);
        buttonOdczyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).mediaList();
            }
        });
        Button buttonOplaty = fragView.findViewById(R.id.button2);
        buttonOplaty.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showOplaty();
            }
        });
        Button buttonNowaWiadomosc = fragView.findViewById(R.id.button3);
        buttonNowaWiadomosc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showNowaWiadomosc();
            }
        });
        Button buttonSkrzynka = fragView.findViewById(R.id.button4);
        buttonSkrzynka.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showSkrzynka();
            }
        });
        Button buttonGlosowanie = fragView.findViewById(R.id.button5);
        buttonGlosowanie.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showGlosowanie();
            }
        });
        Button buttonFinanse = fragView.findViewById(R.id.button6);
        buttonFinanse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).obrotNaKoncie();
            }
        });
        Button buttonSprzety = fragView.findViewById(R.id.button7);
        buttonSprzety.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).listaSprzętów();
            }
        });
        return fragView;
    }

}
