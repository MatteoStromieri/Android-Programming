package it.uniroma2.pjdm.androidstudio.kyf.login.viewModel;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.login.AccessoFragment;
import it.uniroma2.pjdm.androidstudio.kyf.login.RegistrazioneFragment;


public class AccessoViewModel extends AndroidViewModel {

        Context context;
        RegistrazioneFragment registrazioneFragment;
        AccessoFragment accessoFragment;

        public AccessoViewModel(@NonNull Application application) {
            super(application);
            context = getApplication().getApplicationContext();
        }

        public void setRegistrazioneFragment(RegistrazioneFragment registrazioneFragment){
            this.registrazioneFragment = registrazioneFragment;
        }

        public void setAccessoFragment(AccessoFragment accessoFragment){
            this.accessoFragment = accessoFragment;
        }


        /*
            Login account
         */
        public void accesso(String email, String password, String categoriaAccount) {

            Log.d("ms", "Faccio la chiamata di accesso");

            // "http://10.0.2.2:8080/kyf/EntryPointServlet?categoria="
            String url = context.getString(R.string.url)+"?categoria="+categoriaAccount+"&email="+email+"&password="+password;
            Log.d("MS", "accesso: url" + url);

            // proviamo con la request personalizzata
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    // se arriviamo qui possiamo fare lo switch

                    // se abbiamo il codice il codice di stato 200 possiamo parsare il token e inserirlo nelle preferenze

                    Log.d("MS", "onResponse: codice 200");
                    Log.d("MS", "onResponse: " + response.toString());

                    // parsiamo il token
                    Long token = null;
                    try {
                        token = response.getLong("token");
                        Log.d("MS", "onResponse: ecco il token di sessione:" + token);
                    } catch (JSONException e) {
                        Log.d("MS", "onResponse: errore nel parsing del token");
                        //throw new RuntimeException(e);
                    }

                    // se siamo arrivati qui possiamo aprire le preferenze condivise

                    context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE)
                            .edit()
                            .putLong("token", token)
                            .putString("email",email)
                            .commit();


                    Log.d("MS", "onResponse: le preferenze condivise sono state aggiornate");

                    //ora possiamo lanciare l'intent per avviare la nuova activity
                    accessoFragment.launchNewActivity(categoriaAccount);

                    return;
                }
            }, new Response.ErrorListener(){
                    public void onErrorResponse(VolleyError error) {

                        if(error.networkResponse == null){
                            Toast.makeText(context, context.getString(R.string.connessione_persa), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        int codiceErrore = error.networkResponse.statusCode;
                        Log.d("TAG", "onErrorResponse: " + error.getMessage());
                        Log.d("TAG", "onErrorResponse: " + error.toString());
                        switch (codiceErrore) {
                            case 400:
                                Toast.makeText(context, context.getString(R.string.credenziali_errate), Toast.LENGTH_LONG).show();
                                break;
                            case 500:
                                Toast.makeText(context, context.getString(R.string.errore_accesso), Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(context, context.getString(R.string.errore_accesso_sconosciuto), Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                });

            RequestQueue queue = Volley.newRequestQueue(getApplication());
            queue.add(request);
        }


        public void register(String email, String cognome, String nome, String dataDiNascita, String password, String categoriaAccount) {

            Log.d("ms", "Faccio la chiamata di accesso");

            // "http://10.0.2.2:8080/kyf/EntryPointServlet?categoria="
            String url = context.getString(R.string.url)+"?categoria="+categoriaAccount+"&email="+email+"&password="+password+"&nome="+nome+"&cognome="+cognome+"&dataDiNascita="+dataDiNascita;
            Log.d("MS", "accesso: url " + url);

            // proviamo con la request personalizzata
            JsonObjectRequest request = new EmptyJsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    // se arriviamo qui possiamo fare lo switch

                    // se abbiamo il codice il codice di stato 200 possiamo parsare il token e inserirlo nelle preferenze

                    Log.d("MS", "onResponse: codice 200");
                    Log.d("MS", "onResponse: " + response.toString());

                    Toast.makeText(context, context.getString(R.string.registrazione_successo), Toast.LENGTH_LONG).show();

                    // richiamare il metodo per la transizione del fragment di registrazione con quello di accesso
                    registrazioneFragment.navigateToAccessoFragment();

                    return;
                }
            }, new Response.ErrorListener(){
                public void onErrorResponse(VolleyError error) {

                    if(error.networkResponse == null){
                        Toast.makeText(context, context.getString(R.string.connessione_persa), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int codiceErrore = error.networkResponse.statusCode;
                    Log.d("TAG", "onErrorResponse: " + error.getMessage());
                    Log.d("TAG", "onErrorResponse: " + error.toString());
                    switch (codiceErrore) {
                        case 400:
                            Toast.makeText(context, context.getString(R.string.errore_registrazione_richiesta), Toast.LENGTH_LONG).show();
                            break;
                        case 500:
                            Toast.makeText(context, context.getString(R.string.errore_registrazione_email), Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(context, context.getString(R.string.errore_registrazione), Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            });

            RequestQueue queue = Volley.newRequestQueue(getApplication());
            queue.add(request);
        }
    }

