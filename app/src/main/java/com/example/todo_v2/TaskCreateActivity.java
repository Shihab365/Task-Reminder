package com.example.todo_v2;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class TaskCreateActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Button goButton;
    ImageButton pickButton;
    TextView timeText,dateText;

    Calendar now = Calendar.getInstance();
    DatePickerDialog dpd;
    TimePickerDialog tpd;

    TextInputLayout taskCreate;
    String ampm;
    String vDay,vMonth;
    SQLiteHelper sqLiteHelper;
    String cirColor="";

    String crDate;

    RadioButton rb_high,rb_medium,rb_low;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.task_crt_bg));
        }

        sqLiteHelper=new SQLiteHelper(this);
        goButton=findViewById(R.id.letsgo_button_id);
        pickButton=findViewById(R.id.picker_button_id);
        timeText=findViewById(R.id.timetext_id);
        dateText=findViewById(R.id.datetext_id);
        taskCreate=findViewById(R.id.task_text_id);

        rb_high=findViewById(R.id.rb_high_id);
        rb_medium=findViewById(R.id.rb_medium_id);
        rb_low=findViewById(R.id.rb_low_id);

        //Date picker
        dpd=DatePickerDialog.newInstance(TaskCreateActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        //Time picker
        tpd=TimePickerDialog.newInstance(TaskCreateActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),
                false
        );

        //Date and time pick button
        pickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpd.show(getFragmentManager(),"DatePickerDialog");
            }
        });

        //Let's go button
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskString=taskCreate.getEditText().getText().toString().trim();
                String timeStr=timeText.getText().toString().trim();
                String dateStr=dateText.getText().toString().trim();
                String x=vDay;
                String y=vMonth;
                String z=crDate;

                if(taskString.matches("")){
                    LayoutInflater inflater=getLayoutInflater();
                    View view=inflater.inflate(R.layout.custom_toast_1,(ViewGroup)findViewById(R.id.toast_plan_id));
                    Toast toast=new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(view);
                    toast.show();
                }
                else if(timeStr.matches("") && dateStr.matches("")){
                    LayoutInflater inflater=getLayoutInflater();
                    View view=inflater.inflate(R.layout.custom_toast_2,(ViewGroup)findViewById(R.id.toast_schedule_id));
                    Toast toast=new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(view);
                    toast.show();
                }
                else{
                    //Priority boxes
                    if(rb_high.isChecked() || rb_medium.isChecked() || rb_low.isChecked())
                    {
                        if(rb_high.isChecked())
                        {
                            cirColor="red";
                        }
                        if(rb_medium.isChecked())
                        {
                            cirColor="green";
                        }
                        if(rb_low.isChecked())
                        {
                            cirColor="blue";
                        }

                        //alarm info fetch...
                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

                        SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String dateString = dateformatter.format(new Date(now.getTimeInMillis()));//from this line to put DBS

                        Random random=new Random();
                        String strVar= String.valueOf(random.nextInt(1000));


                        //insert to database
                        long rowID=sqLiteHelper.InsertTask(x,y,taskString,timeStr,cirColor,z,dateString,strVar);
                        if(rowID==-1) {
                            Toast.makeText(TaskCreateActivity.this, "Insertion Failed", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent=new Intent(TaskCreateActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }
                    else {
                        LayoutInflater inflater=getLayoutInflater();
                        View view=inflater.inflate(R.layout.custom_toast_3,(ViewGroup)findViewById(R.id.toast_priority_id));
                        Toast toast=new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(view);
                        toast.show();
                    }
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        now.set(Calendar.YEAR,year);
        now.set(Calendar.MONTH,monthOfYear);
        now.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        tpd.show(getFragmentManager(),"TimePickerDialog");

        vDay= String.valueOf(dayOfMonth);
        if(monthOfYear==0){
            vMonth="Jan";
        }
        else if(monthOfYear==1){
            vMonth="Feb";
        }
        else if(monthOfYear==2){
            vMonth="Mar";
        }
        else if(monthOfYear==3){
            vMonth="Apr";
        }
        else if(monthOfYear==4){
            vMonth="May";
        }
        else if(monthOfYear==5){
            vMonth="Jun";
        }
        else if(monthOfYear==6){
            vMonth="Jul";
        }
        else if(monthOfYear==7){
            vMonth="Aug";

        }else if(monthOfYear==8){
            vMonth="Sep";

        }else if(monthOfYear==9){
            vMonth="Oct";

        }else if(monthOfYear==10){
            vMonth="Nov";

        }else if(monthOfYear==11){
            vMonth="Dec";
        }
        crDate= DateFormat.getDateInstance(DateFormat.LONG).format(now.getTime());
        dateText.setText(crDate);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

        now.set(Calendar.HOUR_OF_DAY,hourOfDay);
        now.set(Calendar.MINUTE,minute);
        now.set(Calendar.SECOND,second);

        String hours= String.valueOf(hourOfDay);
        String minutes= String.valueOf(minute);

        //AM-PM setup
        if(hourOfDay>=0 && hourOfDay<=11){
            if(hourOfDay==0){
                hours=String.valueOf(12);
            }
            ampm="am";
        }
        else{
            if(hourOfDay>=13 && hourOfDay<=23){
                hours=String.valueOf(hourOfDay-12);
            }
            ampm="pm";
        }
        //Minute setup
        if(minute<10){
            minutes=String.valueOf("0"+String.valueOf(minute));
        }
        String timeStr=hours+":"+minutes+" "+ampm;
        timeText.setText(timeStr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_layout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.help_id){
            startActivity(new Intent(TaskCreateActivity.this,HelpActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
