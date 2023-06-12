package com.unaerp.trabalhoandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.unaerp.trabalhoandroid.databinding.ActivityPainelDeVagasBinding

class VagaExpandida : AppCompatActivity() {
    private lateinit var binding: ActivityPainelDeVagasBinding
    private lateinit var idVaga: String
    private val db = FirestoreSingleton.getInstance()
    private lateinit var email: String
    private lateinit var areaDaVaga: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPainelDeVagasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idVaga = intent.getStringExtra("idDaVaga").toString()

        PegarDados(idVaga)


        binding.enviarEmail.setOnClickListener {
            EnviaEmail(areaDaVaga, email)
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
    private fun PegarDados(idVaga : String){
        db.collection("AnunciosEmpresas")
            .document(idVaga)
            .get()
            .addOnSuccessListener  { result ->
                val areaVaga = result.getString("AreaDaVaga").toString()
                val descricaoVaga = result.getString("Descricao").toString()
                val valorRemuneracao = result.getString("ValorRemuneracao").toString()
                val localidade = result.getString("Localidade").toString()
                val emailContato = result.getString("EmailContato").toString()
                val telefoneContato = result.getString("TelefoneContato").toString()
                val anunciante = result.getString("NomeEmpresa").toString()
                val dataTermino = result.getString("DataVencimento").toString()

                binding.anunciante.text = getString(R.string.anunciante, anunciante)
                binding.areaVaga.text = getString(R.string.areaVaga, areaVaga)
                binding.descricaoVaga.text = getString(R.string.descricao, descricaoVaga)
                binding.localidadeVaga.text = getString(R.string.text_localidade, localidade)
                binding.dataTerminoDaVaga.text = getString(R.string.dataTermino, dataTermino)
                binding.remuneracaoEstagio.text = getString(R.string.valorRemuneracao, valorRemuneracao)
                binding.email.text = getString(R.string.emailContato, emailContato)
                binding.telefoneContatoEmpresa.text = getString(R.string.telefoneContato, telefoneContato)

                email = emailContato
                areaDaVaga = areaVaga
            }
    }

}
