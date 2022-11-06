package com.example.levelhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    private EditText NameBDreg, SurnameBDreg, BirthBDreg, EmailBDreg, PasswordBDreg, TypeBDreg;
    private DatabaseReference mDataBase;
    private FirebaseAuth mAuth;
    private String USER_KEY = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
    }

    public void init(){
        NameBDreg = findViewById(R.id.NameBDreg);
        SurnameBDreg = findViewById(R.id.SurnameBDreg);
        BirthBDreg = findViewById(R.id.BirthBDreg);
        EmailBDreg = findViewById(R.id.EmailBDreg);
        PasswordBDreg = findViewById(R.id.PasswordBDreg);
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onClickSaveBD(View view) {
        String id = mDataBase.getKey();
        String username = NameBDreg.getText().toString();
        String surname = SurnameBDreg.getText().toString();
        String birth = BirthBDreg.getText().toString();
        String email = EmailBDreg.getText().toString();
        String password = PasswordBDreg.getText().toString();
        String type = "p";
        if(!TextUtils.isEmpty(EmailBDreg.getText().toString()) && !TextUtils.isEmpty(PasswordBDreg.getText().toString())) {
            mAuth.createUserWithEmailAndPassword(EmailBDreg.getText().toString(), PasswordBDreg.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        sendEmailVer();
                        FirebaseUser user = mAuth.getCurrentUser();
                        Log.d("CURRENT_USER", user!=null ? user.getEmail() : "user = null");
                        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                        startActivity(intent);
                    } else
                        Toast.makeText(getApplicationContext(), "Регистрация не удалась, проверьте данные и попробуйте еще раз", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else Toast.makeText(this, "Заполните пустые поля", Toast.LENGTH_SHORT).show();
        UserBD newUser = new UserBD(id, username, surname, birth, email, type);
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(surname) && !TextUtils.isEmpty(birth) &&
                !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(type)) {
            mDataBase.push().setValue(newUser);
        }
    }

    private void sendEmailVer(){
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Проверьте ваш почтовый ящик, подтвердите email и выполните вход", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getApplicationContext(), "Отправка сообщения провалилась, проверьте данные", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

