package it.uniroma2.pjdm.androidstudio.kyf.nutrizionista;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.login.RegistrazioneFragment;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.viewModel.NutrizionistaViewModel;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.viewModel.UtenteViewModel;

import static androidx.appcompat.content.res.AppCompatResources.getColorStateList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AggiungiAlimentoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AggiungiAlimentoFragment extends DialogFragment {

    private static final String TAG = AggiungiAlimentoFragment.class.getSimpleName();
    private NutrizionistaViewModel viewModel;
    public AggiungiAlimentoFragment() {
        // Required empty public constructor
    }

    public static AggiungiAlimentoFragment newInstance(String param1, String param2) {
        AggiungiAlimentoFragment fragment = new AggiungiAlimentoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(NutrizionistaViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_aggiungi_alimento, container, false);

        // ora settiamo il bottoni
        Button btAggiungiAlimento = v.findViewById(R.id.btAggiungiNuovoAlimento);

        btAggiungiAlimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // prendiamoci tutte le view all'interno del fragment
                EditText etNome = v.findViewById(R.id.etNomeNuovoAlimento);
                EditText etDescrizione = v.findViewById(R.id.etDescrizioneNuovoAlimento);
                EditText etCarboidrati = v.findViewById(R.id.etCarboidrati);
                EditText etProteine = v.findViewById(R.id.etProteine);
                EditText etGrassi = v.findViewById(R.id.etGrassi);

                // il nome deve essere presente
                String stringNome = etNome.getText().toString();
                String stringDescrizione = etDescrizione.getText().toString();

                if(stringNome.isEmpty()){
                    // non abbiamo il nome, rendiamo il campo di colore rosso
                    Toast.makeText(getContext(), getString(R.string.compilare_nome_nutrienti), Toast.LENGTH_LONG).show();
                    return;
                }

                // verifichiamo che carboidrati, proteine e grassi non siano vuoti
                String stringCarboidrati, stringProteine, stringGrassi;
                stringCarboidrati = etCarboidrati.getText().toString();
                stringProteine = etGrassi.getText().toString();
                stringGrassi = etGrassi.getText().toString();

                if(stringCarboidrati.isEmpty()  || stringProteine.isEmpty() || stringGrassi.isEmpty()){
                    Toast.makeText(getContext(),  getString(R.string.compilare_nome_nutrienti), Toast.LENGTH_LONG).show();
                    return;
                }

                // carboidrati, proteine e grassi devono sommare a uno
                try {
                    Log.d(TAG, "onClick: " + stringCarboidrati + " " + stringProteine + " " + stringGrassi);

                    Double doubleCarboidrati = Double.parseDouble(stringCarboidrati);
                    Double doubleProteine = Double.parseDouble(stringProteine);
                    Double doubleGrassi = Double.parseDouble(stringGrassi);

                    if(doubleCarboidrati + doubleProteine + doubleGrassi > 100){
                        Toast.makeText(getContext(), getString(R.string.inserire_nutrienti_100_grammi), Toast.LENGTH_LONG).show();
                        return;
                    }

                    // se sono arrivato qui posso fare la request
                    viewModel.aggiungiAlimento(stringNome,stringDescrizione,stringCarboidrati,stringProteine,stringGrassi);
                    dismiss();


                } catch (NumberFormatException e){
                    Toast.makeText(getContext(), getString(R.string.inserire_nutrienti_100_grammi), Toast.LENGTH_LONG).show();
                    return;
                }

            }
        });

        return v;
    }
}