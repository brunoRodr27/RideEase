package com.example.rideease.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rideease.data.ApiClient
import com.example.rideease.data.ConfirmRideRequest
import com.example.rideease.data.Driver
import com.example.rideease.data.EstimateResponse
import com.example.rideease.data.RideOption
import com.example.rideease.databinding.FragmentOptionsBinding
import com.example.rideease.ui.adapter.DriverAdapter
import com.example.rideease.util.showButtomSheet
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class OptionsFragment : Fragment() {

    private var _binding: FragmentOptionsBinding? = null
    private val binding get() = _binding!!

    private lateinit var estimateResponse: EstimateResponse
    private lateinit var driverAdapter: DriverAdapter

    private val args: OptionsFragmentArgs by navArgs()
    private val argsID: OptionsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.estimateResponse.let { it ->
            if (it != null) {
                this.estimateResponse = it
           }
        }

        iniciarClicks()
        configMapa()
        initRecyclerView()
    }

    private fun validarMotorista(rideOption: RideOption) {
        val customer_id = argsID.toString()
        val origin = estimateResponse.origin.toString()
        val destination = estimateResponse.destination.toString()
        val distance = estimateResponse.distance
        val duration = estimateResponse.duration
        val driver = Driver(rideOption.id, rideOption.name)
        val value = rideOption.value

        val request = ConfirmRideRequest(customer_id, origin, destination, distance, duration, driver, value)
        getAPIResponseRide(request)
    }

    private fun getAPIResponseRide(request: ConfirmRideRequest) {
        val serviceAPI = ApiClient.instance

        lifecycleScope.launch {
            try {

                val response = serviceAPI.confirmRide(request)

                if (response.success) {
                    showButtomSheet(
                        titleDialog = "Boa Viagem!",
                        titleButtom = "Continua",
                        message = "Tudo certo, seu motorista está a caminho.",
                        onClick = { confirmaViagem() })
                } else {
                    showButtomSheet(message = "Algo deu errado!")
                }

            } catch (e: Exception) {
                showButtomSheet(message = e.localizedMessage)
            }
        }
    }

    private fun confirmaViagem() {
        val action = OptionsFragmentDirections.actionOptionsFragmentToHistoryFragment()
        findNavController().navigate(action)
    }

    private fun initRecyclerView() {
        driverAdapter = DriverAdapter() {rideOption,  option ->
            optionSelect(rideOption, option)}

        binding.rvDrivers.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDrivers.setHasFixedSize(true)
        binding.rvDrivers.adapter = driverAdapter

        val driversList = estimateResponse.options
        driverAdapter.submitList(driversList)
    }

    private fun optionSelect(rideOption: RideOption, option: Int) {
        when (option) {
            DriverAdapter.SELECT -> validarMotorista(rideOption)
        }
    }

    private fun iniciarClicks() {
        binding.btVoltar.setOnClickListener { findNavController().popBackStack() }
    }

    private fun configMapa() {
        val mapImageView: ImageView = binding.mapStaticImageView

        val originLat = estimateResponse.origin.latitude
        val originLng = estimateResponse.origin.longitude
        val destinationLat = estimateResponse.destination.latitude
        val destinationLng = estimateResponse.destination.longitude
        val centerLat = (originLat + destinationLat) / 2
        val centerLng = (originLng + destinationLng) / 2
        val apiKey = "AIzaSyBrOpxRuNYMXCnDzBjqssRAvzrUb48jVGo"

        val mapUrl = "https://maps.googleapis.com/maps/api/staticmap?" +
                "size=300x300&" +
                "center=$centerLat,$centerLng&" +
                "zoom=11&" +
                "markers=color:green|label:A|$originLat,$originLng&" +
                "markers=color:red|label:B|$destinationLat,$destinationLng&" +
                "path=color:0x0000ff|weight:5|$originLat,$originLng|$destinationLat,$destinationLng&" +
                "key=$apiKey"
        Picasso.get()
            .load(mapUrl)
            .into(mapImageView)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}