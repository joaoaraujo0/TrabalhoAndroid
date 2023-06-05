package com.unaerp.trabalhoandroid.fragments_estagio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.Query
import com.unaerp.trabalhoandroid.Adapter.AdapterVaga
import com.unaerp.trabalhoandroid.FirestoreSingleton
import com.unaerp.trabalhoandroid.databinding.FragmentAnuncioEstagiarioBinding
import com.unaerp.trabalhoandroid.model.Vagas

class AnunciosFragment : Fragment() {
    private val listaVagas: MutableList<Vagas> = mutableListOf()
    private lateinit var adapterVaga: AdapterVaga

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAnuncioEstagiarioBinding.inflate(inflater, container, false)
        val view = binding.root

        //val pesquisa = view.findViewById<SearchView>(R.id.searchViewEstagiario)

        val recyclerViewVagas = binding.recyclerViewVagasEstagio
        recyclerViewVagas.layoutManager = LinearLayoutManager(requireContext())

        adapterVaga = AdapterVaga {  }

        recyclerViewVagas.adapter = adapterVaga

        pegarAnuncios(listaVagas)


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
