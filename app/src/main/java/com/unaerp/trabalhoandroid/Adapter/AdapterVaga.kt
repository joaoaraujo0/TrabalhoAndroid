package com.unaerp.trabalhoandroid.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.unaerp.trabalhoandroid.R
import com.unaerp.trabalhoandroid.VagaExpandida
import com.unaerp.trabalhoandroid.model.Vagas

class AdapterVaga(private val context : Context, private var vagas : MutableList<Vagas>) : RecyclerView.Adapter<AdapterVaga.VagaViewHolder>() {

    inner class VagaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val anunciante = itemView.findViewById<TextView>(R.id.tituloNomeEmpresa)
        val areaVaga = itemView.findViewById<TextView>(R.id.nomeArea)
        val localidade = itemView.findViewById<TextView>(R.id.nomeLocalidade)
        val dataTermino = itemView.findViewById<TextView>(R.id.dataTerminoVaga)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VagaViewHolder {
        val itemLista = LayoutInflater.from(context).inflate(R.layout.card_home, parent, false)
        val holder = VagaViewHolder(itemLista)
        return holder

    }
    fun setFilteredList(vagas: MutableList<Vagas>){
        this.vagas = vagas
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = vagas.size

    override fun onBindViewHolder(holder: VagaViewHolder, position: Int) {
        holder.anunciante.text = vagas[position].anunciante
        holder.areaVaga.text = vagas[position].areaVaga
        holder.localidade.text = vagas[position].localidade
        holder.dataTermino.text = vagas[position].dataTermino

        holder.itemView.findViewById<MaterialButton>(R.id.verVagas).setOnClickListener() {
            val intent = Intent(context, VagaExpandida::class.java)
            intent.putExtra("areaVaga", vagas[position].areaVaga)
            intent.putExtra("descricaoVaga", vagas[position].descricaoVaga)
            intent.putExtra("valorRemuneracao", vagas[position].valorRemuneracao)
            intent.putExtra("localidade", vagas[position].localidade)
            intent.putExtra("emailContato", vagas[position].emailContato)
            intent.putExtra("telefoneContato", vagas[position].telefoneContato)
            intent.putExtra("anunciante", vagas[position].anunciante)
            intent.putExtra("dataInicioVaga", vagas[position].dataInicioVaga)
            intent.putExtra("dataTermino", vagas[position].dataTermino)

            context.startActivity(intent)}
    }


}