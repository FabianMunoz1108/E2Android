package com.example.e2android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.example.e2android.models.Partido

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_activity)

        val image1 = findViewById<ImageView>(R.id.imageView1)
        val image2 = findViewById<ImageView>(R.id.imageView2)
        val image3 = findViewById<ImageView>(R.id.imageView3)
        val image4 = findViewById<ImageView>(R.id.imageView4)
        val image5 = findViewById<ImageView>(R.id.imageView5)

        Glide.with(this)
            .load("https://congreso-gto.s3.amazonaws.com/uploads/partido/logo/1/pan_l.png")
            .into(image1)
        Glide.with(this)
            .load("https://congreso-gto.s3.amazonaws.com/uploads/partido/logo/8/morena_s.png")
            .into(image2)
        Glide.with(this)
            .load("https://congreso-gto.s3.amazonaws.com/uploads/partido/logo/2/pri_s.png")
            .into(image3)
        Glide.with(this)
            .load("https://congreso-gto.s3.amazonaws.com/uploads/partido/logo/3/pvem_s.png")
            .into(image4)
        Glide.with(this)
            .load("https://congreso-gto.s3.amazonaws.com/uploads/partido/logo/9/logo_mc.png")
            .into(image5)

        val nombre = intent.getStringExtra("nombre")
        val total = intent.getDoubleExtra("total", 0.0)
        val lblNombre: EditText = findViewById(R.id.lblNombre)
        lblNombre.setText(nombre + "-"+ total)

        val toast = Toast.makeText(this, "Bienvenido ${nombre}!", Toast.LENGTH_LONG)
        toast.show()

        val btnBack: Button = findViewById(R.id.btnBack)
        btnBack.setOnClickListener()
        {
            onBackPressed()
        }
        val btnClose: Button = findViewById(R.id.btnClose)
        btnClose.setOnClickListener()
        {
            //finish()//cierra actividad
            finishAffinity()//Pone en standby
            //finishAndRemoveTask()//Destruye activity y vuelve al padre
            //System.exit(0)//Reinicia procesos
            //ActivityCompat.finishAffinity(this)

            val serviceIntent = Intent(this, MainActivity::class.java)
            stopService(serviceIntent)
            finishAndRemoveTask()
            android.os.Process.killProcess(android.os.Process.myPid())
        }
    }
}