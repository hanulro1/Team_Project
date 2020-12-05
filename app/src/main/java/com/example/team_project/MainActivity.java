package com.example.team_project;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageButton btn=(ImageButton) findViewById(R.id.imageButton);
        ImageButton btn2=(ImageButton) findViewById(R.id.imageButton2);
        ImageButton btn3=(ImageButton) findViewById(R.id.imageButton3);
        ImageButton btn4=(ImageButton) findViewById(R.id.imageButton4);


        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this, MainActivity3.class);
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this, MainActivity4.class);
                startActivity(intent);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            }
        });
    }
}