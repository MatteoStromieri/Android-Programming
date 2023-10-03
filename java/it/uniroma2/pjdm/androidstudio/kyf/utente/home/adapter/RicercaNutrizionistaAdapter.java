package it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.utente.InserimentoAlimentoFragment;
import it.uniroma2.pjdm.androidstudio.kyf.utente.ListaNutrizionistiFragment;
import it.uniroma2.pjdm.kyf.entity.Nutrizionista;

import java.util.ArrayList;

public class RicercaNutrizionistaAdapter extends RecyclerView.Adapter<RicercaNutrizionistaAdapter.RicercaNutrizionistaVH> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Nutrizionista> data;
    private ListaNutrizionistiFragment listaNutrizionistiFragment;

    public RicercaNutrizionistaAdapter(Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = new ArrayList<Nutrizionista>();
    }
    @NonNull
    @Override
    public RicercaNutrizionistaAdapter.RicercaNutrizionistaVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.riga_lista_tuoi_nutrizionisti,parent,false);
        RicercaNutrizionistaAdapter.RicercaNutrizionistaVH vh= new RicercaNutrizionistaAdapter.RicercaNutrizionistaVH(v);
        return vh;
    }

    public void setListaNutrizionistiFragment(ListaNutrizionistiFragment listaNutrizionistiFragment) {
        this.listaNutrizionistiFragment = listaNutrizionistiFragment;
    }

    @Override
    public void onBindViewHolder(@NonNull RicercaNutrizionistaAdapter.RicercaNutrizionistaVH holder, int position) {
        Nutrizionista elemento = data.get(position);
        holder.tvNomeCognome.setText(elemento.getNome()+" "+elemento.getCognome());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void clear(){
        data.clear();
    }

    public void addList(ArrayList<Nutrizionista> results) {
        data.addAll(results);
    }

    class RicercaNutrizionistaVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvNomeCognome;
        FloatingActionButton button;

        public RicercaNutrizionistaVH(@NonNull View itemView) {
            super(itemView);
            tvNomeCognome = itemView.findViewById(R.id.tvNomeCognome);
            button = itemView.findViewById(R.id.btAddNutrizionista);
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // qui fai l'onclick, prima diamo i parametri al fragment di dialogo
            // l'unico parametro da dare è la email, perchè la descrizione se la prende direttamente l'altro fragment di dialogo
            Bundle dati = new Bundle();
            Nutrizionista nutrizionista = data.get(getAdapterPosition());
            // categoria e id
            dati.putString("email",nutrizionista.getEmail());

            listaNutrizionistiFragment.navigateToDialog(dati);

        }
    }
}
