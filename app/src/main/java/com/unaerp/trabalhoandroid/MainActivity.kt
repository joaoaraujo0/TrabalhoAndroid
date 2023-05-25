package com.unaerp.trabalhoandroid

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.unaerp.trabalhoandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        binding.botaoEntrar.setOnClickListener {
            closeKeyboard()
            if (binding.inputEmailLogin.text.isNullOrEmpty() || binding.inputSenhaLogin.text.isNullOrEmpty()) {
                val snackbar = Snackbar.make(it, "Preencha os campos!!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
                return@setOnClickListener
            } else {
                Login(binding.inputEmailLogin.text.toString(),binding.inputSenhaLogin.text.toString(),it)
                /*val intent = Intent(this, Menu::class.java)
                startActivity(intent)*/
            }
        }

        binding.botaoCadastrar.setOnClickListener {
            val intentCadastro = Intent(this, CadastroUsuario::class.java)
            startActivity(intentCadastro)
        }
    }

    private fun closeKeyboard(){
        val view = currentFocus
        view?.let{
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

        }
    }
    private fun Login(email: String, senha: String,view: View) {
        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                }
            }.addOnFailureListener {exeption ->
            val mensagemErro = when (exeption) {
                is FirebaseAuthWeakPasswordException -> "Digite uma senha com no mínimo 6 caracteres!"
                is FirebaseAuthInvalidCredentialsException -> "Digite uma email válido!"
                is FirebaseNetworkException -> "Sem conexão com a internet!"
                else -> "Erro ao fazer login!"
            }
            val snackbar = Snackbar.make(view, mensagemErro, Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.show()

        }
    }


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, Menu::class.java)
            intent.putExtra("mensagem", "Bem vindo!")
            startActivity(intent)
            binding.inputEmailLogin.setText("")
            binding.inputSenhaLogin.setText("")
        }
    }

    private fun reload() {
    }

}


