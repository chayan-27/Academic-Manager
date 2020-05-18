package com.example.iceb;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentDTB extends SQLiteOpenHelper {

    private static final String DB_NAME="Students";
    private static final int DB_VERSION=1;

    public static String nm;
    public static String rn;
    public static String pd;
    public static String mn;
    public static String imgrid;


    StudentDTB(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USERS(_id INTEGER PRIMARY KEY AUTOINCREMENT,"+"NAME TEXT,"+
                "ROLL_NO TEXT,"+
                "PASSWORD TEXT,"+
                "MOBILE TEXT," +"IMAGE_RESOURCE_ID BLOB);");








      /*  String name=intent.getStringExtra(nm);
        String roll_no=intent.getStringExtra(rn);
        String password=intent.getStringExtra(pd);
        String mobile_no=intent.getStringExtra(mn);
        String res_id=intent.getStringExtra(imgrid);*/

        //insertData(nm,rn,pd,mn,imgrid,db);






    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

   /* public void insertData(String name,String roll_no,String password,String mobile,String res_id,SQLiteDatabase db)
    {

        ContentValues details=new ContentValues();
        details.put("NAME",name);
        details.put("ROLL_NO",roll_no);
        details.put("PASSWORD",password);
        details.put("MOBILE",mobile);
        details.put("IMAGE_RESOURCE_ID",res_id);

        db.insert("USERS",null,details);

        /*db.update("USERS",
                details,
                "NAME = ?",
                new String[] {name});
        db.update("USERS",
                details,
                "ROLL_NO = ?",
                new String[] {roll_no});
        db.update("USERS",
                details,
                "PASSWORD = ?",
                new String[] {password});
        db.update("USERS",
                details,
                "MOBILE = ?",
                new String[] {mobile});*/





    }
