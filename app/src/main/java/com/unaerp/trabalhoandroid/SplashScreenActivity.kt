package com.unaerp.trabalhoandroid

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.ktx.Firebase
import com.unaerp.trabalhoandroid.databinding.SplashViewBinding


private lateinit var auth: FirebaseAuth
private lateinit var binding: SplashViewBinding
private var tipo: String? = null

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        Handler(Looper.getMainLooper()).postDelayed({
            if (auth.currentUser != null) {
                updateUI()
            } else {
                val intentCadastro = Intent(this, MainActivity::class.java)
                startActivity(intentCadastro)
                finish()
            }
        }, 2000)

    }
    private fun updateUI() {
        val db = FirestoreSingleton.getInstance()
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
                    Toast.makeText(
                        this,
                        "Não foi possível verificar seu perfil",
                        Toast.LENGTH_SHORT
                    ).show()
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


}


