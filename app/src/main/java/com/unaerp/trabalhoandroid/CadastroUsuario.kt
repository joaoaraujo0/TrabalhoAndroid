package com.unaerp.trabalhoandroid

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.unaerp.trabalhoandroid.databinding.CadastroBinding

class CadastroUsuario : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: CadastroBinding
    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.submitButtoncancelar.setOnClickListener {
            finish()
        }

        val spinner: Spinner = binding.spinerDefinirPerfil
        ArrayAdapter.createFromResource(
            this,
            R.array.spiner_edittext,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        binding.submitButton.setOnClickListener {
            if (binding.nomeCadastro.text.isNullOrEmpty() || binding.emailCadastro.text.isNullOrEmpty()) {
                val snackbar = Snackbar.make(it, "Campos vazios, preencha! ", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }else if (binding.textSenhaCadastro.text.toString() != binding.textConfirmaSenhaCadastro.text.toString()){
                val snackbar = Snackbar.make(it, "Senhas diferentes! ", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }
            else {
                cadastrarUsuario(binding.emailCadastro.text.toString(), binding.textSenhaCadastro.text.toString(), binding.nomeCadastro.text.toString(),it)
            }
        }
    }

    private fun cadastrarUsuario(email: String, senha: String, nome: String, view:View) {
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "createUserWithEmail:success")
                val user = auth.currentUser
                updateUI(user, nome)
                SalvarDados()
            }
        }.addOnFailureListener { exeption ->
            val mensagemErro = when (exeption) {
                is FirebaseAuthWeakPasswordException -> "Digite uma senha com no mínimo 6 caracteres!"
                is FirebaseAuthInvalidCredentialsException -> "Digite uma email válido!"
                is FirebaseAuthUserCollisionException -> "Email já cadastrado"
                is FirebaseNetworkException -> "Sem conexão com a internet!"
                else -> "Erro ao cadastrar usuario!"
            }
            val snackbar = Snackbar.make(view, mensagemErro, Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.show()
        }
    }
    private fun SalvarDados() {
        val nome = binding.nomeCadastro.text.toString()
        val spiner = binding.spinerDefinirPerfil.selectedItem.toString()

        val user = hashMapOf(
            "Nome" to nome,
            "Tipo do perfil" to spiner
        )

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userId = currentUser.uid

            db.collection("users")
                .document(userId)
                .set(user)
                .addOnSuccessListener {
                    // Armazenamento bem-sucedido
                    // ...
                }
                .addOnFailureListener { e ->
                    // Armazenamento com falha
                    // ...
                }


        }
    }

    private fun updateUI(user: FirebaseUser?, nome: String?) {
        if (user != null) {
            val intent = Intent(this, Menu::class.java)
            intent.putExtra("mensagem", "Bem vindo: $nome.")
            startActivity(intent)
            finish()
        }
    }

    private fun reload() {
        // Método reload
    }
}
