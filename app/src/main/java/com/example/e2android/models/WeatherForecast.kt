package com.example.e2android.models

data class WeatherForecast(
    val date: String,
    val temperatureC: Int,
    val temperatureF: Int,
    val summary: String? = null
)