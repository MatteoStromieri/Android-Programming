package it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.entity.AlimentoAstratto;
import it.uniroma2.pjdm.androidstudio.kyf.entity.Pair;
import it.uniroma2.pjdm.androidstudio.kyf.entity.Pasto;
import it.uniroma2.pjdm.androidstudio.kyf.utente.HomeUtenteFragment;

public class DiarioAdapter extends RecyclerView.Adapter<DiarioAdapter.DiarioVH>{

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ListElement> data;
    private HomeUtenteFragment homeUtenteFragment;


    public DiarioAdapter(Context context){

        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = new ArrayList<ListElement>();
    }

    public void setHomeUtenteFragment(HomeUtenteFragment homeUtenteFragment) {
        this.homeUtenteFragment = homeUtenteFragment;
    }

    @NonNull
    @Override
    public DiarioVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.riga_diario,parent,false);
        DiarioVH vh= new DiarioVH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull DiarioVH holder, int position) {
        ListElement elemento = data.get(position);
        holder.tvNomeAlimento.setText(elemento.getNome());
        holder.tvQuantita.setText(elemento.getQuantita()*100 + "g");
        holder.tvPasto.setText(elemento.getPasto());
    }

    public void add(Pasto pasto){
        // per ogni alimento in pasto facciamo l'inserimento dentro la lista
        String categoria = pasto.getCategoria();

        AlimentoAstratto alimento;
        for(Pair pair: pasto.getAlimenti()){
            alimento = pair.getAlimento();
            data.add(new ListElement(pair,categoria));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void clear() {
        data.clear();
    }

    class DiarioVH extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        TextView tvNomeAlimento;
        TextView tvPasto;
        TextView tvQuantita;
        public DiarioVH(@NonNull View itemView) {
            super(itemView);
            tvNomeAlimento = itemView.findViewById(R.id.tvNomeAlimentoRicerca);
            tvPasto = itemView.findViewById(R.id.tvPastoDiario);
            tvQuantita = itemView.findViewById(R.id.tvQuantitaAlimentoDiario);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            Bundle dati = new Bundle();
            ListElement item = data.get(getAdapterPosition());
            // inseriamo nel bundle categoria, idAlimento, pasto
            dati.putString(context.getString(R.string.categoria),item.getTipologia());
            dati.putInt(context.getString(R.string.id_alimento),item.getId());
            dati.putString(context.getString(R.string.pasto),item.getPasto());

            homeUtenteFragment.navigateToDialog(dati);
            return false;
        }
    }
}
