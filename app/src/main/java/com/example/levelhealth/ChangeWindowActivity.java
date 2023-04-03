package com.example.levelhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;

import com.squareup.picasso.Picasso;


public class ChangeWindowActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;
    private String USER_KEY = "User";
    private EditText nameEdit, surnameEdit, birthEdit;
    private FirebaseUser cUser;

    Button back;

    private Uri filePath;
    private ImageView profilePic;

    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_window);
        init();

        Button bttChooseAvatar = findViewById(R.id.button_choose_avatar);
        Button bttUploadAvatar = findViewById(R.id.button_upload_avatar);

        bttChooseAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        bttUploadAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        String id = cUser.getUid();

        final DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference("User").child(id);
        ref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String fio = snapshot.child("Name").getValue().toString();
                    String fio2 = snapshot.child("Surname").getValue().toString();

                    nameEdit.setText(fio);
                    surnameEdit.setText(fio2);
                    String birth = snapshot.child("Birth").getValue().toString();
                    birthEdit.setText(birth);

                    String link = snapshot.child("image").getValue().toString();
                    Picasso.get().load(link).into(profilePic);
                } catch (Exception ignored) {}

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });




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

        profilePic = findViewById(R.id.profilePic);



        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    public void saveDataChange(View View) {
        String idtable = cUser.getUid();
        mDataBase = FirebaseDatabase.getInstance().getReference("User");

        if (!TextUtils.isEmpty(birthEdit.getText().toString()) && !TextUtils.isEmpty(nameEdit.getText().toString()) && !TextUtils.isEmpty(surnameEdit.getText().toString())) {
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
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }
        else Toast.makeText(this, "Заполните пустые поля", Toast.LENGTH_SHORT).show();
    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageMdata = findViewById(R.id.profilePic);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageMdata.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        final StorageReference profileImageRef = FirebaseStorage.getInstance().getReference("profilepics/" + System.currentTimeMillis() + ".jpg");
        if(filePath != null)
        {
            profileImageRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            profileImageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String profileImageUrl=task.getResult().toString();
                                    FirebaseUser cUser = mAuth.getCurrentUser();
                                    String idtable = cUser.getUid();
                                    final DatabaseReference refdb;
                                    refdb = FirebaseDatabase.getInstance().getReference("User").child(idtable);
                                    refdb.child("image").setValue(profileImageUrl);
                                }
                            });
                        }
                    });
        }
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