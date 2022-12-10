package com.example.levelhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoadingActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
    }

    protected void onStart() {
        super.onStart();
        assert mAuth != null;
        FirebaseUser cUser = mAuth.getCurrentUser();
        if(cUser != null && cUser.isEmailVerified()){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        }
    }
}