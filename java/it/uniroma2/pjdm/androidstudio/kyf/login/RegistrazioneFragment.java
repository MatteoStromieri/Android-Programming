package it.uniroma2.pjdm.androidstudio.kyf.login;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.login.viewModel.AccessoViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrazioneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrazioneFragment extends Fragment {

    private AccessoViewModel viewModel;

    public RegistrazioneFragment() {
        // Required empty public constructor
    }

    public static RegistrazioneFragment newInstance(String param1, String param2) {
        RegistrazioneFragment fragment = new RegistrazioneFragment();
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
        View v = inflater.inflate(R.layout.fragment_registrazione, container, false);
        viewModel = new ViewModelProvider(getActivity()).get(AccessoViewModel.class);
        viewModel.setRegistrazioneFragment(this);

        Button btRegistrazione = v.findViewById(R.id.btRegistrati);

        // settiamo il listener sul bottone
        // dobbiamo testare  nome, cognome, email, password e data
        btRegistrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etEmail = v.findViewById(R.id.etEmailRegistrazione);
                EditText etPassword = v.findViewById(R.id.etPasswordRegistrazione);
                EditText etNome = v.findViewById(R.id.etNomeRegistrazione);
                EditText etCognome = v.findViewById(R.id.etCognomeRegistrazione);
                EditText etData = v.findViewById(R.id.etDataRegistrazione);
                Switch switchNutrizionista = v.findViewById(R.id.switchNutrizionistaRegistrazione);

                String strEmail = etEmail.getText().toString();
                String strPassword = etPassword.getText().toString();
                String strNome = etNome.getText().toString();
                String strCognome = etCognome.getText().toString();
                String strData = etData.getText().toString();


                Log.d("MS", "mail:" + etEmail.getText().toString());

                if(!ValidazioneCampiAccessoRegistrazione.isEmailValida(strEmail)){
                    // rendiamo etMail rosso
                    etEmail.setTextColor(ContextCompat.getColorStateList(RegistrazioneFragment.this.getContext(), R.color.red_error));
                    etEmail.setBackgroundTintList(ContextCompat.getColorStateList(RegistrazioneFragment.this.getContext(), R.color.red_error));
                } else {
                    etEmail.setTextColor(ContextCompat.getColorStateList(RegistrazioneFragment.this.getContext(), R.color.black));
                    etEmail.setBackgroundTintList(ContextCompat.getColorStateList(RegistrazioneFragment.this.getContext(), R.color.black));
                }

                if(!ValidazioneCampiAccessoRegistrazione.isPasswordValida(strPassword)){
                    // rendiamo etMail rosso
                    etPassword.setTextColor(ContextCompat.getColorStateList(RegistrazioneFragment.this.getContext(), R.color.red_error));
                    etPassword.setBackgroundTintList(ContextCompat.getColorStateList(RegistrazioneFragment.this.getContext(), R.color.red_error));
                } else {
                    etPassword.setTextColor(ContextCompat.getColorStateList(RegistrazioneFragment.this.getContext(), R.color.black));
                    etPassword.setBackgroundTintList(ContextCompat.getColorStateList(RegistrazioneFragment.this.getContext(), R.color.black));
                }

                if(!ValidazioneCampiAccessoRegistrazione.isNomeValido(strNome)){
                    // rendiamo etMail rosso
                    etNome.setTextColor(ContextCompat.getColorStateList(RegistrazioneFragment.this.getContext(), R.color.red_error));
                    etNome.setBackgroundTintList(ContextCompat.getColorStateList(RegistrazioneFragment.this.getContext(), R.color.red_error));
                } else {
                    etNome.setTextColor(ContextCompat.getColorStateList(RegistrazioneFragment.this.getContext(), R.color.black));
                    etNome.setBackgroundTintList(ContextCompat.getColorStateList(RegistrazioneFragment.this.getContext(), R.color.black));
                }

                if(!ValidazioneCampiAccessoRegistrazione.isCognomeValido(strCognome)){
                    // rendiamo etMail rosso
                    etCognome.setTextColor(ContextCompat.getColorStateList(RegistrazioneFragment.this.getContext(), R.color.red_error));
                    etCognome.setBackgroundTintList(ContextCompat.getColorStateList(RegistrazioneFragment.this.getContext(), R.color.red_error));
                } else {
                    etCognome.setTextColor(ContextCompat.getColorStateList(RegistrazioneFragment.this.getContext(), R.color.black));
                    etCognome.setBackgroundTintList(ContextCompat.getColorStateList(RegistrazioneFragment.this.getContext(), R.color.black));
                }

                if(!ValidazioneCampiAccessoRegistrazione.isDataValida(strData)){
                    // rendiamo etMail rosso
                    etData.setTextColor(ContextCompat.getColorStateList(RegistrazioneFragment.this.getContext(), R.color.red_error));
                    etData.setBackgroundTintList(ContextCompat.getColorStateList(RegistrazioneFragment.this.getContext(), R.color.red_error));
                } else {
                    etData.setTextColor(ContextCompat.getColorStateList(RegistrazioneFragment.this.getContext(), R.color.black));
                    etData.setBackgroundTintList(ContextCompat.getColorStateList(RegistrazioneFragment.this.getContext(), R.color.black));
                }

                if(ValidazioneCampiAccessoRegistrazione.isTuttoValido(strNome,strCognome,strEmail,strPassword,strData)){
                    if(switchNutrizionista.isChecked())
                        viewModel.register(strEmail,strCognome,strNome,strData,strPassword,getString(R.string.nutrizionista_stringa));
                    else
                        viewModel.register(strEmail,strCognome,strNome,strData,strPassword,getString(R.string.utente_stringa));
                }
            }
        });

        return v;
    }

    public void navigateToAccessoFragment(){
        NavHostFragment.findNavController(this).navigate(R.id.action_registrazioneFragment_to_accessoFragment);
    }
}