package it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.entity.AlimentoAstratto;
import it.uniroma2.pjdm.androidstudio.kyf.entity.Pair;
import it.uniroma2.pjdm.androidstudio.kyf.entity.Pasto;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter.DiarioAdapter;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter.ListElement;

public class DiarioPazienteAdapter extends RecyclerView.Adapter<DiarioPazienteAdapter.DiarioPazienteVH>{

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ListElement> data;

    public DiarioPazienteAdapter(Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = new ArrayList<ListElement>();
    }

    @NonNull
    @Override
    public DiarioPazienteAdapter.DiarioPazienteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.riga_diario,parent,false);
        DiarioPazienteAdapter.DiarioPazienteVH vh= new DiarioPazienteAdapter.DiarioPazienteVH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull DiarioPazienteAdapter.DiarioPazienteVH holder, int position) {
        ListElement elemento = data.get(position);
        holder.tvNomeAlimento.setText(elemento.getNome());
        holder.tvQuantita.setText(elemento.getQuantita()*100 + context.getString(R.string.grammi_unita_misura));
        holder.tvPasto.setText(elemento.getPasto());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void clear() {
        data.clear();
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

    class DiarioPazienteVH extends RecyclerView.ViewHolder {

        TextView tvNomeAlimento;
        TextView tvPasto;
        TextView tvQuantita;
        public DiarioPazienteVH(@NonNull View itemView) {
            super(itemView);
            tvNomeAlimento = itemView.findViewById(R.id.tvNomeAlimentoRicerca);
            tvPasto = itemView.findViewById(R.id.tvPastoDiario);
            tvQuantita = itemView.findViewById(R.id.tvQuantitaAlimentoDiario);
        }
    }
}
