package com.example.levelhealth;

import android.content.Intent;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private RecyclerView daysRecycler;
    private DayAdapter dayAdapter;
    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13;
    private ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9, iv10, iv11, iv12, iv13;
    private Integer smile_res = -1, sleep_res = -1, headache_res = 0;
    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        // Создание вручную списка дней, в дальнейшем через бд
        List<Day> dayList = new ArrayList<>();

        String[] weekDays = new String[]{"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"};
        for (int i = 0; i < 15; i++) {
            System.out.println(weekDays[i % 7]);
            dayList.add(new Day(i+1, weekDays[i % 7], String.valueOf(i+1)));

        }

        setDaysRecycler(dayList);
        //

        iv1 = (ImageView) findViewById(R.id.image_view_smile1);
        iv2 = (ImageView) findViewById(R.id.image_view_smile2);
        iv3 = (ImageView) findViewById(R.id.image_view_smile3);
        iv4 = (ImageView) findViewById(R.id.image_view_smile4);

        iv5 = (ImageView) findViewById(R.id.image_view_sleep1);
        iv6 = (ImageView) findViewById(R.id.image_view_sleep2);
        iv7 = (ImageView) findViewById(R.id.image_view_sleep3);
        iv8 = (ImageView) findViewById(R.id.image_view_sleep4);

        iv9 = (ImageView) findViewById(R.id.image_view_headache1);
        iv10 = (ImageView) findViewById(R.id.image_view_headache2);
        iv11 = (ImageView) findViewById(R.id.image_view_headache3);
        iv12 = (ImageView) findViewById(R.id.image_view_headache4);
        iv13 = (ImageView) findViewById(R.id.image_view_headache5);

        b1 = (Button) findViewById(R.id.button_smile1);
        b2 = (Button) findViewById(R.id.button_smile2);
        b3 = (Button) findViewById(R.id.button_smile3);
        b4 = (Button) findViewById(R.id.button_smile4);

        b5 = (Button) findViewById(R.id.button_sleep1);
        b6 = (Button) findViewById(R.id.button_sleep2);
        b7 = (Button) findViewById(R.id.button_sleep3);
        b8 = (Button) findViewById(R.id.button_sleep4);

        b9 = (Button) findViewById(R.id.button_headache1);
        b10 = (Button) findViewById(R.id.button_headache2);
        b11 = (Button) findViewById(R.id.button_headache3);
        b12 = (Button) findViewById(R.id.button_headache4);
        b13 = (Button) findViewById(R.id.button_headache5);

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

        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv9.setImageResource(R.drawable.headache1_1);
                iv10.setImageResource(R.drawable.headache0_2);
                iv11.setImageResource(R.drawable.headache0_3);
                iv12.setImageResource(R.drawable.headache0_4);
                iv13.setImageResource(R.drawable.headache0_5);
                headache_res = 1;
                saveDB();
            }
        });

        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv9.setImageResource(R.drawable.headache0_1);
                iv10.setImageResource(R.drawable.headache1_2);
                iv11.setImageResource(R.drawable.headache0_3);
                iv12.setImageResource(R.drawable.headache0_4);
                iv13.setImageResource(R.drawable.headache0_5);
                headache_res = 2;
                saveDB();
            }
        });

        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv9.setImageResource(R.drawable.headache0_1);
                iv10.setImageResource(R.drawable.headache0_2);
                iv11.setImageResource(R.drawable.headache1_3);
                iv12.setImageResource(R.drawable.headache0_4);
                iv13.setImageResource(R.drawable.headache0_5);
                headache_res = 3;
                saveDB();
            }
        });

        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv9.setImageResource(R.drawable.headache0_1);
                iv10.setImageResource(R.drawable.headache0_2);
                iv11.setImageResource(R.drawable.headache0_3);
                iv12.setImageResource(R.drawable.headache1_4);
                iv13.setImageResource(R.drawable.headache0_5);
                headache_res = 4;
                saveDB();
            }
        });

        b13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv9.setImageResource(R.drawable.headache0_1);
                iv10.setImageResource(R.drawable.headache0_2);
                iv11.setImageResource(R.drawable.headache0_3);
                iv12.setImageResource(R.drawable.headache0_4);
                iv13.setImageResource(R.drawable.headache1_5);
                headache_res = 5;
                saveDB();
            }
        });



    }

    // вернула метод и классы day и dayAdapter
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
    }

    public void init(){
        mDataBase = FirebaseDatabase.getInstance().getReference("Condition");
        mAuth = FirebaseAuth.getInstance();
        final DatabaseReference RootRef;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date = formatter.format(calendar.getTime());
        FirebaseUser cUser = mAuth.getCurrentUser();
        String idtable = cUser.getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Condition").child(idtable).child(date).exists()){
                    String mood = snapshot.child("Condition").child(idtable).child(date).child("mood").getValue().toString();
                    String sleep = snapshot.child("Condition").child(idtable).child(date).child("sleep").getValue().toString();
                    String headache = snapshot.child("Condition").child(idtable).child(date).child("headache").getValue().toString();
                    if (mood.equals("0")) {
                        iv1.setImageResource(R.drawable.smile1_0);
                    } else if (mood.equals("1")) {
                        iv2.setImageResource(R.drawable.smile1_1);
                    } else if (mood.equals("2")) {
                        iv3.setImageResource(R.drawable.smile1_2);
                    } else if (mood.equals("3")) {
                        iv4.setImageResource(R.drawable.smile1_3);
                    }
                    if (sleep.equals("0")) {
                        iv5.setImageResource(R.drawable.sleep1_0);
                    } else if (sleep.equals("1")) {
                        iv6.setImageResource(R.drawable.sleep1_1);
                    } else if (sleep.equals("2")) {
                        iv7.setImageResource(R.drawable.sleep1_2);
                    } else if (sleep.equals("3")) {
                        iv8.setImageResource(R.drawable.sleep1_3);
                    }
                    if (headache.equals("1")) {
                        iv9.setImageResource(R.drawable.headache1_1);
                    } else if (headache.equals("2")) {
                        iv10.setImageResource(R.drawable.headache1_2);
                    } else if (headache.equals("3")) {
                        iv11.setImageResource(R.drawable.headache1_3);
                    } else if (headache.equals("4")) {
                        iv12.setImageResource(R.drawable.headache1_4);
                    } else if (headache.equals("5")) {
                        iv13.setImageResource(R.drawable.headache1_5);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveDB(){
        final DatabaseReference RootRef;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date = formatter.format(calendar.getTime());
        FirebaseUser cUser = mAuth.getCurrentUser();
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
    public void GoToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void GoToGraphicsActivity(View view) {
        Intent intent = new Intent(this, GraphicsActivity.class);
        startActivity(intent);
    }
}