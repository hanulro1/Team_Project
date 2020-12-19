package com.example.team_project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SituationActivity extends AppCompatActivity {

    static final int SMS_RECEIVE_PERMISSON = 1;
    ContactdbHelper mydbhelper;
    Intent intent;
    Button s1, s2, s3, s4, s5, back;

    final static String dbName = "contacts.db";
    final static int dbVersion = 2;

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int grantResults[]){
        switch(requestCode){
            case SMS_RECEIVE_PERMISSON:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(), "SMS권한 승인함", Toast.LENGTH_SHORT).show(); }
                else{
                    Toast.makeText(getApplicationContext(), "SMS권한 거부함", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situation);

        setTitle(" 나에게 맞는 상황을 선택하세요. ");
        s1 = (Button) findViewById(R.id.situation1);
        s2 = (Button) findViewById(R.id.situation2);
        s3 = (Button) findViewById(R.id.situation3);
        s4 = (Button) findViewById(R.id.situation4);
        s5 = (Button) findViewById(R.id.situation5);
        back = (Button) findViewById(R.id.back);

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

        final String[] name = nametmp.split("\n");
        final String[] phone = phonetmp.split("\n");

        cursor.close();
        contectdb.close();

        int permissonCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        if(permissonCheck == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(), "SMS 수신권한 있음", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "SMS 수신권한 없음", Toast.LENGTH_SHORT).show();
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {
                Toast.makeText(getApplicationContext(), "SMS권한이 필요합니다", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, SMS_RECEIVE_PERMISSON);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, SMS_RECEIVE_PERMISSON);
            }
        }

        View.OnClickListener myclick = new View.OnClickListener() {
            public void onClick(View v){
                switch(v.getId()){
                    case R.id.situation1:
                        SmsManager smsManager1 = SmsManager.getDefault();
                        String message1 = "넘어졌는데 일어날 수가 없어요. 빨리 도와주세요.";
                        try {
                            for (int i=1; i<phone.length;i++) {
                                smsManager1.sendTextMessage(phone[i], null, message1, null, null);
                            }
                            Toast.makeText(getApplicationContext(), "전송이 완료되었습니다.",Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),"메시지 전송에 실패하였습니다. 다시 시도해주세요.",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        break;
                    case R.id.situation2:
                        SmsManager smsManager2 = SmsManager.getDefault();
                        String message2 = "숨을 잘 못쉬겠어요. 빨리 도와주세요.";
                        try {
                            for (int i=1; i<phone.length;i++) {
                                smsManager2.sendTextMessage(phone[i], null, message2, null, null);
                            }
                            Toast.makeText(getApplicationContext(), "전송이 완료되었습니다.",Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),"메시지 전송에 실패하였습니다. 다시 시도해주세요.",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        break;
                    case R.id.situation3:
                        SmsManager smsManager3 = SmsManager.getDefault();
                        String message3 = "가슴이 답답해요. 빨리 도와주세요.";
                        try {
                            for (int i=1;i<phone.length;i++) {
                                smsManager3.sendTextMessage(phone[i], null, message3, null, null);
                            }
                            Toast.makeText(getApplicationContext(), "전송이 완료되었습니다.",Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),"메시지 전송에 실패하였습니다. 다시 시도해주세요.",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        break;
                    case R.id.situation4:
                        SmsManager smsManager4 = SmsManager.getDefault();
                        String message4 = "어지럽고 앞이 잘 안보여요. 빨리 도와주세요.";
                        try {
                            for (int i=1;i<phone.length;i++) {
                                smsManager4.sendTextMessage(phone[i], null, message4, null, null);
                            }
                            Toast.makeText(getApplicationContext(), "전송이 완료되었습니다.",Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),"메시지 전송에 실패하였습니다. 다시 시도해주세요.",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        break;
                    case R.id.situation5:
                        SmsManager smsManager5 = SmsManager.getDefault();
                        String message5 = "온 몸에 힘이 없어요. 빨리 도와주세요.";
                        try {
                            for (int i=1;i<phone.length;i++) {
                                smsManager5.sendTextMessage(phone[i], null, message5, null, null);
                            }
                            Toast.makeText(getApplicationContext(), "전송이 완료되었습니다.",Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),"메시지 전송에 실패하였습니다. 다시 시도해주세요.",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        break;
                    case R.id.back:
                        intent = new Intent(SituationActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        };
       s1.setOnClickListener(myclick);
       s2.setOnClickListener(myclick);
       s3.setOnClickListener(myclick);
       s4.setOnClickListener(myclick);
       s5.setOnClickListener(myclick);
       back.setOnClickListener(myclick);
    }
}