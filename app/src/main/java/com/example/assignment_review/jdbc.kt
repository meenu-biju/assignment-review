package com.example.assignment_review
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException





fun connect(): Connection? {
    val url = "jdbc:mysql://localhost:3306/review_app"
    val user = "root"
    val password = ""

    try {
        Class.forName("com.mysql.cj.jdbc.Driver")
        return DriverManager.getConnection(url, user, password)
    } catch (e: SQLException) {
        println("inside catch")
        e.printStackTrace()
    } catch (e: ClassNotFoundException) {
        println("class not found")
        e.printStackTrace()
    }
    return null
}

fun main(){
    val connection = connect()

            if (connection != null) {
                println("connection success")
            }
    try {
        val statement = connection?.createStatement()
        val query = "select * from users";
        val resultSet = statement?.executeQuery(query);
        while (resultSet!!.next()) {
            val id = resultSet.getInt("id")
            val name = resultSet.getString("firstname")
            val email = resultSet.getString("lastname")
            val username = resultSet.getString("username")
            println("ID: $id, Name: $name, Email: $email : $username" )
        }
//        statement.close()
    }
    catch (e: SQLException){
        e.printStackTrace()
        println("Exception occured")
    }

    println("hello")



}
