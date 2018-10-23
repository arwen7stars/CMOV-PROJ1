package org.feup.cmov.customerapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.feup.cmov.customerapp.dataStructures.CardType;
import org.feup.cmov.customerapp.dataStructures.CreditCard;

import java.util.ArrayList;
import java.util.Calendar;

public class CreditCardDialog extends Dialog {
    public interface MyDialogCloseListener
    {
        public void handleDialogClose(CreditCard card);
    }

    // interface object
    private MyDialogCloseListener dialogListener;

    private Context context;
    private CreditCard card;

    private Spinner card_type;
    private EditText card_number;
    private Spinner date_month;
    private Spinner date_year;

    public CreditCardDialog(Context context, CreditCard card) {
        super(context);

        this.context = context;
        this.card = card;

        setTitle(R.string.credit_card);
        setOwnerActivity((Activity) context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_credit_card);

        this.card_number = findViewById(R.id.number_card);

        ArrayAdapter<String> type_adapter = populateTypeSpinner(R.id.type_card);
        ArrayAdapter<Integer> year_adapter = populateYearsSpinner(R.id.year_date);
        ArrayAdapter<Integer> month_adapter = populateMonthSpinner(R.id.month_date);

        if (this.card != null) {
            setCardData(type_adapter, year_adapter, month_adapter);
        }

        Button saveBtn = findViewById(R.id.btn_save);
        dialogListener = (MyDialogCloseListener) getOwnerActivity();
        saveBtn.setOnClickListener((View v)->saveCreditCard());
    }

    private void saveCreditCard() {
        if (validateCreditCard()) {
            CreditCard c;
            String type = this.card_type.getSelectedItem().toString();

            if (type.equals(CardType.MASTER_CARD.toString())) {
                c = new CreditCard(CardType.MASTER_CARD);
            } else if (type.equals(CardType.VISA.toString())) {
                c = new CreditCard(CardType.VISA);
            } else {
                c = new CreditCard(CardType.AMERICAN_EXPRESS);
            }
            c.setNumber(this.card_number.getText().toString());
            c.setMonthValidity((Integer) this.date_month.getSelectedItem());
            c.setYearValidity((Integer) this.date_year.getSelectedItem());

            // TODO: check if credit card data is valid before setting "credit card" check as true
            dialogListener.handleDialogClose(c);
            dismiss();
        }
    }

    private boolean validateCreditCard() {
        boolean valid = true;

        if(TextUtils.isEmpty(this.card_number.getText())) {
            this.card_number.setError("Enter a valid card number");
            valid = false;
            return valid;
        } else {
            this.card_number.setError(null);
        }

        int this_month = Calendar.getInstance().get(Calendar.MONTH);
        int selected_month = (Integer) this.date_month.getSelectedItem();

        int this_year = Calendar.getInstance().get(Calendar.YEAR);
        int selected_year = (Integer) this.date_year.getSelectedItem();

        if (this_year == selected_year) {
            if (this_month > selected_month) {
                valid = false;
                Toast.makeText(this.context, "Enter a valid date", Toast.LENGTH_LONG).show();
                return valid;
            } else {
                valid = true;
            }
        } else {
            valid = true;
        }

        return valid;
    }

    private ArrayAdapter<String> populateTypeSpinner(int type_id) {

        ArrayList<String> list = new ArrayList<>();

        list.add(CardType.MASTER_CARD.toString());
        list.add(CardType.VISA.toString());
        list.add(CardType.AMERICAN_EXPRESS.toString());

        this.card_type = findViewById(type_id);
        ArrayAdapter<String> type_adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, list);
        card_type.setAdapter(type_adapter);

        return type_adapter;
    }

    private ArrayAdapter<Integer> populateMonthSpinner(int months_id) {
        ArrayList<Integer> months = new ArrayList<>();
        int limit_month = 12;

        for (int i = 1; i <= limit_month; i++) {
            months.add(i);
        }
        ArrayAdapter<Integer> months_adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, months);

        this.date_month = findViewById(months_id);
        date_month.setAdapter(months_adapter);

        return months_adapter;
    }

    private ArrayAdapter<Integer> populateYearsSpinner(int years_id) {
        ArrayList<Integer> years = new ArrayList<>();
        int this_year = Calendar.getInstance().get(Calendar.YEAR);
        int limit_year = this_year + 50;

        for (int i = this_year; i <= limit_year; i++) {
            years.add(i);
        }
        ArrayAdapter<Integer> years_adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, years);

        this.date_year = findViewById(years_id);
        date_year.setAdapter(years_adapter);

        return years_adapter;
    }

    private void setCardData(ArrayAdapter<String> type_adapter, ArrayAdapter<Integer> year_adapter, ArrayAdapter<Integer> month_adapter) {
        int spinnerPosition = type_adapter.getPosition(card.getType().toString());
        card_type.setSelection(spinnerPosition);
        card_number.setText(card.getNumber());

        int month_pos = month_adapter.getPosition(card.getMonthValidity());
        date_month.setSelection(month_pos);

        int year_pos = year_adapter.getPosition(card.getYearValidity());
        date_year.setSelection(year_pos);
    }
}
