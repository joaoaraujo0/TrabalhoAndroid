package com.unaerp.trabalhoandroid.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
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
        val dateFinalEditText = binding.dataVencimentoInput

        val builder = MaterialDatePicker.Builder.datePicker()
        val datePickerFinal = builder.build()


        datePickerFinal.addOnPositiveButtonClickListener(MaterialPickerOnPositiveButtonClickListener { selection ->
            val selectedDate = Calendar.getInstance()
            selectedDate.timeInMillis = selection
            dateFinalEditText.setText(
                SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).format(selectedDate.time)
            )
        })


        dateFinalEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                datePickerFinal.show(requireActivity().supportFragmentManager, "DatePickerFinal")
                dateFinalEditText.clearFocus()
            }
        }



        binding.submitBotao.setOnClickListener {
            closeKeyboard()
            CadastraAnuncio(binding)
        }

        return view
    }

    private fun CadastraAnuncio(binding: FragmentAnunciarVagaBinding) {
        if (binding.nomeEmpresaInput.text.isNullOrEmpty() || binding.descricaoInput.text.isNullOrEmpty()
            || binding.areaDaVagaCadastroInput.text.isNullOrEmpty() || binding.valorRemuneracaoInput.text.isNullOrEmpty()
            || binding.localidadeAnuncioInput.text.isNullOrEmpty() || binding.emailContatoInput.text.isNullOrEmpty()
            || binding.telefoneContatoInput.text.isNullOrEmpty() || binding.dataVencimentoInput.text.isNullOrEmpty())
        {
            Aviso("Campos vazios, preencha!",binding)
        }else{
            val dataHoraAtual = Date()
            val data = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).format(dataHoraAtual)
            val currentUser = FirebaseAuth.getInstance().currentUser
            val db = FirestoreSingleton.getInstance()
            val Anuncio = hashMapOf(
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
            if (currentUser != null) {
                db.collection("AnunciosEmpresas")
                    .add(Anuncio)
                    .addOnSuccessListener {
                        Sucesso("Vaga postada com sucesso!!", binding)
                        LimpaInput(binding)
                    }
                    .addOnFailureListener {
                        Aviso("Não foi possivel cadastrar usuario, verifique as informações",binding)
                    }
            }
        }
    }

    private fun Aviso(mensagem: String, binding: FragmentAnunciarVagaBinding){
        val snackbar = Snackbar.make(binding.root, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.RED)
        snackbar.show()
    }
    private fun Sucesso(mensagem: String, binding: FragmentAnunciarVagaBinding){
        val snackbar = Snackbar.make(binding.root, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.GREEN)
        snackbar.show()
    }
    private fun closeKeyboard() {
        val view = requireActivity().currentFocus
        view?.let {
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
    private fun LimpaInput(binding: FragmentAnunciarVagaBinding){
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