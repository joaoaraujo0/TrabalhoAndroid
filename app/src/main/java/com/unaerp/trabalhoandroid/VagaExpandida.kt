package com.unaerp.trabalhoandroid

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class VagaExpandida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.painel_de_vagas)

        val areaVaga = intent.getStringExtra("areaVaga")
        val descricaoVaga = intent.getStringExtra("descricaoVaga")
        val valorRemuneracao = intent.getStringExtra("valorRemuneracao")
        val localidade = intent.getStringExtra("localidade")
        val emailContato = intent.getStringExtra("emailContato")
        val telefoneContato = intent.getStringExtra("telefoneContato")
        val anunciante = intent.getStringExtra("anunciante")
        val dataInicioVaga = intent.getStringExtra("dataInicioVaga")
        val dataTermino = intent.getStringExtra("dataTermino")

        val textViewAnunciante = findViewById<TextView>(R.id.anunciante)
        val textViewAreaVaga = findViewById<TextView>(R.id.areaVaga)
        val textViewdescricaoVaga = findViewById<TextView>(R.id.descricaoVaga)
        val textViewLocalidade = findViewById<TextView>(R.id.localidadeVaga)
        val textViewDataTermino = findViewById<TextView>(R.id.dataTerminoDaVaga)
        val textViewvalorRemuneracao = findViewById<TextView>(R.id.remuneracaoEstagio)
        val textViewemailContato = findViewById<TextView>(R.id.email)
        val textViewtelefoneContato = findViewById<TextView>(R.id.telefoneContatoEmpresa)

        textViewAnunciante.text = anunciante
        textViewAreaVaga.text = areaVaga
        textViewdescricaoVaga.text = descricaoVaga
        textViewLocalidade.text = localidade
        textViewDataTermino.text = dataTermino
        textViewvalorRemuneracao.text = valorRemuneracao
        textViewemailContato.text = emailContato
        textViewtelefoneContato.text = telefoneContato

        val voltar = findViewById<MaterialButton>(R.id.voltarBotao)
        val enviarEmail = findViewById<MaterialButton>(R.id.enviarEmail)

        enviarEmail.setOnClickListener {
            EnviaEmail(areaVaga.toString(),emailContato.toString())
        }
            voltar.setOnClickListener {
                finish()
            }
        }

    fun EnviaEmail(areaVaga : String, emailContato : String){
        val subject = "Vaga de estágio $areaVaga"
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"

        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("$areaVaga"))

        intent.putExtra(Intent.EXTRA_SUBJECT, subject)

        intent.putExtra(Intent.EXTRA_TEXT, "Digite porque quer o estagio, mande seu curriculo.. :)")

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(intent, "Enviar e-mail"))
        } else {
            Toast.makeText(this, "Nenhum aplicativo de e-mail encontrado", Toast.LENGTH_SHORT)
                .show()
        }
    }


}
