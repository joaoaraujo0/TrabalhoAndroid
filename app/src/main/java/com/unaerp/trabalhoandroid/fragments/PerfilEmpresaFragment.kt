package com.unaerp.trabalhoandroid.fragments

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
import com.unaerp.trabalhoandroid.databinding.FragmentPerfilUserBinding

private var userBitmap: Bitmap? = null
private lateinit var auth: FirebaseAuth
private var nome: String? = null

class PerfilEmpresaFragment : Fragment() {
    private var imgPicture: ImageView? = null
    private var botaoTirarFoto: Button? = null
    private lateinit var binding: FragmentPerfilUserBinding
    private val db = FirestoreSingleton.getInstance()

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        val bitmap: Bitmap? = it.data?.getParcelableExtra("data")
        userBitmap = bitmap
        imgPicture?.setImageBitmap(bitmap)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = Firebase.auth
        binding = FragmentPerfilUserBinding.inflate(inflater, container, false)
        val view = binding.root

        PegarDadoUsuario()

        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it == true) {
                val intentOpenCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraLauncher.launch(intentOpenCamera)
            } else {
                Toast.makeText(requireContext(), "Permissão necessária", Toast.LENGTH_LONG).show()
            }
        }


        binding.sairBotao.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        binding.editarPerfilEmpresa.setOnClickListener {
            val intent = Intent(activity, EditarPerfilActivity::class.java)
            intent.putExtra("nome", nome);
            startActivity(intent)
        }

        imgPicture = binding.imagemPerfilEmpresa
        userBitmap?.let { imgPicture?.setImageBitmap(it) }
        botaoTirarFoto = binding.botaoTirarFotoEmpresa

        botaoTirarFoto?.setOnClickListener {
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

    private fun PegarDadoUsuario() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid
        db.collection("InformacoesPerfil").document(userId.toString())
            .addSnapshotListener { value, error ->
                if (value != null) {
                    nome = value.getString("Nome")

                    val nomePerfilText = getString(R.string.nomePerfil, nome)
                    val emailText = getString(R.string.emailPerfil, auth.currentUser?.email)

                    binding.nomePerfil.text = nomePerfilText
                    binding.emailPerfil.text = emailText
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


    override fun onResume() {
        super.onResume()
        PegarDadoUsuario()
    }

}