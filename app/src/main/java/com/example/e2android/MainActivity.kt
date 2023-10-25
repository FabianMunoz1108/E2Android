package com.example.e2android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.e2android.models.Autenticacion
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
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

class MainActivity : AppCompatActivity() {
    private val URLBASE: String = "https://jsonplaceholder.typicode.com/"
    private val TAG: String = "CHECK_RESPONSE"
    //private val sharedPreferences = getSharedPreferences("MyApp_Prefs", Context.MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val txtUsu: EditText = findViewById(R.id.UserNameEditText)
        val txtCon: EditText = findViewById(R.id.PasswordEditText)
        val btnLogin: Button = findViewById(R.id.LoginButton)
        txtUsu.setText("fabian.munoz@congresogto.gob.mx")
        txtCon.setText("16Ctie2023*")

        btnLogin.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            postAuthenticateUser(txtUsu.text.toString(), txtCon.text.toString())
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

    private fun postAuthenticateUser(user: String, password: String): Respuesta<Usuario>? {
        var usuario: Respuesta<Usuario>? = null
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        val api = Retrofit.Builder()
            .baseUrl("https://pld.congresogto.gob.mx/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)

        val request = Autenticacion(user, password)
        api.autenticarUsuario(request).enqueue(object : Callback<Respuesta<Usuario>> {
            override fun onResponse(
                call: Call<Respuesta<Usuario>>,
                response: Response<Respuesta<Usuario>>
            ) {
                if (response.isSuccessful) {
                    usuario = response.body()

                    if (usuario != null) {
                        val token: String? = usuario!!.datos?.get(0)?.token
                        val nombre = usuario!!.datos?.get(0)?.nombreCompleto
                        //sharedPreferences.edit().putString("auth_token", token).apply()

                        /*Despliegue de nueva actividad*/
                        val intent = Intent(this@MainActivity, WelcomeActivity::class.java)
                        intent.putExtra("nombre", nombre)
                        startActivity(intent)
                    }
                }
            }

            override fun onFailure(call: Call<Respuesta<Usuario>>, t: Throwable) {
                Log.i(TAG, "on Failure: ${t.message}")
                progressBar.visibility = View.GONE
            }
        })
        return usuario
    }

    private fun getWeatherForeCasts() {
        val api = Retrofit.Builder()
            .baseUrl("https://pld.congresogto.gob.mx/api/")
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
            .baseUrl(URLBASE)
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

interface AuthService {
    @Headers("Content-Type: application/json")
    @POST("Usuarios/Token")
    fun autenticarUsuario(@Body request: Autenticacion): Call<Respuesta<Usuario>>
}

interface MyApi {
    @GET("Comments")
    fun getComments(): Call<List<Comments>>
}

interface WeatherService {
    @GET("WeatherForecast")
    fun getWeatherForecasts(): Call<List<WeatherForecast>>
}