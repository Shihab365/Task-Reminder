package com.example.todo_v2;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

public class AlarmRecv extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName receiver = new ComponentName(context, AlarmRecv.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);

        String get_data=intent.getExtras().getString("data");
        int s=intent.getExtras().getInt("data1");
        String getNotify=intent.getExtras().getString("data2");

        Intent intent_rec=new Intent(context,Alarm_notify.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent_rec.putExtra("data",get_data);
        intent_rec.putExtra("data1",s);
        intent_rec.putExtra("data2",getNotify);
        context.startService(intent_rec);
    }
}
