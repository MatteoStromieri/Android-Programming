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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Date;
import java.util.Calendar;

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.viewModel.UtenteViewModel;

/*
* In questo fragment ho bisogno di: categoria, idAlimento, pasto
*
* La quantità se la prende da solo tramite il form
*
* */
public class ModificaQuantitaFragmentDialog extends DialogFragment {

    private static final String TAG = ModificaQuantitaFragmentDialog.class.getSimpleName();
    private static final String ID_ALIMENTO = "idAlimento";
    private static final String CATEGORIA = "categoria";
    private static final String PASTO = "pasto";

    // TODO: Rename and change types of parameters
    private int idAlimento;
    private String categoria;
    private String pasto;
    private UtenteViewModel viewModel;

    public ModificaQuantitaFragmentDialog() {
        // Required empty public constructor
    }


    public static ModificaQuantitaFragmentDialog newInstance(int idAlimento, String categoria, String pasto) {
        Log.d(TAG, "newInstance: creato nuovo fragment con idAlimento " + idAlimento + " e categoria " + categoria + "e pasto " + pasto);
        ModificaQuantitaFragmentDialog fragment = new ModificaQuantitaFragmentDialog();
        Bundle args = new Bundle();
        args.putInt(ID_ALIMENTO, idAlimento);
        args.putString(CATEGORIA, categoria);
        args.putString(PASTO, pasto);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idAlimento = getArguments().getInt(ID_ALIMENTO);
            categoria = getArguments().getString(CATEGORIA);
            pasto = getArguments().getString(PASTO);
        }
        viewModel = new ViewModelProvider(getActivity()).get(UtenteViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_modifica_quantita_dialog, container, false);

        // Toast.makeText(getActivity(), "idAlimento " + idAlimento + " categoria " + categoria + " pasto" + pasto, Toast.LENGTH_LONG).show();

        // settiamo il click listener per il bottone
        Button buttonModifica = v.findViewById(R.id.btModificaQuantitaAlimentoDiario);
        Button buttonElimina = v.findViewById(R.id.btEliminaAlimentoDiario);

        buttonModifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ci prendiamo la quantità dalla edit text e poi avviamo l'operazione sul view model
                EditText etQuantita = v.findViewById(R.id.etNuovaQuantitaUtente);
                String textQuantita = etQuantita.getText().toString();

                Calendar calendar = Calendar.getInstance();
                Date today = new Date(calendar.getTimeInMillis());

                /*
                 * prima di inviare la quantita dobbiamo dividerla per 100 perchè nel db i pasti sono rappresentati con quantità in ETTOGRAMMI
                 * */
                try {
                    Double doubleQuantita = Double.parseDouble(textQuantita) / 100;
                    if(textQuantita != null )
                        viewModel.modificaAlimento(categoria,idAlimento,doubleQuantita.toString(),today.toString(),pasto);
                        NavHostFragment.findNavController(ModificaQuantitaFragmentDialog.this).navigate(R.id.action_modificaQuantitaFragmentDialog_to_homeUtenteFragment);
                } catch (NullPointerException | NumberFormatException e){
                    Toast.makeText(getContext(), getString(R.string.inserisci_quantita_valida), Toast.LENGTH_LONG).show();
                    return;
                }

            }
        });

        buttonElimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                Date today = new Date(calendar.getTimeInMillis());
                // ci prendiamo idAlimento, categoria, pasto e data e poi avviamo l'operazione
                viewModel.eliminaAlimento(categoria,idAlimento,today.toString(),pasto);
                NavHostFragment.findNavController(ModificaQuantitaFragmentDialog.this).navigate(R.id.action_modificaQuantitaFragmentDialog_to_homeUtenteFragment);
            }
        });
        return v;
    }
}