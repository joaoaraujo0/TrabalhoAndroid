package com.unaerp.trabalhoandroid

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.unaerp.trabalhoandroid.databinding.EditarPerfilBinding

class EditarPerfil : AppCompatActivity() {
    private lateinit var binding: EditarPerfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditarPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.botaoCancelar.setOnClickListener {
            finish()
        }

        binding.botaoEditar.setOnClickListener {
            Toast.makeText(this, "Usuario Editado", Toast.LENGTH_SHORT).show()
            finish()
        }

    }
}