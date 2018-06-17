package wat.learning.android.si.daoprojektsi.Fragments.Main;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import wat.learning.android.si.daoprojektsi.Activities.MainActivity;
import wat.learning.android.si.daoprojektsi.Data.Faktura;
import wat.learning.android.si.daoprojektsi.Data.ObrotNaKoncie;
import wat.learning.android.si.daoprojektsi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SzczegolyFakturyFragment extends Fragment {


    public SzczegolyFakturyFragment() {
        // Required empty public constructor
    }

    public long id;
    private List<ObrotNaKoncie> listaObrot = new ArrayList<>();

    public void setId(long i){
        id = i;
    }

  public View fragView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragView = inflater.inflate(R.layout.fragment_szczegoly_faktury, container, false);

        TextView nrF = (TextView) fragView.findViewById(R.id.numer_faktury);
        TextView podmiotWystawiajacy = (TextView) fragView.findViewById(R.id.podmiot_wystawiajacy);
        TextView terminPlatnosci = (TextView) fragView.findViewById(R.id.termin_platnosci);
        TextView dataWystawienia = (TextView) fragView.findViewById(R.id.data_wystawienia);
        TextView kwota = (TextView) fragView.findViewById(R.id.kwota);
        TextView opis = (TextView)  fragView.findViewById(R.id.opis_faktury);

        listaObrot = ((MainActivity)getActivity()).getObrotNaKoncie();
        ObrotNaKoncie obrot = listaObrot.get((int) id);
        int idFaktury = obrot.getIdFaktury();
        List<Faktura> faktury = new ArrayList<>();
        faktury = ((MainActivity)getActivity()).getListaFaktur();
        for(Faktura faktura : faktury){
            if(faktura.getIdFaktury() == idFaktury){
                nrF.setText(Integer.toString(faktura.getIdFaktury()));
                podmiotWystawiajacy.setText(faktura.getPodmiotWystawiajacy());
                terminPlatnosci.setText(faktura.getTerminPlatnosci());
                dataWystawienia.setText(faktura.getDataWystawienia());
                kwota.setText(Float.toString(faktura.getKwotaFaktury()));
                opis.setText(faktura.getOpisFaktury());
            }

        }
        return fragView;
    }

}
