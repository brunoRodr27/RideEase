package com.example.rideease.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rideease.data.Ride
import com.example.rideease.databinding.ItemHistoryBinding

class HistoricoAdapter(
) : ListAdapter<Ride, HistoricoAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Ride>() {
            override fun areItemsTheSame(oldItem: Ride, newItem: Ride): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Ride, newItem: Ride): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        ))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val history = getItem(position)
        val datahora = history.date
        val nome = history.driver
        val origem = history.origin
        val destino = history.destination
        val distancia = String.format("%.2f", history.distance)
        val tempo = history.duration
        val valor = String.format("%.2f", history.value)

        holder.binding.tvNome.text = nome.name
        holder.binding.tvDatahora.text = datahora
        holder.binding.tvOrigem.text = origem
        holder.binding.tvDestino.text = destino
        holder.binding.tvDistancia.text = distancia
        holder.binding.tvTempo.text = tempo
        holder.binding.tvValor.text = "R$: $valor"
    }

    inner class MyViewHolder(val binding: ItemHistoryBinding)
        : RecyclerView.ViewHolder(binding.root)

}
