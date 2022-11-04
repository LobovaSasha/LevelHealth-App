package com.example.levelhealth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private RecyclerView daysRecycler;
    private DayAdapter dayAdapter;

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
    }

    public void GoToCalendarActivity(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
    public void GoToChangeWindowActivity(View view) {
        Intent intent = new Intent(this, ChangeWindowActivity.class);
        startActivity(intent);
    }
    public void GoToChartsActivity(View view) {
        Intent intent = new Intent(this, ChartsActivity.class);
        startActivity(intent);
    }
    public void GoToLoadingActivity(View view) {
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);
    }
    public void GoToMenuActivity(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
    public void GoToNotificationActivity(View view) {
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }
    public void GoToPasswordChangeActivity(View view) {
        Intent intent = new Intent(this, PasswordChangeActivity.class);
        startActivity(intent);
    }
    public void GoToPasswordRecoveryActivity(View view) {
        Intent intent = new Intent(this, PasswordRecoveryActivity.class);
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
    public void Exit(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}