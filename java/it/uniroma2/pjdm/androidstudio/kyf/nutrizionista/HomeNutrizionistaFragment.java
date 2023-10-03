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

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.adapter.RichiesteAdapter;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.viewModel.NutrizionistaViewModel;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter.DiarioAdapter;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.viewModel.UtenteViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeNutrizionistaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeNutrizionistaFragment extends Fragment {

    private static final String TAG = HomeNutrizionistaFragment.class.getSimpleName();
    private RichiesteAdapter adapter;
    private NutrizionistaViewModel viewModel;

    public HomeNutrizionistaFragment() {
        // Required empty public constructor
    }

    public static HomeNutrizionistaFragment newInstance(String param1, String param2) {
        HomeNutrizionistaFragment fragment = new HomeNutrizionistaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new RichiesteAdapter(getActivity());
        viewModel = new ViewModelProvider(getActivity()).get(NutrizionistaViewModel.class);
        viewModel.setRichiesteAdapter(adapter);

        adapter.setNutrizionistaViewModel(viewModel);
        viewModel.getRichieste();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_nutrizionista, container, false);

        RecyclerView rv = v.findViewById(R.id.rvRichieste);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);

        // qui dobbiamo settare i listener sui bottoni
        Button btAggiungiAlimento = v.findViewById(R.id.btAggiungiAlimentoNutrizionista);
        Button btAggiungiAlimentoComposto = v.findViewById(R.id.btAggiungiAlimentoCompostoNutrizionista);

        btAggiungiAlimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //navighiamo al nuovo fragment
                NavHostFragment.findNavController(HomeNutrizionistaFragment.this).navigate(R.id.action_homeNutrizionistaFragment_to_aggiungiAlimentoFragment);
            }
        });

        btAggiungiAlimentoComposto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HomeNutrizionistaFragment.this).navigate(R.id.action_homeNutrizionistaFragment_to_aggiungiAlimentoCompostoFragment);
            }
        });

        return v;
    }
}