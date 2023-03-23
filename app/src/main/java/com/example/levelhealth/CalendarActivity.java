package com.example.levelhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import java.util.Objects;

public class CalendarActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ImageView moodImg, sleepImg, headacheImg;
    TextView commentText;
    String date, idtable;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mAuth = FirebaseAuth.getInstance();
        init();
        back.setOnClickListener(v -> onBackPressed());

        CalendarView calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            if (month > 9) {
                if (dayOfMonth < 10) {
                    date = "0" + dayOfMonth + "-" + (month + 1) + "-" + year;
                } else {
                    date = dayOfMonth + "-" + (month + 1) + "-" + year;
                }
            } else {
                if (dayOfMonth < 10) {
                    date = "0" + dayOfMonth + "-" + "0" + (month + 1) + "-" + year;
                } else {
                    date = dayOfMonth + "-" + "0" + (month + 1) + "-" + year;
                }
            }
            init();
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
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        date = formatter.format(calendar.getTime());
    }

    public void init(){
        back = findViewById(R.id.back);
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference RootRef;
        FirebaseUser cUser = mAuth.getCurrentUser();
        assert cUser != null;
        idtable = cUser.getUid();
        moodImg = findViewById(R.id.moodImg);
        sleepImg = findViewById(R.id.sleepImg);
        headacheImg = findViewById(R.id.headacheImg);
        commentText = findViewById(R.id.comment);
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Condition").child(idtable).child(date).exists()){
                    String mood = Objects.requireNonNull(snapshot.child("Condition").child(idtable).child(date).child("mood").getValue()).toString();
                    String sleep = Objects.requireNonNull(snapshot.child("Condition").child(idtable).child(date).child("sleep").getValue()).toString();
                    String headache = Objects.requireNonNull(snapshot.child("Condition").child(idtable).child(date).child("headache").getValue()).toString();
                    String comment;
                    try {
                        comment = Objects.requireNonNull(snapshot.child("Condition").child(idtable).child(date).child("comment").getValue()).toString();
                    } catch (Exception e) {
                        comment = "Нет записи за этот день";
                    }
                    commentText.setText(comment);
                    switch (mood) {
                        case "-1":
                            moodImg.setVisibility(View.INVISIBLE);
                            break;
                        case "0":
                            moodImg.setImageResource(R.drawable.smile1_0);
                            moodImg.setVisibility(View.VISIBLE);
                            break;
                        case "1":
                            moodImg.setImageResource(R.drawable.smile1_1);
                            moodImg.setVisibility(View.VISIBLE);
                            break;
                        case "2":
                            moodImg.setImageResource(R.drawable.smile1_2);
                            moodImg.setVisibility(View.VISIBLE);
                            break;
                        case "3":
                            moodImg.setImageResource(R.drawable.smile1_3);
                            moodImg.setVisibility(View.VISIBLE);
                            break;
                    }
                    switch (sleep) {
                        case "-1":
                            sleepImg.setVisibility(View.INVISIBLE);
                            break;
                        case "0":
                            sleepImg.setImageResource(R.drawable.sleep0);
                            sleepImg.setVisibility(View.VISIBLE);
                            break;
                        case "1":
                            sleepImg.setImageResource(R.drawable.sleep1);
                            sleepImg.setVisibility(View.VISIBLE);
                            break;
                        case "2":
                            sleepImg.setImageResource(R.drawable.sleep2);
                            sleepImg.setVisibility(View.VISIBLE);
                            break;
                        case "3":
                            sleepImg.setImageResource(R.drawable.sleep3);
                            sleepImg.setVisibility(View.VISIBLE);
                            break;
                    }
                    if (headache.equals("0")) {
                        headacheImg.setVisibility(View.INVISIBLE);
                    } else {
                        headacheImg.setVisibility(View.VISIBLE);
                    }
                } else {
                    moodImg.setVisibility(View.INVISIBLE);
                    sleepImg.setVisibility(View.INVISIBLE);
                    headacheImg.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CalendarActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void GoToMenuActivity(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void ChangeData(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("date", date);
        startActivity(intent);
    }
}