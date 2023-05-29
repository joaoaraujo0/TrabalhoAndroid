package com.unaerp.trabalhoandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.unaerp.trabalhoandroid.Adapter.AdapterVaga
import com.unaerp.trabalhoandroid.FirestoreSingleton
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
        val listaVagas: MutableList<Vagas> = mutableListOf()
        val adapterVaga = AdapterVaga(requireContext(), listaVagas)
        recyclerViewVagas.adapter = adapterVaga

        PegarAnuncios(binding, listaVagas)

        fun filterList(query: String?) {
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


        })


        return view
    }

    private fun PegarAnuncios(binding: FragmentPainelVagasBinding, listaVagas: MutableList<Vagas>) {
        val db = FirestoreSingleton.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            db.collection("AnunciosEmpresas")
                .document(userId)
                .collection("Anuncios")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val nomeEmpresa = document.getString("NomeEmpresa").toString()
                        val descricaoVaga = document.getString("Descricao").toString()
                        val areaVaga = document.getString("AreaDaVaga").toString()
                        val valorRemuneracao = document.getString("ValorRemuneracao").toString()
                        val localidade = document.getString("Localidade").toString()
                        val emailContato = document.getString("EmailContato").toString()
                        val telefoneContato = document.getString("TelefoneContato").toString()
                        val dataTermino = document.getString("DataVencimento").toString()
                        val dataInicioVaga = document.getDate("DataPublicacao")

                        val vaga = Vagas(
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
                }
        }
    }
}
