package org.feup.cmov.validationevents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.feup.cmov.validationevents.shows.ShowsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button initBtn = findViewById(R.id.btn_init);
        initBtn.setOnClickListener((View v)->initShows());
    }

    public void initShows() {
        Intent intent = new Intent(this, ShowsActivity.class);
        startActivity(intent);
        finish();
    }

}
