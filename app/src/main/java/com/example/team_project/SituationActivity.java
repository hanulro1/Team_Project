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

    Intent intent, intent1, intent2, intent3, intent4, intent5;
    Button s1, s2, s3, s4, s5, back;

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

        View.OnClickListener myclick = new View.OnClickListener() {
            public void onClick(View v) {
                if (back.equals(v)) {
                    intent = new Intent(SituationActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else if (s1.equals(v)) {
                    intent1 = new Intent(SituationActivity.this, Situation1.class);
                    startActivity(intent1);
                } else if (s2.equals(v)) {
                    intent2 = new Intent (SituationActivity.this, Situation2.class);
                    startActivity(intent2);
                } else if (s3.equals(v)) {
                    intent3 = new Intent(SituationActivity.this, Situation3.class);
                    startActivity(intent3);
                } else if (s4.equals(v)) {
                    intent4 = new Intent(SituationActivity.this, Situation4.class);
                    startActivity(intent4);
                } else if (s5.equals(v)) {
                    intent5 = new Intent(SituationActivity.this, Situation5.class);
                    startActivity(intent5);
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