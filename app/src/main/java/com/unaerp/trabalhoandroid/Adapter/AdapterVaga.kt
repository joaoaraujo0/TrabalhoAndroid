package com.unaerp.trabalhoandroid.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unaerp.trabalhoandroid.R
import com.unaerp.trabalhoandroid.model.Vagas

class AdapterVaga(private val context : Context, private val vagas : MutableList<Vagas>) : RecyclerView.Adapter<AdapterVaga.VagaViewHolder>() {

    inner class VagaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeEmpresa = itemView.findViewById<TextView>(R.id.tituloNomeEmpresa)
        val areaVaga = itemView.findViewById<TextView>(R.id.nomeArea)
        val localidade = itemView.findViewById<TextView>(R.id.nomeLocalidade)
        val dataTermino = itemView.findViewById<TextView>(R.id.dataTerminoVaga)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VagaViewHolder {
        val itemLista = LayoutInflater.from(context).inflate(R.layout.card_home, parent, false)
        val holder = VagaViewHolder(itemLista)
        return holder

    }

    override fun getItemCount(): Int = vagas.size

    override fun onBindViewHolder(holder: VagaViewHolder, position: Int) {
        holder.nomeEmpresa.text = vagas[position].nomeEmpresa
        holder.areaVaga.text = vagas[position].areaVaga
        holder.localidade.text = vagas[position].localidade
        holder.dataTermino.text = vagas[position].dataTermino



    }
}