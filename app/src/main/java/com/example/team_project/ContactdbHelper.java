package com.example.team_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper {
    //dbHelper 클래스를 만들 때 onCreate 함수, onUpgrade함수가 반드시 필요
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