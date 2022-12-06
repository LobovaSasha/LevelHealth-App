package com.example.levelhealth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private RecyclerView daysRecycler;
    private DayAdapter dayAdapter;
    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13;
    private ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9, iv10, iv11, iv12, iv13;
    private Integer smile_res = -1, sleep_res = -1, headache_res = -1;

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
        //

        iv1 = (ImageView) findViewById(R.id.image_view_smile1);
        iv2 = (ImageView) findViewById(R.id.image_view_smile2);
        iv3 = (ImageView) findViewById(R.id.image_view_smile3);
        iv4 = (ImageView) findViewById(R.id.image_view_smile4);

        iv5 = (ImageView) findViewById(R.id.image_view_sleep1);
        iv6 = (ImageView) findViewById(R.id.image_view_sleep2);
        iv7 = (ImageView) findViewById(R.id.image_view_sleep3);
        iv8 = (ImageView) findViewById(R.id.image_view_sleep4);

        iv9 = (ImageView) findViewById(R.id.image_view_headache1);
        iv10 = (ImageView) findViewById(R.id.image_view_headache2);
        iv11 = (ImageView) findViewById(R.id.image_view_headache3);
        iv12 = (ImageView) findViewById(R.id.image_view_headache4);
        iv13 = (ImageView) findViewById(R.id.image_view_headache5);

        b1 = (Button) findViewById(R.id.button_smile1);
        b2 = (Button) findViewById(R.id.button_smile2);
        b3 = (Button) findViewById(R.id.button_smile3);
        b4 = (Button) findViewById(R.id.button_smile4);

        b5 = (Button) findViewById(R.id.button_sleep1);
        b6 = (Button) findViewById(R.id.button_sleep2);
        b7 = (Button) findViewById(R.id.button_sleep3);
        b8 = (Button) findViewById(R.id.button_sleep4);

        b9 = (Button) findViewById(R.id.button_headache1);
        b10 = (Button) findViewById(R.id.button_headache2);
        b11 = (Button) findViewById(R.id.button_headache3);
        b12 = (Button) findViewById(R.id.button_headache4);
        b13 = (Button) findViewById(R.id.button_headache5);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv1.setImageResource(R.drawable.smile1_0);
                iv2.setImageResource(R.drawable.smile0_1);
                iv3.setImageResource(R.drawable.smile0_2);
                iv4.setImageResource(R.drawable.smile0_3);
                smile_res = 0;
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv1.setImageResource(R.drawable.smile0_0);
                iv2.setImageResource(R.drawable.smile1_1);
                iv3.setImageResource(R.drawable.smile0_2);
                iv4.setImageResource(R.drawable.smile0_3);
                smile_res = 1;
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv1.setImageResource(R.drawable.smile0_0);
                iv2.setImageResource(R.drawable.smile0_1);
                iv3.setImageResource(R.drawable.smile1_2);
                iv4.setImageResource(R.drawable.smile0_3);
                smile_res = 2;
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv1.setImageResource(R.drawable.smile0_0);
                iv2.setImageResource(R.drawable.smile0_1);
                iv3.setImageResource(R.drawable.smile0_2);
                iv4.setImageResource(R.drawable.smile1_3);
                smile_res = 3;
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv5.setImageResource(R.drawable.sleep1_0);
                iv6.setImageResource(R.drawable.sleep0_1);
                iv7.setImageResource(R.drawable.sleep0_2);
                iv8.setImageResource(R.drawable.sleep0_3);
                sleep_res = 0;
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv5.setImageResource(R.drawable.sleep0_0);
                iv6.setImageResource(R.drawable.sleep1_1);
                iv7.setImageResource(R.drawable.sleep0_2);
                iv8.setImageResource(R.drawable.sleep0_3);
                sleep_res = 1;
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv5.setImageResource(R.drawable.sleep0_0);
                iv6.setImageResource(R.drawable.sleep0_1);
                iv7.setImageResource(R.drawable.sleep1_2);
                iv8.setImageResource(R.drawable.sleep0_3);
                sleep_res = 2;
            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv5.setImageResource(R.drawable.sleep0_0);
                iv6.setImageResource(R.drawable.sleep0_1);
                iv7.setImageResource(R.drawable.sleep0_2);
                iv8.setImageResource(R.drawable.sleep1_3);
                sleep_res = 3;
            }
        });

        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv9.setImageResource(R.drawable.headache1_1);
                iv10.setImageResource(R.drawable.headache0_2);
                iv11.setImageResource(R.drawable.headache0_3);
                iv12.setImageResource(R.drawable.headache0_4);
                iv13.setImageResource(R.drawable.headache0_5);
                headache_res = 1;
            }
        });

        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv9.setImageResource(R.drawable.headache0_1);
                iv10.setImageResource(R.drawable.headache1_2);
                iv11.setImageResource(R.drawable.headache0_3);
                iv12.setImageResource(R.drawable.headache0_4);
                iv13.setImageResource(R.drawable.headache0_5);
                headache_res = 2;
            }
        });

        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv9.setImageResource(R.drawable.headache0_1);
                iv10.setImageResource(R.drawable.headache0_2);
                iv11.setImageResource(R.drawable.headache1_3);
                iv12.setImageResource(R.drawable.headache0_4);
                iv13.setImageResource(R.drawable.headache0_5);
                headache_res = 3;
            }
        });

        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv9.setImageResource(R.drawable.headache0_1);
                iv10.setImageResource(R.drawable.headache0_2);
                iv11.setImageResource(R.drawable.headache0_3);
                iv12.setImageResource(R.drawable.headache1_4);
                iv13.setImageResource(R.drawable.headache0_5);
                headache_res = 4;
            }
        });

        b13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv9.setImageResource(R.drawable.headache0_1);
                iv10.setImageResource(R.drawable.headache0_2);
                iv11.setImageResource(R.drawable.headache0_3);
                iv12.setImageResource(R.drawable.headache0_4);
                iv13.setImageResource(R.drawable.headache1_5);
                headache_res = 5;
            }
        });

    }

    // вернула метод и классы day и dayAdapter
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