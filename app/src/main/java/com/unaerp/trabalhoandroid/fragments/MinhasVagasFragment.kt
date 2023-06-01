package com.unaerp.trabalhoandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.unaerp.trabalhoandroid.Adapter.AdapterMinhasVagas
import com.unaerp.trabalhoandroid.FirestoreSingleton
import com.unaerp.trabalhoandroid.databinding.FragmentMinhasVagasBinding
import com.unaerp.trabalhoandroid.model.Vagas

class MinhasVagasFragment : Fragment() {
    val listaMinhasVagas: MutableList<Vagas> = mutableListOf()
    val adapterMinhasVaga by lazy { AdapterMinhasVagas(requireContext(), listaMinhasVagas) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMinhasVagasBinding.inflate(inflater, container, false)
        val view = binding.root

        val recyclerViewMinhasVagas = binding.recyclerViewMinhasVagas
        recyclerViewMinhasVagas.layoutManager = LinearLayoutManager(requireContext())



        //Configurar adapter

        recyclerViewMinhasVagas.adapter = adapterMinhasVaga

        pegarAnuncios(listaMinhasVagas)


        return view


    }

    private fun pegarAnuncios(listadeVagas: MutableList<Vagas>) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val db = FirestoreSingleton.getInstance()
        val userId = currentUser?.uid
        db.collection("AnunciosEmpresas")
            .whereEqualTo("IdEmpresa", userId)
            .get()
            .addOnSuccessListener { result ->
                listadeVagas.clear()
                if (result.isEmpty) {
                    Toast.makeText(requireContext(), "Não foi encontrado nenhum anúncio!.", Toast.LENGTH_SHORT).show()
                } else {
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

                        listadeVagas.add(vaga)
                        adapterMinhasVaga.notifyDataSetChanged()
                    }
                }

            }
    }

}