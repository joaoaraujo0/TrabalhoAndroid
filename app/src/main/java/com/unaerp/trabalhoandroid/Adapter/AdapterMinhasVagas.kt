package com.unaerp.trabalhoandroid.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unaerp.trabalhoandroid.databinding.CardMinhasVagasBinding
import com.unaerp.trabalhoandroid.model.Vagas

class AdapterMinhasVagas(
    private val onItemClickListener: (Vagas) -> Unit
) : RecyclerView.Adapter<AdapterMinhasVagas.MinhasVagasHolder>() {

    private val minhasVagas: MutableList<Vagas> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MinhasVagasHolder {
        val binding =
            CardMinhasVagasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MinhasVagasHolder(binding)
    }

    override fun getItemCount(): Int = minhasVagas.size

    override fun onBindViewHolder(holder: MinhasVagasHolder, position: Int) {
        holder.bind(minhasVagas[position])
    }

    fun updateList(newMinhasVagas: List<Vagas>) {
        minhasVagas.clear()
        minhasVagas.addAll(newMinhasVagas)
        notifyDataSetChanged()
    }

    inner class MinhasVagasHolder(private val binding: CardMinhasVagasBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(vaga: Vagas) {
            binding.nomeEmpresaMinhasVagas.text = "Anunciante: ${vaga.anunciante}"
            binding.areaDaVagaMinhasVagas.text = vaga.areaVaga
            binding.localidadeMinhasVagas.text = vaga.localidade
            binding.dataTerminoMinhasVagas.text = vaga.dataTermino
            binding.valorRemuneracaoMinhasVagas.text = vaga.valorRemuneracao
            binding.emailContatoMinhasVagas.text = vaga.emailContato
            binding.telefoneContatoMinhasVagas.text = vaga.telefoneContato

            binding.excluirvaga.setOnClickListener {
                onItemClickListener(vaga)
            }
        }
    }
}
