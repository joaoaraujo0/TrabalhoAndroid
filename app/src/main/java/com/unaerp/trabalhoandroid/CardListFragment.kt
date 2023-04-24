package com.unaerp.trabalhoandroid


import android.os.Bundle
import android.widget.Adapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unaerp.trabalhoandroid.Adapter.AdapterVaga
import com.unaerp.trabalhoandroid.model.Vagas

class CardListFragment : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_painel_vagas)

        val recyclerViewVagas = findViewById<RecyclerView>(R.id.recyclerViewVagas)
        recyclerViewVagas.layoutManager = LinearLayoutManager(this)
        recyclerViewVagas.setHasFixedSize(true)
        //Configurar adapter
        val listaVagas : MutableList<Vagas> = mutableListOf()
        val adapterVaga = AdapterVaga(this, listaVagas)
        recyclerViewVagas.adapter = adapterVaga


        val vaga1 = Vagas(
            "Microsoft",
            "Ti",
            "São Paulo",
            "11/05/2023"
        )
        listaVagas.add(vaga1)

        val vaga2 = Vagas(
            "Naiu",
            "Biologia",
            "São Paulo",
            "05/05/2023"
        )
        listaVagas.add(vaga2)


    }
}