package com.example.todo_v2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;

public class CustomAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<TaskStorage> arrayList;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Intent intent;

    CustomAdapter(Context context,ArrayList<TaskStorage> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position,View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.task_sample,parent,false);
        }
        TextView taskDay=convertView.findViewById(R.id.task_day_id);
        TextView taskMonth=convertView.findViewById(R.id.task_month_id);
        TextView taskName=convertView.findViewById(R.id.task_name_id);
        TextView taskTime=convertView.findViewById(R.id.task_time_id);

        final LinearLayout linearLayout=convertView.findViewById(R.id.toast_delete_id);

        final TaskStorage taskStorage=arrayList.get(position);
        taskDay.setText(taskStorage.getTaskday());
        taskMonth.setText(taskStorage.getTaskmonth());
        taskName.setText(taskStorage.getTaskname());
        taskTime.setText(taskStorage.getTasktime());

        //alarm info retrieve from DBS...
        String temp_req=taskStorage.getAlarmID();
        String temp_fire=taskStorage.getAlarmTime2();
        int alr_Code= Integer.parseInt(temp_req);
        String notifyStr=taskStorage.getTaskname();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = df.parse(temp_fire);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long epoch = date.getTime();

        alarmManager=(AlarmManager)context.getSystemService(ALARM_SERVICE);
        intent=new Intent(context,AlarmRecv.class);
        intent.putExtra("data","on");//status data
        intent.putExtra("data1",alr_Code);
        intent.putExtra("data2",notifyStr);
        pendingIntent= PendingIntent.getBroadcast(context,alr_Code,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,epoch,pendingIntent);

        String recCode=taskStorage.getCirColor();
        if(recCode.matches("red")){
            GradientDrawable gradientDrawable=(GradientDrawable)convertView.findViewById(R.id.circle_layout_id).getBackground().mutate();
            gradientDrawable.setColor(Color.parseColor("#FC473B"));
        }
        else if(recCode.matches("green")){
            GradientDrawable gradientDrawable=(GradientDrawable)convertView.findViewById(R.id.circle_layout_id).getBackground().mutate();
            gradientDrawable.setColor(Color.parseColor("#5AC515"));
        }
        else if(recCode.matches("blue")){
            GradientDrawable gradientDrawable=(GradientDrawable)convertView.findViewById(R.id.circle_layout_id).getBackground().mutate();
            gradientDrawable.setColor(Color.parseColor("#ff8f00"));
        }

        convertView.findViewById(R.id.delete_btn_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("data","off");
                alarmManager.cancel(pendingIntent);
                context.sendBroadcast(intent);

                SQLiteHelper.deleteEntry(taskStorage.getID());
                arrayList.remove(position);
                //Custom toast
                inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view=inflater.inflate(R.layout.custom_toast_4,linearLayout);
                Toast toast=new Toast(context);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(view);
                toast.show();
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}

