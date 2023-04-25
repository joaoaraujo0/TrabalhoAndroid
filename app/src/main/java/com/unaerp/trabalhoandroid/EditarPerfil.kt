package com.unaerp.trabalhoandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class EditarPerfil : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editar_perfil)

        val botaoCancelar = findViewById<MaterialButton>(R.id.botaoCancelar)
        val botaoEditar = findViewById<MaterialButton>(R.id.botaoEditar)

        botaoCancelar.setOnClickListener {
            finish()
        }

        botaoEditar.setOnClickListener {
            Toast.makeText(this, "Usuario Editado", Toast.LENGTH_SHORT).show()
            finish()
        }

    }
}