package com.example.assignment_review

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteManager(context:Context) : SQLiteOpenHelper(context,"USERDB",null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        println("inside manager")
        db?.execSQL("CREATE TABLE REGISTER(REGID INTEGER PRIMARY KEY AUTOINCREMENT, REGNAME TEXT, REGEMAIL VARCHAR, REGUSERNAME TEXT,  REGPWD TEXT)")
        db?.execSQL("INSERT INTO REGISTER(REGNAME,REGEMAIL,REGUSERNAME,REGPWD) VALUES ('meenubiju','meenu@123','meenu','123')")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertIntoRegisterTable(regName: String, regEmail: String, regUsername: String, regPwd: String) {
        val values = ContentValues()
        values.put("REGNAME", regName)
        values.put("REGEMAIL", regEmail)
        values.put("REGUSERNAME", regUsername)
        values.put("REGPWD", regPwd)

        val db = writableDatabase
        db.insert("REGISTER", null, values)
        db.close()
    }

    fun loginAuthentication(regUsername: String, regPwd: String): Boolean {
        val db = readableDatabase
        val query = "REGUSERNAME = ? AND REGPWD = ?"
        val selectionArgs = arrayOf(regUsername, regPwd)
        val cursor = db.query("REGISTER", null, query, selectionArgs, null, null, null)
        val isLoggedIn = cursor.moveToFirst()
        cursor.close()
        db.close()
        return isLoggedIn
    }
}
