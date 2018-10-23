package org.feup.cmov.customerapp.database;

import android.util.Log;

import org.feup.cmov.customerapp.RegisterActivity;
import org.feup.cmov.customerapp.dataStructures.CreditCard;
import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class AddCreditCard extends ServerConnection implements Runnable {
    private RegisterActivity activity;
    private String userID;
    private String type;
    private String number;
    private String validity_date;

    public AddCreditCard(RegisterActivity activity, String userID, CreditCard creditCard) {
        super();
        this.activity = activity;
        this.userID = userID;
        this.type = creditCard.getType().toString();
        this.number = creditCard.getNumber();
        this.validity_date = formatDate(creditCard.getMonthValidity(), creditCard.getYearValidity());
    }

    public String formatDate(int month, int year) {
        DecimalFormat formatter = new DecimalFormat("00");
        String monthStr = formatter.format(month);

        String date = year + "-" + monthStr + "-01";

        return date;
    }

    @Override
    public void run() {
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL("http://" + address + ":" + port + "/users/" + userID + "/creditcard");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setUseCaches(false);

            urlConnection.connect();

            //Create JSONObject here
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("type", this.type);
            jsonParam.put("number", this.number);
            jsonParam.put("validity", this.validity_date);
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(jsonParam.toString());
            out.close();

            int responseCode = urlConnection.getResponseCode();
            String response;
            if (responseCode == 200) {
                response = readStream(urlConnection.getInputStream());
                Log.d("connection", response);
            } else {
                response = readStream(urlConnection.getErrorStream());
                Log.d("connection", response);
            }
            activity.handleResponseCC(responseCode, response);
        }
        catch (Exception e) {
            activity.handleResponseCC(0, e.getMessage());
        }
        finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }
    }

}
