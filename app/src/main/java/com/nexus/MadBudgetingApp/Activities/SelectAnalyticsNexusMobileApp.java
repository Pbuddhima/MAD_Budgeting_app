package com.nexus.MadBudgetingApp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.nexus.MadBudgetingApp.R;

public class SelectAnalyticsNexusMobileApp extends AppCompatActivity {

    private Toolbar toolbar;
    private CardView cardView, weekcardview, monthcardview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_select_analytics_nexus_mobile_app);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Select Analytics");

        cardView = findViewById(R.id.todayCardView);

        weekcardview = findViewById(R.id.weekCardView);

        monthcardview = findViewById(R.id.monthCardView);


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectAnalyticsNexusMobileApp.this, DailyAnalyticsNexusMobileApp.class);
                startActivity(intent);
            }
        });

        weekcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectAnalyticsNexusMobileApp.this, WeeklyAnalyticsNexusMobileApp.class);
                startActivity(intent);
            }
        });

        monthcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectAnalyticsNexusMobileApp.this, MonthlyAnalyticsNexusMobileApp.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}