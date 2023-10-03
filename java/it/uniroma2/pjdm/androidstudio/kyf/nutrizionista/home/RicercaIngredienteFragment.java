package it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home;

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
import android.widget.Toast;

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.AggiungiAlimentoCompostoFragment;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.adapter.RicercaIngredienteAdapter;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.viewModel.NutrizionistaViewModel;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter.RicercaAlimentoAdapter;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.viewModel.UtenteViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RicercaIngredienteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RicercaIngredienteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = RicercaIngredienteFragment.class.getSimpleName();
    private RicercaIngredienteAdapter adapter;
    private NutrizionistaViewModel viewModel;
    public RicercaIngredienteFragment() {
        // Required empty public constructor
    }

    public static RicercaIngredienteFragment newInstance(String param1, String param2) {
        RicercaIngredienteFragment fragment = new RicercaIngredienteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inizializziamo l'adapter
        adapter = new RicercaIngredienteAdapter(getActivity());
        adapter.setRicercaIngredienteFragment(this);
        // facciamo il binding con il view model
        viewModel = new ViewModelProvider(getActivity()).get(NutrizionistaViewModel.class);
        viewModel.setRicercaIngredienteAdapter(adapter);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ricerca_ingrediente, container, false);

        // settiamo l'adapter sulla recycler view
        RecyclerView rv = v.findViewById(R.id.rvRicercaIngredienti);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);

        // settiamo il listener sul bottone di ricerca
        v.findViewById(R.id.btCercaIngrediente).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // verifichiamo che la editText non sia vuota
                EditText et = v.findViewById(R.id.etNomeIngrediente);
                String text = et.getText().toString();

                if(!text.equals("")){
                    viewModel.ricercaAlimento(text);
                } else {
                    Toast.makeText(getContext(), getString(R.string.inserire_nome_alimento_ricerca), Toast.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }

    public void navigateToDialog(Bundle dati) {
        NavHostFragment.findNavController(this).navigate(R.id.action_ricercaIngredienteFragment_to_raccoltaQuantitaIngredienteFragment,dati);
    }
}