package com.example.levelhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class NoteActivity extends AppCompatActivity {

    Button backBtn;
    private String comment, header;
    private TextView dateTimeDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private DatabaseReference RootRef;
    private FirebaseAuth mAuth;
    private String idtable;


    private void init() {
        backBtn = findViewById(R.id.back);
        dateTimeDisplay = findViewById(R.id.dateText);
        mAuth = FirebaseAuth.getInstance();
        String date = getDate();
        FirebaseUser cUser = mAuth.getCurrentUser();
        assert cUser != null;
        String idtable = cUser.getUid();
        EditText headerText = findViewById(R.id.titleText);
        EditText commentText = findViewById(R.id.noteText);
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String comment = Objects.requireNonNull(snapshot.child("Condition").child(idtable).child(date).child("comment").getValue()).toString();
                    String header = Objects.requireNonNull(snapshot.child("Condition").child(idtable).child(date).child("header").getValue()).toString();
                    commentText.setText(comment);
                    headerText.setText(header);
                } catch (Exception ignored) {}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(NoteActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        init();

        backBtn.setOnClickListener(v -> {
            onBackPressed();
        });

        calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            calendar.setTime(Objects.requireNonNull(sdf.parse(getDate())));// all done
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        dateFormat = new SimpleDateFormat("dd MMMM", new Locale("ru") );
        String date = dateFormat.format(calendar.getTime());
        dateTimeDisplay.setText(date);

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();

    }

    public String getDate() {
        try {
            Bundle arguments = getIntent().getExtras();
            return arguments.get("date").toString();
        } catch (Exception e) {
            Calendar calendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            return formatter.format(calendar.getTime());
        }
    }

    public void saveComment(View view) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser cUser = mAuth.getCurrentUser();
        assert cUser != null;
        idtable = cUser.getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();
        EditText headerText = findViewById(R.id.titleText);
        EditText commentText = findViewById(R.id.noteText);
        header = headerText.getText().toString();
        comment = commentText.getText().toString();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> userDataMap = new HashMap<>();
                userDataMap.put("header", header);
                userDataMap.put("comment", comment);
                RootRef.child("Condition").child(idtable).child(getDate()).updateChildren(userDataMap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(NoteActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        });
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("date", getDate());
        startActivity(intent);
    }
}
