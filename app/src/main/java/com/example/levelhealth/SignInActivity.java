package com.example.levelhealth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    private EditText EmailBDent, PasswordBDent;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        assert mAuth != null;
        FirebaseUser cUser = mAuth.getCurrentUser();
        if(cUser != null && cUser.isEmailVerified()){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void GoToRegistrationActivity(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void GoToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void GoToPasswordRecoveryActivity(View view) {
        Intent intent = new Intent(this, PasswordRecoveryActivity.class);
        startActivity(intent);
    }

    public void init(){
        EmailBDent = findViewById(R.id.EmailBDent);
        PasswordBDent = findViewById(R.id.PasswordBDent);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onClickEnterBD(View view) {
        if(!TextUtils.isEmpty(EmailBDent.getText().toString()) && !TextUtils.isEmpty(PasswordBDent.getText().toString())) {
            mAuth.signInWithEmailAndPassword(EmailBDent.getText().toString(), PasswordBDent.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (task.isSuccessful() && user.isEmailVerified()) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Вход не произведен, подтвердите email и проверьте данные", Toast.LENGTH_SHORT).show();
                        Log.d("USER", user != null ? user.getEmail():"user == null");
                    }
                }
            });
        }
    }
}