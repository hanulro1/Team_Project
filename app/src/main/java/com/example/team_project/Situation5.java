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

public class Situation5 extends AppCompatActivity {

    ContactdbHelper mydbhelper;

    final static String dbName = "contacts.db";
    final static int dbVersion = 2;
    String addr=((AddressActivity)AddressActivity.context_addressActivity).HelpAd;

    private static final int REQUEST_USED_PERMISSION = 200;
    private static final String[] needPermissons = { Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SEND_SMS ,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean permissionToFileAccepted = true;
        switch(requestCode)
        {
            case REQUEST_USED_PERMISSION:
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        permissionToFileAccepted = false;
                        break;
                    }
                }
                break;
        }
        if(permissionToFileAccepted == false) {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

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

        for(String permission : needPermissons){
            if(ActivityCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,needPermissons, REQUEST_USED_PERMISSION);
                break;
            }
        }

        SmsManager smsManager1 = SmsManager.getDefault();
        String message1 = "온 몸에 힘이 없어요. 빨리 도와주세요.\n"+addr;
        try {
            for (int i=1; i<phone.length;i++) {
                smsManager1.sendTextMessage(phone[i], null, message1, null, null);
            }
            Toast.makeText(getApplicationContext(), "전송이 완료되었습니다.",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"메시지 전송에 실패하였습니다. 다시 시도해주세요.",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}