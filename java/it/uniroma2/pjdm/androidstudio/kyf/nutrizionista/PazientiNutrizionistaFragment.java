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

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.adapter.PazientiAdapter;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.adapter.RichiesteAdapter;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.viewModel.NutrizionistaViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PazientiNutrizionistaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PazientiNutrizionistaFragment extends Fragment {

    private static final String TAG = PazientiNutrizionistaFragment.class.getSimpleName();
    private PazientiAdapter adapter;
    private NutrizionistaViewModel viewModel;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PazientiNutrizionistaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PazientiNutrizionistaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PazientiNutrizionistaFragment newInstance(String param1, String param2) {
        PazientiNutrizionistaFragment fragment = new PazientiNutrizionistaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new PazientiAdapter(getActivity());
        viewModel = new ViewModelProvider(getActivity()).get(NutrizionistaViewModel.class);
        viewModel.setPazientiAdapter(adapter);
        adapter.setPazientiNutrizionistaFragment(this);

        adapter.setNutrizionistaViewModel(viewModel);
        viewModel.getPazienti();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ricerca_pazienti, container, false);

        RecyclerView rv = v.findViewById(R.id.rvPazienti);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);

        return v;
    }


    public void navigateToDiario(Bundle dati) {
        //aggiungere i dati alla navigazione
        // i dati che gli stiamo dando sono
        /*
        *   "emailUtente"
            "data"
        * */
        NavHostFragment.findNavController(this).navigate(R.id.action_ricercaPazientiFragment_to_diarioPazienteFragment,dati);

    }
}