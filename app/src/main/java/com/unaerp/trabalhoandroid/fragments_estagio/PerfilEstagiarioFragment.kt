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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.unaerp.trabalhoandroid.EditarPerfil
import com.unaerp.trabalhoandroid.databinding.FragmentPerfilEstagiarioBinding

private var userBitmap:Bitmap? = null
class PerfilEstagiarioFragment : Fragment() {

    private var imgPicture: ImageView? = null
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


        binding.sairBotao.setOnClickListener {
            Firebase.auth.signOut()
            requireActivity().finish()
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

}