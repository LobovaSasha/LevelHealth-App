package com.example.levelhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {
    private EditText NameBDreg, SurnameBDreg, BirthBDreg, EmailBDreg, PasswordBDreg, TypeBDreg;
    private DatabaseReference mDataBase;
    private FirebaseAuth mAuth;
    private String USER_KEY = "User";
    private String idtable;
    private CheckBox checkBox;

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
        checkBox = findViewById(R.id.checkBox);
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
        mAuth = FirebaseAuth.getInstance();
    }

    public boolean isDateValid(String date) {
        try {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void onClickSaveBD(View view) {
        String id = mDataBase.getKey();
        String username = NameBDreg.getText().toString();
        String usersurname = SurnameBDreg.getText().toString();
        String email = EmailBDreg.getText().toString();
        String password = PasswordBDreg.getText().toString();
<<<<<<< Updated upstream

        String birth = "Пока не заполнено";
        String doc = "Пока не заполнено";
        String gender = "Пока не заполнено";
        if(!TextUtils.isEmpty(EmailBDreg.getText().toString()) && !TextUtils.isEmpty(PasswordBDreg.getText().toString()) && checkBox.isChecked()) {
            mAuth.createUserWithEmailAndPassword(EmailBDreg.getText().toString(), PasswordBDreg.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
=======
        String birth = BirthBDreg.getText().toString();

        if ( !(isDateValid(birth) || birth.isEmpty())) {
            Toast.makeText(getApplicationContext(), "Укажите корректную дату рождения", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!TextUtils.isEmpty(EmailBDreg.getText().toString()) &&
           !TextUtils.isEmpty(PasswordBDreg.getText().toString()) &&
           checkBox.isChecked()) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
>>>>>>> Stashed changes
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("REG_ERROR", task.getException() != null ? task.getException().toString() : "null");
                    if (task.isSuccessful()) {
                        sendEmailVer();
                        FirebaseUser cUser = mAuth.getCurrentUser();
                        idtable = cUser.getUid();
<<<<<<< Updated upstream
                        saveBD(id, idtable, username, usersurname, email, birth);
                    } else
                        Toast.makeText(getApplicationContext(), "Регистрация не удалась, проверьте данные и попробуйте еще раз", Toast.LENGTH_SHORT).show();
=======
                        saveBD(id, idtable, user_name, user_surname, email, birth);
                    }
                    else Toast.makeText(getApplicationContext(), "Регистрация не удалась, проверьте данные и попробуйте еще раз", Toast.LENGTH_SHORT).show();
>>>>>>> Stashed changes
                }
            });

        }
        else Toast.makeText(this, "Заполните пустые поля", Toast.LENGTH_SHORT).show();

    }

    private void saveBD(String id, String idtable, String username, String usersurname, String email, String birth){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("User").child(idtable).exists())){
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("id", id);
                    userDataMap.put("idtable", idtable);
                    userDataMap.put("Name", username);
                    userDataMap.put("Surname", usersurname);
                    userDataMap.put("Email", email);
                    userDataMap.put("Birth", birth);

                    RootRef.child("User").child(idtable).updateChildren(userDataMap);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RegistrationActivity.this, "E-mail" + email +"зарегистрирован", Toast.LENGTH_SHORT).show();
            }
        });
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

