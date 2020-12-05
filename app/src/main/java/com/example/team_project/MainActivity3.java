package com.example.team_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Button btn3=(Button) findViewById(R.id.button3);
        Button btn4=(Button) findViewById(R.id.button4);
        Button btn5=(Button) findViewById(R.id.button5);
        Button btn6=(Button) findViewById(R.id.button6);
        Button btn7=(Button) findViewById(R.id.button7);
        Button btn8=(Button) findViewById(R.id.button8);


        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity3.this, MainActivity5.class);
                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity3.this, MainActivity6.class);
                startActivity(intent);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity3.this, MainActivity7.class);
                startActivity(intent);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity3.this, MainActivity8.class);
                startActivity(intent);
            }
        });

        btn7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity3.this, MainActivity9.class);
                startActivity(intent);
            }
        });

        btn8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity3.this, MainActivity10.class);
                startActivity(intent);
            }
        });
    }


}