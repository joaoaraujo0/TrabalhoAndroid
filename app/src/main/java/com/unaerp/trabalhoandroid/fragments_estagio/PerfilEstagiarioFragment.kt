package com.unaerp.trabalhoandroid.fragments_estagio

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.ktx.Firebase
import com.unaerp.trabalhoandroid.EditarPerfil
import com.unaerp.trabalhoandroid.FirestoreSingleton
import com.unaerp.trabalhoandroid.MainActivity
import com.unaerp.trabalhoandroid.R
import com.unaerp.trabalhoandroid.databinding.FragmentPerfilEstagiarioBinding

private var userBitmap:Bitmap? = null
class PerfilEstagiarioFragment : Fragment() {
    private var imgPicture: ImageView? = null
    private var dadosCarregados = false
    private var nome: String? = null
    private var email: String? = null
    private var btnTakePicture: Button? = null

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
        }else {
            Toast.makeText(requireContext(), "Permissão necessária", Toast.LENGTH_LONG).show()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPerfilEstagiarioBinding.inflate(inflater, container, false)
        val view = binding.root


        if (!dadosCarregados) {
            PegarDadoUsuario(binding)
        } else {
            val nomePerfilText = getString(R.string.nomePerfil, nome)
            val tipoDoPerfilText = getString(R.string.emailPerfil, email)

            binding.nomeEstagiario.text = nomePerfilText
            binding.emailEstagiario.text = tipoDoPerfilText
        }

        binding.sairBotao.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(requireContext(), MainActivity ::class.java)
            startActivity(intent)
        }

        binding.editarEstagiario.setOnClickListener {
            val intent = Intent(activity, EditarPerfil::class.java)
            startActivity(intent)
        }

        imgPicture = binding.imagemPerfilEstagiario
        userBitmap?.let { imgPicture?.setImageBitmap(it) }
        btnTakePicture =binding.botaoTirarFoto

        btnTakePicture?.setOnClickListener {
            if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val intentOpenCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraLauncher.launch(intentOpenCamera)
            } else {
                permissionLauncher.launch(android.Manifest.permission.CAMERA)
            }
        }

        return view
    }

    private fun PegarDadoUsuario(binding: FragmentPerfilEstagiarioBinding) {
        val db = FirestoreSingleton.getInstance()
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

                        nome = document?.getString("Nome")
                        email = document?.getString("Email")

                        val nomePerfilText = getString(R.string.nomePerfil, nome)
                        val tipoDoPerfilText = getString(R.string.emailPerfil, email)

                        binding.nomeEstagiario.text = nomePerfilText
                        binding.emailEstagiario.text = tipoDoPerfilText
                        dadosCarregados = true
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Não foi possível verificar seu perfil",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

}