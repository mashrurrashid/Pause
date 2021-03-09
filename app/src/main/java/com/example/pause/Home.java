package com.example.pause;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.CalendarView;

public class Home extends AppCompatActivity {

    CalendarView calendar;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        calendar = (CalendarView) findViewById(R.id.calender);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                message = new String(dayOfMonth + "" + (month+1) + "" + year);
                Intent intent = new Intent(getApplicationContext(), Notes.class);
                intent.putExtra("message",message);
                startActivity(intent);
            }

        });

    }

}
