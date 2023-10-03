package it.uniroma2.pjdm.androidstudio.kyf.utente.home.viewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.entity.AlimentoAstratto;
import it.uniroma2.pjdm.androidstudio.kyf.entity.Pasto;
import it.uniroma2.pjdm.androidstudio.kyf.login.viewModel.EmptyJsonObjectRequest;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter.DiarioAdapter;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter.RicercaAlimentoAdapter;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter.RicercaNutrizionistaAdapter;
import it.uniroma2.pjdm.kyf.entity.Nutrizionista;

public class UtenteViewModel extends AndroidViewModel {
    private static final String TAG = UtenteViewModel.class.getSimpleName();
    public Context context;
    public RicercaAlimentoAdapter ricercaAlimentoAdapter;
    public DiarioAdapter diarioAdapter;
    public RicercaNutrizionistaAdapter ricercaNutrizionistaAdapter;

    public UtenteViewModel(@NonNull Application application) {
        super(application);
        context = getApplication().getApplicationContext();
    }

    public void setRicercaAlimentoAdapter(RicercaAlimentoAdapter ricercaAlimentoAdapter) {
        this.ricercaAlimentoAdapter = ricercaAlimentoAdapter;
    }

    public void setDiarioAdapter(DiarioAdapter diarioAdapter) {
        this.diarioAdapter = diarioAdapter;
    }

    public void setRicercaNutrizionistaAdapter(RicercaNutrizionistaAdapter ricercaNutrizionistaAdapter) {
        this.ricercaNutrizionistaAdapter = ricercaNutrizionistaAdapter;
    }

    /*
     * funzione di recupero del diario
     *
     *  per fare la chiamata ho bisogno di categoria: diario, token dalle shared_prefs, una data
     *
     * l'obiettivo parsare i pasti colazione, pranzo, merenda e cena
     * questi verranno dati al DiarioAdapter
     * */
    public void getDiario(Date data) {
        Log.d(TAG, "getDiario: inizio chiamata getDiario() in data " + data.toString());
        // prendiamo il nostro token
        Long token = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE).getLong("token", 0);

        Log.d(TAG, "getDiario: il token che è stato recuperato è pari a " + token);
        if (token == 0) {
            return;
        }
        // prendiamo l'url e inseriamo i nostri campi
        String url = context.getString(R.string.url_utente) + "?categoria=diario&data=" + data + "&token=" + token;
        Log.d(TAG, "getDiario url: " + url);

        /* una volta che abbiamo l'url possiamo fare la richiesta
            riceveremo come output un jsonObject, quindi facciamo una jsonrequest
         */
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // qui inseriamo il codice che viene eseguito quando ci viene ritornato il codice 200
                Log.d(TAG, "onRespose: è stato richiamato il metodo OnResponse");
                Log.d(TAG, "response: " + response.toString());

                // verifichiamo se sono presenti i campi colazione, pranzo, merenda e cena
                if (!response.has("colazione") || !response.has("pranzo") || !response.has("merenda") || !response.has("cena")) {
                    Log.d(TAG, "onRespose: risposta mal formata, alcuni parametri non sono presenti");
                    Toast.makeText(context, context.getString(R.string.errore_del_server), Toast.LENGTH_LONG).show();
                    return;
                }

                // se siamo arrivati fin qui possiamo prendere i vari campi e darli in pasto al metodo fromJsonObject della classe Pasto
                Pasto colazione, pranzo, merenda, cena;
                try {
                    colazione = Pasto.fromJsonObject(response.getJSONObject("colazione"));
                    pranzo = Pasto.fromJsonObject(response.getJSONObject("pranzo"));
                    merenda = Pasto.fromJsonObject(response.getJSONObject("merenda"));
                    cena = Pasto.fromJsonObject(response.getJSONObject("cena"));

                    // ora possiamo fare l'inserimento contattando il RicercaAlimentoAdapter
                    diarioAdapter.clear();
                    diarioAdapter.add(colazione);
                    diarioAdapter.add(pranzo);
                    diarioAdapter.add(merenda);
                    diarioAdapter.add(cena);
                    diarioAdapter.notifyDataSetChanged();

                    return;
                } catch (JSONException e) {
                    Log.d(TAG, "onRespose: risposta mal formata, alcuni parametri non sono presenti");
                    Toast.makeText(context, context.getString(R.string.errore_del_server), Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int codiceErrore = error.networkResponse.statusCode;
                Log.d("TAG", "onErrorResponse: " + error.getMessage());
                Log.d("TAG", "onErrorResponse: " + error.toString());
                switch (codiceErrore) {
                    case 400:
                        Toast.makeText(context, context.getString(R.string.richiesta_malformata), Toast.LENGTH_LONG).show();
                        break;
                    case 500:
                        Toast.makeText(context, context.getString(R.string.errore_del_server), Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(context, context.getString(R.string.errore_registrazione), Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }
        );

        // inserisco la richiesta dentro la coda
        Log.d(TAG, "getDiario: sto inserendo la richiesta dentro la coda");
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        queue.add(request);
        Log.d(TAG, "getDiario: ho inserito la richiesta nella coda");
    }

    /*
     * funzione di ricerca di un alimento/alimentoComposto
     *
     *  per fare la chiamata ho bisogno di categoria: query(testo), token dalle shared_prefs, categoria
     *
     *ci viene restituito un arraylist di alimento, quindi bisogna parsarlo, aggiornare il RicercaAlimentoAdapter e poi chiamare notify
     * */
    public void ricercaAlimento(String query, String categoriaAlimento) {
        // per prima cosa prendiamo il token dalle shared_prefs
        Long token = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE).getLong("token", 0);

        Log.d(TAG, "ricercaAlimento: il token che è stato recuperato è pari a " + token);
        if (token == 0) {
            return;
        }

        // prendiamo i nostri campi e formiamo l'url
        String url = context.getString(R.string.url_utente) + "?categoria=" + categoriaAlimento + "&query=" + query + "&token=" + token;

        // ora possiamo fare la richiesta, che sarà una JSONArrayRequest
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "onResponse: è stato richiamato il metodo onResponse");
                // andiamo a verificare che la ripsosta non sia vuota
                if (response == null) {
                    Log.d(TAG, "onRespose: risposta mal formata, la response ha un valore null");
                    Toast.makeText(context, context.getString(R.string.errore_del_server), Toast.LENGTH_LONG).show();
                    return;
                }

                // se la risposta non è null parsiamo ogni elemento e lo inseriamo dentro il RicercaAlimentoAdapter
                ArrayList<AlimentoAstratto> results = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonObjectAlimento = null;
                    try {
                        jsonObjectAlimento = response.getJSONObject(i);
                        results.add(AlimentoAstratto.fromJsonObject(jsonObjectAlimento));
                        Log.d(TAG, "onResponse: un elemento è stato parsato");
                    } catch (JSONException e) {
                        // se un alimento non riesce ad essere parsato per qualche motivo, viene ignorato
                        Log.d(TAG, "onResponse: un elemento non è stato parsato");
                        continue;
                    }
                }
                // ora puliamo la nostra lista
                Log.d(TAG, "onResponse: ripulisco la lista");
                ricercaAlimentoAdapter.clear();
                // ora aggiungiamo la lista dei nostri alimenti dentro l'adapter
                Log.d(TAG, "onResponse: inserisco i nuovi alimenti");
                ricercaAlimentoAdapter.addList(results);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int codiceErrore = error.networkResponse.statusCode;
                Log.d("TAG", "onErrorResponse: " + error.getMessage());
                Log.d("TAG", "onErrorResponse: " + error.toString());
                switch (codiceErrore) {
                    case 400:
                        Toast.makeText(context, context.getString(R.string.richiesta_malformata), Toast.LENGTH_LONG).show();
                        break;
                    case 401:
                        Toast.makeText(context, context.getString(R.string.credenziali_errate), Toast.LENGTH_LONG).show();
                        break;
                    case 500:
                        Toast.makeText(context, context.getString(R.string.errore_del_server), Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(context, context.getString(R.string.errore_registrazione), Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

        // inserisco la richiesta dentro la coda
        Log.d(TAG, "ricercaAlimento: sto inserendo la richiesta dentro la coda");
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        queue.add(request);
        Log.d(TAG, "ricercaAlimento: ho inserito la richiesta nella coda");
    }

    // viewModel.aggiungiAlimentoPasto(categoria,today.toString(),idAlimento,selectedItem,textQuantita);
    /*
     * vogliamo fare la richiesta e se ci arriva una risposta positiva procediamo ad aggiornare il diario
     * */
    public void aggiungiAlimentoPasto(String categoria, String data, int idAlimento, String pasto, String quantita) {
        // come prima cosa prendiamo il token
        Long token = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE).getLong("token", 0);

        Log.d(TAG, "AggiungiAlimentoPasto: il token che è stato recuperato è pari a " + token);
        if (token == 0) {
            return;
        }


        // http://localhost:8080/kyf/UtenteServlet?categoria=alimentoComposto&token=2810962854627907850&data=2023-06-24&idAlimento=1&pasto=merenda&quantita=1
        // prendiamo i nostri campi e formiamo l'url
        String url = context.getString(R.string.url_utente) + "?categoria=" + categoria + "&data=" + data + "&token=" + token + "&idAlimento=" + idAlimento + "&pasto=" + pasto + "&quantita=" + quantita;

        // ora possiamo procedere a inviare la richiesta, utilizzeremo la nostra richiesta custom perchè non ci aspettiamo dei risultati
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                //verifichiamo che la risopsta ci siano "alimenti", "data" e "categoria"

                if (!response.has("alimenti") || !response.has("data") || !response.has("categoria")) {
                    Log.d(TAG, "onRespose: risposta mal formata, almeno uno tra alimenti, data e categoria non è presente");
                    Toast.makeText(context, context.getString(R.string.errore_del_server), Toast.LENGTH_LONG).show();
                    return;
                }

                Log.d(TAG, "onResponse: codice 200");
                Log.d(TAG, "onResponse: " + response.toString());

                Toast.makeText(context, context.getString(R.string.alimento_aggiunto_con_successo), Toast.LENGTH_LONG).show();

                // prendiamo il pasto dalla risposta
                Pasto pasto;
                pasto = Pasto.fromJsonObject(response);

                diarioAdapter.add(pasto);
                diarioAdapter.notifyDataSetChanged();
                return;
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                int codiceErrore = error.networkResponse.statusCode;
                Log.d("TAG", "onErrorResponse: " + error.getMessage());
                Log.d("TAG", "onErrorResponse: " + error.toString());
                switch (codiceErrore) {
                    case 400:
                        Toast.makeText(context, context.getString(R.string.richiesta_malformata), Toast.LENGTH_LONG).show();
                        break;
                    case 401:
                        Toast.makeText(context, context.getString(R.string.credenziali_errate), Toast.LENGTH_LONG).show();
                        break;
                    case 500:
                        Toast.makeText(context, context.getString(R.string.alimento_duplicato), Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(context, context.getString(R.string.errore_registrazione), Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        // inserisco la richiesta dentro la coda
        Log.d(TAG, "aggiungiAlimentoPasto: sto inserendo la richiesta dentro la coda");
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        queue.add(request);
        Log.d(TAG, "aggiungiAlimentoPasto: ho inserito la richiesta nella coda");
    }

    // categoria,idAlimento,textQuantita,today.toString(),pasto

    public void modificaAlimento(String categoria, int idAlimento, String quantita, String data, String pasto) {
        // come prima cosa prendiamo il token
        Long token = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE).getLong("token", 0);

        Log.d(TAG, "AggiungiAlimentoPasto: il token che è stato recuperato è pari a " + token);
        if (token == 0) {
            return;
        }

        // http://localhost:8080/kyf/UtenteServlet?categoria=alimentoComposto&token=2810962854627907850&data=2023-06-24&idAlimento=1&pasto=merenda&quantita=1
        // prendiamo i nostri campi e formiamo l'url
        String url = context.getString(R.string.url_utente) + "?categoria=" + categoria + "&data=" + data + "&token=" + token + "&idAlimento=" + idAlimento + "&pasto=" + pasto + "&quantita=" + quantita;

        // procediamo con la richiesta, non ci aspettiamo risultati e quindi usiamo la soluzione custom
        EmptyJsonObjectRequest request = new EmptyJsonObjectRequest(Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // se arriviamo qui significa che abbiamo ricevuto il codice di stato 200

                Log.d(TAG, "onResponse: codice 200");
                Log.d(TAG, "onResponse: " + response.toString());

                Calendar calendar = Calendar.getInstance();
                Date today = new Date(calendar.getTimeInMillis());

                Toast.makeText(context, context.getString(R.string.aggiornamento_avvenuto_con_successo), Toast.LENGTH_LONG).show();
                UtenteViewModel.this.getDiario(today);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int codiceErrore = error.networkResponse.statusCode;
                Log.d(TAG, "onErrorResponse: " + error.getMessage());
                Log.d(TAG, "onErrorResponse: " + error.toString());
                switch (codiceErrore) {
                    case 400:
                        Toast.makeText(context, context.getString(R.string.richiesta_malformata), Toast.LENGTH_LONG).show();
                        break;
                    case 401:
                        Toast.makeText(context, context.getString(R.string.credenziali_errate), Toast.LENGTH_LONG).show();
                        break;
                    case 500:
                        Toast.makeText(context, context.getString(R.string.errore_del_server), Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(context, context.getString(R.string.errore_registrazione), Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        // inserisco la richiesta dentro la coda
        Log.d(TAG, "modificaAlimento: sto inserendo la richiesta dentro la coda");
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        queue.add(request);
        Log.d(TAG, "modificaAlimento: ho inserito la richiesta nella coda");
    }


    public void eliminaAlimento(String categoria, int idAlimento, String data, String pasto) {
        // come prima cosa prendiamo il token
        Long token = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE).getLong("token", 0);

        Log.d(TAG, "EliminaAlimento: il token che è stato recuperato è pari a " + token);
        if (token == 0) {
            return;
        }

        // prendiamo i nostri campi e formiamo l'url
        String url = context.getString(R.string.url_utente) + "?categoria=" + categoria + "&data=" + data + "&token=" + token + "&idAlimento=" + idAlimento + "&pasto=" + pasto;

        // procediamo con la richiesta, non ci aspettiamo risultati e quindi usiamo la soluzione custom
        EmptyJsonObjectRequest request = new EmptyJsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // se arriviamo qui significa che abbiamo ricevuto il codice di stato 200

                Log.d(TAG, "onResponse: codice 200");
                Log.d(TAG, "onResponse: " + response.toString());

                Calendar calendar = Calendar.getInstance();
                Date today = new Date(calendar.getTimeInMillis());

                Toast.makeText(context, context.getString(R.string.aggiornamento_avvenuto_con_successo), Toast.LENGTH_LONG).show();
                UtenteViewModel.this.getDiario(today);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int codiceErrore = error.networkResponse.statusCode;
                Log.d(TAG, "onErrorResponse: " + error.getMessage());
                Log.d(TAG, "onErrorResponse: " + error.toString());
                switch (codiceErrore) {
                    case 400:
                        Toast.makeText(context, context.getString(R.string.richiesta_malformata), Toast.LENGTH_LONG).show();
                        break;
                    case 401:
                        Toast.makeText(context, context.getString(R.string.credenziali_errate), Toast.LENGTH_LONG).show();
                        break;
                    case 500:
                        Toast.makeText(context, context.getString(R.string.errore_del_server), Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(context, context.getString(R.string.errore_registrazione), Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        // inserisco la richiesta dentro la coda
        Log.d(TAG, "eliminaAlimento: sto inserendo la richiesta dentro la coda");
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        queue.add(request);
        Log.d(TAG, "eliminaAlimento: ho inserito la richiesta nella coda");
    }

    public void ricercaNutrizionista(String queryNome, String queryCognome) {
        // prendiamo come prima cosa il nostro token
        Long token = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE).getLong("token", 0);

        Log.d(TAG, "EliminaAlimento: il token che è stato recuperato è pari a " + token);
        if (token == 0) {
            return;
        }

        // prendiamo i nostri campi e formiamo l'url
        String url = context.getString(R.string.url_utente) + "?categoria=nutrizionista&token=" + token + "&queryNome=" + queryNome + "&queryCognome=" + queryCognome;

        //facciamo la nostra get, ci aspettiamo un jsonArray
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "onResponse: è stato richiamato il metodo onResponse");
                // andiamo a verificare che la ripsosta non sia vuota
                if (response == null) {
                    Log.d(TAG, "onRespose: risposta mal formata, la response ha un valore null");
                    Toast.makeText(context, context.getString(R.string.errore_del_server), Toast.LENGTH_LONG).show();
                    return;
                }

                // se la risposta non è null parsiamo ogni elemento e lo inseriamo dentro il RicercaAlimentoAdapter
                ArrayList<Nutrizionista> results = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonObjectNutrizionista = null;
                    try {
                        jsonObjectNutrizionista = response.getJSONObject(i);
                    } catch (JSONException e) {
                        // se un alimento non riesce ad essere parsato per qualche motivo, viene ignorato
                        Log.d(TAG, "onResponse: un elemento non è stato parsato");
                    }

                    results.add(Nutrizionista.fromJsonObject(jsonObjectNutrizionista));
                    Log.d(TAG, "onResponse: un elemento è stato parsato");
                }
                // ora puliamo la nostra lista
                Log.d(TAG, "onResponse: ripulisco la lista");
                ricercaNutrizionistaAdapter.clear();
                // ora aggiungiamo la lista dei nostri alimenti dentro l'adapter
                Log.d(TAG, "onResponse: inserisco i nuovi alimenti");
                ricercaNutrizionistaAdapter.addList(results);
                ricercaNutrizionistaAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int codiceErrore = error.networkResponse.statusCode;
                Log.d("TAG", "onErrorResponse: " + error.getMessage());
                Log.d("TAG", "onErrorResponse: " + error.toString());
                switch (codiceErrore) {
                    case 400:
                        Toast.makeText(context, context.getString(R.string.richiesta_malformata), Toast.LENGTH_LONG).show();
                        break;
                    case 401:
                        Toast.makeText(context, context.getString(R.string.credenziali_errate), Toast.LENGTH_LONG).show();
                        break;
                    case 500:
                        Toast.makeText(context, context.getString(R.string.errore_del_server), Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(context, context.getString(R.string.errore_registrazione), Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        Log.d(TAG, "ricercaNutrizionista: sto inserendo la richiesta dentro la coda");
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        queue.add(request);
        Log.d(TAG, "ricercaNutrizionista: ho inserito la richiesta nella coda");
    }

    public void inviaRichiesta(String email, String textDescrizione, String data) {
        // come prima cosa prendiamo il token
        Long token = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE).getLong("token", 0);

        Log.d(TAG, "InviaRichiesta: il token che è stato recuperato è pari a " + token);
        if (token == 0) {
            return;
        }

        // prendiamo i nostri campi e formiamo l'url
        String url = context.getString(R.string.url_utente) + "?categoria=richiesta&data=" + data + "&token=" + token + "&emailNutrizionista=" + email + "&descrizione=" + textDescrizione;

        // procediamo con la richiesta, non ci aspettiamo risultati e quindi usiamo la soluzione custom
        EmptyJsonObjectRequest request = new EmptyJsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // se arriviamo qui significa che abbiamo ricevuto il codice di stato 200

                Log.d(TAG, "onResponse: codice 200");
                Log.d(TAG, "onResponse: " + response.toString());

                Calendar calendar = Calendar.getInstance();
                Date today = new Date(calendar.getTimeInMillis());

                Toast.makeText(context, context.getString(R.string.aggiornamento_avvenuto_con_successo), Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int codiceErrore = error.networkResponse.statusCode;
                Log.d(TAG, "onErrorResponse: " + error.getMessage());
                Log.d(TAG, "onErrorResponse: " + error.toString());
                switch (codiceErrore) {
                    case 400:
                        Toast.makeText(context, context.getString(R.string.richiesta_malformata), Toast.LENGTH_LONG).show();
                        break;
                    case 401:
                        Toast.makeText(context, context.getString(R.string.credenziali_errate), Toast.LENGTH_LONG).show();
                        break;
                    case 500:
                        Toast.makeText(context, context.getString(R.string.errore_del_server), Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(context, context.getString(R.string.errore_registrazione), Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        // inserisco la richiesta dentro la coda
        Log.d(TAG, "modificaAlimento: sto inserendo la richiesta dentro la coda");
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        queue.add(request);
        Log.d(TAG, "modificaAlimento: ho inserito la richiesta nella coda");
    }
}

