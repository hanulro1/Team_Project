package com.example.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DialogActivity extends AppCompatActivity {

    //dbHelper는 클래스이름
    dbHelper mydbhelper;
    EditText edtName, edtPhone, edtRes1, edtRes2;
    Button initB, insB, searchB;
    SQLiteDatabase mysqlDB;

    //SQLite에서 제공
    public class dbHelper extends SQLiteOpenHelper {
        public dbHelper(Context context){
            //telDB 라는 데이터베이스 (데이터베이스 이름이다.)
            super(context, "telDB", null, 1);
        }

        @Override
        //db는 dbhelper에서 만들어졌던 'tellDB'의 의미
        public void onCreate(SQLiteDatabase db){
            //괄호 안은 SQL안에서 사용하는 명령어
            //contacts는 테이블 이름
            db.execSQL("CREATE TABLE contacts(gName CHAR(20) PRIMARY KEY, gphone CHAR(20))");
        }
        @Override
        //onUpgrade는 초기화하는 용도
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(" 데이터베이스 실습 ");
        edtName = (EditText) findViewById(R.id.editText);
        edtPhone = (EditText) findViewById(R.id.editTextPhone);
        edtRes1 = (EditText) findViewById(R.id.editTextNames);
        edtRes2 = (EditText) findViewById(R.id.editTextPhones);
        initB = (Button) findViewById(R.id.button5);
        insB = (Button) findViewById(R.id.button6);
        searchB = (Button) findViewById(R.id.button7);

        //context에서 dbhelper를 생성해 mydbhelper에 넣기
        //mydbhepler는 현재 사용자가 만든 하나의 데이터베이스를 관리하는 하나의 dbms 파일
        mydbhelper = new dbHelper(this);
        initB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mysqlDB = mydbhelper.getWritableDatabase();
                //초기화
                mydbhelper.onUpgrade(mysqlDB, 1, 2);
                mysqlDB.close();
            }
        });

        insB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mysqlDB = mydbhelper.getWritableDatabase();
                mysqlDB.execSQL(" INSERT INTO contacts VALUES ('"+edtName.getText().toString() + "','"+edtPhone.getText().toString() + "');");
                mysqlDB.close();
            }
        });
        //읽어오는 역할(조회)
        searchB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mysqlDB = mydbhelper.getReadableDatabase();
                Cursor cursor;
                cursor = mysqlDB.rawQuery("SELECT * FROM contacts", null);
                String strname = " name \r\n"+"----------------"+"\r\n";
                String strphone = "phone number" + "\r\n" + "-------------" + "\r\n";
                while(cursor.moveToNext()){
                    //0과 1은 필드번호
                    strname += cursor.getString(0) + "\r\n";
                    strphone += cursor.getString(1) + "\r\n";
                }
                edtRes1.setText(strname);
                edtRes2.setText(strphone);
                cursor.close();
                mysqlDB.close();
            }
        });

    }
}