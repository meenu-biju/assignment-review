package com.example.assignment_review

import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Connection

class Database {

    val url = "jdbc:mysql://sql.freedb.tech:3306/freedb_review_app_2"
    val user = "freedb_meenuadmin_2"
    val password = "DD%??3XpW!vU#4\$"
    private var connection: Connection? = null

    init {
        connect()
    }

    fun connect() {

        val thread = Thread {
            try {
                Class.forName("com.mysql.jdbc.Driver")
                connection = DriverManager.getConnection(url, user, password)
            } catch (e: SQLException) {
                println("inside catch")
                e.printStackTrace()
            } catch (e: ClassNotFoundException) {
                println("class not found")
                e.printStackTrace()
            }
        }

        thread.start()
        try{
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    fun authenticate(username: String, password: String): Boolean{
        println("$username $password")
        var userExist = false
        val authenticateQuery = "select * from users where username='$username' and password='$password'";
        val thread = Thread {
            try {
                val statement = connection?.createStatement()
                val resultSet = statement?.executeQuery(authenticateQuery);
                while (resultSet?.next() == true) {
                    userExist = true
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        thread.start()
        try{
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return userExist

    }
}