package com.example.levelhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class GraphicsActivity extends AppCompatActivity {

    LineChart sleep, mood;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);
        init();
        back.setOnClickListener(v -> {
            onBackPressed();
        });

        buildGraphicSleep();
        buildGraphicMood();
        buildGraphicHeadHeart();

    }

    public void init() {
        sleep = findViewById(R.id.sleep);
        mood = findViewById(R.id.mood);
        back = findViewById(R.id.back);
    }

    public void buildGraphicHeadHeart() {
        TextView[] days;
        TextView[] hlvls;

        days = new TextView[] {
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


        List<String> dayTexts = Arrays.asList("пн", "вт", "ср", "чт", "пт", "сб", "вс");
        String curDate = new SimpleDateFormat("E").format(new Date());
        int idx = dayTexts.indexOf(curDate);
        for (int i=0; i<7; i++) {
            days[6-i].setText(dayTexts.get((idx+7-i)%7));
            hlvls[6-i].setText(""+(int)(Math.random()*10));
        }
        days[6].setText(curDate);

    }

    public void buildGraphicSleep() {
        ArrayList<Entry> entries = new ArrayList<>();

        for (int i=0; i<7; i++) {
            entries.add(new Entry(i, (int)(Math.random()*5)+1));
        }

        LineDataSet dataSet = new LineDataSet(entries, "");

        LineData lineData = new LineData(dataSet);
        sleep.setData(lineData);

        customizeGraphicsSleepMood(dataSet, sleep);
    }

    public void buildGraphicMood() {
        ArrayList<Entry> entries = new ArrayList<>();

        for (int i=0; i<7; i++) {
            entries.add(new Entry(i, (int)(Math.random()*4)+2));
        }
        LineDataSet dataSet = new LineDataSet(entries, "");
        LineData lineData = new LineData(dataSet);
        mood.setData(lineData);
        customizeGraphicsSleepMood(dataSet, mood);

        String[] moods = {">:(", ":( ", ":|", ":З", "=)"};
        mood.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return value>=1 && value<=5 ? ""+moods[(int)value-1] : "";
            }
        });
    }

    public void customizeGraphicsSleepMood(LineDataSet dataSet, LineChart lineChart) {
        String[] days = {"пн.", "вт.", "ср.", "чт.", "пт.", "сб.", "вс."};
        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(days)); // навесить дни вместо чисел на оси X

        dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSet.setDrawValues(false); // удалить подписи на точках
        dataSet.disableDashedLine();
        dataSet.setColor(Color.rgb(113, 194, 195));
        dataSet.setDrawCircles(false); // не рисовать точки
        dataSet.setLineWidth(3); // ширина линии

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
}