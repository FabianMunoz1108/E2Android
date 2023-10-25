package com.example.e2android.models

data class Usuario(
    val token: String,
    val nombreCompleto: String?,
    val tipoInicianteDesc: String?,
    val privilegios: List<Privilegio> = emptyList(),
    val foto: String?,
    val idPartido: Int?,
    val idDiputado: Int?,
    val coordinadorGP: Boolean
)
