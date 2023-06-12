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
    private lateinit var binding:FragmentAnunciarVagaBinding
    private lateinit var dataVencimento: Date
    private val db = FirestoreSingleton.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnunciarVagaBinding.inflate(inflater, container, false)
        val view = binding.root

        //Calendario
        val calendar = Calendar.getInstance()
        val datePickerFinal = DatePickerDialog(
            requireContext(), { _, year, month, dayOfMonth ->
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
        val dataHoraAtual = Date()

        val formatoDataSemHora = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
        val formatoDataComHora = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale("pt", "BR")).format(dataHoraAtual)


        if (binding.dataVencimentoInput.text.isNullOrEmpty()) {
            Aviso("Campos vazios, preencha!")
            return
        } else {
            dataVencimento = formatoDataSemHora.parse(binding.dataVencimentoInput.text.toString())
        }

        val dataDeAgora = formatoDataSemHora.parse(formatoDataComHora)

        val formatoHora = SimpleDateFormat(" HH:mm:ss", Locale("pt", "BR"))
        val horaAtual = formatoHora.format(Date())

        if (binding.nomeEmpresaInput.text.isNullOrEmpty() || binding.descricaoInput.text.isNullOrEmpty()
            || binding.areaDaVagaCadastroInput.text.isNullOrEmpty() || binding.valorRemuneracaoInput.text.isNullOrEmpty()
            || binding.localidadeAnuncioInput.text.isNullOrEmpty() || binding.emailContatoInput.text.isNullOrEmpty()
            || binding.telefoneContatoInput.text.isNullOrEmpty()
        ) {
            Aviso("Campos vazios, preencha!")
        } else if (dataVencimento.before(dataDeAgora) || dataVencimento.equals(dataDeAgora) ) {
            Aviso("Data invalida!")
        } else {
            val currentUser = FirebaseAuth.getInstance().currentUser
            val anuncio = hashMapOf(
                "NomeEmpresa" to binding.nomeEmpresaInput.text.toString(),
                "Descricao" to binding.descricaoInput.text.toString(),
                "AreaDaVaga" to binding.areaDaVagaCadastroInput.text.toString(),
                "ValorRemuneracao" to binding.valorRemuneracaoInput.text.toString(),
                "Localidade" to binding.localidadeAnuncioInput.text.toString(),
                "EmailContato" to binding.emailContatoInput.text.toString(),
                "TelefoneContato" to binding.telefoneContatoInput.text.toString(),
                "DataVencimento" to "${binding.dataVencimentoInput.text.toString() + horaAtual} ",
                "DataPublicacao" to formatoDataComHora,
                "IdEmpresa" to currentUser?.uid
            )
            db.collection("AnunciosEmpresas")
                .add(anuncio)
                .addOnSuccessListener {
                    Sucesso("Vaga postada com sucesso!!")
                    LimpaInput()
                }
                .addOnFailureListener {
                    Aviso("Não foi possível cadastrar usuário, verifique as informações")
                }
        }
    }


    private fun Aviso(mensagem: String) {
        val snackbar = Snackbar.make(binding.root, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor("#ED2B2A"))
        snackbar.show()
    }

    private fun Sucesso(mensagem: String) {
        val snackbar = Snackbar.make(binding.root, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor("#03C988"))
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

    private fun LimpaInput() {
        binding.nomeEmpresaInput.setText("")
        binding.descricaoInput.setText("")
        binding.areaDaVagaCadastroInput.setText("")
        binding.valorRemuneracaoInput.setText("")
        binding.localidadeAnuncioInput.setText("")
        binding.emailContatoInput.setText("")
        binding.telefoneContatoInput.setText("")
        binding.dataVencimentoInput.setText("")
    }

}