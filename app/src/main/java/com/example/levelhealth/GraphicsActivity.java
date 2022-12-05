package com.example.levelhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class GraphicsActivity extends AppCompatActivity {

    private final DatabaseReference dbCond =
            FirebaseDatabase.getInstance().getReference("Condition");
    CombinedChart sleep, mood;
    Button back;

    List<String> days = Arrays.asList("пн", "вт", "ср", "чт", "пт", "сб", "вс");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);
        init();
        back.setOnClickListener(v -> {
            onBackPressed();
        });

        Intent intent = getIntent();
        int user_id = intent.getIntExtra("user_id", -1);

        dbCond.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Condition> table = new ArrayList<>();
                int ct = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ct += 1;
                    if (ct > 7) break;
                    Condition el = ds.getValue(Condition.class);
                    if (el.user_id == user_id)
                        table.add(el);
                }

                // сортировка
                for (int i=0; i<table.size(); i++) {
                    for (int j=i; j<table.size(); j++) {
                        if (days.indexOf(table.get(i).day) > days.indexOf(table.get(j).day)) {
                            Condition temp = table.get(i);
                            table.set(i, table.get(j));
                            table.set(j, temp);
                        }
                    }
                }

                // удаление повторов по дате (возможно не понадобится)
                ArrayList<Condition> t2 = new ArrayList<>();
                t2.add(table.get(0));
                int idx = 0;
                for (Condition el : table) {
                    if (!el.day.equals(t2.get(idx).day)) {
                        t2.add(el);
                        idx += 1;
                    }
                }

                for (Condition el : t2) {
                    Log.d("T2 COND", el.unwrap());
                }

                // создаем график сна
                buildGraphics(table);
                buildGraphicHeadHeart(table);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("DB_ERROR", error.toString());
            }
        });

    }

    public void init() {
        sleep = findViewById(R.id.sleep);
        mood = findViewById(R.id.mood);
        back = findViewById(R.id.back);
    }

    public void buildGraphicHeadHeart(ArrayList<Condition> table) {
        TextView[] daysView;
        TextView[] hlvls;

        daysView = new TextView[] {
                findViewById(R.id.day1),
                findViewById(R.id.day2),
                findViewById(R.id.day3),
                findViewById(R.id.day4),
                findViewById(R.id.day5),
                findViewById(R.id.day6),
                findViewById(R.id.day7)
        };

        hlvls = new TextView[] {
                findViewById(R.id.hlvl1),
                findViewById(R.id.hlvl2),
                findViewById(R.id.hlvl3),
                findViewById(R.id.hlvl4),
                findViewById(R.id.hlvl5),
                findViewById(R.id.hlvl6),
                findViewById(R.id.hlvl7)
        };



        String curDate = new SimpleDateFormat("E").format(new Date());
        int idx = days.indexOf(curDate);
        for (int i=0; i<7; i++) {
            daysView[6-i].setText(days.get((idx+7-i)%7));
            hlvls[i].setText("-");
        }
        for (int i=0; i<table.size(); i++) {
            hlvls[days.indexOf(table.get(i).day)].setText(""+table.get(i).headache);
        }
        daysView[6].setText(curDate);

    }

    public void buildGraphics(ArrayList<Condition> table) {
        CombinedData combinedDataSleep = new CombinedData();
        CombinedData combinedDataMood = new CombinedData();

        // заполняем датасет пустышек (для сохранения размера графика)
        ArrayList<BarEntry> empties = new ArrayList<>();
        for (int i=0; i<7; i++) {
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
            entriesSleep.add(new Entry(days.indexOf(table.get(i).day), table.get(i).sleep));
            entriesMood.add(new Entry(days.indexOf(table.get(i).day), table.get(i).mood));
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
        String[] days = {"пн.", "вт.", "ср.", "чт.", "пт.", "сб.", "вс."};
        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(days)); // навесить дни вместо чисел на оси X
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
        yAxis.setAxisMinimum(1);
        yAxis.setAxisMaximum(5);
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