package it.uniroma2.pjdm.androidstudio.kyf.nutrizionista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.uniroma2.pjdm.androidstudio.kyf.ConnectivityChangeListener;
import it.uniroma2.pjdm.androidstudio.kyf.NetworkChangeReceiver;
import it.uniroma2.pjdm.androidstudio.kyf.R;
import it.uniroma2.pjdm.androidstudio.kyf.nutrizionista.home.adapter.ListaIngredientiAdapter;

public class MainActivityNutrizionista extends AppCompatActivity implements ConnectivityChangeListener {

    private static final String TAG = MainActivityNutrizionista.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nutrizionista);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarNutrizionista);
        String email = getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE).getString(getString(R.string.email),"");
        myToolbar.setTitle(email);
        setSupportActionBar(myToolbar);

        // tutto
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeNutrizionistaFragment, R.id.ricercaPazientiFragment)
                .build();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationViewNutrizionista);
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerViewNutrizionista);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // creo una istanza del broadcast receiver
        NetworkChangeReceiver receiver = new NetworkChangeReceiver();
        receiver.setListener(this);
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: libero le preferenze condivise");
        getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE).edit().remove(getString(R.string.token)).remove(getString(R.string.email)).commit();
        // verifichiamo che siano state liberate
        Long token = getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE).getLong(getString(R.string.token), 0);
        Log.d(TAG, "onDestroy: l'attuale token è " + token);
    }

    @Override
    public void onNetworkDisconnected() {
        Toast.makeText(getApplication(), getString(R.string.connessione_persa), Toast.LENGTH_SHORT).show();
        finish();
    }
}