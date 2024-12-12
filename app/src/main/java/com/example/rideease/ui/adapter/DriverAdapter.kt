package com.example.rideease.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rideease.data.RideOption
import com.example.rideease.databinding.ItemDriverBinding

class DriverAdapter(
    private val driverSelected: (RideOption, Int) -> Unit
) : ListAdapter<RideOption, DriverAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        val SELECT: Int = 1

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RideOption>() {
            override fun areItemsTheSame(oldItem: RideOption, newItem: RideOption): Boolean {
                return oldItem.id == newItem.id && oldItem.description == newItem.description
            }

            override fun areContentsTheSame(oldItem: RideOption, newItem: RideOption): Boolean {
                return oldItem == newItem && oldItem.description == newItem.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemDriverBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        ))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val driver = getItem(position)
        val vehicle = driver.vehicle
        val value = driver.value
        val rating = driver.review.rating

        holder.binding.tvNome.text = driver.name
        holder.binding.tvDescricao.text = driver.description
        holder.binding.tvVeiculo.text = "Veículo: $vehicle"
        holder.binding.tvValor.text = "R$: $value"
        holder.binding.tvAvaliacao.text = "$rating / 5.0"

        setIndicators(driver, holder)
    }

    private fun setIndicators(rideOption: RideOption, holder: MyViewHolder) {
        holder.binding.btSelecionar.setOnClickListener { driverSelected(rideOption, SELECT) }
    }

    inner class MyViewHolder(val binding: ItemDriverBinding)
        : RecyclerView.ViewHolder(binding.root)

}