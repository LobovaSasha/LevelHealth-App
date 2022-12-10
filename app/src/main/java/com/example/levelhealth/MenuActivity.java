package com.example.levelhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_menu);
        FirebaseUser cUser = mAuth.getCurrentUser();
        String id = cUser.getUid();
        TextView FioText = findViewById(R.id.NameSurname);
        TextView BirthText = findViewById(R.id.DateOfBirth);
        TextView EmailText = findViewById(R.id.Mail);
        String email = cUser.getEmail();
        EmailText.setText(email);

        final DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference("User").child(id);
        ref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String fio = snapshot.child("Name").getValue().toString();
                String fio2 = snapshot.child("Surname").getValue().toString();

                FioText.setText(fio + " " + fio2);
                String birth = snapshot.child("Birth").getValue().toString();
                BirthText.setText(birth);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
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

    public void GoToGraphicsActivity(View view) {
        Intent intent = new Intent(this, GraphicsActivity.class);
        startActivity(intent);
    }

    public void GoToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}