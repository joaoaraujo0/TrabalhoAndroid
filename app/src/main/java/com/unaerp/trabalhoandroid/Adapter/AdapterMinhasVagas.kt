package com.unaerp.trabalhoandroid.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unaerp.trabalhoandroid.R
import com.unaerp.trabalhoandroid.databinding.CardMinhasVagasBinding
import com.unaerp.trabalhoandroid.model.Vagas

class AdapterMinhasVagas(
    private val onItemClickListener: (Vagas, excluir :Boolean) -> Unit
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
            binding.nomeEmpresaMinhasVagas.text = itemView.context.getString(R.string.NomedaEmpresa,vaga.anunciante)
            binding.areaDaVagaMinhasVagas.text = itemView.context.getString(R.string.area_da_vaga,vaga.areaVaga)
            binding.localidadeMinhasVagas.text = itemView.context.getString(R.string.localidade,vaga.localidade)
            binding.dataTerminoMinhasVagas.text = itemView.context.getString(R.string.data_termino_da_vaga,vaga.dataTermino)
            binding.valorRemuneracaoMinhasVagas.text = itemView.context.getString(R.string.remuneracao,vaga.valorRemuneracao)
            binding.emailContatoMinhasVagas.text = itemView.context.getString(R.string.e_mail_de_contato,vaga.emailContato)
            binding.telefoneContatoMinhasVagas.text = itemView.context.getString(R.string.telefone,vaga.telefoneContato)
            binding.excluirvaga.setOnClickListener {
                onItemClickListener(vaga, true)
            }
            binding.editarVagasEmpresa.setOnClickListener {
                onItemClickListener(vaga, false)
            }
        }
    }
}
