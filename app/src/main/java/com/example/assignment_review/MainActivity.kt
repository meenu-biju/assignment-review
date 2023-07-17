package com.example.assignment_review

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.assignment_review.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity ::class.java ))
        }
        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity ::class.java))
        }
        var helper = MyDBHelper(applicationContext)
        var db = helper.readableDatabase
        var rs = db.rawQuery("SELECT * FROM REGISTER",null)



// Assuming you have the database object or helper class assigned to the "db" variable

        if (rs.moveToNext())
            Toast.makeText(applicationContext,rs.getString(1),Toast.LENGTH_LONG).show()

        val btn_register = findViewById<Button>(R.id.btn_register)
        val et_fullname = findViewById<TextInputEditText>(R.id.et_fullname)
        val et_email = findViewById<TextInputEditText>(R.id.et_email)
        val et_username = findViewById<TextInputEditText>(R.id.et_username)
        val et_password = findViewById<TextInputEditText>(R.id.et_password)

        btn_register.setOnClickListener {
            val cv = ContentValues()
            cv.put("REGNAME", et_fullname.text.toString())
            cv.put("REGEMAIL", et_email.text.toString())
            cv.put("REGUSERNAME", et_username.text.toString())
            cv.put("REGPWD", et_password.text.toString())

            db.insert("REGISTER", null, cv)
                et_fullname.setText("")
                et_email.setText("")
                et_username.setText("")
                et_password.setText("")
            }
        }
    }



