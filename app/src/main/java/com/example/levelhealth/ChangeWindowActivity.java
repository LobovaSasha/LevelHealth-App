package com.example.levelhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ChangeWindowActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;
    private String USER_KEY = "User";
    private EditText nameEdit, surnameEdit, birthEdit;
    private FirebaseUser cUser;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_window);
        init();

        back.setOnClickListener(v -> {
            onBackPressed();
        });

    }

    public void init(){
        back = findViewById(R.id.back);
        mAuth = FirebaseAuth.getInstance();
        cUser = mAuth.getCurrentUser();
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
        nameEdit = findViewById(R.id.name);
        surnameEdit = findViewById(R.id.surname);
        birthEdit = findViewById(R.id.birthday);
    }

    public void saveDataChange(View View){
        String idtable = cUser.getUid();
        mDataBase = FirebaseDatabase.getInstance().getReference("User");
        mDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> userDataMap = new HashMap<>();
                userDataMap.put("Birth", birthEdit.getText().toString());
                userDataMap.put("Name", nameEdit.getText().toString());
                userDataMap.put("Surname", surnameEdit.getText().toString());

                mDataBase.child(idtable).updateChildren(userDataMap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChangeWindowActivity.this, "Не удалось обновить данные.", Toast.LENGTH_SHORT).show();
            }
        });
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void GoToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



    protected void onStart() {
        super.onStart();
        FirebaseUser cUser = mAuth.getCurrentUser();
        if(cUser==null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }


}