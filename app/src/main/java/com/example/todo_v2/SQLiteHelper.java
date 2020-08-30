package com.example.todo_v2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="ToDo_db";
    private static final String TABLE_NAME="TaskList_DBS";
    private static final int VERSION_NUMBER=1;
    private static final String ID="id";
    private static final String TASK_DAY="taskday";
    private static final String TASK_MONTH="taskmonth";
    private static final String TASK_NAME="taskname";
    private static final String TASK_TIME="tasktime";
    private static final String CIRCLE_COLOR="cirColor";
    private static final String ALARM_DATE="alarmDate";
    private static final String ALARM_TIME_2="alarmTime2";
    private static final String ALARM_ID="alarmID";


    private static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+TASK_DAY+" VARCHAR(20),"+TASK_MONTH+" VARCHAR(10),"+TASK_NAME+" VARCHAR(20),"+TASK_TIME+" VARCHAR(20)," +
            ""+CIRCLE_COLOR+" VARCHAR(20),"+ALARM_DATE+" VARCHAR(20)," +
            ""+ALARM_TIME_2+" VARCHAR(20),"+ALARM_ID+" VARCHAR(10));";

    private static final String DROP_TABLE="DROP TABLE IF EXISTS "+TABLE_NAME;
    private static final String SELECT_TABLE="SELECT * FROM "+TABLE_NAME;
    private static final String DELETE_TABLE="DELETE FROM "+TABLE_NAME;
    private Context context;
    public static SQLiteDatabase sqLiteDatabase;

    public SQLiteHelper(Context context) {
        super(context, TABLE_NAME, null, VERSION_NUMBER);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try{
            db.execSQL(CREATE_TABLE);
        }catch(Exception e)
        {
            Toast.makeText(context, "Exception: "+e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try{
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }catch(Exception e)
        {
            Toast.makeText(context, "Exception: "+e, Toast.LENGTH_SHORT).show();
        }
    }
    public long InsertTask(String taskday,String taskmonth,String taskname,
                           String tasktime,String cirColor,String alarmDate,String alarmTime2,String alarmID)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(TASK_DAY,taskday);
        contentValues.put(TASK_MONTH,taskmonth);
        contentValues.put(TASK_NAME,taskname);
        contentValues.put(TASK_TIME,tasktime);
        contentValues.put(CIRCLE_COLOR,cirColor);
        contentValues.put(ALARM_DATE,alarmDate); //Alarm date stored here
        contentValues.put(ALARM_TIME_2,alarmTime2);//extra
        contentValues.put(ALARM_ID,alarmID);//extra
        long rowID=sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        return rowID;
    }
    public ArrayList<TaskStorage> TaskListView()
    {
        TaskStorage taskStorage;
        ArrayList<TaskStorage> arrayList=new ArrayList<>();
        sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(SELECT_TABLE,null);
        while(cursor.moveToNext())
        {
            taskStorage=new TaskStorage();
            taskStorage.setID(cursor.getString(cursor.getColumnIndex("ID")));
            taskStorage.setTaskday(cursor.getString(cursor.getColumnIndex("taskday")));
            taskStorage.setTaskmonth(cursor.getString(cursor.getColumnIndex("taskmonth")));
            taskStorage.setTaskname(cursor.getString(cursor.getColumnIndex("taskname")));
            taskStorage.setTasktime(cursor.getString(cursor.getColumnIndex("tasktime")));
            taskStorage.setCirColor(cursor.getString(cursor.getColumnIndex("cirColor")));
            taskStorage.setAlarmDate(cursor.getString(cursor.getColumnIndex("alarmDate")));
            taskStorage.setAlarmTime2(cursor.getString(cursor.getColumnIndex("alarmTime2")));
            taskStorage.setAlarmID(cursor.getString(cursor.getColumnIndex("alarmID")));
            arrayList.add(taskStorage);
        }
        return arrayList;
    }
    public static int deleteEntry(String ID)
    {
        String where="ID=?";
        int numberDeleted=sqLiteDatabase.delete(TABLE_NAME, where, new String[]{ID}) ;
        return numberDeleted;
    }
}

