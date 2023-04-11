package com.example.levelhealth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private SeekBar sb;
    private ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8;
    private Integer smile_res = -1, sleep_res = -1, headache_res = 0;
    private DatabaseReference RootRef;
    private String date, idtable;
    Calendar calendar = Calendar.getInstance();
    int calendarMonthDay = calendar.get(Calendar.DAY_OF_MONTH);
    int calendarWeekDay = calendar.get(Calendar.DAY_OF_WEEK);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        List<Day> dayList = new ArrayList<>();

        String[] weekDays = new String[]{"Вс", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб"};


        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH)+1;
        int daysInMonth = 31, daysInLastMonth = 31;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            daysInMonth = YearMonth.of(currentYear, currentMonth).lengthOfMonth();
            daysInLastMonth = YearMonth.of(currentYear, (currentMonth + 11) % 12).lengthOfMonth();
        }

        int i = calendarMonthDay - 24;

        for (int j = 0; j < 29; j++) {

            if (i <= 0){
                i = i + daysInLastMonth - 1;

            }
            if (i > daysInMonth){
                i = i - daysInMonth - 1;
            }

            i = i + 1;

            dayList.add(new Day(j, weekDays[(calendarWeekDay + j + 3) % 7], String.valueOf(i)));

        }

        setDaysRecycler(dayList);

        iv1 = findViewById(R.id.image_view_smile1);
        iv2 = findViewById(R.id.image_view_smile2);
        iv3 = findViewById(R.id.image_view_smile3);
        iv4 = findViewById(R.id.image_view_smile4);

        iv5 = findViewById(R.id.image_view_sleep1);
        iv6 = findViewById(R.id.image_view_sleep2);
        iv7 = findViewById(R.id.image_view_sleep3);
        iv8 = findViewById(R.id.image_view_sleep4);

        sb = findViewById(R.id.seekBar2);

        Button b1 = findViewById(R.id.button_smile1);
        Button b2 = findViewById(R.id.button_smile2);
        Button b3 = findViewById(R.id.button_smile3);
        Button b4 = findViewById(R.id.button_smile4);

        Button b5 = findViewById(R.id.button_sleep1);
        Button b6 = findViewById(R.id.button_sleep2);
        Button b7 = findViewById(R.id.button_sleep3);
        Button b8 = findViewById(R.id.button_sleep4);

        b1.setOnClickListener(v -> {
            iv1.setImageResource(R.drawable.smile1_0);
            iv2.setImageResource(R.drawable.smile0_1);
            iv3.setImageResource(R.drawable.smile0_2);
            iv4.setImageResource(R.drawable.smile0_3);
            smile_res = 0;
            saveDB();
        });

        b2.setOnClickListener(v -> {
            iv1.setImageResource(R.drawable.smile0_0);
            iv2.setImageResource(R.drawable.smile1_1);
            iv3.setImageResource(R.drawable.smile0_2);
            iv4.setImageResource(R.drawable.smile0_3);
            smile_res = 1;
            saveDB();
        });

        b3.setOnClickListener(v -> {
            iv1.setImageResource(R.drawable.smile0_0);
            iv2.setImageResource(R.drawable.smile0_1);
            iv3.setImageResource(R.drawable.smile1_2);
            iv4.setImageResource(R.drawable.smile0_3);
            smile_res = 2;
            saveDB();
        });

        b4.setOnClickListener(v -> {
            iv1.setImageResource(R.drawable.smile0_0);
            iv2.setImageResource(R.drawable.smile0_1);
            iv3.setImageResource(R.drawable.smile0_2);
            iv4.setImageResource(R.drawable.smile1_3);
            smile_res = 3;
            saveDB();
        });

        b5.setOnClickListener(v -> {
            iv5.setImageResource(R.drawable.sleep1_0);
            iv6.setImageResource(R.drawable.sleep0_1);
            iv7.setImageResource(R.drawable.sleep0_2);
            iv8.setImageResource(R.drawable.sleep0_3);
            sleep_res = 0;
            saveDB();
        });

        b6.setOnClickListener(v -> {
            iv5.setImageResource(R.drawable.sleep0_0);
            iv6.setImageResource(R.drawable.sleep1_1);
            iv7.setImageResource(R.drawable.sleep0_2);
            iv8.setImageResource(R.drawable.sleep0_3);
            sleep_res = 1;
            saveDB();
        });

        b7.setOnClickListener(v -> {
            iv5.setImageResource(R.drawable.sleep0_0);
            iv6.setImageResource(R.drawable.sleep0_1);
            iv7.setImageResource(R.drawable.sleep1_2);
            iv8.setImageResource(R.drawable.sleep0_3);
            sleep_res = 2;
            saveDB();
        });

        b8.setOnClickListener(v -> {
            iv5.setImageResource(R.drawable.sleep0_0);
            iv6.setImageResource(R.drawable.sleep0_1);
            iv7.setImageResource(R.drawable.sleep0_2);
            iv8.setImageResource(R.drawable.sleep1_3);
            sleep_res = 3;
            saveDB();
        });

        sb.setOnSeekBarChangeListener(seekBarChangeListener);

    }

    private void setDaysRecycler(List<Day> dayList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        RecyclerView daysRecycler = findViewById(R.id.daysRecycler);
        daysRecycler.setLayoutManager(layoutManager);

        DayAdapter dayAdapter = new DayAdapter(this, dayList);
        daysRecycler.setAdapter(dayAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser cUser = mAuth.getCurrentUser();
        if(cUser ==null) {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        }
        date = getDate();
        assert cUser != null;
        idtable = cUser.getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();
        init();
    }

    public void init(){
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    boolean sleep = Boolean.parseBoolean(Objects.requireNonNull(snapshot.child("User").child(idtable).child("Settings").child("sleep").getValue()).toString());
                    boolean headache = Boolean.parseBoolean(Objects.requireNonNull(snapshot.child("User").child(idtable).child("Settings").child("headache").getValue()).toString());
                    boolean notes = Boolean.parseBoolean(Objects.requireNonNull(snapshot.child("User").child(idtable).child("Settings").child("notes").getValue()).toString());
                    if (!sleep) {
                            findViewById(R.id.textView10).setVisibility(View.GONE);
                            findViewById(R.id.frameLayout3).setVisibility(View.GONE);
                    }
                    if (!headache) {
                        findViewById(R.id.textView11).setVisibility(View.GONE);
                        findViewById(R.id.frameLayout4).setVisibility(View.GONE);
                    }
                    if (!notes) {
                        findViewById(R.id.textView13).setVisibility(View.GONE);
                        findViewById(R.id.frameLayout5).setVisibility(View.GONE);
                    }
                } catch (Exception ignored) {
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("sleep", "true");
                    userDataMap.put("headache", "true");
                    userDataMap.put("tablets", "true");
                    userDataMap.put("notes", "true");
                    RootRef.child("User").child(idtable).child("Settings").updateChildren(userDataMap);
                }
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
                    headache_res = Integer.parseInt(headache);
                    sb.setProgress(headache_res);
                } catch (Exception ignored) {}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveDB(){
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

    private final SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener(){
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            headache_res = sb.getProgress();
            saveDB();
        }
    };
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
        TextView text_data = findViewById(R.id.textView8);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM", new Locale("ru"));
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Bundle arguments = getIntent().getExtras();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Objects.requireNonNull(formatter.parse(arguments.get("date").toString())));
            text_data.setText(dateFormat.format(calendar.getTime()));
            return arguments.get("date").toString();
        } catch (Exception e) {
            Calendar calendar = Calendar.getInstance();
            text_data.setText("Сегодня");
            return formatter.format(calendar.getTime());
        }
    }
}