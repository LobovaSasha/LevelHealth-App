package com.example.levelhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeWindow extends AppCompatActivity {

    EditText name, birthday, address, phone;
    Switch smoking, alcohol;
    Button save;
    DatabaseReference db;
    String USER_KEY = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_window);
        init();
        save.setOnClickListener(v -> {
            saveData();
        });
    }

    private void init() {
        name = findViewById(R.id.name);
        birthday = findViewById(R.id.birthday);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        smoking = findViewById(R.id.smoking);
        alcohol = findViewById(R.id.alcohol);
        save = findViewById(R.id.save);
        db = FirebaseDatabase.getInstance().getReference(USER_KEY);
    }

    private void saveData() {
        String id = db.getKey();
        Log.d("ID", id);
    }
}