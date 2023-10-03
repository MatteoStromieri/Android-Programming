package it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.entity.AlimentoAstratto;
import it.uniroma2.pjdm.androidstudio.kyf.utente.HomeUtenteFragment;
import it.uniroma2.pjdm.androidstudio.kyf.utente.InserimentoAlimentoFragment;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.RaccoltaDatiAlimentoPastoFragment;

public class RicercaAlimentoAdapter extends RecyclerView.Adapter<RicercaAlimentoAdapter.RicercaAlimentoVH> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ListElement> data;
    private InserimentoAlimentoFragment inserimentoAlimentoFragment;


    public RicercaAlimentoAdapter(Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = new ArrayList<ListElement>();
    }

    public void setInserimentoAlimentoFragment(InserimentoAlimentoFragment inserimentoAlimentoFragment){
        this.inserimentoAlimentoFragment = inserimentoAlimentoFragment;
    }

    @NonNull
    @Override
    public RicercaAlimentoAdapter.RicercaAlimentoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.riga_ricerca_alimento,parent,false);
        RicercaAlimentoAdapter.RicercaAlimentoVH vh= new RicercaAlimentoAdapter.RicercaAlimentoVH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RicercaAlimentoAdapter.RicercaAlimentoVH holder, int position) {
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

    class RicercaAlimentoVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvNomeAlimento;
        FloatingActionButton button;

        public RicercaAlimentoVH(@NonNull View itemView) {
            super(itemView);
            tvNomeAlimento = itemView.findViewById(R.id.tvNomeAlimentoRicerca);
            button = itemView.findViewById(R.id.btAggiungiAlimento);
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // qui fai l'onclick, prima diamo i parametri al fragment di dialogo
            Bundle dati = new Bundle();
            ListElement item = data.get(getAdapterPosition());
            // categoria e id
            dati.putString(context.getString(R.string.categoria),item.getTipologia());
            dati.putInt(context.getString(R.string.id_alimento),item.getId());
            inserimentoAlimentoFragment.navigateToDialog(dati);

        }
    }
}
