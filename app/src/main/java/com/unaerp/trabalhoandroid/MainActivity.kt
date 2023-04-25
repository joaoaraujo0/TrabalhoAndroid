package com.unaerp.trabalhoandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnEntrar = findViewById<MaterialButton>(R.id.botaoEntrar)

        btnEntrar.setOnClickListener {
            val intentCadastro = Intent(this, Menu::class.java)
            startActivity(intentCadastro)
        }

        val textView = findViewById<MaterialButton>(R.id.botaoCadastrar)
        textView.setOnClickListener {
            val intentCadastro = Intent(this, CadastroUsuario::class.java)
            startActivity(intentCadastro)
        }


    }


}


