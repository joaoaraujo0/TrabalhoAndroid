package com.unaerp.trabalhoandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<MaterialButton>(R.id.botaoCadastrar)

        textView.setOnClickListener {
            setContentView(R.layout.cadastro)
        }

        val btnEntrar = findViewById<MaterialButton>(R.id.botaoEntrar)

        btnEntrar.setOnClickListener {
            val intentCadastro = Intent(this, CadastroActivity::class.java)
            startActivity(intentCadastro)
        }






    }


}


