package com.unaerp.trabalhoandroid.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.button.MaterialButton
import com.unaerp.trabalhoandroid.EditarPerfil
import com.unaerp.trabalhoandroid.EditarVaga
import com.unaerp.trabalhoandroid.MenuEstagiario
import com.unaerp.trabalhoandroid.R
import com.unaerp.trabalhoandroid.fragments_estagio.AnunciosFragment


class MinhasVagasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view = inflater.inflate(R.layout.fragment_minhas_vagas, container, false)

        val botaoEditarVagaEmpresa = view.findViewById<MaterialButton>(R.id.editarVagasEmpresa)

        botaoEditarVagaEmpresa.setOnClickListener {
            val intent = Intent(activity, EditarVaga::class.java)
            startActivity(intent)
        }






        return view





    }

}