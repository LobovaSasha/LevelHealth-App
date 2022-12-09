package com.example.levelhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GraphicsActivity extends AppCompatActivity {

    private final DatabaseReference dbCond =
            FirebaseDatabase.getInstance().getReference("Condition");
    CombinedChart sleep, mood;
    Button back;

    private FirebaseAuth mAuth;

    List<String> days = Arrays.asList("пн", "вт", "ср", "чт", "пт", "сб", "вс");

    SimpleDateFormat dayOfWeekFormatter = new SimpleDateFormat("E");
    SimpleDateFormat fullDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
    ArrayList<String> weekDates = new ArrayList<>();
    String today = "";

    public void init() {
        sleep = findViewById(R.id.sleep);
        mood = findViewById(R.id.mood);
        back = findViewById(R.id.back);

        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 7; i++) {
            weekDates.add(fullDateFormatter.format(calendar.getTime()));
            calendar.roll(Calendar.DATE, -1);
        }
        Collections.reverse(weekDates);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);
        mAuth = FirebaseAuth.getInstance();
        init();
        back.setOnClickListener(v -> {
            onBackPressed();
        });

        today = dayOfWeekFormatter.format(new Date());

        FirebaseUser cUser = mAuth.getCurrentUser();
        if (cUser == null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        assert cUser != null;
        String user_id = cUser.getUid();

//        dbCond.child(user_id).child("02-12-2022").setValue(new Condition(4, 5, 3));

        dbCond.child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Condition> table = new ArrayList<>();
                // проходим по бд, и смотрим текущую строку, есть ли ее дата в списке дат на неделю,
                // и, если да, одновременно получаем индекс
                for (DataSnapshot ds : snapshot.getChildren()) {
                    for (int i = 0; i < weekDates.size(); i++) {
                        if (Objects.equals(ds.getKey(), weekDates.get(i))) {
                            Condition el = ds.getValue(Condition.class);
                            el.idx = i;
                            table.add(el);
                            Log.d("EL", el.unwrap());
                            break;
                        }
                    }
                }

                Log.d("SIZE", table.size() + "");

                // создаем графики
                buildGraphics(table);
                buildGraphicHeadHeart(table);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("DB_ERROR", error.toString());
            }
        });

    }

    public void buildGraphicHeadHeart(ArrayList<Condition> table) {
        TextView[] daysView;
        ImageView[] hlvls;

        daysView = new TextView[] {
                findViewById(R.id.day1),
                findViewById(R.id.day2),
                findViewById(R.id.day3),
                findViewById(R.id.day4),
                findViewById(R.id.day5),
                findViewById(R.id.day6),
                findViewById(R.id.day7)
        };

        hlvls = new ImageView[] {
                findViewById(R.id.hlvl1),
                findViewById(R.id.hlvl2),
                findViewById(R.id.hlvl3),
                findViewById(R.id.hlvl4),
                findViewById(R.id.hlvl5),
                findViewById(R.id.hlvl6),
                findViewById(R.id.hlvl7)
        };

        HashMap<Integer, String> acheColor = new HashMap<>();
        acheColor.put(0, "#ffffff");
        acheColor.put(1, "#62d2cc");
        acheColor.put(2, "#4bbbb7");
        acheColor.put(3, "#389f9b");
        acheColor.put(4, "#29817f");
        acheColor.put(5, "#216563");

        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 7; i++) {
            daysView[6 - i].setText("" + calendar.get(Calendar.DATE));
            calendar.roll(Calendar.DATE, -1);
            GradientDrawable src = (GradientDrawable)hlvls[i].getDrawable();
            src.setColor(Color.parseColor(acheColor.get(0)));
            hlvls[i].invalidate();
        }

        for (Condition el : table) {
            GradientDrawable src = (GradientDrawable)hlvls[el.idx].getDrawable();
            src.setColor(Color.parseColor(acheColor.get(el.headache)));
            hlvls[el.idx].invalidate();
        }

    }

    public void buildGraphics(ArrayList<Condition> table) {
        CombinedData combinedDataSleep = new CombinedData();
        CombinedData combinedDataMood = new CombinedData();

        // заполняем датасет пустышек (для сохранения размера графика)
        ArrayList<BarEntry> empties = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            empties.add(new BarEntry(i, 1));
        }
        BarDataSet barDataSet = new BarDataSet(empties, "");
        barDataSet.setColor(Color.argb(0, 0, 0, 0));
        barDataSet.setDrawValues(false);
        BarData barData = new BarData(barDataSet);
        combinedDataSleep.setData(barData);
        combinedDataMood.setData(barData);

        // заполняем датасет данными из бд
        ArrayList<Entry> entriesSleep = new ArrayList<>();
        ArrayList<Entry> entriesMood = new ArrayList<>();
        for (int i=0; i<table.size(); i++) {
            entriesSleep.add(new Entry(table.get(i).idx, table.get(i).sleep));
            entriesMood.add(new Entry(table.get(i).idx, table.get(i).mood));
        }

        LineDataSet dataSetSleep = new LineDataSet(entriesSleep, "");
        LineDataSet dataSetMood = new LineDataSet(entriesMood, "");
        LineData lineDataSleep = new LineData(dataSetSleep);
        LineData lineDataMood = new LineData(dataSetMood);

        combinedDataSleep.setData(lineDataSleep);
        combinedDataMood.setData(lineDataMood);
        sleep.setData(combinedDataSleep);
        mood.setData(combinedDataMood);

        // дизайн графиков
        customizeGraphics(sleep);
        customizeDataSet(dataSetSleep);
        customizeGraphics(mood);
        customizeDataSet(dataSetMood);

        // обновление данных на экране
        sleep.notifyDataSetChanged();
        sleep.invalidate();
        mood.notifyDataSetChanged();
        mood.invalidate();
    }

    public void customizeGraphics(CombinedChart lineChart) {
        int idx = days.indexOf(today);
        String[] daysTxt = new String[7];
        for (int i = 0; i < 7; i++) {
            daysTxt[6 - i] = days.get((idx + 7 - i) % 7);
        }
        Log.d("IDX", idx + "");
        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(daysTxt)); // навесить дни вместо чисел на оси X
        lineChart.setTouchEnabled(false); // сделать график некликабельным
        lineChart.getAxisRight().setEnabled(false); // удалить правую ось Y
        lineChart.getLegend().setEnabled(false); // удалить легенду
        lineChart.getDescription().setEnabled(false); // удалить текст "description label"
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // опустить дни вниз графика
        lineChart.getXAxis().setTextColor(Color.rgb(150, 150, 150));

        // удаляет значения по Y слева (меняет цифры на пустые строки)
        lineChart.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return "";
            }
        });

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum((float) -1);
        yAxis.setAxisMaximum((float) 3.1);
        yAxis.setGranularity(1f);
    }

    public void customizeDataSet(LineDataSet dataSet) {
        dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSet.setDrawValues(false); // удалить подписи на точках
        dataSet.disableDashedLine();
        dataSet.setColor(Color.rgb(113, 194, 195));
        dataSet.setDrawCircles(false); // не рисовать точки
        dataSet.setLineWidth(3); // ширина линии
    }
}