package com.unaerp.trabalhoandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unaerp.trabalhoandroid.Adapter.AdapterVaga
import com.unaerp.trabalhoandroid.R
import com.unaerp.trabalhoandroid.model.Vagas

class PainelVagasFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_painel_vagas, container, false)
        val searchView = view.findViewById<SearchView>(R.id.searchView)


        val recyclerViewVagas = view.findViewById<RecyclerView>(R.id.recyclerViewVagas)
        recyclerViewVagas.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewVagas.setHasFixedSize(true)

        //Configurar adapter
        val listaVagas : MutableList<Vagas> = mutableListOf()
        val adapterVaga = AdapterVaga(requireContext(), listaVagas)
        recyclerViewVagas.adapter = adapterVaga


        val vaga1 = Vagas(
            "Ti",
            "Para esta vaga buscamos gente com experiencia em kotlin",
            "2500.00",
            "São Paulo",
            "joao@gmail.com",
            "158877665544",
            "Microsoft",
            "15/05/2010",
            "25/06/2022",
        )
        listaVagas.add(vaga1)

        val vaga2 = Vagas(
            "Analista de Sistemas Kotlin",
            "Vaga para analista de sistemas com experiência em Kotlin",
            "3200.00",
            "Rio de Janeiro",
            "maria@gmail.com",
            "119988776655",
            "Google",
            "01/06/2019",
            "30/07/2022"
        )
        listaVagas.add(vaga2)

        val vaga3 = Vagas(
            "Engenheiro(a) de Software Kotlin",
            "Oportunidade para engenheiro(a) de software com expertise em Kotlin",
            "4500.00",
            "Belo Horizonte",
            "carlos@gmail.com",
            "112233445566",
            "Amazon",
            "10/02/2015",
            "15/09/2022"
        )
        listaVagas.add(vaga3)

        return view
    }
}
