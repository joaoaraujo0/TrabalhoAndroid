package com.unaerp.trabalhoandroid.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unaerp.trabalhoandroid.R
import com.unaerp.trabalhoandroid.VagaExpandida
import com.unaerp.trabalhoandroid.databinding.CardHomeBinding
import com.unaerp.trabalhoandroid.model.Vagas

class AdapterVaga(
    private val onItemClickListener: (Vagas) -> Unit
) : RecyclerView.Adapter<AdapterVaga.VagaViewHolder>() {

    private val Vagas: MutableList<Vagas> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VagaViewHolder {
        val binding =
            CardHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VagaViewHolder(binding)
    }

    override fun getItemCount(): Int = Vagas.size

    override fun onBindViewHolder(holder: VagaViewHolder, position: Int) {
        holder.bind(Vagas[position])
    }

    fun updateList(newMinhasVagas: List<Vagas>) {
        Vagas.clear()
        Vagas.addAll(newMinhasVagas)
        notifyDataSetChanged()
    }

    inner class VagaViewHolder(private val binding: CardHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(vaga: Vagas) {
            binding.tituloNomeEmpresa.text = itemView.context.getString(R.string.anunciantecard,vaga.anunciante)
            binding.nomeArea.text = itemView.context.getString(R.string.areadavagacard,vaga.areaVaga)
            binding.nomeLocalidade.text = itemView.context.getString(R.string.localidadecard,vaga.localidade )
            binding.dataTerminoVaga.text = itemView.context.getString(R.string.datacard,vaga.dataTermino )

            binding.verVagas.setOnClickListener {
                val intent = Intent(itemView.context, VagaExpandida::class.java)
                intent.putExtra("areaVaga", vaga.areaVaga)
                intent.putExtra("descricaoVaga", vaga.descricaoVaga)
                intent.putExtra("valorRemuneracao", vaga.valorRemuneracao)
                intent.putExtra("localidade", vaga.localidade)
                intent.putExtra("emailContato", vaga.emailContato)
                intent.putExtra("telefoneContato", vaga.telefoneContato)
                intent.putExtra("anunciante", vaga.anunciante)
                intent.putExtra("dataTermino", vaga.dataTermino)
                itemView.context.startActivity(intent)
            }
        }
    }
}
