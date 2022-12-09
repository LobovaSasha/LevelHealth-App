package com.example.levelhealth;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class DBHandler<T> {

    private final DatabaseReference db;
    private final Class<T> type;
    private ValueEventListener vel = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    /*
    указать название таблицы и тип данных
    пример:
    DBHandler<User> dbh = new DBHandler<>("User", User.class);
     */
    public DBHandler(String tableName, Class<T> type) {
        this.type = type;
        db = FirebaseDatabase.getInstance().getReference(tableName);
        db.addValueEventListener(vel);
    }

    /*
    DBHandler<User> dbh = new DBHandler<>("User", User.class);
    dbh.onRead(table -> {
      // TO DO
    })
    */
    public void onRead(Consumer<ArrayList<T>> f) {
        db.removeEventListener(vel);
        Log.d("READ", "onRead()");

        vel = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("READ", "onDataChange()");
                ArrayList<T> table = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    table.add(
                            ds.getValue(type)
                    );
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    f.accept(table);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };
        db.addListenerForSingleValueEvent(vel);
    }

    /*
    f - функция, получающая объект и возвращающая Bool, upd - новый объект (на что заменить)
    DBHandler<Condition> dbh = new DBHandler<>("Condition", Condition.class);
    HashMap<String, Object> newCond = new HashMap<>();
    newCond.put("mood", 10);
    dbh.update(el -> el.mood<10, newCond);
    */
    public void update(Function<T, Boolean> f, HashMap<String, Object> upd) {
        db.removeEventListener(vel);

        vel = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    T item = ds.getValue(type);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        if (f.apply(item)) {
                            db.child(ds.getKey()).updateChildren(upd);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };
        db.addListenerForSingleValueEvent(vel);
    }

    /*
    DBHandler<Condition> dbh = new DBHandler<>("Condition", Condition.class);
    dbh.add(new Condition(
           2, "30.11.2022",
           15, 7, 0, 1
    ));
    */
    public void add(T element) {
        db.push().setValue(element);
    }


    /*
    DBHandler<Condition> dbh = new DBHandler<>("Condition", Condition.class);
    dbh.delete(el -> el.id==1 || el.id==5); --- удалит записи с id=1 и id=5
    */
    public void delete(Function<T, Boolean> f) {
        db.removeEventListener(vel);

        vel = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    T item = ds.getValue(type);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        if (f.apply(item)) {
                            final String key = ds.getKey();
                            db.child(key).removeValue();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };
        db.addListenerForSingleValueEvent(vel);
    }

}
