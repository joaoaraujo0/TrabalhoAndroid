package com.unaerp.trabalhoandroid.fragments_estagio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.unaerp.trabalhoandroid.Adapter.AdapterVaga
import com.unaerp.trabalhoandroid.databinding.FragmentAnuncioEstagiarioBinding
import com.unaerp.trabalhoandroid.model.Vagas

class AnunciosFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAnuncioEstagiarioBinding.inflate(inflater, container, false)
        val view = binding.root
        //val pesquisa = view.findViewById<SearchView>(R.id.searchViewEstagiario)

        val recyclerViewVagas = binding.recyclerViewVagasEstagio
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
        val vaga4 = Vagas(
            "Engenheiro(a) de Software csharp",
            "Oportunidade para engenheiro(a) de software com expertise em csharp",
            "4500.00",
            "Belo csharp",
            "carlos@csharp.com",
            "112233445566",
            "Amazon",
            "10/02/2015",
            "15/09/2022"
        )
        listaVagas.add(vaga4)



        return view
    }
}
