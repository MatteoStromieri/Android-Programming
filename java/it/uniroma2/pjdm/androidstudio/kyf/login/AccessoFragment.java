package it.uniroma2.pjdm.androidstudio.kyf.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.login.viewModel.AccessoViewModel;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.MainActivityNutrizionista;
import it.uniroma2.pjdm.androidstudio.kyf.utente.MainActivityUtente;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccessoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccessoFragment extends Fragment {

    private AccessoViewModel viewModel;

    public AccessoFragment() {
        // Required empty public constructor
    }


    public static AccessoFragment newInstance(String param1, String param2) {
        AccessoFragment fragment = new AccessoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_accesso, container, false);
        viewModel = new ViewModelProvider(getActivity()).get(AccessoViewModel.class);
        viewModel.setAccessoFragment(this);

        // settiamo il listener sul bottone
        Button btAccesso = v.findViewById(R.id.btAccedi);

        btAccesso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // dobbiamo verificare che tutti i campi siano stati compilati nel modo giusto
                EditText etEmail = v.findViewById(R.id.etEmailAccesso);
                EditText etPassword = v.findViewById(R.id.etPasswordAccesso);
                Switch switchNutrizionista = v.findViewById(R.id.nutrizionistaSwitch);

                String strEmail = etEmail.getText().toString();
                String strPassword = etPassword.getText().toString();

                // verifichiamo se la email è benformata
                if(!ValidazioneCampiAccessoRegistrazione.isEmailValida(strEmail)){
                    // rendiamo etMail rosso
                    etEmail.setTextColor(ContextCompat.getColorStateList(AccessoFragment.this.getContext(), R.color.red_error));
                    etEmail.setBackgroundTintList(ContextCompat.getColorStateList(AccessoFragment.this.getContext(), R.color.red_error));
                } else {
                    etEmail.setTextColor(ContextCompat.getColorStateList(AccessoFragment.this.getContext(), R.color.black));
                    etEmail.setBackgroundTintList(ContextCompat.getColorStateList(AccessoFragment.this.getContext(), R.color.black));
                }

                // verifichiamo che la password sia benformata
                if(!ValidazioneCampiAccessoRegistrazione.isPasswordValida(strPassword)){
                    // rendiamo etMail rosso
                    etPassword.setTextColor(ContextCompat.getColorStateList(AccessoFragment.this.getContext(), R.color.red_error));
                    etPassword.setBackgroundTintList(ContextCompat.getColorStateList(AccessoFragment.this.getContext(), R.color.red_error));
                } else {
                    etPassword.setTextColor(ContextCompat.getColorStateList(AccessoFragment.this.getContext(), R.color.black));
                    etPassword.setBackgroundTintList(ContextCompat.getColorStateList(AccessoFragment.this.getContext(), R.color.black));
                }

                if(ValidazioneCampiAccessoRegistrazione.isTuttoValido(strEmail,strPassword)){
                    Log.d("tag", "contatto il view model");
                    if(switchNutrizionista.isChecked())
                        viewModel.accesso(strEmail,strPassword,getString(R.string.nutrizionista_stringa));
                    else
                        viewModel.accesso(strEmail,strPassword,getString(R.string.utente_stringa));
                }
            }
        });

        return v;
    }

    public void launchNewActivity(String categoriaAccount){
        Intent nuovaActivity;
        if(categoriaAccount.equals(getString(R.string.utente_stringa))){
            nuovaActivity = new Intent(getActivity(), MainActivityUtente.class);
        } else if (categoriaAccount.equals(getString(R.string.nutrizionista_stringa))){
            nuovaActivity = new Intent(getActivity(), MainActivityNutrizionista.class);
        } else {
            return;
        }
        startActivity(nuovaActivity);
    }
}