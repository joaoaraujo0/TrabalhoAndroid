package com.unaerp.trabalhoandroid

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.unaerp.trabalhoandroid.Adapter.AdapterMinhasVagas
import com.unaerp.trabalhoandroid.databinding.EditarVagaBinding

class EditarVagaActivity : AppCompatActivity() {
    private lateinit var adapterMinhasVaga: AdapterMinhasVagas

    private lateinit var binding: EditarVagaBinding
    private lateinit var nomeEmpresa: String
    private lateinit var idAnuncio: String
    private lateinit var descricao: String
    private lateinit var areaDaVaga: String
    private lateinit var valorRemuneracao: String
    private lateinit var localidade: String
    private lateinit var emailContato: String
    private lateinit var telefoneContato: String

    private val db = FirestoreSingleton.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditarVagaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dados = intent.extras

        if (dados != null) {
            idAnuncio = dados.getString("idAnuncio").toString()
        }

        PegarInformacoesDoAnuncio(idAnuncio)

        //Calendario
        /*val calendar = Calendar.getInstance()
        val datePickerFinal = DatePickerDialog(
            this, { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                binding.dataVencimentoInputEditar.setText(
                    SimpleDateFormat("dd/MM/yyyy").format(
                        selectedDate.time
                    )
                )
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        binding.dataVencimentoInputEditar.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                datePickerFinal.show()
                binding.dataVencimentoInputEditar.clearFocus()
            }
        }*/

        binding.botaoVoltarEditarVaga.setOnClickListener {
            finish()
        }

        binding.botaoEditarVagaEmpresa.setOnClickListener {
            if (binding.nomeEmpresaInputEditar.text.isNullOrEmpty() || binding.descricaoInputEditar.text.isNullOrEmpty() ||
                binding.areaDaVagaCadastroInputEditar.text.isNullOrEmpty() || binding.valorRemuneracaoInputEditar.text.isNullOrEmpty() ||
                binding.localidadeAnuncioInputEditar.text.isNullOrEmpty() || binding.emailContatoInputEditar.text.isNullOrEmpty() ||
                binding.telefoneContatoInputEditar.text.isNullOrEmpty()
            ) {
                closeKeyboard()
                Aviso("Preencha todos os campos!!")
            } else {
                EditarDados(idAnuncio)
            }
        }
    }


    private fun EditarDados(idAnuncio:String) {
            db.collection("AnunciosEmpresas")
                .document(idAnuncio)
                .update("NomeEmpresa", binding.nomeEmpresaInputEditar.text.toString(),
                    "Descricao", binding.descricaoInputEditar.text.toString(),
                    "AreaDaVaga", binding.areaDaVagaCadastroInputEditar.text.toString(),
                    "ValorRemuneracao", binding.valorRemuneracaoInputEditar.text.toString(),
                    "Localidade", binding.localidadeAnuncioInputEditar.text.toString(),
                    "EmailContato", binding.emailContatoInputEditar.text.toString(),
                    "TelefoneContato", binding.telefoneContatoInputEditar.text.toString()
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Sucesso("Anuncio editado com sucesso!!")
                        finish()
                    } else {
                        Aviso("Erro ao editar anuncio, tente mais tarde!")
                        finish()
                    }


        }
    }

    private fun PegarInformacoesDoAnuncio(idAnuncio: String) {
        db.collection("AnunciosEmpresas")
            .document(idAnuncio)
            .get().addOnSuccessListener { document ->
                if (document != null) {
                    nomeEmpresa = document.getString("NomeEmpresa").toString()
                    descricao = document.getString("Descricao").toString()
                    areaDaVaga = document.getString("AreaDaVaga").toString()
                    valorRemuneracao = document.getString("ValorRemuneracao").toString()
                    localidade = document.getString("Localidade").toString()
                    emailContato = document.getString("EmailContato").toString()
                    telefoneContato = document.getString("TelefoneContato").toString()


                    binding.nomeEmpresaInputEditar.text = Editable.Factory.getInstance().newEditable(nomeEmpresa)
                    binding.descricaoInputEditar.text = Editable.Factory.getInstance().newEditable(descricao)
                    binding.areaDaVagaCadastroInputEditar.text = Editable.Factory.getInstance().newEditable(areaDaVaga)
                    binding.valorRemuneracaoInputEditar.text = Editable.Factory.getInstance().newEditable(valorRemuneracao)
                    binding.localidadeAnuncioInputEditar.text = Editable.Factory.getInstance().newEditable(localidade)
                    binding.emailContatoInputEditar.text = Editable.Factory.getInstance().newEditable(emailContato)
                    binding.telefoneContatoInputEditar.text = Editable.Factory.getInstance().newEditable(telefoneContato)

                }
            }

    }


    private fun Sucesso(mensagem: String) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
    }

    private fun closeKeyboard() {
        val view = currentFocus
        view?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

        }
    }

    private fun Aviso(mensagem: String) {
        val snackbar = Snackbar.make(binding.root, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.RED)
        snackbar.show()
    }
}