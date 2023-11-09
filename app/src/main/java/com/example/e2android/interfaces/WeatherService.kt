package com.example.e2android.interfaces

import com.example.e2android.models.WeatherForecast
import retrofit2.Call
import retrofit2.http.GET

interface WeatherService {
    @GET("WeatherForecast")
    fun getWeatherForecasts(): Call<List<WeatherForecast>>
}