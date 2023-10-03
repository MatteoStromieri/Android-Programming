package it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.AggiungiAlimentoCompostoFragment;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.viewModel.NutrizionistaViewModel;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter.ListElement;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.viewModel.UtenteViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RaccoltaQuantitaIngredienteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RaccoltaQuantitaIngredienteFragment extends DialogFragment {

    private static final String TAG = RaccoltaQuantitaIngredienteFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NOME = "nome";
    private static final String QUANTITA = "quantita";
    private static final String PASTO = "pasto";
    private static final String ID = "id";
    private static final String TIPOLOGIA = "tipologia";

    // TODO: Rename and change types of parameters
    private String nome;
    private Double quantita;
    private String pasto;
    private int id;
    private String tipologia;
    private NutrizionistaViewModel nutrizionistaViewModel;

    public RaccoltaQuantitaIngredienteFragment() {
        // Required empty public constructor
    }


    public static RaccoltaQuantitaIngredienteFragment newInstance(String nome, double quantita, String pasto, int id, String tipologia) {
        RaccoltaQuantitaIngredienteFragment fragment = new RaccoltaQuantitaIngredienteFragment();
        Bundle args = new Bundle();
        args.putString("nome",nome);
        args.putDouble("quantita", quantita);
        args.putString("pasto",pasto);
        args.putInt("id",id);
        args.putString("tipologia",tipologia);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nome = getArguments().getString(NOME);
            quantita = getArguments().getDouble(QUANTITA);
            pasto = getArguments().getString(PASTO);
            id = getArguments().getInt(ID);
            tipologia = getArguments().getString(TIPOLOGIA);
        }
        nutrizionistaViewModel = new ViewModelProvider(getActivity()).get(NutrizionistaViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_raccolta_quantita_ingrediente, container, false);

        EditText tvQuantita = v.findViewById(R.id.etQuantitaIngrediente);
        Button button = v.findViewById(R.id.btAggiungiIngredienteConQuantita);
        // settiamo il listener
        // quando il bottone viene cliccato dobbiamo aggiungere l'alimento all'adapter degli ingredienti
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // prendiamo la nostra itemlist
                if(tvQuantita.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), getString(R.string.inserisci_quantita), Toast.LENGTH_LONG).show();
                    return;
                }
                quantita = Double.parseDouble(tvQuantita.getText().toString());
                ListElement element = new ListElement(nome,quantita,pasto,id,tipologia);

                // dopo aver fatto la richiesta torniamo al fragment di inserimento alimento
                nutrizionistaViewModel.listaIngredientiAdapter.add(element);
                getParentFragmentManager().popBackStack();
                // NavHostFragment.findNavController(RaccoltaQuantitaIngredienteFragment.this).navigate(R.id.action_raccoltaQuantitaIngredienteFragment_to_ricercaIngredienteFragment);

            }
        });

        return v;
    }
}