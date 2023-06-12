package com.unaerp.trabalhoandroid

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.unaerp.trabalhoandroid.databinding.EditarPerfilBinding


private lateinit var auth: FirebaseAuth

class EditarPerfilActivity : AppCompatActivity() {
    private lateinit var binding: EditarPerfilBinding
    private lateinit var nome: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditarPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        //recuperar nome e email
        val dados = intent.extras
        if (dados != null) {
            nome = dados.getString("nome").toString()

            binding.inputNomeEditar.text = Editable.Factory.getInstance().newEditable(nome)

        } else {
            Aviso("Não foi possível editar perfil tente novamente")
            finish()
        }


        binding.botaoCancelar.setOnClickListener {
            finish()
        }

        binding.botaoEditar.setOnClickListener {

            if (binding.inputNomeEditar.text.isNullOrEmpty()) {
                closeKeyboard()
                Aviso("Preencha todos os campos")
            }else{
                EditarPerfil()
            }
        }

    }


    private fun EditarPerfil(){
        val db = FirestoreSingleton.getInstance()

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            db.collection("InformacoesPerfil")
                .document(userId)
                .update("Nome", binding.inputNomeEditar.text.toString()
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Usuario Editado com sucesso!!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Aviso("Não foi possivel cadastrar usuario")
                    }
                }


        }
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