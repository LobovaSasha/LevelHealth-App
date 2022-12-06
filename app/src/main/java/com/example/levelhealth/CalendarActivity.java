package com.example.levelhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CalendarActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mAuth = FirebaseAuth.getInstance();

        CalendarView calendarView = findViewById(R.id.calendarView);
//        TextView date = findViewById(R.id.textView12);
//        date.setVisibility(View.INVISIBLE);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {
//                TextView date = findViewById(R.id.textView12);
                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;
                String selectedDate = new StringBuilder().append(mDay)
                        .append("-").append(mMonth + 1)
                        .append("-").append(mYear)
                        .append(" ").toString();
//                date.setText(selectedDate);
//                date.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser cUser = mAuth.getCurrentUser();
        if(cUser==null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void GoToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void GoToMenuActivity(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}