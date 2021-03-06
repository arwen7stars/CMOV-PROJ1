package org.feup.cmov.validationcafeteria.server;

import android.util.Log;

import org.feup.cmov.validationcafeteria.util.Constants;
import org.feup.cmov.validationcafeteria.dataStructures.User;
import org.feup.cmov.validationcafeteria.order.OrderActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

public class GetUser extends ServerConnection implements Runnable {
    private OrderActivity activity;

    private String userId;

    public GetUser(OrderActivity activity, String userId) {
        this.activity = activity;
        this.userId = userId;
    }

    @Override
    public void run() {
        URL url;
        HttpURLConnection urlConnection = null;
        int responseCode = Constants.NO_INTERNET;

        try {
            String serverURL = "http://" + address + ":" + port + "/users/" + userId;

            url = new URL(serverURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(Constants.SERVER_TIMEOUT);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setUseCaches(false);
            urlConnection.getRequestMethod();

            urlConnection.connect();

            responseCode = urlConnection.getResponseCode();
            String response;

            if (responseCode == Constants.OK_RESPONSE) {
                response = readStream(urlConnection.getInputStream());
                Log.d("connection", response);

                User user = jsonToArray(response);
                activity.handleResponse(responseCode, response, user);
            } else {
                response = readStream(urlConnection.getErrorStream());
                Log.d("connection", response);

                activity.handleResponse(responseCode, response, null);
            }

        } catch (Exception e) {
            if (responseCode == Constants.NO_INTERNET) {
                e.printStackTrace();

                String errorMessage = Constants.ERROR_CONNECTING;
                activity.handleResponse(responseCode, errorMessage, null);
            }
        }
        finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }
    }

    private User jsonToArray(String jsonString) {
        User user = null;
        try {
            JSONObject response = new JSONObject(jsonString);

            String username = response.getString("username");
            String name = response.getString("name");
            String nif = response.getString("nif");

            user = new User(name, username, nif);
        } catch(JSONException e) {
            Log.e("ERROR", e.getMessage());
        }

        return user;
    }
}
