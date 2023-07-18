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
        db?.execSQL("CREATE TABLE ACTIVITY(ID INTEGER PRIMARY KEY AUTOINCREMENT, USERID INTEGER, REVIEWID INTEGER, USERACTION VARCHAR)")
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

    fun getUserNameFromSession(): String {
        val db = readableDatabase
        val query = "SELECT USERNAME FROM SESSION"
        val cursor = db.rawQuery(query, null)
        var username: String = ""

        if (cursor.moveToFirst()) {
            val usernameIndex = cursor.getColumnIndex("USERNAME")
            username = if (usernameIndex != -1) cursor.getString(usernameIndex) else ""

        }

        cursor.close()
        db.close()

        return username
    }
    fun getUserIDFromSession(): Int {
        val db = readableDatabase
        val query = "SELECT USERID FROM SESSION"
        val cursor = db.rawQuery(query, null)
        var UserID: Int = 0

        if (cursor.moveToFirst()) {
            val USERIDIndex = cursor.getColumnIndex("USERID")
            UserID = if (USERIDIndex != -1) cursor.getInt(USERIDIndex) else 0

        }

        cursor.close()
        db.close()

        return UserID
    }

    fun toggleReviewAction(userId: Int, reviewId: Int, userAction: String) {
        val db = writableDatabase

        // Delete the opposite action row if it exists
        val oppositeAction = if (userAction == "like") "dislike" else "like"
        db.delete("ACTIVITY", "USERID = ? AND REVIEWID = ?", arrayOf(userId.toString(), reviewId.toString()))

        // Insert the new action row
        val values = ContentValues().apply {
            put("USERID", userId)
            put("REVIEWID", reviewId)
            put("USERACTION", userAction)
        }

        db.insert("ACTIVITY", null, values)
//        db.close()
    }

    fun isReviewDisliked(userId: Int, reviewId: Int): Boolean {
        val db = readableDatabase

        val selection = "USERID = ? AND REVIEWID = ? AND USERACTION = ?"
        val selectionArgs = arrayOf(userId.toString(), reviewId.toString(), "dislike")

        val cursor = db.query("ACTIVITY", null, selection, selectionArgs, null, null, null)
        val isDisliked = cursor.count > 0

        cursor.close()
        db.close()

        return isDisliked
    }

    fun isReviewLiked(userId: Int, reviewId: Int): Boolean {
        val db = readableDatabase

        val selection = "USERID = ? AND REVIEWID = ? AND USERACTION = ?"
        val selectionArgs = arrayOf(userId.toString(), reviewId.toString(), "like")

        val cursor = db.query("ACTIVITY", null, selection, selectionArgs, null, null, null)
        val liked = cursor.count > 0

        cursor.close()
        db.close()

        return liked
    }


    fun updateReview(reviewId: Int, content: String): Boolean {
        val db = writableDatabase

        // Prepare the values to be updated
        val reviewValues = ContentValues().apply {
            put("CONTENT", content)
        }

        // Update the review with the specified reviewId
        val rowsAffected = db.update("REVIEW", reviewValues, "ID = ?", arrayOf(reviewId.toString()))
        db.close()

        return rowsAffected > 0
    }


}
