package com.example.e2android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val email: EditText = findViewById(R.id.UserNameEditText)
        val pswrd: EditText = findViewById(R.id.PasswordEditText)
        val lgnbtn : Button = findViewById(R.id.LoginButton)

        lgnbtn.setOnClickListener{
            val correo = email.text.toString()
            val psw = pswrd.text.toString()

            val text = "Hello $correo!"
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(this, text, duration) // in Activity
            toast.show()

            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
        }
    }
}