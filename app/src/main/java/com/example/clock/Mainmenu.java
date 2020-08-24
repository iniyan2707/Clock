package com.example.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Mainmenu extends AppCompatActivity {

    private Button btnAlarm,btnTimer,btnStopWatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        btnAlarm=(Button) findViewById(R.id.Alarm_clock);
        btnTimer=(Button) findViewById(R.id.Timer);
        btnStopWatch=(Button) findViewById(R.id.StopWatch);

        btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Mainmenu.this,MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        btnTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Mainmenu.this,Timer.class);
                finish();
                startActivity(intent);
            }
        });
        btnStopWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Mainmenu.this,StopWatch.class);
                finish();
                startActivity(intent);
            }
        });
    }
}