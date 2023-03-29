package com.example.levelhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SettingsActivity extends AppCompatActivity {

    final static String FILE_NAME = "settings.txt";

    Button back, home;
    SwitchCompat sw_sleep, sw_headache, sw_tablets, sw_notes;

    boolean sleep = false, headache = false, tablets = false, notes = false;

    public void init() {
        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        sw_sleep = findViewById(R.id.sw_sleep);
        sw_headache = findViewById(R.id.sw_headache);
        sw_tablets = findViewById(R.id.sw_tablets);
        sw_notes = findViewById(R.id.sw_notes);
    }

    /*
    * settings.txt:
    * sleep;headache;tablets;notes
    * */

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
        String data = "";
        data += sleep ? "sleep;" : "";
        data += headache ? "headache;" : "";
        data += tablets ? "tablets;" : "";
        data += notes ? "notes" : "";
        try {
            FileOutputStream file = openFileOutput(FILE_NAME, MODE_PRIVATE);
            file.write(data.getBytes(StandardCharsets.UTF_8));
            file.close();
        } catch (Exception e) {}
    }

    public void setSwitches() {
        sw_sleep.setChecked(sleep);
        sw_headache.setChecked(headache);
        sw_tablets.setChecked(tablets);
        sw_notes.setChecked(notes);
    }

    public void loadSettings() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(FILE_NAME)));
            String txt = br.readLine();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ArrayList<String> settings = (ArrayList<String>) Arrays.stream(txt.split(";"))
                        .collect(Collectors.toList());
                sleep = settings.contains("sleep");
                headache = settings.contains("headache");
                tablets = settings.contains("tablets");
                notes = settings.contains("notes");
                setSwitches();
            }
        } catch (FileNotFoundException fe) {
            try {
                FileOutputStream file = openFileOutput(FILE_NAME, MODE_PRIVATE);
                file.write("sleep;headache;tablets;notes".getBytes(StandardCharsets.UTF_8));
                file.close();

                loadSettings();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void GoToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}