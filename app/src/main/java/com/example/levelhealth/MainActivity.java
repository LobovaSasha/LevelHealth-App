package com.example.levelhealth;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private RecyclerView daysRecycler;
    private DayAdapter dayAdapter;
    private Button group_120, group_121, group_122, group_123;

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
        init();
    }

    public void init(){
        /*NameBDreg = findViewById(R.id.NameBDreg);
        SurnameBDreg = findViewById(R.id.SurnameBDreg);
        BirthBDreg = findViewById(R.id.BirthBDreg);
        EmailBDreg = findViewById(R.id.EmailBDreg);
        PasswordBDreg = findViewById(R.id.PasswordBDreg);
        checkBox = findViewById(R.id.checkBox);
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
        mAuth = FirebaseAuth.getInstance();*/
    }

    public void SaveDB(View view) {
        /*String id = mDataBase.getKey();
        String user_name = NameBDreg.getText().toString();
        String user_surname = SurnameBDreg.getText().toString();
        String email = EmailBDreg.getText().toString();
        String password = PasswordBDreg.getText().toString();
        String birth = BirthBDreg.getText().toString();
        if(!TextUtils.isEmpty(EmailBDreg.getText().toString()) && !TextUtils.isEmpty(PasswordBDreg.getText().toString()) && checkBox.isChecked()) {
            mAuth.createUserWithEmailAndPassword(EmailBDreg.getText().toString(), PasswordBDreg.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        sendEmailVer();
                        FirebaseUser cUser = mAuth.getCurrentUser();
                        idtable = cUser.getUid();
                        saveBD(id, idtable, user_name, user_surname, email, birth);
                    } else
                        Toast.makeText(getApplicationContext(), "Регистрация не удалась, проверьте данные и попробуйте еще раз", Toast.LENGTH_SHORT).show();
                }
            });
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
        }
        else Toast.makeText(this, "Заполните пустые поля", Toast.LENGTH_SHORT).show();*/
    }

    private void saveBD(String id, String idtable, String username, String usersurname, String email, String birth){
        /*final DatabaseReference RootRef;
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
                Toast.makeText(MainActivity.this, "E-mail" + email +"зарегистрирован", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    public void onFirstSmile(View view) {
        /*group_120 = findViewById(R.id.group_120);
        group_121 = findViewById(R.id.group_121);
        group_122 = findViewById(R.id.group_122);
        group_123 = findViewById(R.id.group_123);

        group_120.setBackground(Drawable.createFromPath("@drawable/group_122"));*/
    }

    public void onSecondSmile(View view) {

    }

    public void onThirdSmile(View view) {

    }

    public void onForthSmile(View view) {

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