package com.example.team_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DeleteContactActivity extends AppCompatActivity {

    ContactdbHelper mydbhelper;
    Intent intent;
    Button backbtn;
    ListView mylistview;

    final static String dbName = "contacts.db";
    final static int dbVersion = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        setTitle(" 연락처 삭제 ");
        backbtn = (Button) findViewById(R.id.backbtn);
        mydbhelper = new ContactdbHelper(this, dbName, null, dbVersion);

        final SQLiteDatabase contectdb;

        contectdb = mydbhelper.getWritableDatabase();
        Cursor cursor;
        cursor = contectdb.rawQuery("SELECT * FROM contacts", null);

        String nametmp = "\n"; String phonetmp = "\n";
        while(cursor.moveToNext()){
            nametmp += cursor.getString(0) + "\n";
            phonetmp += cursor.getString(1) + "\n";
        }
        //string을 잘라서 배열로 만들기
        final String[] name = nametmp.split("\n");
        final String[] phone = phonetmp.split("\n");

        mylistview = (ListView) findViewById(R.id.listview1);
        final ArrayAdapter<String> adaptername = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name);
        mylistview.setAdapter(adaptername);

        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String a = name[i], b=phone[i];
                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(DeleteContactActivity.this);
                alert_confirm.setMessage("연락처를 삭제하시겠습니까?").setCancelable(false).setPositiveButton("네",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                contectdb.execSQL("DELETE FROM contacts WHERE gName = '"+a+"'and  gPhone = '"+b+ "';");
                                intent = new Intent(DeleteContactActivity.this, DialogActivity.class);
                                startActivity(intent);
                            }}).setNegativeButton("아니요",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });
                AlertDialog alert = alert_confirm.create();
                alert.show();
            }
        });

        View.OnClickListener myclick = new View.OnClickListener() {
            public void onClick(View v){

                switch(v.getId()){
                    case R.id.backbtn:
                        intent = new Intent(DeleteContactActivity.this, DialogActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        };
        backbtn.setOnClickListener(myclick);
    }
}