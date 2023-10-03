package it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.entity.Richiesta;
import it.uniroma2.pjdm.androidstudio.kyf.entity.Utente;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.PazientiNutrizionistaFragment;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.viewModel.NutrizionistaViewModel;

public class PazientiAdapter extends RecyclerView.Adapter<PazientiAdapter.PazientiVH> {

    private static final String TAG = PazientiAdapter.class.getSimpleName();
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Utente> data;
    private NutrizionistaViewModel nutrizionistaViewModel;
    private PazientiNutrizionistaFragment pazientiNutrizionistaFragment;

    public PazientiAdapter(Context context){

        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = new ArrayList<Utente>();
    }

    @NonNull
    @Override
    public PazientiVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.riga_lista_pazienti,parent,false);
        PazientiAdapter.PazientiVH vh= new PazientiVH(v);
        return vh;
    }

    public void setNutrizionistaViewModel(NutrizionistaViewModel nutrizionistaViewModel) {
        this.nutrizionistaViewModel = nutrizionistaViewModel;
    }

    public void setPazientiNutrizionistaFragment(PazientiNutrizionistaFragment pazientiNutrizionistaFragment) {
        this.pazientiNutrizionistaFragment = pazientiNutrizionistaFragment;
    }

    @Override
    public void onBindViewHolder(@NonNull PazientiVH holder, int position) {
        Utente elemento = data.get(position);
        holder.tvNomeCognome.setText(elemento.getNome() + " " + elemento.getCognome());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void clear() {
        data.clear();
    }

    public void addList(ArrayList<Utente> results) {
        data.addAll(results);
        notifyDataSetChanged();
    }

    class PazientiVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvNomeCognome;
        FloatingActionButton btVisualizzaDiario;


        public PazientiVH(@NonNull View itemView) {
            super(itemView);
            tvNomeCognome = itemView.findViewById(R.id.tvNomeCognomePaziente);
            btVisualizzaDiario = itemView.findViewById(R.id.btDiarioPaziente);
            // settiamo gli onclick dei due bottoni
            btVisualizzaDiario.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            // qui lanciamo il fragment della visualizzazione del diario del paziente
            // gli dobbiamo dare come unico parametro la data e l'email del paziente
            // il nuovo fragment farà la richiesta al viewmodel
            // il view model aggiornerà l'adapter del nuovo fragment (diario)
            // qui dentro lanciamo la richiesta per recuperare il diario del paziente
            String emailPaziente = data.get(getAdapterPosition()).getEmail();
            Log.d(TAG, "onClick: l'email dell'paziente è " + emailPaziente);

            Calendar calendar = Calendar.getInstance();
            Date today = new Date(calendar.getTimeInMillis());

            Bundle dati = new Bundle();
            dati.putString(context.getString(R.string.email_utente), emailPaziente);
            dati.putString(context.getString(R.string.data),today.toString());

            pazientiNutrizionistaFragment.navigateToDiario(dati);
            // facciamo la chiamata al view model
            //nutrizionistaViewModel.getDiario(emailPaziente,today);
        }
    }
}
