package com.example.e2android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.e2android.interfaces.AuthService
import com.example.e2android.interfaces.MyApi
import com.example.e2android.interfaces.WeatherService
import com.example.e2android.models.Autenticacion
import com.example.e2android.models.Partido
import com.example.e2android.models.Respuesta
import com.example.e2android.models.Usuario
import com.example.e2android.models.WeatherForecast
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream

class MainActivity : AppCompatActivity() {
    private val URLBASE: String = "https://pld.congresogto.gob.mx/api/"
    private val TAG: String = "CHECK_RESPONSE"
    private var NOMBREUSUARIO : String = "Fabián"//Este valor se remplazará con el obtenido desde la API

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val txtUsu: EditText = findViewById(R.id.UserNameEditText)
        val txtCon: EditText = findViewById(R.id.PasswordEditText)
        val btnLogin: Button = findViewById(R.id.LoginButton)
        txtUsu.setText("fabian.munoz@congresogto.gob.mx")
        txtCon.setText("21Ctie2023*")

        btnLogin.setOnClickListener {
            progressBar.visibility = View.VISIBLE

            /*Omitimos temporalmente la validación de usuario a traves de API, ya que es necesario VPN*/
            //postAuthenticateUser(txtUsu.text.toString(), txtCon.text.toString())
            mostrarActividad()
        }
    }

    private fun main(u: String, p: String) = runBlocking {

        try {
            //launch {
            //getWeatherForeCast()
            //}
            launch {
                postAuthenticateUser(u, p)
            }
        } catch (e: Exception) {
            println("${e.message}")
        }
    }
    private fun mostrarActividad() {
        /*Despliegue de nueva actividad*/
        val intent = Intent(this@MainActivity, WelcomeActivity::class.java)
        val partidos = arrayOf(
            Partido(
                "https://congreso-gto.s3.amazonaws.com/uploads/partido/logo/1/pan_l.png",
                "PAN",
                21
            ),
            Partido(
                "https://congreso-gto.s3.amazonaws.com/uploads/partido/logo/8/morena_s.png",
                "MORENA",
                6
            ),
            Partido(
                "https://congreso-gto.s3.amazonaws.com/uploads/partido/logo/2/pri_s.png",
                "PRI",
                4
            ),
            Partido(
                "https://congreso-gto.s3.amazonaws.com/uploads/partido/logo/3/pvem_s.png",
                "PVEM",
                2
            ),
            Partido(
                "https://congreso-gto.s3.amazonaws.com/uploads/partido/logo/9/logo_mc.png",
                "MC",
                1
            )
        )
        intent.putExtra("partidos", partidos)
        intent.putExtra("nombre", NOMBREUSUARIO)
        intent.putExtra("partidos", serializePartidoArray(partidos))
        startActivity(intent)
    }

    fun serializePartidoArray(partidos: Array<Partido>): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
        objectOutputStream.writeObject(partidos)
        objectOutputStream.close()

        return byteArrayOutputStream.toByteArray()
    }

    private fun postAuthenticateUser(user: String, password: String): Respuesta<Usuario>? {
        var usuario: Respuesta<Usuario>? = null
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        val api = Retrofit.Builder()
            .baseUrl(URLBASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)

        val request = Autenticacion(user, password)
        api.autenticarUsuario(request).enqueue(object : Callback<Respuesta<Usuario>> {
            override fun onResponse(
                call: Call<Respuesta<Usuario>>,
                response: Response<Respuesta<Usuario>>
            ) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    usuario = response.body()

                    if (usuario != null) {
                        val token: String? = usuario!!.datos?.get(0)?.token
                        NOMBREUSUARIO = usuario!!.datos?.get(0)?.nombreCompleto!!

                        /*Despliegue de actividad de bienvenida*/
                        mostrarActividad()
                    }
                }
            }

            override fun onFailure(call: Call<Respuesta<Usuario>>, t: Throwable) {
                Log.i(TAG, "on Failure: ${t.message}")
                progressBar.visibility = View.GONE

                val toast = Toast.makeText(this@MainActivity, "Error ${t.message}!", Toast.LENGTH_LONG)
                toast.show()
            }
        })
        return usuario
    }

    private fun getWeatherForeCasts() {
        val api = Retrofit.Builder()
            .baseUrl(URLBASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)

        try {

            api.getWeatherForecasts().enqueue(object : Callback<List<WeatherForecast>> {
                override fun onResponse(
                    call: Call<List<WeatherForecast>>,
                    response: Response<List<WeatherForecast>>
                ) {
                    if (response.isSuccessful) {
                        val weatherForecasts = response.body()
                        if (weatherForecasts != null) {
                            for (forecast in weatherForecasts) {
                                // Handle each WeatherForecast object in the collection
                                println("Date: ${forecast.date}, TemperatureC: ${forecast.temperatureC}, Summary: ${forecast.summary}")
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<WeatherForecast>>, t: Throwable) {
                    Log.i(TAG, "on Failure: ${t.message}")
                }
            })
        } catch (e: Exception) {
            println("${e.message}")
        }
    }

    private fun getAllComments() {
        val api = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)

        api.getComments().enqueue(object : Callback<List<Comments>> {
            override fun onResponse(
                call: Call<List<Comments>>,
                response: Response<List<Comments>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        for (comentario in it) {
                            Log.i(TAG, "onResponse: ${comentario.email}")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Comments>>, t: Throwable) {
                Log.i(TAG, "on Failure: ${t.message}")
            }
        })
    }
}