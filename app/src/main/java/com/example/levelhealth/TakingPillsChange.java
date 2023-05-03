package com.example.levelhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class TakingPillsChange extends AppCompatActivity {
    private EditText editTextTime;
    private EditText editTextDose;
    double dose = 1.0;

    int startPosition = 1;

    String[] takingPillsSpinner;

    private BottomSheetBehavior sheetBehavior;
    private LinearLayout bottomСalendar;
    RelativeLayout calendarButton;
    TextView dateText;

    String date;
    Integer today_year = 0, today_month = 0, today_day = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taking_pills_change);

        takingPillsSpinner = getResources().getStringArray(R.array.taking_days_spinner);
        final Spinner spinner = findViewById(R.id.taking_days_button);

        MyCustomAdapter adapter = new MyCustomAdapter(this, R.layout.taking_pills_change_spinner, takingPillsSpinner);

        spinner.setAdapter(adapter);
        spinner.setPromptId(R.string.add_pill);
        spinner.setSelection(1, true);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        bottomСalendar = findViewById(R.id.bottom_calendar);
        sheetBehavior = BottomSheetBehavior.from(bottomСalendar);
        calendarButton = findViewById(R.id.taking_start1);

        dateText = findViewById(R.id.calendar_text);
        CalendarView calendarView = findViewById(R.id.calendarView);

        editTextTime = findViewById(R.id.time1);
        editTextTime.setText("8:00");

        editTextDose = findViewById(R.id.dose1);
        editTextDose.setText(Double.toString(dose));

        editTextDose.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                String text = editTextDose.getText().toString();
                if (text != "") {
                    dose =  Double.parseDouble(text);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    //btn_bottom_calendar.setText("Close sheet");
                }
            }
        });

        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        dateText.setText(date);
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
            }
        });

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            if (month > 9) {
                if (dayOfMonth < 10) {
                    date = "0" + dayOfMonth + "." + (month + 1) + "." + year;
                } else {
                    date = dayOfMonth + "." + (month + 1) + "." + year;
                }
            } else {
                if (dayOfMonth < 10) {
                    date = "0" + dayOfMonth + "." + "0" + (month + 1) + "." + year;
                } else {
                    date = dayOfMonth + "." + "0" + (month + 1) + "." + year;
                }
            }
            if (today_year > year) {
            } else if (today_year == year) {
                if (today_month > month + 1) {
                } else if (today_month == month + 1) {
                    if (today_day >= dayOfMonth) {
                    } else {
                    }
                } else {
                }
            } else {
            }
        });
    }

    public void PlusAction(View view) {
        dose += 0.5;
        editTextDose.setText(Double.toString(dose));
    }
    public void MinusAction(View view) {
        dose -= 0.5;
        editTextDose.setText(Double.toString(dose));
    }

    public void GoToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void GoToTakingPillsActivity(View view) {
        Intent intent = new Intent(this, TakingPills.class);
        startActivity(intent);
    }

    public class MyCustomAdapter extends ArrayAdapter<String> {

        public MyCustomAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
        }
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row1 = inflater.inflate(R.layout.taking_pills_change_spinner_dropdown, parent, false);
            TextView label = (TextView) row1.findViewById(R.id.spinnerdropdown);
            label.setText(takingPillsSpinner[position]);
            return row1;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (startPosition == 1)
                position = 0;
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.taking_pills_change_spinner, parent, false);
            TextView label = (TextView) row.findViewById(R.id.spinner);
            label.setText(takingPillsSpinner[position]);
            startPosition = 0;
            return row;
        }
    }
}
