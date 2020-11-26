package com.example.grademanagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper{
	//学生表全局变量
	public static final String TABLE_NAME="personInfo";
	public static final String ID="ID";
	public static final String NAME="Name";
	public static final String SCORE="Score";
	public static final String CLASS="Class";
	public static final String GRADE="Grade";
	//用户表全局变量
	public static final String TABLE2_NAME="UserData";
	public static final String LOGIN_ID="ID";
	public static final String Username="username";
	public static final String Password="password";

        public MyOpenHelper(Context context, String name, CursorFactory factory,int version) {
            super(context, name, factory, version);
        }


	@Override
	public void onCreate(SQLiteDatabase db) {
        	//用户登陆表
        db.execSQL("create table if not exists "+
				TABLE2_NAME+" ("+
                LOGIN_ID+" integer primary key autoincrement,"+
				Username+" varchar,"+
                Password+" varchar)");

            //学生信息表
        db.execSQL("drop table if exists "+TABLE_NAME);
		db.execSQL("create table if not exists "+
				TABLE_NAME+" ("+
				ID+" integer primary key autoincrement,"+
				NAME+" varchar,"+
				SCORE+" varchar,"+
				CLASS+" varchar,"+
				GRADE+" varchar)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
}
