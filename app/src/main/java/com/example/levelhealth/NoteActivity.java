package com.example.levelhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class NoteActivity extends AppCompatActivity {

    Button backBtn;
    TextView dateTV, readyBtn, titleTV, noteTV;

    final String DATE_KEY = "date", NEW_KEY = "new";

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

        Bundle args = getIntent().getExtras();
        String numericDate = "";
        boolean isNewNote = false;
        if (args != null) {
            try {
                numericDate = args.getString(DATE_KEY);
            } catch (Exception e) {}

            try {
                isNewNote = args.getBoolean(NEW_KEY);
            } catch (Exception e) {}
        }

    }
}