package com.example.team_project;

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

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CallActivity extends AppCompatActivity {

    ContactdbHelper mydbhelper;
    Intent intent;
    Button backbtn;
    ListView mylistview;

    final static String dbName = "contacts.db";
    final static int dbVersion = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        setTitle(" 나의 비상연락망 ");
        backbtn = (Button) findViewById(R.id.backbtn);
        mydbhelper = new ContactdbHelper(this, dbName, null, dbVersion);

        SQLiteDatabase contectdb;

        contectdb = mydbhelper.getReadableDatabase();
        Cursor cursor;
        cursor = contectdb.rawQuery("SELECT * FROM contacts", null);

        String nametmp = "\n"; String phonetmp = "\n";
        while(cursor.moveToNext()){
            nametmp += cursor.getString(0) + "\n";
            phonetmp += cursor.getString(1) + "\n";
        }

        //string을 잘라서 배열로 만들기
        String[] name = nametmp.split("\n");
        final String[] phone = phonetmp.split("\n");

        mylistview = (ListView) findViewById(R.id.listview1);
        ArrayAdapter<String> adaptername = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name);
        mylistview.setAdapter(adaptername);

        cursor.close();
        contectdb.close();

        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone[i]));
                startActivity(intent);
            }
        });

        View.OnClickListener myclick = new View.OnClickListener() {
            public void onClick(View v){

                switch(v.getId()){
                    case R.id.backbtn:
                        intent = new Intent(CallActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        };
        backbtn.setOnClickListener(myclick);
    }
}