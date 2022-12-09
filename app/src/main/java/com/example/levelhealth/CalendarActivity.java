package com.example.levelhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class CalendarActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ImageView moodImg, sleepImg, headacheImg;
    String date, idtable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mAuth = FirebaseAuth.getInstance();

        CalendarView calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {
                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;
                if (mDay < 10) {
                    date = new StringBuilder().append("0").append(mDay)
                            .append("-").append(mMonth + 1)
                            .append("-").append(mYear).toString();
                } else {
                    date = new StringBuilder().append(mDay)
                            .append("-").append(mMonth + 1)
                            .append("-").append(mYear).toString();
                }
                init();
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
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        date = formatter.format(calendar.getTime());
        init();
    }

    public void init(){
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference RootRef;
        FirebaseUser cUser = mAuth.getCurrentUser();
        idtable = cUser.getUid();
        moodImg = findViewById(R.id.moodImg);
        sleepImg = findViewById(R.id.sleepImg);
        headacheImg = findViewById(R.id.headacheImg);
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Condition").child(idtable).child(date).exists()){
                    String mood = snapshot.child("Condition").child(idtable).child(date).child("mood").getValue().toString();
                    String sleep = snapshot.child("Condition").child(idtable).child(date).child("sleep").getValue().toString();
                    String headache = snapshot.child("Condition").child(idtable).child(date).child("headache").getValue().toString();
                    if (mood.equals("-1")) {
                        moodImg.setVisibility(View.INVISIBLE);
                    } else if (mood.equals("0")) {
                        moodImg.setImageResource(R.drawable.smile1_0);
                        moodImg.setVisibility(View.VISIBLE);
                    } else if (mood.equals("1")) {
                        moodImg.setImageResource(R.drawable.smile1_1);
                        moodImg.setVisibility(View.VISIBLE);
                    } else if (mood.equals("2")) {
                        moodImg.setImageResource(R.drawable.smile1_2);
                        moodImg.setVisibility(View.VISIBLE);
                    } else if (mood.equals("3")) {
                        moodImg.setImageResource(R.drawable.smile1_3);
                        moodImg.setVisibility(View.VISIBLE);
                    }
                    if (sleep.equals("-1")) {
                        sleepImg.setVisibility(View.INVISIBLE);
                    } else if (sleep.equals("0")) {
                        sleepImg.setImageResource(R.drawable.sleep1_0);
                        sleepImg.setVisibility(View.VISIBLE);
                    } else if (sleep.equals("1")) {
                        sleepImg.setImageResource(R.drawable.sleep1_1);
                        sleepImg.setVisibility(View.VISIBLE);
                    } else if (sleep.equals("2")) {
                        sleepImg.setImageResource(R.drawable.sleep1_2);
                        sleepImg.setVisibility(View.VISIBLE);
                    } else if (sleep.equals("3")) {
                        sleepImg.setImageResource(R.drawable.sleep1_3);
                        sleepImg.setVisibility(View.VISIBLE);
                    }
                    if (headache.equals("0")) {
                        headacheImg.setVisibility(View.INVISIBLE);
                    } else {
                        headacheImg.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CalendarActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        });
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