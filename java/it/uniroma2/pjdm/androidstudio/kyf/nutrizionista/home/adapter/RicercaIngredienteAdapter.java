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
import it.uniroma2.pjdm.androidstudio.kyf.entity.AlimentoAstratto;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.RicercaIngredienteFragment;
import it.uniroma2.pjdm.androidstudio.kyf.utente.InserimentoAlimentoFragment;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter.ListElement;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter.RicercaAlimentoAdapter;

public class RicercaIngredienteAdapter extends RecyclerView.Adapter<RicercaIngredienteAdapter.RicercaIngredienteVH> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ListElement> data;
    private RicercaIngredienteFragment ricercaIngredienteFragment;


    public RicercaIngredienteAdapter(Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = new ArrayList<ListElement>();
    }

    public void setRicercaIngredienteFragment(RicercaIngredienteFragment ricercaIngredienteFragment){
        this.ricercaIngredienteFragment = ricercaIngredienteFragment;
    }

    @NonNull
    @Override
    public RicercaIngredienteAdapter.RicercaIngredienteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.riga_ricerca_alimento,parent,false);
        RicercaIngredienteAdapter.RicercaIngredienteVH vh= new RicercaIngredienteAdapter.RicercaIngredienteVH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RicercaIngredienteAdapter.RicercaIngredienteVH holder, int position) {
        ListElement elemento = data.get(position);
        holder.tvNomeAlimento.setText(elemento.getNome());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void clear(){
        data.clear();
        notifyDataSetChanged();
    }

    public void addList(ArrayList<AlimentoAstratto> listaAlimento){

        for(AlimentoAstratto alimentoAstratto : listaAlimento) {
            data.add(new ListElement(alimentoAstratto));
        }

        notifyDataSetChanged();
    }

    class RicercaIngredienteVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvNomeAlimento;
        FloatingActionButton button;

        public RicercaIngredienteVH(@NonNull View itemView) {
            super(itemView);
            tvNomeAlimento = itemView.findViewById(R.id.tvNomeAlimentoRicerca);
            button = itemView.findViewById(R.id.btAggiungiAlimento);
            button.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
           // qui fai l'onclick, prima diamo i parametri al fragment di dialogo
            // al nuovo fragment dobbiamo passare il list item selezionato
            Bundle dati = new Bundle();
            ListElement item = data.get(getAdapterPosition());
            // categoria e id
            dati.putAll(item.toBundle());
            ricercaIngredienteFragment.navigateToDialog(dati);

        }
    }
}
