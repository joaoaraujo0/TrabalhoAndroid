package com.unaerp.trabalhoandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.unaerp.trabalhoandroid.Adapter.AdapterVaga
import com.unaerp.trabalhoandroid.databinding.FragmentPainelVagasBinding
import com.unaerp.trabalhoandroid.model.Vagas
import java.util.Locale

class PainelVagasFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPainelVagasBinding.inflate(inflater, container, false)
        val view = binding.root

        val recyclerViewVagas = binding.recyclerViewVagas
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
            "Analista de Sistemas Java",
            "Vaga para analista de sistemas com experiência em Java",
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
            "Engenheiro(a) de Software php",
            "Oportunidade para engenheiro(a) de software com expertise em php",
            "43400.00",
            "bh",
            "carlos@@#@gmail.com",
            "112233445566",
            "Amazon",
            "10/02/2015",
            "15/09/2022"
        )
        listaVagas.add(vaga3)

        val vaga4 = Vagas(
            "Engenheiro(a) de Software lua",
            "Oportunidade para engenheiro(a) de software com expertise em lua",
            "450000.00",
            "Belo Mar",
            "Lulu@gmail.com",
            "1734y73y43y",
            "polupi",
            "10/02/2015",
            "15/09/2022"
        )
        listaVagas.add(vaga4)
        fun filterList(query: String?) {
            val filteredList: MutableList<Vagas> = mutableListOf()

            if (!query.isNullOrEmpty()) {
                for (vaga in listaVagas) {
                    if (vaga.anunciante.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))) {
                        filteredList.add(vaga)
                    }
                }
            } else {
                filteredList.addAll(listaVagas) // Restaurar a lista original quando o texto de pesquisa estiver vazio
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(requireContext(), "Não foi encontrado nenhum resultado.", Toast.LENGTH_SHORT).show()
            } else {
                adapterVaga.setFilteredList(filteredList)
            }
        }


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }


        })


        return view
    }
}
