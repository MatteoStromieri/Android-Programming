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

import org.w3c.dom.Text;

import java.text.CollationElementIterator;
import java.util.ArrayList;

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.entity.Richiesta;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.viewModel.NutrizionistaViewModel;
import it.uniroma2.pjdm.androidstudio.kyf.utente.HomeUtenteFragment;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter.DiarioAdapter;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter.ListElement;

public class RichiesteAdapter extends RecyclerView.Adapter<RichiesteAdapter.RichiesteVH>{

    private static final String TAG = RichiesteAdapter.class.getSimpleName();
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Richiesta> data;
    private NutrizionistaViewModel nutrizionistaViewModel;

    public RichiesteAdapter(Context context){

        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = new ArrayList<Richiesta>();
    }

    public void setNutrizionistaViewModel(NutrizionistaViewModel nutrizionistaViewModel) {
        this.nutrizionistaViewModel = nutrizionistaViewModel;
    }

    @NonNull
    @Override
    public RichiesteAdapter.RichiesteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.riga_lista_richieste,parent,false);
        RichiesteAdapter.RichiesteVH vh= new RichiesteAdapter.RichiesteVH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RichiesteAdapter.RichiesteVH holder, int position) {
        Richiesta elemento = data.get(position);
        holder.tvNomeCognome.setText(elemento.getMittente().getNome() + " " + elemento.getMittente().getCognome());
        holder.tvDescrizione.setText(elemento.getDescrizione());
        }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void clear() {
        data.clear();
    }

    public void addList(ArrayList<Richiesta> results) {
        data.addAll(results);
        notifyDataSetChanged();
    }

    class RichiesteVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvNomeCognome;
        TextView tvDescrizione;
        FloatingActionButton btAccettaRichiesta, btRifiutaRichiesta;


        public RichiesteVH(@NonNull View itemView) {
            super(itemView);
            tvNomeCognome = itemView.findViewById(R.id.tvNomeCognomeRichiesta);
            tvDescrizione = itemView.findViewById(R.id.tvDescrizioneRichiesta);
            btAccettaRichiesta = itemView.findViewById(R.id.btAddRichiesta);
            btRifiutaRichiesta = itemView.findViewById(R.id.btRemoveRichiesta);
            // settiamo gli onclick dei due bottoni
            btAccettaRichiesta.setOnClickListener(this);
            btRifiutaRichiesta.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            // facciamo due cose diverse in base all'id
            String emailMittente = data.get(getAdapterPosition()).getMittente().getEmail();
            Log.d(TAG, "onClick: l'email dell'utente è " + emailMittente);
            if(view.getId() == R.id.btAddRichiesta){
                nutrizionistaViewModel.accettaRichiesta(emailMittente);
            } else if (view.getId() == R.id.btRemoveRichiesta){
                nutrizionistaViewModel.rifiutaRichiesta(emailMittente);
            }

        }
    }
}
