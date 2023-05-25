package com.unaerp.trabalhoandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.unaerp.trabalhoandroid.databinding.PainelDeVagasBinding

class VagaExpandida : AppCompatActivity() {
    private lateinit var binding: PainelDeVagasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PainelDeVagasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val areaVaga = intent.getStringExtra("areaVaga")
        val descricaoVaga = intent.getStringExtra("descricaoVaga")
        val valorRemuneracao = intent.getStringExtra("valorRemuneracao")
        val localidade = intent.getStringExtra("localidade")
        val emailContato = intent.getStringExtra("emailContato")
        val telefoneContato = intent.getStringExtra("telefoneContato")
        val anunciante = intent.getStringExtra("anunciante")
        val dataInicioVaga = intent.getStringExtra("dataInicioVaga")
        val dataTermino = intent.getStringExtra("dataTermino")

        binding.anunciante.text = getString(R.string.anunciante, anunciante)
        binding.areaVaga.text = getString(R.string.areaVaga, areaVaga)
        binding.descricaoVaga.text = getString(R.string.descricao, descricaoVaga)
        binding.localidadeVaga.text = getString(R.string.text_localidade, localidade)
        binding.dataTerminoDaVaga.text = getString(R.string.dataTermino, dataTermino)
        binding.remuneracaoEstagio.text = getString(R.string.valorRemuneracao, valorRemuneracao)
        binding.email.text = getString(R.string.emailContato, emailContato)
        binding.telefoneContatoEmpresa.text = getString(R.string.telefoneContato, telefoneContato)

        binding.enviarEmail.setOnClickListener {
            EnviaEmail(areaVaga.toString(), emailContato.toString())
        }

        binding.voltarBotao.setOnClickListener {
            finish()
        }
    }

    private fun EnviaEmail(areaVaga: String, emailContato: String) {
        val subject = "Vaga de estágio $areaVaga"
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"

        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailContato))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, "Digite porque quer o estágio, mande seu currículo.. :)")

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(intent, "Enviar e-mail"))
        } else {
            Toast.makeText(this, "Nenhum aplicativo de e-mail encontrado", Toast.LENGTH_SHORT).show()
        }
    }
}
