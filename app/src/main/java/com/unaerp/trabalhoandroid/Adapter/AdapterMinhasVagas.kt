package com.unaerp.trabalhoandroid.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unaerp.trabalhoandroid.R
import com.unaerp.trabalhoandroid.model.Vagas

class AdapterMinhasVagas(private val context: Context,private val minhasVagas: MutableList<Vagas>): RecyclerView.Adapter<AdapterMinhasVagas.MinhasVagasHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MinhasVagasHolder {
        val itemLista = LayoutInflater.from(context).inflate(R.layout.card_minhas_vagas, parent, false)
        val holder = MinhasVagasHolder(itemLista)
        return holder
    }

    override fun getItemCount(): Int = minhasVagas.size

    override fun onBindViewHolder(holder: MinhasVagasHolder, position: Int) {
        holder.nomeEmpresa.text = minhasVagas[position].anunciante
        holder.areaDaVaga.text = minhasVagas[position].areaVaga
        holder.localidade.text = minhasVagas[position].localidade
        holder.dataTermino.text = minhasVagas[position].dataTermino
        holder.valorRemuneracao.text = minhasVagas[position].valorRemuneracao
        holder.emailContato.text = minhasVagas[position].emailContato
        holder.telefoneContato.text = minhasVagas[position].telefoneContato



    }

    inner class MinhasVagasHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nomeEmpresa = itemView.findViewById<TextView>(R.id.nomeEmpresaMinhasVagas)
        val areaDaVaga = itemView.findViewById<TextView>(R.id.areaDaVagaMinhasVagas)
        val localidade = itemView.findViewById<TextView>(R.id.localidadeMinhasVagas)
        val dataTermino = itemView.findViewById<TextView>(R.id.dataTerminoMinhasVagas)
        val valorRemuneracao = itemView.findViewById<TextView>(R.id.valorRemuneracaoMinhasVagas)
        val emailContato = itemView.findViewById<TextView>(R.id.emailContatoMinhasVagas)
        val telefoneContato = itemView.findViewById<TextView>(R.id.telefoneContatoMinhasVagas)


    }

}