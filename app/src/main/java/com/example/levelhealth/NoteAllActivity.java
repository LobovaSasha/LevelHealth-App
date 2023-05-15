package com.example.levelhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NoteAllActivity extends AppCompatActivity {
    Button back, home;
    private DatabaseReference RootRef;
    private String idtable, iddate, p, iddate2, p2, iddate3, p3;
    TextView header, header2, header3;
    ListView listView, listView2, listView3;
    ArrayAdapter<String> adapter, adapter2, adapter3;
    List<String> listData, listData2, listData3;

    public void init() {
        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        header = findViewById(R.id.name_note_all);
        header2 = findViewById(R.id.name_note_all_april);
        header3 = findViewById(R.id.name_note_all_march);
        //text = findViewById(R.id.text_note_all);
        listView = findViewById(R.id.list_may);
        listView2 = findViewById(R.id.list_april);
        listView3 = findViewById(R.id.list_march);
        listData = new ArrayList<>();
        listData2 = new ArrayList<>();
        listData3 = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData2);
        adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData3);
        listView.setAdapter(adapter);
        listView2.setAdapter(adapter2);
        listView3.setAdapter(adapter3);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser cUser = mAuth.getCurrentUser();
        assert cUser != null;
        idtable = cUser.getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_all);
        init();
        may();
        april();
        march();
}

    public void may() {
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (listData.size() > 0) listData.clear();
                for (int i = 1; i <= 31; i++) {
                    if (i < 10) p = "0" + Integer.toString(i);
                    else p = Integer.toString(i);
                    iddate = p + "-05-2023";
                    if (snapshot.child("Condition").child(idtable).child(iddate).child("header").exists()) {
                        listData.add(snapshot.child("Condition").child(idtable).child(iddate).child("header").getValue().toString() + "\n" + snapshot.child("Condition").child(idtable).child(iddate).child("comment").getValue().toString());
                        //header.setText(snapshot.child("Condition").child(idtable).child(iddate).child("header").getValue().toString());
                        //text.setText(snapshot.child("Condition").child(idtable).child(iddate).child("comment").getValue().toString());
                    }
                }
                if (listData.isEmpty()) {
                    header.setVisibility(View.VISIBLE);
                    header.setText("Заметок нет");
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(NoteAllActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void april() {
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (listData2.size() > 0) listData2.clear();
                for (int i = 1; i <= 30; i++) {
                    if (i < 10) p = "0" + Integer.toString(i);
                    else p = Integer.toString(i);
                    iddate = p + "-04-2023";
                    if (snapshot.child("Condition").child(idtable).child(iddate).child("header").exists()) {
                        listData2.add(snapshot.child("Condition").child(idtable).child(iddate).child("header").getValue().toString() + "\n" + snapshot.child("Condition").child(idtable).child(iddate).child("comment").getValue().toString());
                        //header.setText(snapshot.child("Condition").child(idtable).child(iddate).child("header").getValue().toString());
                        //text.setText(snapshot.child("Condition").child(idtable).child(iddate).child("comment").getValue().toString());
                    }
                }
                if (listData2.isEmpty()) {
                    header2.setVisibility(View.VISIBLE);
                    header2.setText("Заметок нет");
                }
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(NoteAllActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void march() {
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (listData3.size() > 0) listData3.clear();
                for (int i = 1; i <= 31; i++) {
                    if (i < 10) p = "0" + Integer.toString(i);
                    else p = Integer.toString(i);
                    iddate = p + "-03-2023";
                    if (snapshot.child("Condition").child(idtable).child(iddate).child("header").exists()) {
                        listData3.add(snapshot.child("Condition").child(idtable).child(iddate).child("header").getValue().toString() + "\n" + snapshot.child("Condition").child(idtable).child(iddate).child("comment").getValue().toString());
                        //header.setText(snapshot.child("Condition").child(idtable).child(iddate).child("header").getValue().toString());
                        //text.setText(snapshot.child("Condition").child(idtable).child(iddate).child("comment").getValue().toString());
                    }
                }
                if (listData3.isEmpty()) {
                    header3.setVisibility(View.VISIBLE);
                    header3.setText("Заметок нет");
                }
                adapter3.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(NoteAllActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void GoToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}