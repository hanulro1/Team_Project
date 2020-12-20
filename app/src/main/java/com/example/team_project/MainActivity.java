package com.example.team_project;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton callbtn, emergencybtn, addressbtn, dialogbtn;
    Intent intent1, intent2, intent3, intent4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callbtn=(ImageButton) findViewById(R.id.callbtn);
        emergencybtn=(ImageButton) findViewById(R.id.emergencybtn);
        addressbtn=(ImageButton) findViewById(R.id.addressbtn);
        dialogbtn=(ImageButton) findViewById(R.id.dialogbtn);

        View.OnClickListener myclick = new View.OnClickListener() {
            public void onClick(View v) {
                if (callbtn.equals(v)) {
                    intent1 = new Intent(MainActivity.this, SituationActivity.class);
                    startActivity(intent1);
                } else if (emergencybtn.equals(v)) {
                    intent2 = new Intent (MainActivity.this, EmergencyActivity.class);
                    startActivity(intent2);
                } else if (addressbtn.equals(v)) {
                    intent3 = new Intent(MainActivity.this, AddressActivity.class);
                    startActivity(intent3);
                } else if (dialogbtn.equals(v)) {
                    intent4 = new Intent(MainActivity.this, DialogActivity.class);
                    startActivity(intent4);
                }
            }
        };
        callbtn.setOnClickListener(myclick);
        emergencybtn.setOnClickListener(myclick);
        addressbtn.setOnClickListener(myclick);
        dialogbtn.setOnClickListener(myclick);
    }
}