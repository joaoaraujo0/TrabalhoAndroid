package com.unaerp.trabalhoandroid.model

data class Vagas(
    val id : String,
    val anunciante : String,
    val descricaoVaga : String,
    val areaVaga : String,
    val valorRemuneracao : String,
    val localidade : String,
    val emailContato : String,
    val telefoneContato : String,
    val dataTermino : String,
    val dataInicioVaga : String,
)