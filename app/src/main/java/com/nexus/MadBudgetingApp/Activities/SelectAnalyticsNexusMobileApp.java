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

    private Toolbar settingsToolbar;

    private CardView todayCardView, weekCardView, monthCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_select_analytics_nexus_mobile_app);

        settingsToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(settingsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Select Analytics");

        todayCardView = findViewById(R.id.todayCardView);

        weekCardView = findViewById(R.id.weekCardView);

        monthCardView = findViewById(R.id.monthCardView);


        todayCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectAnalyticsNexusMobileApp.this, DailyAnalyticsNexusMobileApp.class);
                startActivity(intent);
            }
        });

        weekCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectAnalyticsNexusMobileApp.this, WeeklyAnalyticsNexusMobileApp.class);
                startActivity(intent);
            }
        });

        monthCardView.setOnClickListener(new View.OnClickListener() {
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