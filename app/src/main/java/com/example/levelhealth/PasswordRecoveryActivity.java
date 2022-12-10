package com.example.levelhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PasswordRecoveryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);
    }

    protected void onStart() {
        super.onStart();
    }

    public void RecoveryPassword(View view) {
        EditText email = findViewById(R.id.editTextEmail);
        String wow = email.getText().toString();
        FirebaseAuth.getInstance().sendPasswordResetEmail(wow).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Проверьте свою почту", Toast.LENGTH_SHORT).show();

                }
            }
        });
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}