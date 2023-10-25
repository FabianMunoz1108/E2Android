package com.example.e2android.models

data class Privilegio(
    var idPermiso: String = "",   // Use an appropriate default value
    var idUsuario: Int,
    var activo: Boolean,
    var idPermisoNavigation: Permiso = Permiso()  // Use an appropriate default value
)
