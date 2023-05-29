package com.unaerp.trabalhoandroid.model

import java.util.Date

data class Vagas(
    val anunciante : String,
    val descricaoVaga : String,
    val areaVaga : String,
    val valorRemuneracao : String,
    val localidade : String,
    val emailContato : String,
    val telefoneContato : String,
    val dataTermino : String,
    val dataInicioVaga : Date?,
)