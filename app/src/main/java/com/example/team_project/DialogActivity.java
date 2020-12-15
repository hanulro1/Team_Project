package com.example.team_project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class DialogActivity extends AppCompatActivity {

    //dbHelper는 클래스이름
    ContactdbHelper mydbhelper;
    Intent intent;
    EditText edtName, edtPhone, edtRes1, edtRes2;
    Button backbtn, initB, insB, searchB;

    final static String dbName = "contacts.db";
    final static int dbVersion = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        setTitle(" 전화번호부를 작성해주세요 ");
        backbtn = (Button) findViewById(R.id.backbtn);
        edtName = (EditText) findViewById(R.id.editText);
        edtPhone = (EditText) findViewById(R.id.editTextPhone);
        edtRes1 = (EditText) findViewById(R.id.editTextNames);
        edtRes2 = (EditText) findViewById(R.id.editTextPhones);
        initB = (Button) findViewById(R.id.initB);
        insB = (Button) findViewById(R.id.insB);
        searchB = (Button) findViewById(R.id.searchB);
        mydbhelper = new ContactdbHelper(this, dbName, null, dbVersion);
        //context에서 dbhelper를 생성해 mydbhelper에 넣기
        //mydbhepler는 현재 사용자가 만든 하나의 데이터베이스를 관리하는 하나의 dbms 파일

        View.OnClickListener myclick = new View.OnClickListener() {
            public void onClick(View v){

                SQLiteDatabase contectdb;
                String strname = "이름 \r\n"+"----------------"+"\r\n";
                String strphone = "전화번호" + "\r\n" + "-------------" + "\r\n";

                switch(v.getId()){
                    case R.id.backbtn:
                        intent = new Intent(DialogActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.initB:
                        contectdb = mydbhelper.getWritableDatabase();
                        //초기화
                        mydbhelper.onUpgrade(contectdb, 1, 2);
                        contectdb.close();
                        edtRes1.setText(strname); edtRes2.setText(strphone);
                        break;
                    case R.id.insB:
                        //추가
                        contectdb = mydbhelper.getWritableDatabase();
                        contectdb.execSQL(" INSERT INTO contacts VALUES ('"+edtName.getText().toString() + "','"+edtPhone.getText().toString() + "');");
                        edtName.setText(null); edtPhone.setText(null);
                        contectdb.close();
                        break;
                    case R.id.searchB:
                        //읽어오는 역할(조회)
                        contectdb = mydbhelper.getReadableDatabase();
                        Cursor cursor;
                        cursor = contectdb.rawQuery("SELECT * FROM contacts", null);
                        while(cursor.moveToNext()){
                            //0과 1은 필드번호
                            strname += cursor.getString(0) + "\r\n";
                            strphone += cursor.getString(1) + "\r\n";
                        }
                        edtRes1.setText(strname);
                        edtRes2.setText(strphone);
                        cursor.close();
                        contectdb.close();
                        break;
                }
            }
        };
        backbtn.setOnClickListener(myclick);
        initB.setOnClickListener(myclick);
        insB.setOnClickListener(myclick);
        searchB.setOnClickListener(myclick);
    }
}