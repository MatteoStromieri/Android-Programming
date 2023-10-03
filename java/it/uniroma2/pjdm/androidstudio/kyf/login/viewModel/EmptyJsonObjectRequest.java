package it.uniroma2.pjdm.androidstudio.kyf.login.viewModel;

import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class EmptyJsonObjectRequest extends JsonObjectRequest {

    Response.Listener<JSONObject> listener;

    public EmptyJsonObjectRequest(int method, String url, @Nullable JSONObject jsonRequest, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        this.listener = listener;
    }

    /*
    * ritorniamo un jsonObject che abbia la risposta e il codice di errore
    * */
    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

        try {
            try {
                if (response.statusCode == 200) {
                    byte[] responseData = "{}".getBytes("UTF8");
                    response = new NetworkResponse(response.statusCode, responseData, response.headers, response.notModified);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return super.parseNetworkResponse(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
