package it.uniroma2.pjdm.androidstudio.kyf.utente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;
import java.util.Calendar;

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter.DiarioAdapter;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter.RicercaAlimentoAdapter;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter.RicercaNutrizionistaAdapter;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.viewModel.UtenteViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaNutrizionistiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaNutrizionistiFragment extends Fragment {
    private static final String TAG = ListaNutrizionistiFragment.class.getSimpleName();
    private UtenteViewModel viewModel;
    private RicercaNutrizionistaAdapter adapter;


    public ListaNutrizionistiFragment() {
        // Required empty public constructor
    }


    public static ListaNutrizionistiFragment newInstance(String param1, String param2) {
        ListaNutrizionistiFragment fragment = new ListaNutrizionistiFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: onCreate è stato richiamato");

        adapter = new RicercaNutrizionistaAdapter(getActivity());
        adapter.setListaNutrizionistiFragment(this);
        viewModel = new ViewModelProvider(getActivity()).get(UtenteViewModel.class);
        viewModel.setRicercaNutrizionistaAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lista_nutrizionisti, container, false);

        // settiamo l'adapter
        RecyclerView rv = v.findViewById(R.id.rvNutrizionisti);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);

        // settiamo il bottone di ricerca
        v.findViewById(R.id.btRicercaNutrizionista).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // verifichiamo che la editText non sia vuota
                EditText etNome = v.findViewById(R.id.etQueryNutrizionistaNome);
                EditText etCognome = v.findViewById(R.id.etQueryNutrizionistaCognome);
                String textNome = etNome.getText().toString();
                String textCognome = etCognome.getText().toString();

                if(!textNome.equals("") && !textCognome.equals("")){
                    // se la edit text non è vuota facciamo la ricerca
                    viewModel.ricercaNutrizionista(textNome,textCognome);
                } else {
                    Toast.makeText(getContext(), getString(R.string.inserisci_nome_cognome_nutrizionista), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    public void navigateToDialog(Bundle dati) {
        NavHostFragment.findNavController(this).navigate(R.id.action_ricercaNutrizionistaFragment_to_inserimentoRichiestaFragmentDialog,dati);
    }
}