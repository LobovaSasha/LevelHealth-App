package com.example.levelhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NoteActivity extends AppCompatActivity {

    Button backBtn;

    private TextView dateTimeDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;


    private void init() {
        backBtn = findViewById(R.id.back);

        dateTimeDisplay = findViewById(R.id.dateText);

//        dateTV = findViewById(R.id.dateText);
//        readyBtn = findViewById(R.id.ready);
//        titleTV = findViewById(R.id.titleText);
//        noteTV = findViewById(R.id.noteText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        init();

        backBtn.setOnClickListener(v -> {
            onBackPressed();
        });

        calendar = Calendar.getInstance();

        dateFormat = new SimpleDateFormat("dd MMMM", new Locale("ru") );
        date = dateFormat.format(calendar.getTime());
        dateTimeDisplay.setText(date);

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();

    }
}


/*
*
* Пример вызова страницы:
  Intent intent = new Intent(this, NoteActivity.class);
  intent.putExtra("day", "11");
  intent.putExtra("month", "4");
  startActivity(intent);
*
* */
