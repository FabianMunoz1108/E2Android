package com.example.e2android.models
import java.io.Serializable

data class Partido(
    var logo: String = "",
    var nombre: String = "",
    var integrantes: Int = 0
) : Serializable