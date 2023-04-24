package com.unaerp.trabalhoandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unaerp.trabalhoandroid.Adapter.AdapterVaga
import com.unaerp.trabalhoandroid.R
import com.unaerp.trabalhoandroid.model.Vagas

class PainelVagas : Fragment() { // Adicione a herança de Fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_painel_vagas, container, false)

        val recyclerViewVagas = view.findViewById<RecyclerView>(R.id.recyclerViewVagas)
        recyclerViewVagas.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewVagas.setHasFixedSize(true)

        //Configurar adapter
        val listaVagas : MutableList<Vagas> = mutableListOf()
        val adapterVaga = AdapterVaga(requireContext(), listaVagas)
        recyclerViewVagas.adapter = adapterVaga


        val vaga1 = Vagas(
            "Microsoft",
            "Ti",
            "São Paulo",
            "11/05/2023"
        )
        listaVagas.add(vaga1)

        val vaga2 = Vagas(
            "Naiu",
            "Biologia",
            "São Paulo",
            "05/05/2023"
        )
        listaVagas.add(vaga2)

        return view
    }
}
