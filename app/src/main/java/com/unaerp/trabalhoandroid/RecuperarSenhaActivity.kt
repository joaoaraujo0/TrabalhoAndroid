package com.unaerp.trabalhoandroid

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.unaerp.trabalhoandroid.databinding.ActivityRecuperarSenhaBinding

private lateinit var auth: FirebaseAuth
private lateinit var binding: ActivityRecuperarSenhaBinding

class RecuperarSenhaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecuperarSenhaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.botaoVoltarRecuperar.setOnClickListener {
            finish()
        }

        binding.botaoEnviar.setOnClickListener {
            if (binding.inputEmailLogin.text.isNullOrEmpty()) {
                Aviso("Preencha os campos!!", binding)
            } else {
                auth.sendPasswordResetEmail(binding.inputEmailLogin.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "E-mail enviado com sucesso, verifique sua caixa de entrada!!",
                                Toast.LENGTH_SHORT
                            ).show()
                            closeKeyboard()
                            finish()
                        } else {
                            Aviso("Erro ao enviar e-mail, tente novamente.", binding)
                            closeKeyboard()
                        }

                    }


            }

        }


    }

    private fun Aviso(mensagem: String, binding: ActivityRecuperarSenhaBinding) {
        val snackbar = Snackbar.make(binding.root, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.RED)
        snackbar.show()
    }

    private fun closeKeyboard() {
        val view = currentFocus
        view?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

        }
    }
}