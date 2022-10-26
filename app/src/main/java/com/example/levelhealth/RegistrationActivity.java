package com.example.levelhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;

public class RegistrationActivity extends AppCompatActivity {
    private EditText NameBDreg, SurnameBDreg, BirthBDreg, EmailBDreg, PasswordBDreg, TypeBDreg;
    private DatabaseReference mDataBase;
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
        TypeBDreg = findViewById(R.id.TypeBDreg);
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
    }

    public void onClickSaveBD(View view) {
        String id = mDataBase.getKey();
        String username = NameBDreg.getText().toString();
        String surname = SurnameBDreg.getText().toString();
        String birth = BirthBDreg.getText().toString();
        String email = EmailBDreg.getText().toString();
        String password = PasswordBDreg.getText().toString();
        String type = TypeBDreg.getText().toString();
        Toast.makeText(this, username, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, surname, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, birth, Toast.LENGTH_SHORT).show();
        UserBD newUser = new UserBD(id, username, surname, birth, email, password, type);
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(surname) && !TextUtils.isEmpty(birth) &&
                !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(type)) {
            mDataBase.push().setValue(newUser);
            Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
    }

}