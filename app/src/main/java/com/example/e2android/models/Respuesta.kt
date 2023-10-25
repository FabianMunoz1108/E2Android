package com.example.e2android.models

data class Respuesta<Any>(
    var mensaje: String = "",
    var codigoEstatus: Int = 0,
    var datos: List<Any>? = null
)
