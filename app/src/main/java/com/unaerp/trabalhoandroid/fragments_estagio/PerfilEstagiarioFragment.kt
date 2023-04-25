package com.unaerp.trabalhoandroid.fragments_estagio

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.button.MaterialButton
import com.unaerp.trabalhoandroid.EditarPerfil
import com.unaerp.trabalhoandroid.MainActivity
import com.unaerp.trabalhoandroid.R

class PerfilEstagiarioFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil_estagiario, container, false)

        val botaoSair = view.findViewById<MaterialButton>(R.id.sairBotao)
        val editarEstagiario = view.findViewById<MaterialButton>(R.id.editarEstagiario)



        botaoSair.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        editarEstagiario.setOnClickListener {
            val intent = Intent(activity, EditarPerfil::class.java)
            startActivity(intent)
        }


        return view
    }

}