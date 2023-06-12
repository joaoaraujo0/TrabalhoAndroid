package com.unaerp.trabalhoandroid.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.Query
import com.unaerp.trabalhoandroid.Adapter.AdapterVaga
import com.unaerp.trabalhoandroid.FirestoreSingleton
import com.unaerp.trabalhoandroid.databinding.FragmentPainelVagasBinding
import com.unaerp.trabalhoandroid.model.Vagas

class PainelVagasFragment : Fragment() {
    private val listaVagas: MutableList<Vagas> = mutableListOf()
    private lateinit var adapterVaga: AdapterVaga
    private lateinit var binding: FragmentPainelVagasBinding
    private val db = FirestoreSingleton.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPainelVagasBinding.inflate(inflater, container, false)
        val view = binding.root

        val recyclerViewVagas = binding.recyclerViewVagas
        recyclerViewVagas.layoutManager = LinearLayoutManager(requireContext())

        adapterVaga = AdapterVaga {  }

        recyclerViewVagas.adapter = adapterVaga

        pegarAnuncios()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Lógica para lidar com a submissão do texto de pesquisa
                pesquisar(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()){
                    pegarAnuncios()
                }
                return true
            }
        })


        return view
    }

    private fun pesquisar(mensagem: String){
        db.collection("AnunciosEmpresas")
            .orderBy("DataPublicacao", Query.Direction.DESCENDING)
            .whereEqualTo("NomeEmpresa",mensagem)
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
                }
            }

    }

    private fun pegarAnuncios() {
        db.collection("AnunciosEmpresas")
            .orderBy("DataPublicacao", Query.Direction.DESCENDING)
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
                    Aviso("Não foi encontrado nenhum anúncio!")
                }
            }
    }

    private fun Aviso(mensagem: String) {
        val snackbar = Snackbar.make(binding.root, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor("#ED2B2A"))
        snackbar.show()
    }
}

