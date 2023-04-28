package com.unaerp.trabalhoandroid

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class EditarVaga : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editar_vaga)

        val botaoCancelar = findViewById<MaterialButton>(R.id.botaoVoltarEditarVaga)
        val botaoEditar = findViewById<MaterialButton>(R.id.botaoEditarVagaEmpresa)

        botaoCancelar.setOnClickListener {
            finish()
        }

        botaoEditar.setOnClickListener {
            Toast.makeText(this, "Anuncio de vaga editado", Toast.LENGTH_SHORT).show()
            finish()
        }



    }
}