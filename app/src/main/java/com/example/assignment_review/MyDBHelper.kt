package com.example.assignment_review

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHelper(context:Context) : SQLiteOpenHelper(context,"USERDB",null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE REGISTER(REGID INTEGER PRIMARY KEY AUTOINCREMENT, REGNAME TEXT, REGEMAIL VARCHAR, REGUSERNAME TEXT,  REGPWD TEXT)")
        db?.execSQL("INSERT INTO REGISTER(REGNAME,REGEMAIL,REGUSERNAME,REGPWD) VALUES ('meenubiju','meenu@123','meenu','123')")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}
