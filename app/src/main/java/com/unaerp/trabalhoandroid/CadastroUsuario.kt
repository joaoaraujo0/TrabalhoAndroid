package com.unaerp.trabalhoandroid

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class CadastroUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastro)

        val submit_buttoncancelar = findViewById<MaterialButton>(R.id.submit_buttoncancelar)
        val submit_button = findViewById<MaterialButton>(R.id.submit_button)

        submit_button.setOnClickListener {
            val intentCadastro = Intent(this, MenuEstagiario::class.java)
            startActivity(intentCadastro)
        }

        submit_buttoncancelar.setOnClickListener {
            finish()
        }
        val spinner: Spinner = findViewById(R.id.spiner_edittext)
        ArrayAdapter.createFromResource(
            this,
            R.array.spiner_edittext,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }


    }
}