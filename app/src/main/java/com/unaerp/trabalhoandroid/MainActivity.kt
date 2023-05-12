package com.unaerp.trabalhoandroid

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth

        val btnEntrar = findViewById<MaterialButton>(R.id.botaoEntrar)

        btnEntrar.setOnClickListener {
            val email = findViewById<TextInputEditText>(R.id.inputEmailLogin).text.toString()
            val senha = findViewById<TextInputEditText>(R.id.inputSenhaLogin).text.toString()
            if(email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(it,"Preencha os campos!!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }else{
                Login(email,senha)
            }


        }

        val textView = findViewById<MaterialButton>(R.id.botaoCadastrar)
        textView.setOnClickListener {
            val intentCadastro = Intent(this, CadastroUsuario::class.java)
            startActivity(intentCadastro)
        }


    }
    private fun Login(email:String,senha:String){
        auth.signInWithEmailAndPassword(email,senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCustomToken:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Ops, algo deu errado tente novamente.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    private fun updateUI(user: FirebaseUser?/*, nome: String?*/) {
        if (user != null) {
            val intent = Intent(this, Menu::class.java)
            intent.putExtra("mensagem", "Bem vindo: .")
            startActivity(intent)
        }
    }
    private fun reload() {
    }

}


