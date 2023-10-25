package com.example.e2android

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_activity)

        val nombre = intent.getStringExtra("nombre")

        val image = findViewById<ImageView>(R.id.imageView)
        Glide.with(this)
            .load("https://congreso-gto.s3.amazonaws.com/uploads/diputado/imagen/134/FERNANDEZ_GONZALEZ.jpg")
            .into(image)

        val lblNombre: EditText = findViewById(R.id.lblNombre)
        lblNombre.setText(nombre)

        val toast = Toast.makeText(this, "Bienvenido ${nombre}!", Toast.LENGTH_LONG)
        toast.show()
    }
}