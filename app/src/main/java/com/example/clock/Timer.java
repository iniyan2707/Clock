package com.example.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Timer extends AppCompatActivity {

    private EditText editText;
    private Button btnSet;
    private TextView textCountDown;
    private Button btnStartPause;
    private Button btnReset;
    private Button btnmainmenu;

    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long startTimeInMillis;
    private long timeLeftInMillis= startTimeInMillis;
    private long endTime;
    public Ringtone r;
    public boolean ringtoneplaying;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        setTitle("Timer");

        editText=(EditText) findViewById(R.id.edit_text_input);
        textCountDown=(TextView) findViewById(R.id.text_countdown);
        btnSet=(Button) findViewById(R.id.button_set);
        btnStartPause =(Button) findViewById(R.id.btn_start_pause);
        btnReset=(Button) findViewById(R.id.btn_reset);
        btnmainmenu=(Button) findViewById(R.id.mainmenu1);
        btnmainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Timer.this,Mainmenu.class);
                finish();
                startActivity(intent);
            }
        });

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input=editText.getText().toString();
                if(input.length() ==0)
                {
                    Toast.makeText(Timer.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                long millisinput=Long.parseLong(input)*60000;
                if(millisinput==0)
                    {
                        Toast.makeText(Timer.this, "Enter a positive number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                setTime(millisinput);
                editText.setText("");

            }
        });


        btnStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timerRunning)
                {
                    pauseTimer();
                }
                else
                {
                    startTimer();
                }

            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();

            }
        });
        updateCountDownText();

    }
    private void setTime(long milliseconds)
    {
        startTimeInMillis=milliseconds;
        resetTimer();
        closeKeyboard();
    }

    private void startTimer()
    {
        endTime=System.currentTimeMillis()+timeLeftInMillis;
        countDownTimer=new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillis=l;
                updateCountDownText();

            }

            @Override
            public void onFinish() {
                timerRunning=false;
                 updateInterface();
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                r = RingtoneManager.getRingtone(Timer.this, notification);

                r.play();
                 ringtoneplaying = true;


            }
        }.start();
        timerRunning=true;
        updateInterface();
    }

    private void pauseTimer()
    {
        countDownTimer.cancel();
        timerRunning=false;
        updateInterface();

    }
    private void resetTimer()
    {
        timeLeftInMillis= startTimeInMillis;
        updateCountDownText();
       updateInterface();
       if(ringtoneplaying){
           r.stop();
       }
    }
    private void updateCountDownText()
    {
        int hours=(int) (timeLeftInMillis/1000)/3600;
        int minutes= (int)((timeLeftInMillis/1000)%3600)/60;
        int seconds=(int)(timeLeftInMillis/1000)%60;
        String timeLeftFormatted;
        if(hours>0)
        {
            timeLeftFormatted=String.format(Locale.getDefault(),
                    "%d:%02d:%02d",hours,minutes,seconds);
        }
        else{
            timeLeftFormatted=String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        }
        textCountDown.setText(timeLeftFormatted);
    }

    private void updateInterface()
    {

        if(timerRunning)
        {
            editText.setVisibility(View.INVISIBLE);
            btnSet.setVisibility(View.INVISIBLE);
            btnReset.setVisibility(View.INVISIBLE);
            btnStartPause.setText("pause");
        }
        else
        {
            editText.setVisibility(View.VISIBLE);
            btnSet.setVisibility(View.VISIBLE);
            btnStartPause.setText("start");
            if(timeLeftInMillis<1000) //when timer is finished
            {
                btnStartPause.setVisibility(View.INVISIBLE);
            }
            else  //timer is paused or reset
            {
                btnStartPause.setVisibility(View.VISIBLE);
            }
            if(timeLeftInMillis< startTimeInMillis)
            {
                btnReset.setVisibility(View.VISIBLE);
            }
            else
            {
                btnReset.setVisibility(View.INVISIBLE);
            }

        }
    }
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences=getSharedPreferences("prefs",MODE_PRIVATE);
        startTimeInMillis=sharedPreferences.getLong("startTimeInMillis",600000);
        timeLeftInMillis=sharedPreferences.getLong("millisLeft", startTimeInMillis);
        timerRunning= sharedPreferences.getBoolean("timerRunning",false);
        updateCountDownText();
        updateInterface();
        if(timerRunning)
        {
            endTime=sharedPreferences.getLong("endTime",0);
            timeLeftInMillis=endTime-System.currentTimeMillis(); //makes sure that timeleftinmillis is exact.
            if(timeLeftInMillis<0)
            {
                timeLeftInMillis=0;
                timerRunning=false;
                updateCountDownText();
                updateInterface();
            }
            else{
                startTimer();
            }
        }




    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences= getSharedPreferences("prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putLong("startTimeInMillis",startTimeInMillis);
        editor.putLong("millisLeft",timeLeftInMillis);
        editor.putBoolean("timerRunning",timerRunning);
        editor.putLong("endTime",endTime);
        editor.apply();
        if(countDownTimer!=null)
        {
            countDownTimer.cancel();
        }
    }
}