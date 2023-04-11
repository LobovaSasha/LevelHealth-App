package com.example.levelhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

public class SettingsActivity extends AppCompatActivity {

    Button back, home;
    SwitchCompat sw_sleep, sw_headache, sw_tablets, sw_notes;
    private DatabaseReference RootRef;
    private String idtable;
    boolean sleep = false, headache = false, tablets = false, notes = false;

    public void init() {
        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        sw_sleep = findViewById(R.id.sw_sleep);
        sw_headache = findViewById(R.id.sw_headache);
        sw_tablets = findViewById(R.id.sw_tablets);
        sw_notes = findViewById(R.id.sw_notes);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser cUser = mAuth.getCurrentUser();
        assert cUser != null;
        idtable = cUser.getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
        loadSettings();


        sw_sleep.setOnClickListener(v -> {
            sleep = !sleep;
            setSettings();
        });
        sw_headache.setOnClickListener(v -> {
            headache = !headache;
            setSettings();
        });
        sw_tablets.setOnClickListener(v -> {
            tablets = !tablets;
            setSettings();
        });
        sw_notes.setOnClickListener(v -> {
            notes = !notes;
            setSettings();
        });


        back.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    // функция вызывается при изменении состояния любого Switch
    // и обновляет settings.txt
    public void setSettings() {
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("sleep", sleep);
                    userDataMap.put("headache", headache);
                    userDataMap.put("tablets", tablets);
                    userDataMap.put("notes", notes);
                    RootRef.child("User").child(idtable).child("Settings").updateChildren(userDataMap);
                } catch (Exception ignored) {}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SettingsActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setSwitches() {

    }

    public void loadSettings() {
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    sleep = Boolean.parseBoolean(Objects.requireNonNull(snapshot.child("User").child(idtable).child("Settings").child("sleep").getValue()).toString());
                    headache = Boolean.parseBoolean(Objects.requireNonNull(snapshot.child("User").child(idtable).child("Settings").child("headache").getValue()).toString());
                    tablets = Boolean.parseBoolean(Objects.requireNonNull(snapshot.child("User").child(idtable).child("Settings").child("tablets").getValue()).toString());
                    notes = Boolean.parseBoolean(Objects.requireNonNull(snapshot.child("User").child(idtable).child("Settings").child("notes").getValue()).toString());
                } catch (Exception ignored) {
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("sleep", "true");
                    userDataMap.put("headache", "true");
                    userDataMap.put("tablets", "true");
                    userDataMap.put("notes", "true");
                    RootRef.child("User").child(idtable).child("Settings").updateChildren(userDataMap);
                    sleep = true;
                    headache = true;
                    tablets = true;
                    notes = true;
                }
                sw_sleep.setChecked(sleep);
                sw_headache.setChecked(headache);
                sw_tablets.setChecked(tablets);
                sw_notes.setChecked(notes);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SettingsActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void GoToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}