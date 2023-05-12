package com.unaerp.trabalhoandroid

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CadastroUsuario : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastro)

        auth = Firebase.auth
        val submit_buttoncancelar = findViewById<MaterialButton>(R.id.submit_buttoncancelar)
        val submit_button = findViewById<MaterialButton>(R.id.submit_button)

        submit_buttoncancelar.setOnClickListener {
            finish()
        }

        val spinner: Spinner = findViewById(R.id.spinerDefinirPerfil)
        ArrayAdapter.createFromResource(
            this,
            R.array.spiner_edittext,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        submit_button.setOnClickListener {
            val nome = findViewById<TextInputEditText>(R.id.nomeCadastro).text.toString()
            val email = findViewById<TextInputEditText>(R.id.emailCadastro).text.toString()
            val definirPerfil = findViewById<Spinner>(R.id.spinerDefinirPerfil).selectedItem.toString()
            val senha = findViewById<TextInputEditText>(R.id.textSenhaCadastro).text.toString()
            val confirmaSenha = findViewById<TextInputEditText>(R.id.textConfirmaSenhaCadastro).text.toString()



            if(nome.isEmpty() || email.isEmpty()) {
                val snackbar = Snackbar.make(it, "Campos vazios, preencha! ", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }
            else if(VerficaSenha(senha,confirmaSenha)){
                val snackbar = Snackbar.make(it,"Senha Incorreta, tente novamente ",Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            } else{
                CadastraUsuario(email,senha,nome)
            }
        }
    }

    private fun VerficaSenha(senha: String,confirmaSenha:String):Boolean{
        return senha != confirmaSenha || senha.isEmpty() || confirmaSenha.isEmpty() || senha.length < 6
    }
    private fun CadastraUsuario(email:String, senha:String,nome:String){
        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user,nome)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Erro de cadastro.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null,null)
                }
            }
    }
    private fun updateUI(user: FirebaseUser?,nome: String?) {
            if (user != null) {
                val intent = Intent(this, Menu::class.java)
                intent.putExtra("mensagem", "Bem vindo: ${nome}.")
                startActivity(intent)
                finish()
            }
        }
    private fun reload() {
    }

    }



