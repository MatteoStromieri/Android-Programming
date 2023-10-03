package it.uniroma2.pjdm.androidstudio.kyf.nutrizionista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.adapter.ListaIngredientiAdapter;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.adapter.RichiesteAdapter;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.viewModel.NutrizionistaViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AggiungiAlimentoCompostoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AggiungiAlimentoCompostoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = AggiungiAlimentoCompostoFragment.class.getSimpleName();
    private ListaIngredientiAdapter adapter;
    private NutrizionistaViewModel viewModel;

    public AggiungiAlimentoCompostoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AggiungiAlimentoCompostoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AggiungiAlimentoCompostoFragment newInstance(String param1, String param2) {
        AggiungiAlimentoCompostoFragment fragment = new AggiungiAlimentoCompostoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ListaIngredientiAdapter(getActivity());
        // il viewmodel mi servirà solo per inviare la richiesta alla fine
        viewModel = new ViewModelProvider(getActivity()).get(NutrizionistaViewModel.class);
        adapter.setNutrizionistaViewModel(viewModel);
        viewModel.setListaIngredientiAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_aggiungi_alimento_composto, container, false);

        // adapter = ListaIngredientiAdapter.getLastCreated();

        RecyclerView rv = v.findViewById(R.id.rvListaIngredienti);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);

        // ora settiamo i listener sui bottoni
        Button btAggiungiAlimentoComposto = v.findViewById(R.id.btInserisciNuovoAlimentoComposto);
        Button btAggiungiIngrediente = v.findViewById(R.id.btAggiungiIngrediente);
        // qui bisogna settare i listener

        // Facciamo prima il bottone per l'inserimento dell'alimento
        /*
        * E' importante che ci sia almeno un ingrediente e che le quantità non superino 100
        * */

        btAggiungiAlimentoComposto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // verifichiamo subito che ci sia almeno un ingrediente
                if(adapter.getItemCount() == 0){
                    Toast.makeText(getContext(), getString(R.string.inserire_almeno_un_ingrediente), Toast.LENGTH_SHORT).show();
                    return;
                }

                // se abbiamo più di un ingrediente sommiamo le loro quantità e verifichiamo che non superi 100
                if(adapter.getSommaQuantita()>100){
                    Toast.makeText(getContext(), getString(R.string.inserire_ingredienti_100_grammi), Toast.LENGTH_SHORT).show();
                    return;
                }

                // verifichiamo che sia presente il nome
                EditText etNome = v.findViewById(R.id.etNomeNuovoAlimentoComposto);
                EditText etDescrizione = v.findViewById(R.id.etDescrizioneNuovoAlimentoComposto);

                String nome = etNome.getText().toString();
                String descrizione = etDescrizione.getText().toString();
                if(nome.isEmpty()){
                    Toast.makeText(getContext(), getString(R.string.inserire_nome_alimento), Toast.LENGTH_SHORT).show();
                    return;
                }
                // fare la chiamata al ViewModel
                viewModel.inserisciAlimentoComposto(nome, descrizione, adapter.getData());
                // torniamo al fragment principale
                getParentFragmentManager().popBackStack();
            }
        });

        // con l'altro bottone dobbiamo navigare ad una schermata di ricerca

        btAggiungiIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AggiungiAlimentoCompostoFragment.this).navigate(R.id.action_aggiungiAlimentoCompostoFragment_to_ricercaIngredienteFragment);
            }
        });

        // alla fine ritorniamo la view
        return v;
    }
}