package wat.learning.android.si.daoprojektsi.Fragments.Main;


import android.os.Bundle;
import android.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import wat.learning.android.si.daoprojektsi.Activities.MainActivity;
import wat.learning.android.si.daoprojektsi.Data.Media;
import wat.learning.android.si.daoprojektsi.DecimalDigitsInputFilter;
import wat.learning.android.si.daoprojektsi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OdczytLicznikowFragment extends Fragment {

    private List<Media> mediaList = new ArrayList<>();
    private View fragView;
    private String chosenMedia;

    public OdczytLicznikowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragView = inflater.inflate(R.layout.fragment_odczyt_licznikow, container, false);

        mediaList = ((MainActivity)getActivity()).getMediaList();

        ArrayList<String> list = new ArrayList<>();
       // final ArrayList<Integer> idList = new ArrayList<>();
        for(Media media : mediaList){
            String nazwa = media.getNazwaMedia();
            list.add(nazwa);

        }

        EditText odczytET = (EditText) fragView.findViewById(R.id.wartosc_odczytu);
        odczytET.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10,2)});

        Spinner sMedia = fragView.findViewById(R.id.spinnerOdczyt);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(fragView.getContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sMedia.setAdapter(adapter);

        TextView dataOdczytu = (TextView) fragView.findViewById(R.id.data_odczytu);
        String date = getDate();
        dataOdczytu.setText("Data odczytu: " + date);

        Button dodaj = (Button) fragView.findViewById(R.id.dodaj_odczyt);
        dodaj.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText odczytET = (EditText) fragView.findViewById(R.id.wartosc_odczytu);

                Spinner spinner = fragView.findViewById(R.id.spinnerOdczyt);
                long id = spinner.getSelectedItemId();
//                for(int idMedia  : idList){
//                    if(idMedia == (int)id){
//                        media = idMedia;
//                    }
//                }
                int media = mediaList.get((int)id).getIdMedia();
                float odczyt = Float.parseFloat(odczytET.getText().toString());
                int nrMieszkania = mediaList.get(0).getNrMieszkania();

                int idCeny = mediaList.get((int)id).getIdCeny();

                odczytET.setText("");

                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
                Date now = new Date();
                String strDate = sdfDate.format(now);

                ((MainActivity)getActivity()).dodajOdczyt(media, odczyt, strDate, nrMieszkania, idCeny);
            }
        });

        return fragView;
    }

    public String getDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(calendar.getTime());
    }

}
