package it.uniroma2.pjdm.androidstudio.kyf;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkChangeReceiver extends BroadcastReceiver {
    ConnectivityChangeListener listener;
    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // La connessione di rete è attiva
        } else {
            // Hai perso la connessione di rete
            listener.onNetworkDisconnected();
        }
    }

    public void setListener(ConnectivityChangeListener listener) {
        this.listener = listener;
    }
}
