package org.feup.cmov.customerapp.database;

import android.util.Log;

import org.feup.cmov.customerapp.dataStructures.Ticket;
import org.feup.cmov.customerapp.transactions.TransactionsActivity;
import org.feup.cmov.customerapp.utils.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetTickets extends ServerConnection implements Runnable {

    // transactions' activity
    private TransactionsActivity activity;

    // current user's id
    private String userID;

    public GetTickets(TransactionsActivity activity, String userID) {
        this.activity = activity;
        this.userID = userID;
    }

    @Override
    public void run() {
        URL url;
        HttpURLConnection urlConnection = null;
        int responseCode = Constants.NO_INTERNET;

        try {
            url = new URL("http://" + address + ":" + port + "/users/" + userID + "/tickets");
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setConnectTimeout(Constants.SERVER_TIMEOUT);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setUseCaches(false);

            urlConnection.connect();

            responseCode = urlConnection.getResponseCode();
            String response;

            if (responseCode == Constants.OK_RESPONSE) {
                response = readStream(urlConnection.getInputStream());

                List<Ticket> tickets = jsonToArray(response);
                activity.handleResponseTickets(responseCode, response, tickets);
            } else {
                response = readStream(urlConnection.getErrorStream());
                activity.handleResponseTickets(responseCode, response, null);
            }
        } catch (Exception e) {
            if (responseCode == Constants.NO_INTERNET) {
                String errorMessage = Constants.ERROR_CONNECTING;
                activity.handleResponseTickets(responseCode, errorMessage, null);
            }
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }

    private List<Ticket> jsonToArray(String jsonString) {
        List<Ticket> ticketList = new ArrayList<>();

        try {
            JSONArray jArray = new JSONArray(jsonString);

            for(int i=0; i<jArray.length(); i++){
                JSONObject ticket = jArray.getJSONObject(i);

                String id = ticket.getString("id");
                boolean available = ticket.getBoolean("available");
                String name = ticket.getString("name");
                String date = ticket.getString("date");
                int seatNumber = ticket.getInt("seatNumber");
                double price = ticket.getDouble("price");

                if (available) {
                    Ticket t = new Ticket(id, name, date, seatNumber, price);
                    ticketList.add(t);
                }
            }
        } catch(JSONException e) {
            Log.e("ERROR", e.getMessage());
        }

        return ticketList;
    }
}
