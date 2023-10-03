package it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.viewModel.NutrizionistaViewModel;
import it.uniroma2.pjdm.androidstudio.kyf.utente.InserimentoAlimentoFragment;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter.ListElement;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter.RicercaAlimentoAdapter;

public class ListaIngredientiAdapter extends RecyclerView.Adapter<ListaIngredientiAdapter.ListaIngredientiVH> {

    private static ListaIngredientiAdapter lastCreated;
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ListElement> data;
    private NutrizionistaViewModel viewModel;

    public static ListaIngredientiAdapter getLastCreated(){
        return lastCreated;
    }

    public ListaIngredientiAdapter(Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = new ArrayList<ListElement>();
        lastCreated = this;
    }

    public void setNutrizionistaViewModel(NutrizionistaViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ListaIngredientiVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.riga_lista_ingredienti,parent,false);
        ListaIngredientiAdapter.ListaIngredientiVH vh= new ListaIngredientiAdapter.ListaIngredientiVH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ListaIngredientiVH holder, int position) {
        ListElement elemento = data.get(position);
        holder.tvNomeingrediente.setText(elemento.getNome());
        holder.tvQuantitaIngrediente.setText(elemento.getQuantita().toString() + "g");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public double getSommaQuantita() {
        double somma = 0;
        for(ListElement e:data){
            somma += e.getQuantita();
        }
        return somma;
    }

    public ArrayList<ListElement> getData() {
        return data;
    }

    public void add(ListElement listElement){
        data.add(listElement);
        notifyDataSetChanged();
    }


    class ListaIngredientiVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvNomeingrediente;
        TextView tvQuantitaIngrediente;
        FloatingActionButton button;

        public ListaIngredientiVH(@NonNull View itemView) {
            super(itemView);
            tvNomeingrediente = itemView.findViewById(R.id.tvNomeIngrediente);
            tvQuantitaIngrediente = itemView.findViewById(R.id.tvQuantitaIngrediente);
            button = itemView.findViewById(R.id.btEliminaIngrediente);
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // se si clicca su un pulsante si elimina l'ingrediente
            data.remove(getAdapterPosition());
            notifyDataSetChanged();
        }
    }


}
