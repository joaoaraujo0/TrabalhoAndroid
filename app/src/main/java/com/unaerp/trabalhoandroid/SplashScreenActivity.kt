package com.unaerp.trabalhoandroid

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.ktx.Firebase
import com.unaerp.trabalhoandroid.databinding.ActivitySplashViewBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


private lateinit var auth: FirebaseAuth
private lateinit var binding: ActivitySplashViewBinding
private var tipo: String? = null

class SplashScreenActivity : AppCompatActivity() {
    private val db = FirestoreSingleton.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        VerificaVencimentoPublicacao()

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

    private fun VerificaVencimentoPublicacao() {
        val dataAgora = Date()

        val formatoDataComHora = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale("pt", "BR")).format(dataAgora)
        val formatoData = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale("pt", "BR"))

        db.collection("AnunciosEmpresas")
            .get()
            .addOnSuccessListener {result ->
                for (document in result){
                    val dataVencimento = document.getString("DataVencimento")

                    val dataVencimentoDate = dataVencimento?.let { formatoData.parse(it) }

                    val dataDeAgora = formatoData.parse(formatoDataComHora)

                    if (dataDeAgora != null) {
                        if(dataDeAgora.after(dataVencimentoDate)){
                            db.collection("AnunciosEmpresas")
                                .document(document.id)
                                .delete()

                        }
                    }



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

    private fun Aviso(mensagem: String) {
        val snackbar = Snackbar.make(binding.root, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor("#ED2B2A"))
        snackbar.show()
    }
}


