package com.unaerp.trabalhoandroid.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.unaerp.trabalhoandroid.FirestoreSingleton
import com.unaerp.trabalhoandroid.databinding.FragmentAnunciarVagaBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AnunciarVagaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAnunciarVagaBinding.inflate(inflater, container, false)
        val view = binding.root

        //Calendario
        val calendar = Calendar.getInstance()
        val datePickerFinal = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                binding.dataVencimentoInput.setText(
                    SimpleDateFormat("dd/MM/yyyy").format(
                        selectedDate.time
                    )
                )
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        binding.dataVencimentoInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                datePickerFinal.show()
                binding.dataVencimentoInput.clearFocus()
            }
        }



        binding.submitBotao.setOnClickListener {
            closeKeyboard()
            CadastraAnuncio(binding)
        }

        return view
    }

    private fun CadastraAnuncio(binding: FragmentAnunciarVagaBinding) {
        val formatoData = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
        val dataHoraAtual = Date()
        val data = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).format(dataHoraAtual)
        val dataVencimento = formatoData.parse(binding.dataVencimentoInput.text.toString())
        val dataAgora = formatoData.parse(data)

        if (binding.nomeEmpresaInput.text.isNullOrEmpty() || binding.descricaoInput.text.isNullOrEmpty()
            || binding.areaDaVagaCadastroInput.text.isNullOrEmpty() || binding.valorRemuneracaoInput.text.isNullOrEmpty()
            || binding.localidadeAnuncioInput.text.isNullOrEmpty() || binding.emailContatoInput.text.isNullOrEmpty()
            || binding.telefoneContatoInput.text.isNullOrEmpty() || binding.dataVencimentoInput.text.isNullOrEmpty()
        ) {
            Aviso("Campos vazios, preencha!", binding)
        } else if (dataVencimento.before(dataAgora)) {
            Aviso("Data menor que o dia atual!", binding)
        } else {
            val currentUser = FirebaseAuth.getInstance().currentUser
            val db = FirestoreSingleton.getInstance()
            val anuncio = hashMapOf(
                "NomeEmpresa" to binding.nomeEmpresaInput.text.toString(),
                "Descricao" to binding.descricaoInput.text.toString(),
                "AreaDaVaga" to binding.areaDaVagaCadastroInput.text.toString(),
                "ValorRemuneracao" to binding.valorRemuneracaoInput.text.toString(),
                "Localidade" to binding.localidadeAnuncioInput.text.toString(),
                "EmailContato" to binding.emailContatoInput.text.toString(),
                "TelefoneContato" to binding.telefoneContatoInput.text.toString(),
                "DataVencimento" to binding.dataVencimentoInput.text.toString(),
                "DataPublicacao" to data,
                "IdEmpresa" to currentUser?.uid
            )
                db.collection("AnunciosEmpresas")
                    .add(anuncio)
                    .addOnSuccessListener {
                        Sucesso("Vaga postada com sucesso!!", binding)
                        LimpaInput(binding)
                    }
                    .addOnFailureListener {
                        Aviso("Não foi possível cadastrar usuário, verifique as informações", binding)
            }
        }
    }


    private fun Aviso(mensagem: String, binding: FragmentAnunciarVagaBinding) {
        val snackbar = Snackbar.make(binding.root, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.RED)
        snackbar.show()
    }

    private fun Sucesso(mensagem: String, binding: FragmentAnunciarVagaBinding) {
        val snackbar = Snackbar.make(binding.root, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.GREEN)
        snackbar.show()
    }

    private fun closeKeyboard() {
        val view = requireActivity().currentFocus
        view?.let {
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun LimpaInput(binding: FragmentAnunciarVagaBinding) {
        binding.nomeEmpresaInput.setText("")
        binding.descricaoInput.setText("")
        binding.areaDaVagaCadastroInput.setText("")
        binding.valorRemuneracaoInput.setText("")
        binding.localidadeAnuncioInput.setText("")
        binding.emailContatoInput.setText("")
        binding.telefoneContatoInput.setText("")
        binding.dataVencimentoInput.setText("")
    }

    fun criarMascaraData(): SimpleDateFormat {
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    }
}