package com.unaerp.trabalhoandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.unaerp.trabalhoandroid.Adapter.AdapterVaga
import com.unaerp.trabalhoandroid.FirestoreSingleton
import com.unaerp.trabalhoandroid.databinding.FragmentPainelVagasBinding
import com.unaerp.trabalhoandroid.model.Vagas

class PainelVagasFragment : Fragment() {
    private val listaVagas: MutableList<Vagas> = mutableListOf()
    private lateinit var adapterVaga: AdapterVaga

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPainelVagasBinding.inflate(inflater, container, false)
        val view = binding.root
        val db = Firebase.firestore


        val recyclerViewVagas = binding.recyclerViewVagas
        recyclerViewVagas.layoutManager = LinearLayoutManager(requireContext())

        adapterVaga = AdapterVaga {  }

        recyclerViewVagas.adapter = adapterVaga

        pegarAnuncios(listaVagas)

        /*fun filterList(query: String?) {
            val filteredList: MutableList<Vagas> = mutableListOf()

            if (!query.isNullOrEmpty()) {
                for (vaga in listaVagas) {
                    if (vaga.anunciante.lowercase(Locale.ROOT)
                            .contains(query.lowercase(Locale.ROOT))
                    ) {
                        filteredList.add(vaga)
                    }
                }
            } else {
                filteredList.addAll(listaVagas) // Restaurar a lista original quando o texto de pesquisa estiver vazio
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Não foi encontrado nenhum resultado.",
                    Toast.LENGTH_SHORT
                ).show()
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


        })*/


        return view
    }

    private fun pegarAnuncios(listaVagas: MutableList<Vagas>) {
        val db = FirestoreSingleton.getInstance()
        db.collection("AnunciosEmpresas")
            .orderBy("DataPublicacao", Query.Direction.ASCENDING)
            .addSnapshotListener { result, error ->
                listaVagas.clear()
                if (result != null) {
                    for (document in result) {
                        val nomeEmpresa = document.getString("NomeEmpresa").toString()
                        val descricaoVaga = document.getString("Descricao").toString()
                        val areaVaga = document.getString("AreaDaVaga").toString()
                        val valorRemuneracao = document.getString("ValorRemuneracao").toString()
                        val localidade = document.getString("Localidade").toString()
                        val emailContato = document.getString("EmailContato").toString()
                        val telefoneContato = document.getString("TelefoneContato").toString()
                        val dataTermino = document.getString("DataVencimento").toString()
                        val dataInicioVaga = document.getString("DataPublicacao").toString()

                        val vaga = Vagas(
                            document.id,
                            nomeEmpresa,
                            descricaoVaga,
                            areaVaga,
                            valorRemuneracao,
                            localidade,
                            emailContato,
                            telefoneContato,
                            dataTermino,
                            dataInicioVaga,
                        )

                        listaVagas.add(vaga)

                    }
                    adapterVaga.updateList(listaVagas)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Não foi encontrado nenhum anúncio!.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}

