package it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.adapter.DiarioPazienteAdapter;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.adapter.PazientiAdapter;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.viewModel.NutrizionistaViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiarioPazienteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiarioPazienteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = DiarioPazienteFragment.class.getSimpleName();
    // non possiamo recuperare queste stringhe dal file strings.xml perchè siamo in un contesto statico
    private static final String EMAIL_UTENTE = "emailUtente";

    private static final String DATA = "data";

    private DiarioPazienteAdapter adapter;
    private NutrizionistaViewModel viewModel;
    private String emailUtente;
    private String data;

    public DiarioPazienteFragment() {
        // Required empty public constructor
    }

    public static DiarioPazienteFragment newInstance(String emailUtente, String data) {
        DiarioPazienteFragment fragment = new DiarioPazienteFragment();
        Bundle args = new Bundle();
        args.putString(EMAIL_UTENTE, emailUtente);
        args.putString(DATA, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            emailUtente = getArguments().getString(EMAIL_UTENTE);
            data = getArguments().getString(DATA);
        }
        adapter = new DiarioPazienteAdapter(getActivity());
        viewModel = new ViewModelProvider(getActivity()).get(NutrizionistaViewModel.class);
        /*
        Log.d(TAG, "onCreate: imposto l'adapter sul view model");
        viewModel.setDiarioPazienteAdapter(adapter);
        // adapter.setDiarioPazientefragment(this);
        viewModel.getDiario(emailUtente,data);
        */

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_diario_paziente, container, false);

        RecyclerView rv = v.findViewById(R.id.rvDiarioPaziente);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);

        Log.d(TAG, "onCreateView: imposto l'adapter sul view model");
        viewModel.setDiarioPazienteAdapter(adapter);
        // adapter.setDiarioPazientefragment(this);
        viewModel.getDiario(emailUtente,data);

        return v;
    }
}