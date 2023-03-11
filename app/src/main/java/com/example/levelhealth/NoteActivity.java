package com.example.levelhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class NoteActivity extends AppCompatActivity {

    Button backBtn;
    TextView dateTV, readyBtn, titleTV, noteTV;

    final String DAY_KEY = "day", MONTH_KEY = "month", NEW_KEY = "new";

    String[] months = new String[]{
            "января", "февраля", "марта", "апреля",
            "мая", "июня", "июля", "августа",
            "сентября", "октября", "ноября", "декабря"};

    private void init() {
        backBtn = findViewById(R.id.back);
        dateTV = findViewById(R.id.dateText);
        readyBtn = findViewById(R.id.ready);
        titleTV = findViewById(R.id.titleText);
        noteTV = findViewById(R.id.noteText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        init();

        backBtn.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();

        Bundle args = getIntent().getExtras();
        boolean isNewNote = false;
        if (args != null) {
            try {
                // numericDate == "08.04.2023"
                String numDay = args.getString(DAY_KEY);
                String numMonth = args.getString(MONTH_KEY);
                int idMonth = Integer.parseInt(numMonth) - 1;
                dateTV.setText(numDay+" "+months[idMonth]);
            } catch (Exception e) {
                Log.d("DATE_ERROR", e.toString());
                dateTV.setText("");
            }

            try {
                isNewNote = args.getBoolean(NEW_KEY);
            } catch (Exception e) {}
        }

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