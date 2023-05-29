package com.unaerp.trabalhoandroid

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.ktx.Firebase
import com.unaerp.trabalhoandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private var tipo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        binding.botaoEntrar.setOnClickListener {
            closeKeyboard()
            if (binding.inputEmailLogin.text.isNullOrEmpty() || binding.inputSenhaLogin.text.isNullOrEmpty()) {
                Aviso("Preencha os campos!!",binding)
            } else {
                Login(binding.inputEmailLogin.text.toString(), binding.inputSenhaLogin.text.toString(), it)
            }
        }

        binding.botaoCadastrar.setOnClickListener {
            val intentCadastro = Intent(this, CadastroUsuario::class.java)
            startActivity(intentCadastro)
        }
    }

    private fun closeKeyboard() {
        val view = currentFocus
        view?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

        }
    }

    private fun Login(email: String, senha: String, view: View) {
        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithEmail:success")
                val user = auth.currentUser
                updateUI(user)
            }
        }.addOnFailureListener { exeption ->
            val mensagemErro = when (exeption) {
                is FirebaseAuthWeakPasswordException -> "Digite uma senha com no mínimo 6 caracteres!"
                is FirebaseNetworkException -> "Sem conexão com a internet!"
                else -> "Erro ao fazer login!"
            }
            Aviso(mensagemErro,binding)

        }
    }


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        val db = FirestoreSingleton.getInstance()
        //PEGAR TIPO DO PERFIL
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            // Recupere o documento do usuário no Firestore
            db.collection("InformacoesPerfil")
                .document(userId)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document: DocumentSnapshot? = task.result
                        tipo = document?.getString("Tipo do perfil")
                        VerificaoPerfil(tipo.toString())
                    } else {
                        Toast.makeText(
                            this,
                            "Não foi possível verificar seu perfil",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }


    }

    private fun VerificaoPerfil(tipo:String){
        if (tipo == "Empresa") {
            val intent = Intent(this, Menu::class.java)
            intent.putExtra("mensagem", "Bem vindo!")
            startActivity(intent)
            finish()
            binding.inputEmailLogin.setText("")
            binding.inputSenhaLogin.setText("")
        } else {
            val intent = Intent(this, MenuEstagiario::class.java)
            intent.putExtra("mensagem", "Bem vindo!")
            startActivity(intent)
            finish()
            binding.inputEmailLogin.setText("")
            binding.inputSenhaLogin.setText("")
        }
    }

    private fun LiparInput(){
        binding.inputEmailLogin.setText("")
        binding.inputSenhaLogin.setText("")
    }
    private fun Aviso(mensagem: String, binding: ActivityMainBinding){
        val snackbar = Snackbar.make(binding.root, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.RED)
        snackbar.show()
    }

}




