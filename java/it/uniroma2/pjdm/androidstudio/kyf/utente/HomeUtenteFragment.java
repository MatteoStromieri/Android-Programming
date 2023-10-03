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
import android.widget.Button;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter.DiarioAdapter;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter.ListElement;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.viewModel.UtenteViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeUtenteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeUtenteFragment extends Fragment {

    private static final String TAG = HomeUtenteFragment.class.getSimpleName();
    private DiarioAdapter adapter;
    private UtenteViewModel viewModel;

    public HomeUtenteFragment() {
        // Required empty public constructor
    }

    public static HomeUtenteFragment newInstance(String param1, String param2) {
        HomeUtenteFragment fragment = new HomeUtenteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: onCreate è stato richiamato");

        super.onCreate(savedInstanceState);

        adapter = new DiarioAdapter(getActivity());
        adapter.setHomeUtenteFragment(this);
        viewModel = new ViewModelProvider(getActivity()).get(UtenteViewModel.class);
        viewModel.setDiarioAdapter(adapter);


        // recuperiamo il diario

        Calendar calendar = Calendar.getInstance();
        Date today = new Date(calendar.getTimeInMillis());

        //Date today = Date.valueOf("2023-06-16");
        viewModel.getDiario(today);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: onCreateView è stato richiamato");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_utente, container, false);

        RecyclerView rv = v.findViewById(R.id.rvDiario);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);

        // settiamo il listener sul bottone, se il bottone viene cliccato andiamo sul fragment di aggiunta alimento
        Button btAlimento = v.findViewById(R.id.btAggiungiAlimentoUtente);
        btAlimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HomeUtenteFragment.this).navigate(R.id.action_homeUntenteFragment_to_inserimentoAlimentoFragment);
            }
        });
        return v;
    }

    public void navigateToDialog(Bundle dati) {
        NavHostFragment.findNavController(this).navigate(R.id.action_homeUtenteFragment_to_modificaQuantitaFragmentDialog,dati);
    }
}