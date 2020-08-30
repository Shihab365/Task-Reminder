package com.example.todo_v2;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fabCreate;
    ListView listView;
    SQLiteHelper sqLiteHelper;
    CustomAdapter customAdapter;
    ArrayList<TaskStorage> arrayList;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        dialog=new Dialog(this);
        sqLiteHelper=new SQLiteHelper(this);
        fabCreate=findViewById(R.id.create_task_id);
        listView=findViewById(R.id.listview_id);

        arrayList=new ArrayList<>();
        arrayList=sqLiteHelper.TaskListView();
        customAdapter=new CustomAdapter(this,arrayList);
        if(customAdapter.isEmpty()){
            dialog.setContentView(R.layout.custom_dialog);
            ImageButton imageButton=dialog.findViewById(R.id.dialog_btn_id);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,TaskCreateActivity.class));
                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
        listView.setAdapter(customAdapter);

        fabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TaskCreateActivity.class));
            }
        });
    }
}
