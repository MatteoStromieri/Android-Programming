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

import java.sql.Date;
import java.util.Calendar;

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.utente.ListaNutrizionistiFragment;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.viewModel.UtenteViewModel;


public class InserimentoRichiestaFragmentDialog extends DialogFragment {

    private static final String TAG = InserimentoRichiestaFragmentDialog.class.getSimpleName();
    private static final String EMAIL = "email";


    private String email;
    private UtenteViewModel viewModel;


    public InserimentoRichiestaFragmentDialog() {
        // Required empty public constructor
    }

    public static InserimentoRichiestaFragmentDialog newInstance(String email) {
        Log.d(TAG, "newInstance: creato nuovo fragment con email " + email);
        InserimentoRichiestaFragmentDialog fragment = new InserimentoRichiestaFragmentDialog();
        Bundle args = new Bundle();
        args.putString(EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString(EMAIL);
        }
        viewModel = new ViewModelProvider(getActivity()).get(UtenteViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_inserimento_richiesta_dialog, container, false);

        // settiamo il listener del pulsante
        Button buttonInvia = v.findViewById(R.id.btInviaRichiesta);

        buttonInvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etDescrizione = v.findViewById(R.id.etDescrizione);
                String textDescrizione = etDescrizione.getText().toString();

                Calendar calendar = Calendar.getInstance();
                Date today = new Date(calendar.getTimeInMillis());

                viewModel.inviaRichiesta(email,textDescrizione,today.toString());
                // NavHostFragment.findNavController(InserimentoRichiestaFragmentDialog.this).navigate(R.id.action_inserimentoRichiestaFragmentDialog_to_homeUtenteFragment);
                getParentFragmentManager().popBackStack();
            }
        });

        return v;
    }
}