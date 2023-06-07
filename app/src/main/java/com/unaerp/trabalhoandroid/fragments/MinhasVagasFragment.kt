package com.unaerp.trabalhoandroid.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.unaerp.trabalhoandroid.Adapter.AdapterMinhasVagas
import com.unaerp.trabalhoandroid.EditarVagaActivity
import com.unaerp.trabalhoandroid.FirestoreSingleton
import com.unaerp.trabalhoandroid.databinding.FragmentMinhasVagasBinding
import com.unaerp.trabalhoandroid.model.Vagas

class MinhasVagasFragment : Fragment() {
    private val listaMinhasVagas: MutableList<Vagas> = mutableListOf()
    private lateinit var adapterMinhasVaga: AdapterMinhasVagas

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMinhasVagasBinding.inflate(inflater, container, false)
        val view = binding.root
        val db = Firebase.firestore

        val recyclerViewMinhasVagas = binding.recyclerViewMinhasVagas
        recyclerViewMinhasVagas.layoutManager = LinearLayoutManager(requireContext())


        adapterMinhasVaga = AdapterMinhasVagas { task, excluir ->
            if (excluir) {
                db.collection("AnunciosEmpresas")
                    .document(task.id)
                    .delete().addOnCompleteListener {
                        Aviso("Vaga excluida com sucesso!!", binding)
                    }.addOnFailureListener {
                        Aviso("Erro ao excluir vaga, tente novamente mais tarde", binding)
                    }
            }
            else{
                val intent = Intent(context, EditarVagaActivity::class.java)
                intent.putExtra("idAnuncio", task.id)
                context?.startActivity(intent)
            }

        }

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
            .orderBy("DataPublicacao",Query.Direction.DESCENDING)
            .addSnapshotListener { result, error ->
                listadeVagas.clear()
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

                            listadeVagas.add(vaga)
                        }

                        adapterMinhasVaga.updateList(listadeVagas)
                    }
            }
    }
}
private fun Aviso(mensagem: String, binding: FragmentMinhasVagasBinding){
    val snackbar = Snackbar.make(binding.root, mensagem, Snackbar.LENGTH_SHORT)
    snackbar.setBackgroundTint(Color.GREEN)
    snackbar.show()
}
