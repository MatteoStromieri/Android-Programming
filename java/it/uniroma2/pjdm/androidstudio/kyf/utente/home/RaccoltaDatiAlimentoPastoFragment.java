package it.uniroma2.pjdm.androidstudio.kyf.utente.home;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Date;
import java.util.Calendar;

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.viewModel.UtenteViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RaccoltaDatiAlimentoPastoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RaccoltaDatiAlimentoPastoFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = RaccoltaDatiAlimentoPastoFragment.class.getSimpleName();
    private static final String ID_ALIMENTO = "idAlimento";
    private static final String CATEGORIA = "categoria";

    private int idAlimento;
    private String categoria;
    private UtenteViewModel viewModel;

    public RaccoltaDatiAlimentoPastoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RaccoltaDatiAlimentoPastoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RaccoltaDatiAlimentoPastoFragment newInstance(int idAlimento, String categoria) {
        Log.d(TAG, "newInstance: creato nuovo fragment con idAlimento " + idAlimento + " e categoria " + categoria);
        RaccoltaDatiAlimentoPastoFragment fragment = new RaccoltaDatiAlimentoPastoFragment();
        Bundle args = new Bundle();
        args.putInt(ID_ALIMENTO, idAlimento);
        args.putString(CATEGORIA, categoria);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idAlimento = getArguments().getInt(ID_ALIMENTO);
            categoria = getArguments().getString(CATEGORIA);
        }
        viewModel = new ViewModelProvider(getActivity()).get(UtenteViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_raccolta_dati_alimento_pasto, container, false);

        // Toast.makeText(getActivity(), "idAlimento " + idAlimento + " categoria " + categoria , Toast.LENGTH_LONG).show();

        // settiamo il click listener per il bottone
        Button button = v.findViewById(R.id.btAggiungiAlimentoDialogo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner spinner = v.findViewById(R.id.spinnerPasto);
                EditText etQuantita = v.findViewById(R.id.etQuantitaAlimentoPasto);
                String selectedItem = (String) spinner.getSelectedItem();
                String textQuantita = etQuantita.getText().toString();
                Calendar calendar = Calendar.getInstance();
                Date today = new Date(calendar.getTimeInMillis());

                /*
                 * prima di inviare la quantita dobbiamo dividerla per 100 perchè nel db i pasti sono rappresentati con quantità in ETTOGRAMMI
                 * */
                try {
                    Double doubleQuantita = Double.parseDouble(textQuantita) / 100;
                    if(spinner.getSelectedItem() != null && textQuantita != null ) {
                        viewModel.aggiungiAlimentoPasto(categoria, today.toString(), idAlimento, selectedItem, doubleQuantita.toString());
                        getParentFragmentManager().popBackStack();
                    }
                } catch (NullPointerException | NumberFormatException e){
                    Toast.makeText(getContext(), getString(R.string.inserisci_quantita_valida), Toast.LENGTH_LONG).show();
                    return;
                }

            }
        });
        return v;
    }
}