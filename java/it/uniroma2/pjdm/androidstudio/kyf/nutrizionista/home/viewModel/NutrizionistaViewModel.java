package it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.viewModel;

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

import java.util.ArrayList;

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.entity.AlimentoAstratto;
import it.uniroma2.pjdm.androidstudio.kyf.entity.Pasto;
import it.uniroma2.pjdm.androidstudio.kyf.entity.Richiesta;
import it.uniroma2.pjdm.androidstudio.kyf.entity.Utente;
import it.uniroma2.pjdm.androidstudio.kyf.login.viewModel.EmptyJsonObjectRequest;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.adapter.DiarioPazienteAdapter;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.adapter.ListaIngredientiAdapter;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.adapter.PazientiAdapter;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.adapter.RicercaIngredienteAdapter;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.adapter.RichiesteAdapter;
import it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter.ListElement;

public class NutrizionistaViewModel extends AndroidViewModel {

    private static final String TAG = NutrizionistaViewModel.class.getSimpleName();
    public RichiesteAdapter richiesteAdapter;
    public PazientiAdapter pazientiAdapter;
    public DiarioPazienteAdapter diarioPazienteAdapter;
    public RicercaIngredienteAdapter ricercaIngredienteAdapter;
    public ListaIngredientiAdapter listaIngredientiAdapter;
    Context context;
    public NutrizionistaViewModel(@NonNull Application application) {
        super(application);
        context = getApplication().getApplicationContext();
    }

    public void setRichiesteAdapter(RichiesteAdapter richiesteAdapter) {
        this.richiesteAdapter = richiesteAdapter;
    }

    public void setPazientiAdapter(PazientiAdapter pazientiAdapter) {
        this.pazientiAdapter = pazientiAdapter;
    }

    public void setDiarioPazienteAdapter(DiarioPazienteAdapter diarioPazienteAdapter) {
        this.diarioPazienteAdapter = diarioPazienteAdapter;
    }

    public void setRicercaIngredienteAdapter(RicercaIngredienteAdapter ricercaIngredienteAdapter) {
        this.ricercaIngredienteAdapter = ricercaIngredienteAdapter;
    }

    public void setListaIngredientiAdapter(ListaIngredientiAdapter listaIngredientiAdapter) {
        this.listaIngredientiAdapter = listaIngredientiAdapter;
    }

    public void getRichieste(){
        Log.d(TAG, "getRichieste: è stato chiamato getRichieste");
        // prendiamo il nostro token
        Long token = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE).getLong("token", 0);
        Log.d(TAG, "getRichieste: il token che è stato recuperato è pari a " + token);
        if (token == 0) {
            return;
        }
        // prendiamo l'url e inseriamo i nostri campi
        String url = context.getString(R.string.url_nutrizionista) + "?categoria=richieste&token=" + token;

        // ora possiamo fare la richiesta, usiamo una JSONArrayRequest visto che ci aspettiamo un jsonarray
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "onResponse: è stato richiamato onResponse, il JSON di risposta è \n" + response.toString());

                if (response == null) {
                    Log.d(TAG, "onRespose: risposta mal formata, la response ha un valore null");
                    Toast.makeText(context, context.getString(R.string.errore_del_server), Toast.LENGTH_LONG).show();
                    return;
                }

                // facciamo un arrayList di Richieste che poi andiamo ad aggiungere all'adapter
                ArrayList<Richiesta> results = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {

                    JSONObject jsonObjectRichiesta = null;
                    try {
                        jsonObjectRichiesta = response.getJSONObject(i);
                        results.add(Richiesta.fromJsonObject(jsonObjectRichiesta));
                        Log.d(TAG, "onResponse: un elemento è stato parsato");
                    } catch (JSONException e) {
                        // se un alimento non riesce ad essere parsato per qualche motivo, viene ignorato
                        Log.d(TAG, "onResponse: un elemento non è stato parsato");
                    }
                }
                // ripulisco la lista
                richiesteAdapter.clear();
                richiesteAdapter.addList(results);
                Log.d(TAG, "onResponse: richieste aggiunte a richiesteAdapter");
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
                            // richiesteAdapter.notifyDataSetChanged();
                            break;
                        default:
                            Toast.makeText(context, context.getString(R.string.errore_registrazione), Toast.LENGTH_LONG).show();
                            break;
                    }
            }
        });
        // inserisco la richiesta dentro la coda
        Log.d(TAG, "getRichieste: sto inserendo la richiesta dentro la coda");
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        queue.add(request);
        Log.d(TAG, "getRichieste: ho inserito la richiesta nella coda");
        }

    public void accettaRichiesta(String emailMittente) {
        Log.d(TAG, "accettaRichiesta: è stato chiamato getRichieste");
        // prendiamo il nostro token
        Long token = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE).getLong("token", 0);
        Log.d(TAG, "accettaRichiesta: il token che è stato recuperato è pari a " + token);
        if (token == 0) {
            return;
        }
        // prendiamo l'url e inseriamo i nostri campi
        String url = context.getString(R.string.url_nutrizionista) + "?categoria=richiesta&token=" + token + "&mittente=" + emailMittente;

        // ora possiamo fare la richiesta , sarà di tipo put e usiamo la request custom perchè non ci aspettiamo risultati
        EmptyJsonObjectRequest request = new EmptyJsonObjectRequest(Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context, context.getString(R.string.aggiornamento_avvenuto_con_successo), Toast.LENGTH_LONG).show();
                // ricarichiamo il contenuto della home page
                getRichieste();
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
                        Toast.makeText(context, context.getString(R.string.utente_gia_paziente), Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(context, context.getString(R.string.errore_registrazione), Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

        Log.d(TAG, "accettaRichiesta: sto inserendo la richiesta dentro la coda");
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        queue.add(request);
        Log.d(TAG, "accettaRichiesta: ho inserito la richiesta nella coda");
    }

    public void rifiutaRichiesta(String emailMittente) {
        Log.d(TAG, "rifiutaRichiesta: è stato chiamato getRichieste");
        // prendiamo il nostro token
        Long token = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE).getLong("token", 0);
        Log.d(TAG, "rifiutaRichiesta: il token che è stato recuperato è pari a " + token);
        if (token == 0) {
            return;
        }
        // prendiamo l'url e inseriamo i nostri campi
        String url = context.getString(R.string.url_nutrizionista) + "?categoria=richiesta&token=" + token + "&mittente=" + emailMittente;

        // ora possiamo fare la richiesta , sarà di tipo put e usiamo la request custom perchè non ci aspettiamo risultati
        EmptyJsonObjectRequest request = new EmptyJsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context, context.getString(R.string.aggiornamento_avvenuto_con_successo), Toast.LENGTH_LONG).show();
                // ricarichiamo il contenuto della home page
                getRichieste();
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

        Log.d(TAG, "rifiutaRichiesta: sto inserendo la richiesta dentro la coda");
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        queue.add(request);
        Log.d(TAG, "rifiutaRichiesta: ho inserito la richiesta nella coda");
    }



    public void getPazienti() {
        Log.d(TAG, "getPazienti: inizio chiamata");
        // prendiamo il nostro token
        Long token = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE).getLong("token", 0);

        Log.d(TAG, "getPazienti: il token che è stato recuperato è pari a " + token);
        if (token == 0) {
            return;
        }
        // prendiamo l'url e inseriamo i nostri campi
        String url = context.getString(R.string.url_nutrizionista) + "?categoria=pazienti&token=" + token;

        /* una volta che abbiamo l'url possiamo fare la richiesta
            riceveremo come output un jsonArray, quindi facciamo una jsonrequest
         */
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "onResponse: è stato richiamato onResponse, il JSON di risposta è \n" + response.toString());

                if (response == null) {
                    Log.d(TAG, "onRespose: risposta mal formata, la response ha un valore null");
                    Toast.makeText(context, context.getString(R.string.errore_del_server), Toast.LENGTH_LONG).show();
                    return;
                }

                // facciamo un arrayList di Richieste che poi andiamo ad aggiungere all'adapter
                ArrayList<Utente> results = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {

                    JSONObject jsonObjectPaziente = null;
                    try {
                        jsonObjectPaziente = response.getJSONObject(i);
                        results.add(Utente.fromJsonObject(jsonObjectPaziente));
                        Log.d(TAG, "onResponse: un elemento è stato parsato");
                    } catch (JSONException e) {
                        // se un alimento non riesce ad essere parsato per qualche motivo, viene ignorato
                        Log.d(TAG, "onResponse: un elemento non è stato parsato");
                    }
                }
                // ripulisco la lista
                pazientiAdapter.clear();
                pazientiAdapter.addList(results);
                Log.d(TAG, "onResponse: pazienti aggiunti a pazientiAdapter");
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
        }
        );

        // inserisco la richiesta dentro la coda
        Log.d(TAG, "getPazienti: sto inserendo la richiesta dentro la coda");
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        queue.add(request);
        Log.d(TAG, "getPazienti: ho inserito la richiesta nella coda");
    }

    public void getDiario(String emailPaziente, String data) {
        Log.d(TAG, "getDiario: inizio chiamata getDiario() per l'utente come email " + emailPaziente + " in data " + data.toString());
        // prendiamo il nostro token
        Long token = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE).getLong("token", 0);

        Log.d(TAG, "getDiario: il token che è stato recuperato è pari a " + token);
        if (token == 0) {
            return;
        }
        // prendiamo l'url e inseriamo i nostri campi
        String url = context.getString(R.string.url_nutrizionista) + "?categoria=diario&data=" + data + "&token=" + token+ "&emailUtente=" + emailPaziente;

        /* una volta che abbiamo l'url possiamo fare la richiesta
            riceveremo come output un jsonObject, quindi facciamo una jsonrequest
         */
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // qui inseriamo il codice che viene eseguito quando ci viene ritornato il codice 200
                Log.d(TAG, "onRespose: è stato richiamato il metodo OnResponse");
                Log.d(TAG, "response: " + response.toString());

                String strColazione = context.getString(R.string.colazione_minuscolo);
                String strPranzo = context.getString(R.string.pranzo_minuscolo);
                String strMerenda = context.getString(R.string.merenda_minuscolo);
                String strCena = context.getString(R.string.cena_minuscolo);

                // verifichiamo se sono presenti i campi colazione, pranzo, merenda e cena
                if (!response.has(strColazione) || !response.has(strPranzo) || !response.has(strMerenda) || !response.has(strCena)) {
                    Log.d(TAG, "onRespose: risposta mal formata, alcuni parametri non sono presenti");
                    Toast.makeText(context, context.getString(R.string.errore_del_server), Toast.LENGTH_LONG).show();
                    return;
                }

                // se siamo arrivati fin qui possiamo prendere i vari campi e darli in pasto al metodo fromJsonObject della classe Pasto
                Pasto colazione, pranzo, merenda, cena;
                try {
                    colazione = Pasto.fromJsonObject(response.getJSONObject(strColazione));
                    pranzo = Pasto.fromJsonObject(response.getJSONObject(strPranzo));
                    merenda = Pasto.fromJsonObject(response.getJSONObject(strMerenda));
                    cena = Pasto.fromJsonObject(response.getJSONObject(strCena));

                    // ora possiamo fare l'inserimento contattando il RicercaAlimentoAdapter
                    diarioPazienteAdapter.clear();
                    diarioPazienteAdapter.add(colazione);
                    diarioPazienteAdapter.add(pranzo);
                    diarioPazienteAdapter.add(merenda);
                    diarioPazienteAdapter.add(cena);
                    diarioPazienteAdapter.notifyDataSetChanged();

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
        }
        );

        // inserisco la richiesta dentro la coda
        Log.d(TAG, "getDiario: sto inserendo la richiesta dentro la coda");
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        queue.add(request);
        Log.d(TAG, "getDiario: ho inserito la richiesta nella coda");
    }

    public void aggiungiAlimento(String stringNome, String stringDescrizione, String stringCarboidrati, String stringProteine, String stringGrassi) {
        Log.d(TAG, "aggiungiAlimento: inizio chiamata aggiungiAlimento() per l'alimento con nome " + stringNome);
        // prendiamo il nostro token
        Long token = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE).getLong("token", 0);

        Log.d(TAG, "aggiungiAlimento: il token che è stato recuperato è pari a " + token);
        if (token == 0) {
            return;
        }
        // prendiamo l'url e inseriamo i nostri campi
        String url = context.getString(R.string.url_nutrizionista) + "?categoria=alimento&nome=" + stringNome + "&token=" + token+ "&descrizione=" + stringDescrizione+ "&carboidrati=" + stringCarboidrati + "&proteine=" + stringProteine + "&grassi=" + stringGrassi;

        Log.d(TAG, "aggiungiAlimento: url " + url);
        // siamo pronti a fare una empty request
        EmptyJsonObjectRequest request = new EmptyJsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context, context.getString(R.string.aggiornamento_avvenuto_con_successo), Toast.LENGTH_LONG).show();
                // ricarichiamo il contenuto della home page0.1
                getRichieste();
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

        Log.d(TAG, "aggiungiAlimento: sto inserendo la richiesta dentro la coda");
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        queue.add(request);
        Log.d(TAG, "aggiungiAlimento: ho inserito la richiesta nella coda");
    }

    // mi serve solo categoria=alimenti e query=text oltre che al TOKEN
    public void ricercaAlimento(String query) {
        // per prima cosa prendiamo il token dalle shared_prefs
        Long token = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE).getLong("token", 0);

        Log.d(TAG, "ricercaAlimento: il token che è stato recuperato è pari a " + token);
        if (token == 0) {
            return;
        }

        // prendiamo i nostri campi e formiamo l'url
        String url = context.getString(R.string.url_nutrizionista) + "?categoria=alimenti&query=" + query + "&token=" + token;

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
                ricercaIngredienteAdapter.clear();
                // ora aggiungiamo la lista dei nostri alimenti dentro l'adapter
                Log.d(TAG, "onResponse: inserisco i nuovi alimenti");
                ricercaIngredienteAdapter.addList(results);

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


    public void inserisciAlimentoComposto(String nome, String descrizione, ArrayList<ListElement> data) {
        // prendiamoci il token
        Long token = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE).getLong("token", 0);

        Log.d(TAG, "InserisciAlimentoComposto: il token che è stato recuperato è pari a " + token);
        if (token == 0) {
            return;
        }

        // inizialmente inseriamo nome, descrizione, categoria e token
        String url = context.getString(R.string.url_nutrizionista) + "?categoria=alimentoComposto&nome=" + nome + "&token=" + token + "&descrizione=" + descrizione;
        Log.d(TAG, "inserisciAlimentoComposto: ecco l'url prima dell'estensione: " + url);

        // per ogni ListElement aggiungiamo id e quantità
        for(ListElement listElement: data){
            url = url + "&id="+listElement.getId()+"&quantita="+listElement.getQuantita();
        }

        Log.d(TAG, "inserisciAlimentoComposto: ecco l'url dopo l'estensione: " + url);

        // procedo a fare la empty request
        EmptyJsonObjectRequest request = new EmptyJsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // se arriviamo qui significa che abbiamo ricevuto il codice di stato 200

                Log.d(TAG, "onResponse: codice 200");
                Log.d(TAG, "onResponse: " + response.toString());

                Toast.makeText(context, context.getString(R.string.aggiornamento_avvenuto_con_successo), Toast.LENGTH_LONG).show();

                // torniamo al fragment principale

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
        Log.d(TAG, "inserisciAlimentoComposto: sto inserendo la richiesta dentro la coda");
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        queue.add(request);
        Log.d(TAG, "inserisciAlimentoComposto: ho inserito la richiesta nella coda");

    }
}

