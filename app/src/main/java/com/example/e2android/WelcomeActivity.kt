package com.example.e2android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.e2android.models.DecimalTextWatcher
import com.example.e2android.models.Partido
import java.io.ByteArrayInputStream
import java.io.ObjectInputStream

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_activity)

        val layout = findViewById<ConstraintLayout>(R.id.contenedor)
        val editTextList = mutableListOf<EditText>()
        val imageViewList = mutableListOf<ImageView>()

        for (i in 0 until layout.childCount) {
            val child = layout.getChildAt(i)

            if (child is EditText) {
                editTextList.add(child)
            }
            else if(child is ImageView)
            {
                imageViewList.add(child)
            }
        }
        val serializedPartidoArray = intent.getByteArrayExtra("partidos")
        val partidos = serializedPartidoArray?.let { deserializePartidoArray(it) }
        val total = partidos?.sumOf { it.integrantes }

        partidos?.forEachIndexed { indice, partido ->

            val img = imageViewList[indice]
            Glide.with(this)
                .load(partido.logo)
                .into(img)

            val txt = editTextList[indice]
            txt.addTextChangedListener(DecimalTextWatcher(txt))

            val avr = total?.let { calculatePercentage(partido.integrantes.toDouble(), it.toDouble()) }
            txt.setText(avr.toString())
        }
        val nombre = intent.getStringExtra("nombre")
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
        val btnCalculadora: Button = findViewById(R.id.btnCalculadora)
        btnCalculadora.setOnClickListener {
            val intent = Intent(this, CalculadoraActivity::class.java)

            /*pasamos kilos y estatura para calcular el IMC*/
            intent.putExtra("kilos", "75".toDouble())
            intent.putExtra("estatura", "170".toDouble())

            startActivity(intent)
        }
    }
    fun calculatePercentage(part: Double, total: Double): Double {
        return (part / total) * 100
    }
    fun deserializePartidoArray(serializedPartidoArray: ByteArray): Array<Partido> {
        val byteArrayInputStream = ByteArrayInputStream(serializedPartidoArray)
        val objectInputStream = ObjectInputStream(byteArrayInputStream)
        val partidos = objectInputStream.readObject() as Array<Partido>
        objectInputStream.close()

        return partidos
    }
}