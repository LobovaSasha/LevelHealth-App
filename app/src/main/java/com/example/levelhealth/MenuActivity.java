package com.example.levelhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    protected void onStart() {
        super.onStart();
        FirebaseUser cUser = mAuth.getCurrentUser();
        if(cUser==null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void Exit(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public void GoToChangeWindowActivity(View view) {
        Intent intent = new Intent(this, ChangeWindowActivity.class);
        startActivity(intent);
    }

    public void GoToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}