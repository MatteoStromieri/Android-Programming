package it.uniroma2.pjdm.androidstudio.kyf.utente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter.RicercaAlimentoAdapter;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.viewModel.UtenteViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InserimentoAlimentoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InserimentoAlimentoFragment extends Fragment {

    private RicercaAlimentoAdapter adapter;
    private UtenteViewModel viewModel;

    public InserimentoAlimentoFragment() {
        // Required empty public constructor
    }

    public static InserimentoAlimentoFragment newInstance(String param1, String param2) {
        InserimentoAlimentoFragment fragment = new InserimentoAlimentoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // instaziomo l'adapter
        adapter = new RicercaAlimentoAdapter(getActivity());
        adapter.setInserimentoAlimentoFragment(this);
        // facciamo il binding con il view model
        viewModel = new ViewModelProvider(getActivity()).get(UtenteViewModel.class);
        viewModel.setRicercaAlimentoAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_inserimento_alimento, container, false);

        // settiamo l'dapter
        RecyclerView rv = v.findViewById(R.id.rvRicercaAlimentoUtente);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);


        // settiamo il bottone di ricerca, quando viene cliccato deve richiamare il metodo del viewmodel
        v.findViewById(R.id.btCercaAlimentoUtente).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // verifichiamo che la editText non sia vuota
                EditText et = v.findViewById(R.id.etRicercaAlimento);
                CheckBox switchAlimentoComposto = v.findViewById(R.id.cbRicercaAlimento);
                String text = et.getText().toString();

                if(!text.equals("") && switchAlimentoComposto.isChecked()){
                    // se la edit text non è vuota facciamo la ricerca
                    viewModel.ricercaAlimento(text,getString(R.string.categoria_alimenti_composti));
                } else if (!text.equals("") && !switchAlimentoComposto.isChecked()){
                    viewModel.ricercaAlimento(text,getString(R.string.categoria_alimenti));
                }
            }
        });

        // ritorniamo la view
        return v;
    }

    public void navigateToDialog(Bundle dati) {
        // settiamo prima i parametri del fragment, ovvero id alimento e categoria

        NavHostFragment.findNavController(this).navigate(R.id.action_inserimentoAlimentoFragment_to_raccoltaDatiAlimentoPastoFragment,dati);
    }
}