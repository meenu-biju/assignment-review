package com.example.assignment_review

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteManager(context:Context) : SQLiteOpenHelper(context,"REVIEWS",null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        println("inside manager")
        db?.execSQL("CREATE TABLE REGISTER(REGID INTEGER PRIMARY KEY AUTOINCREMENT, REGNAME TEXT, REGEMAIL VARCHAR, REGUSERNAME TEXT,  REGPWD TEXT, ADMIN INTEGER DEFAULT 0 )")
        db?.execSQL("CREATE TABLE REVIEW(ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, CONTENT TEXT)")
        db?.execSQL("CREATE TABLE SESSION(ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, USERID INTEGER)")
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
        val userIdIndex = cursor.getColumnIndex("REGID")
        val userId = cursor.getLong(userIdIndex)
        cursor.close()
        db.close()
        val sessionDb = writableDatabase
        if (isLoggedIn) {
            sessionDb.delete("SESSION", null, null)
            val values = ContentValues()
            values.put("USERNAME", regUsername)
            values.put("USERID", userId)
            sessionDb.insert("SESSION", null, values)
        }
        sessionDb.close()

        return isLoggedIn
    }

    fun insertReview(content: String): Long {
        val db = writableDatabase

        // Fetch the userid from the SESSION table
        val sessionQuery = "SELECT USERNAME FROM SESSION"
        val sessionCursor = db.rawQuery(sessionQuery, null)
        val userIdIndex = sessionCursor.getColumnIndex("USERNAME")
        val username: String? = if (sessionCursor.moveToFirst()) {
            sessionCursor.getString(userIdIndex)
        } else {
            null // Default value if no entry is found in the SESSION table
        }
        sessionCursor.close()

        val reviewValues = ContentValues()
        reviewValues.put("USERNAME", username)
        reviewValues.put("CONTENT", content)
        val reviewId = db.insert("REVIEW", null, reviewValues)
        db.close()

        return reviewId
    }


}
