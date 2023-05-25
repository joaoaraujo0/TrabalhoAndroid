package com.unaerp.trabalhoandroid.fragments

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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.unaerp.trabalhoandroid.EditarPerfil
import com.unaerp.trabalhoandroid.R
import com.unaerp.trabalhoandroid.databinding.FragmentPerfilUserBinding

private var userBitmap:Bitmap? = null
class PerfilEmpresaFragment : Fragment() {
    private var imgPicture: ImageView? = null
    private var botaoTirarFoto: Button? = null
    private var db = FirebaseFirestore.getInstance()


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
        val binding = FragmentPerfilUserBinding.inflate(inflater, container, false)
        val view = binding.root

        PegarDadoUsuario(binding)

        binding.sairBotao.setOnClickListener {
            Firebase.auth.signOut()
            requireActivity().finish()
        }

        binding.editarPerfilEmpresa.setOnClickListener {
            val intent = Intent(activity, EditarPerfil::class.java)
            startActivity(intent)
        }

        imgPicture = binding.imagemPerfilEmpresa
        userBitmap?.let { imgPicture?.setImageBitmap(it) }
        botaoTirarFoto = binding.botaoTirarFotoEmpresa

        botaoTirarFoto?.setOnClickListener {
                if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    val intentOpenCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    cameraLauncher.launch(intentOpenCamera)
                } else {
                    permissionLauncher.launch(android.Manifest.permission.CAMERA)
                }
            }



        return view
    }
    private fun PegarDadoUsuario(binding:FragmentPerfilUserBinding){
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            // Recupere o documento do usuário no Firestore
            db.collection("users")
                .document(userId)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document: DocumentSnapshot? = task.result
                        if (document != null && document.exists()) {
                            // O documento existe, você pode acessar os dados
                            val nome = document.getString("Nome")
                            val tipoDoPerfil = document.getString("Tipo do perfil")
                            val nomePerfilText = getString(R.string.nomePerfil, nome)
                            val tipoDoPerfilText = getString(R.string.emailPerfil, tipoDoPerfil)
                            binding.nomePerfil.text = nomePerfilText
                            binding.emailPerfil.text = tipoDoPerfilText






                        }
                    }
                }
        }

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

}



