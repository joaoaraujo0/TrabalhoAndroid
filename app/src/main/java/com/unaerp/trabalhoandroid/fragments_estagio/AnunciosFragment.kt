package com.unaerp.trabalhoandroid.fragments_estagio

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.Query
import com.unaerp.trabalhoandroid.Adapter.AdapterVaga
import com.unaerp.trabalhoandroid.FirestoreSingleton
import com.unaerp.trabalhoandroid.databinding.FragmentAnuncioEstagiarioBinding
import com.unaerp.trabalhoandroid.model.Vagas

class AnunciosFragment : Fragment() {
    private val listaVagas: MutableList<Vagas> = mutableListOf()
    private lateinit var adapterVaga: AdapterVaga
    private lateinit var binding: FragmentAnuncioEstagiarioBinding
    private val db = FirestoreSingleton.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnuncioEstagiarioBinding.inflate(inflater, container, false)
        val view = binding.root

        //val pesquisa = view.findViewById<SearchView>(R.id.searchViewEstagiario)

        val recyclerViewVagas = binding.recyclerViewVagasEstagio
        recyclerViewVagas.layoutManager = LinearLayoutManager(requireContext())

        adapterVaga = AdapterVaga {  }

        recyclerViewVagas.adapter = adapterVaga

        pegarAnuncios()


        return view
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
