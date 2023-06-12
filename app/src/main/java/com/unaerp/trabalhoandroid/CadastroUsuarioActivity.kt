package com.unaerp.trabalhoandroid

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.ktx.Firebase
import com.unaerp.trabalhoandroid.databinding.CadastroBinding
private var tipo: String? = null
class CadastroUsuarioActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: CadastroBinding
    private val db = FirestoreSingleton.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.submitButtoncancelar.setOnClickListener {
            closeKeyboard()
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
            closeKeyboard()
            if (binding.nomeCadastro.text.isNullOrEmpty() || binding.emailCadastro.text.isNullOrEmpty()
                ||binding.textSenhaCadastro.text.isNullOrEmpty() || binding.textConfirmaSenhaCadastro.text.isNullOrEmpty()
            ) {
                Aviso("Campos vazios, preencha!")
            }else if (binding.textSenhaCadastro.text.toString() != binding.textConfirmaSenhaCadastro.text.toString()){
                Aviso("Senhas diferentes! ")
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
                updateUI()
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
            Aviso(mensagemErro)

        }
    }
    private fun SalvarDados() {
        val user = hashMapOf(
            "Nome" to binding.nomeCadastro.text.toString(),
            "TipodoPerfil" to binding.spinerDefinirPerfil.selectedItem.toString(),
        )

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userId = currentUser.uid

            db.collection("InformacoesPerfil")
                .document(userId)
                .set(user)
                .addOnSuccessListener {

                    // Armazenamento bem-sucedido
                    // ...
                }
                .addOnFailureListener { e ->
                     Aviso("Não foi possivel cadastrar usuario")
                }

        }
    }

    private fun updateUI() {
        //PEGAR TIPO DO PERFIL
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid
        // Recupere o documento do usuário no Firestore
        db.collection("InformacoesPerfil")
            .document(userId.toString())
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document: DocumentSnapshot? = task.result
                    tipo = document?.getString("TipodoPerfil")
                    VerificaoPerfil(tipo.toString())
                } else {
                   Aviso("Não foi possível verificar seu perfil")
                }
            }
    }



    private fun VerificaoPerfil(tipo: String) {
        if (tipo == "Empresa") {
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("mensagem", "Bem vindo!")
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, MenuEstagiario::class.java)
            intent.putExtra("mensagem", "Bem vindo!")
            startActivity(intent)
            finish()
        }
    }
    private fun closeKeyboard(){
        val view = currentFocus
        view?.let{
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

        }
    }

    private fun Aviso(mensagem: String){
        val snackbar = Snackbar.make(binding.root, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.RED)
        snackbar.show()
    }
}
