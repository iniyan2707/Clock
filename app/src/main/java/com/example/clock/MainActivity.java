package com.example.clock;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

import static com.example.clock.AlertReceiver.mediaPlayer;
import static com.example.clock.AlertReceiver.mediaplaying;
import static com.example.clock.AlertReceiver.received;


public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private TextView textView;
    private EditText editText;
    private Button buttonSetAlarm;
    private Button buttonCancel;
    private Button btnmainmenu;
    private RadioGroup rbgroup;
    private RadioButton rb1;
    private RadioButton rb3;
    private RadioButton rb2;
    public static int ans;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Alarm Clock");

        rbgroup=findViewById(R.id.radioGroup);
        rb1=findViewById(R.id.radioButton1);
        rb2=findViewById(R.id.radioButton2);
        rb3=findViewById(R.id.radioButton3);
        editText=findViewById(R.id.edittext);

        textView = findViewById(R.id.textView);
        buttonSetAlarm = findViewById(R.id.set_alarm);
        buttonCancel = findViewById(R.id.cancel_alarm);
        btnmainmenu=(Button)findViewById(R.id.mainmenu);

        buttonSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timepicker = new TimePickerFragment();
                timepicker.show(getSupportFragmentManager(), "time picker");

            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canelAlarm();

            }
        });
        btnmainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Mainmenu.class);
                finish();
                startActivity(intent);
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hour);
        c.set(Calendar.MINUTE,minute);
        c.set(Calendar.SECOND,0);
        updateTimeText(c);
        startAlarm(c);

    }
    private void updateTimeText(Calendar c)
    {
        String timeText="Alarm set for:";
        timeText+= DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        textView.setText(timeText);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(Calendar c){
        RadioButton selected= findViewById(rbgroup.getCheckedRadioButtonId());
        ans= rbgroup.indexOfChild(selected)+1;
        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this,AlertReceiver.class);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(this,1,intent,0);
        if(c.before(Calendar.getInstance()))
        {
            c.add(Calendar.DATE,1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);

    }

    private void canelAlarm()
    {
        closeKeyboard();

        if(!received){
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
            alarmManager.cancel(pendingIntent);
            textView.setText("Alarm Cancelled");
        }


            else {


                String input = editText.getText().toString();
               Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_WEEK);

                switch (day) {
                    case Calendar.SUNDAY:
                        if(input.equalsIgnoreCase("Sunday"))
                    {alrcancel();}
                        else{
                        Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show();
                        editText.setText("");
                    }

                        break;
                    case Calendar.MONDAY:
                        if(input.equalsIgnoreCase("Monday"))
                        alrcancel();
                        else{
                            Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show();
                            editText.setText("");
                        }
                        break;
                    case Calendar.TUESDAY:
                        if(input.equalsIgnoreCase("Tuesday"))
                        alrcancel();
                         else{
                            Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show();
                            editText.setText("");
                        }
                        break;
                    case Calendar.WEDNESDAY:
                        if(input.equalsIgnoreCase("Wednesday"))
                        alrcancel();
                        else{
                            Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show();
                            editText.setText("");
                        }
                        break;
                    case Calendar.THURSDAY:
                        if(input.equalsIgnoreCase("Thursday"))
                        alrcancel();
                        else{
                            Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show();
                            editText.setText("");
                        }
                        break;
                    case Calendar.FRIDAY:
                        if(input.equalsIgnoreCase("Friday"))
                        alrcancel();
                         else{
                        Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show();
                            editText.setText("");
                    }
                    break;
                    case Calendar.SATURDAY:
                        if(input.equalsIgnoreCase("Saturday"))
                        alrcancel();
                        else{
                            Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show();
                            editText.setText("");
                        }
                        break;
                    default:
                        Toast.makeText(this, "Enter a valid day", Toast.LENGTH_SHORT).show();
                        editText.setText("");
                        break;
                }
            }





    }
    private void alrcancel()
    {
        received=false;
        mediaPlayer.stop();
        textView.setText("Alarm Cancelled");
    }
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }


}