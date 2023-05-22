package com.unaerp.trabalhoandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unaerp.trabalhoandroid.Adapter.AdapterMinhasVagas
import com.unaerp.trabalhoandroid.R
import com.unaerp.trabalhoandroid.model.Vagas


class MinhasVagasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_minhas_vagas, container, false)

        val recyclerViewMinhasVagas = view.findViewById<RecyclerView>(R.id.recyclerViewMinhasVagas)
        recyclerViewMinhasVagas.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewMinhasVagas.setHasFixedSize(true)

        //Configurar adapter
        val listaMinhasVagas : MutableList<Vagas> = mutableListOf()
        val adapterMinhasVaga = AdapterMinhasVagas(requireContext(), listaMinhasVagas)
        recyclerViewMinhasVagas.adapter = adapterMinhasVaga


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
        listaMinhasVagas.add(vaga1)

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
        listaMinhasVagas.add(vaga2)

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
        listaMinhasVagas.add(vaga3)

        /*val botaoEditarVagaEmpresa = view.findViewById<MaterialButton>(R.id.editarVagasEmpresa)

        botaoEditarVagaEmpresa.setOnClickListener {
            val intent = Intent(activity, EditarVaga::class.java)
            startActivity(intent)
        }*/






        return view


    }

}