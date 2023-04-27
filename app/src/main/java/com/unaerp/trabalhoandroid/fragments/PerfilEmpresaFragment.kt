package com.unaerp.trabalhoandroid.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.unaerp.trabalhoandroid.EditarPerfil
import com.unaerp.trabalhoandroid.MainActivity
import com.unaerp.trabalhoandroid.R
import java.io.File
import java.io.FileOutputStream

private var userBitmap:Bitmap? = null
class PerfilEmpresaFragment : Fragment() {
    private var imgPicture: ImageView? = null
    private var botaoTirarFoto: Button? = null

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
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil_user, container, false)

        val botaoSair = view.findViewById<MaterialButton>(R.id.sairBotao)
        val editarPerfilEmpresa = view.findViewById<MaterialButton>(R.id.editarPerfilEmpresa)

        botaoSair.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        editarPerfilEmpresa.setOnClickListener {
            val intent = Intent(activity, EditarPerfil::class.java)
            startActivity(intent)
        }

        imgPicture = view.findViewById(R.id.imagemPerfilEmpresa)
        userBitmap?.let { imgPicture?.setImageBitmap(it) }
        botaoTirarFoto = view.findViewById(R.id.botaoTirarFotoEmpresa)

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
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }





}



