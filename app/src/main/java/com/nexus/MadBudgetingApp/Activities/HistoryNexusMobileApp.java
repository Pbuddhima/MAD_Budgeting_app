package com.nexus.MadBudgetingApp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nexus.MadBudgetingApp.Adapters.TodayItemsAdapter;
import com.nexus.MadBudgetingApp.Model.Data;
import com.nexus.MadBudgetingApp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class HistoryNexusMobileApp extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private RecyclerView recyclerView;

    private TodayItemsAdapter todayItemsAdapter;
    private List<Data> myDataList;

    private FirebaseAuth firebaseAuth;
    private String userId = "";
    private DatabaseReference todayExpenditureRef, personalRef;

    private Toolbar toolbar;

    private Button search;
    private TextView totalExpenditureAmountTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_history_nexus_mobile_app);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("History");


        search = findViewById(R.id.history_search);
        totalExpenditureAmountTextView = findViewById(R.id.history_total_spent);

        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        recyclerView = findViewById(R.id.recycler_view_id);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        myDataList = new ArrayList<>();
        todayItemsAdapter = new TodayItemsAdapter(HistoryNexusMobileApp.this, myDataList);
        recyclerView.setAdapter(todayItemsAdapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
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

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        int months = month+1;
        String date = dayOfMonth+"-"+"0"+months +"-"+year;
        Toast.makeText(this, date, Toast.LENGTH_SHORT).show();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("expenses").child(userId);
        Query query = reference.orderByChild("date").equalTo(date);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myDataList.clear();
                for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                    Data data = snapshot.getValue(Data.class);
                    myDataList.add(data);
                }
                todayItemsAdapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.VISIBLE);

                int totalAmount = 0;
                for (DataSnapshot ds :  dataSnapshot.getChildren()){
                    Map<String, Object> map = (Map<String, Object>)ds.getValue();
                    Object total = map.get("amount");
                    int pTotal = Integer.parseInt(String.valueOf(total));
                    totalAmount+=pTotal;
                    if (totalAmount >0){
                        totalExpenditureAmountTextView.setVisibility(View.VISIBLE);
                        totalExpenditureAmountTextView.setText("This day you spent Ksh: "+ totalAmount);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HistoryNexusMobileApp.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}