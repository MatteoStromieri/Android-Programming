package it.uniroma2.pjdm.androidstudio.kyf.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import it.uniroma2.pjdm.androidstudio.kyf.R;
/*
* In questo fragment dobbiamo semplicemente inserire i listener sui due bottoni e fare i cambi di schermata
*
* */
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryPointFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryPointFragment extends Fragment {

    public EntryPointFragment() {
        // Required empty public constructor
    }

    public static EntryPointFragment newInstance() {
        EntryPointFragment fragment = new EntryPointFragment();
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
        View v = inflater.inflate(R.layout.fragment_entry_point, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // settiamo i listener per i due bottoni
        Button accediButton = view.findViewById(R.id.btAccediAccount);
        Button registratiButton = view.findViewById(R.id.btRegistratiAccount);

        accediButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // questa è la classe che ci permette di passare da un fragment all'altro
                NavHostFragment.findNavController(EntryPointFragment.this).navigate(R.id.action_entryPointFragment_to_accessoFragment);
            }
        });

        registratiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // questa è la classe che ci permette di passare da un fragment all'altro
                NavHostFragment.findNavController(EntryPointFragment.this).navigate(R.id.action_entryPointFragment_to_registrazioneFragment);
            }
        });
    }
}