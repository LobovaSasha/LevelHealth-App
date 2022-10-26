package com.example.levelhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    public void GoToConfirmationActivity(View view) {
        Intent intent = new Intent(this, ConfirmationActivity.class);
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
}