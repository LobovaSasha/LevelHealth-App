package com.example.levelhealth;

import static java.lang.Math.abs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private RecyclerView daysRecycler;
    private DayAdapter dayAdapter;
    private ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9, iv10, iv11, iv12, iv13;
    private Integer smile_res = -1, sleep_res = -1, headache_res = 0;
    private DatabaseReference mDataBase;

    Calendar calendar = Calendar.getInstance();
    int calendarMonthDay = calendar.get(Calendar.DAY_OF_MONTH);
    int calendarWeekDay = calendar.get(Calendar.DAY_OF_WEEK);

    private ArrayList<String> settingsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        // Создание вручную списка дней, в дальнейшем через бд
        List<Day> dayList = new ArrayList<>();

//        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
//
//        String[] parts = date.split("-");
//        Integer currentDate = Integer.valueOf(parts[2]);

        String[] weekDays = new String[]{"Вс", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб"};


        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH)+1;


        YearMonth yearMonthObject = YearMonth.of(currentYear, currentMonth);
        int daysInMonth = yearMonthObject.lengthOfMonth();

        YearMonth yearLastMonthObject = YearMonth.of(currentYear, currentMonth-1);
        int daysInLastMonth = yearLastMonthObject.lengthOfMonth();

        int i = calendarMonthDay - 25;

        for (int j = 0; j < 29; j++) {

            if (i <= 0){
                i = i + daysInLastMonth - 1;

            }
            if (i > daysInMonth){
                i = i - daysInMonth - 1;
            }

            i = i + 1;

//            System.out.println(calendarWeekDay);

            dayList.add(new Day(j, weekDays[(calendarWeekDay + j + 2) % 7], String.valueOf(i)));

        }

        setDaysRecycler(dayList);

        iv1 = (ImageView) findViewById(R.id.image_view_smile1);
        iv2 = (ImageView) findViewById(R.id.image_view_smile2);
        iv3 = (ImageView) findViewById(R.id.image_view_smile3);
        iv4 = (ImageView) findViewById(R.id.image_view_smile4);

        iv5 = (ImageView) findViewById(R.id.image_view_sleep1);
        iv6 = (ImageView) findViewById(R.id.image_view_sleep2);
        iv7 = (ImageView) findViewById(R.id.image_view_sleep3);
        iv8 = (ImageView) findViewById(R.id.image_view_sleep4);

        Button b1 = (Button) findViewById(R.id.button_smile1);
        Button b2 = (Button) findViewById(R.id.button_smile2);
        Button b3 = (Button) findViewById(R.id.button_smile3);
        Button b4 = (Button) findViewById(R.id.button_smile4);

        Button b5 = (Button) findViewById(R.id.button_sleep1);
        Button b6 = (Button) findViewById(R.id.button_sleep2);
        Button b7 = (Button) findViewById(R.id.button_sleep3);
        Button b8 = (Button) findViewById(R.id.button_sleep4);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv1.setImageResource(R.drawable.smile1_0);
                iv2.setImageResource(R.drawable.smile0_1);
                iv3.setImageResource(R.drawable.smile0_2);
                iv4.setImageResource(R.drawable.smile0_3);
                smile_res = 0;
                saveDB();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv1.setImageResource(R.drawable.smile0_0);
                iv2.setImageResource(R.drawable.smile1_1);
                iv3.setImageResource(R.drawable.smile0_2);
                iv4.setImageResource(R.drawable.smile0_3);
                smile_res = 1;
                saveDB();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv1.setImageResource(R.drawable.smile0_0);
                iv2.setImageResource(R.drawable.smile0_1);
                iv3.setImageResource(R.drawable.smile1_2);
                iv4.setImageResource(R.drawable.smile0_3);
                smile_res = 2;
                saveDB();
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv1.setImageResource(R.drawable.smile0_0);
                iv2.setImageResource(R.drawable.smile0_1);
                iv3.setImageResource(R.drawable.smile0_2);
                iv4.setImageResource(R.drawable.smile1_3);
                smile_res = 3;
                saveDB();
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv5.setImageResource(R.drawable.sleep1_0);
                iv6.setImageResource(R.drawable.sleep0_1);
                iv7.setImageResource(R.drawable.sleep0_2);
                iv8.setImageResource(R.drawable.sleep0_3);
                sleep_res = 0;
                saveDB();
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv5.setImageResource(R.drawable.sleep0_0);
                iv6.setImageResource(R.drawable.sleep1_1);
                iv7.setImageResource(R.drawable.sleep0_2);
                iv8.setImageResource(R.drawable.sleep0_3);
                sleep_res = 1;
                saveDB();
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv5.setImageResource(R.drawable.sleep0_0);
                iv6.setImageResource(R.drawable.sleep0_1);
                iv7.setImageResource(R.drawable.sleep1_2);
                iv8.setImageResource(R.drawable.sleep0_3);
                sleep_res = 2;
                saveDB();
            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv5.setImageResource(R.drawable.sleep0_0);
                iv6.setImageResource(R.drawable.sleep0_1);
                iv7.setImageResource(R.drawable.sleep0_2);
                iv8.setImageResource(R.drawable.sleep1_3);
                sleep_res = 3;
                saveDB();
            }
        });
    }

    private void setDaysRecycler(List<Day> dayList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        daysRecycler = findViewById(R.id.daysRecycler);
        daysRecycler.setLayoutManager(layoutManager);

        dayAdapter = new DayAdapter(this, dayList);
        daysRecycler.setAdapter(dayAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser cUser = mAuth.getCurrentUser();
        if(cUser==null) {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        }
        init();

        // получение списка настроек
        settingsList = getSettings();
        HashMap<String, int[]> settingsItems = new HashMap<>();
        settingsItems.put("sleep", new int[]{R.id.textView10,  R.id.frameLayout3});
        settingsItems.put("headache", new int[]{R.id.textView11,  R.id.frameLayout4});
        settingsItems.put("notes", new int[]{R.id.frameLayout4});
        for (String s : settingsItems.keySet()) {
            if (!settingsList.contains(s)) {
                for (int id : settingsItems.get(s)) findViewById(id).setVisibility(View.GONE);
            }
        }
    }

    public void init(){
        mDataBase = FirebaseDatabase.getInstance().getReference("Condition");
        mAuth = FirebaseAuth.getInstance();
        final DatabaseReference RootRef;
        String date = getDate();
        FirebaseUser cUser = mAuth.getCurrentUser();
        assert cUser != null;
        String idtable = cUser.getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String mood = Objects.requireNonNull(snapshot.child("Condition").child(idtable).child(date).child("mood").getValue()).toString();
                    String sleep = Objects.requireNonNull(snapshot.child("Condition").child(idtable).child(date).child("sleep").getValue()).toString();
                    String headache = Objects.requireNonNull(snapshot.child("Condition").child(idtable).child(date).child("headache").getValue()).toString();
                    switch (mood) {
                        case "0":
                            iv1.setImageResource(R.drawable.smile1_0);
                            smile_res = 0;
                            break;
                        case "1":
                            iv2.setImageResource(R.drawable.smile1_1);
                            smile_res = 1;
                            break;
                        case "2":
                            iv3.setImageResource(R.drawable.smile1_2);
                            smile_res = 2;
                            break;
                        case "3":
                            iv4.setImageResource(R.drawable.smile1_3);
                            smile_res = 3;
                            break;
                    }
                    switch (sleep) {
                        case "0":
                            iv5.setImageResource(R.drawable.sleep1_0);
                            sleep_res = 0;
                            break;
                        case "1":
                            iv6.setImageResource(R.drawable.sleep1_1);
                            sleep_res = 1;
                            break;
                        case "2":
                            iv7.setImageResource(R.drawable.sleep1_2);
                            sleep_res = 2;
                            break;
                        case "3":
                            iv8.setImageResource(R.drawable.sleep1_3);
                            sleep_res = 3;
                            break;
                    }
                    switch (headache) {
                        case "1":
                            iv9.setImageResource(R.drawable.headache1_1);
                            headache_res = 1;
                            break;
                        case "2":
                            iv10.setImageResource(R.drawable.headache1_2);
                            headache_res = 2;
                            break;
                        case "3":
                            iv11.setImageResource(R.drawable.headache1_3);
                            headache_res = 3;
                            break;
                        case "4":
                            iv12.setImageResource(R.drawable.headache1_4);
                            headache_res = 4;
                            break;
                        case "5":
                            iv13.setImageResource(R.drawable.headache1_5);
                            headache_res = 5;
                            break;
                    }
                } catch (Exception ignored) {}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveDB(){
        final DatabaseReference RootRef;
        String date = getDate();
        FirebaseUser cUser = mAuth.getCurrentUser();
        assert cUser != null;
        String idtable = cUser.getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> userDataMap = new HashMap<>();
                userDataMap.put("mood", smile_res);
                userDataMap.put("sleep", sleep_res);
                userDataMap.put("headache", headache_res);
                RootRef.child("Condition").child(idtable).child(date).updateChildren(userDataMap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public ArrayList<String> getSettings() {
        ArrayList<String> res = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("settings.txt")));
            String txt = br.readLine();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                res = (ArrayList<String>) Arrays.stream(txt.split(";"))
                        .collect(Collectors.toList());
            }
        } catch (FileNotFoundException fe) {
            try {
                FileOutputStream file = openFileOutput("settings.txt", MODE_PRIVATE);
                file.write("sleep;headache;tablets;notes".getBytes(StandardCharsets.UTF_8));
                file.close();

                res = getSettings();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void GoToCalendarActivity(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
    public void GoToChangeWindowActivity(View view) {
        Intent intent = new Intent(this, ChangeWindowActivity.class);
        startActivity(intent);
    }
    public void GoToMenuActivity(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
    public void GoToRegistrationActivity(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
    public void GoToSignInActivity(View view) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
    public void GoToGraphicsActivity(View view) {
        Intent intent = new Intent(this, GraphicsActivity.class);
        startActivity(intent);
    }

    public void GoToNoteActivity(View view) {
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("date", getDate());
        startActivity(intent);
    }

    public String getDate() {
        try {
            Bundle arguments = getIntent().getExtras();
            return arguments.get("date").toString();
        } catch (Exception e) {
            Calendar calendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            return formatter.format(calendar.getTime());
        }
    }
}