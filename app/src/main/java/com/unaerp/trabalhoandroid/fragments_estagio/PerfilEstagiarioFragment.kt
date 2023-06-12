package com.unaerp.trabalhoandroid.fragments_estagio

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.unaerp.trabalhoandroid.EditarPerfilActivity
import com.unaerp.trabalhoandroid.FirestoreSingleton
import com.unaerp.trabalhoandroid.MainActivity
import com.unaerp.trabalhoandroid.R
import com.unaerp.trabalhoandroid.databinding.FragmentPerfilEstagiarioBinding

private var userBitmap: Bitmap? = null
private lateinit var auth: FirebaseAuth
private var nome: String? = null

class PerfilEstagiarioFragment : Fragment() {
    private var imgPicture: ImageView? = null
    private var btnTakePicture: Button? = null
    private lateinit var binding: FragmentPerfilEstagiarioBinding
    private val db = FirestoreSingleton.getInstance()

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        val bitmap: Bitmap? = it.data?.getParcelableExtra("data")
        userBitmap = bitmap
        imgPicture?.setImageBitmap(bitmap)

    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it == true) {
            val intentOpenCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(intentOpenCamera)
        } else {
            Toast.makeText(requireContext(), "Permissão necessária", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = Firebase.auth
        binding = FragmentPerfilEstagiarioBinding.inflate(inflater, container, false)
        val view = binding.root

        PegarDadoUsuario(binding)


        binding.sairBotao.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        binding.editarEstagiario.setOnClickListener {
            val intent = Intent(activity, EditarPerfilActivity::class.java)
            intent.putExtra("nome", nome);
            startActivity(intent)
        }

        imgPicture = binding.imagemPerfilEstagiario
        userBitmap?.let { imgPicture?.setImageBitmap(it) }
        btnTakePicture = binding.botaoTirarFoto

        btnTakePicture?.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val intentOpenCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraLauncher.launch(intentOpenCamera)
            } else {
                permissionLauncher.launch(android.Manifest.permission.CAMERA)
            }
        }

        return view
    }

    private fun PegarDadoUsuario(binding: FragmentPerfilEstagiarioBinding) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid
        db.collection("InformacoesPerfil").document(userId.toString())
            .addSnapshotListener { value, error ->
                if (value != null) {
                    nome = value.getString("Nome")

                    val nomePerfilText = getString(R.string.nome_estagiario, nome)
                    val emailText = getString(R.string.email_estagiario, auth.currentUser?.email)

                    binding.nomeEstagiario.text = nomePerfilText
                    binding.emailEstagiario.text = emailText
                } else {
                    Aviso("Não foi possível verificar seu perfil")
                }

            }

    }
    private fun Aviso(mensagem: String) {
        val snackbar = Snackbar.make(binding.root, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor("#ED2B2A"))
        snackbar.show()
    }
}