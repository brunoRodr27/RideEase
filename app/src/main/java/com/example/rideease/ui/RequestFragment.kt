package com.example.rideease.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.rideease.data.ApiClient
import com.example.rideease.data.EstimateRequest
import com.example.rideease.databinding.FragmentRequestBinding
import com.example.rideease.util.showButtomSheet
import kotlinx.coroutines.launch

class RequestFragment : Fragment() {

    private var _binding: FragmentRequestBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iniciarClicks()
    }

    private fun iniciarClicks() {
        binding.btBuscar.setOnClickListener {
            validarDados()
        }
    }

    private fun validarDados() {
        val id_usuario = binding.etId.text.toString()
        val endOrigem = binding.etOrigem.text.toString()
        val endDestino = binding.etDestino.text.toString()

        if (id_usuario.isNotEmpty()) {
            if (endOrigem.isNotEmpty()) {
                if (endDestino.isNotEmpty()) {
                    if (endOrigem != endDestino) {

                        binding.progressBar.isVisible = true

                        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(view?.windowToken, 0)

                        val request = EstimateRequest(id_usuario, endOrigem, endDestino)
                        getAPIResponse(request)

                    } else {
                        showButtomSheet(message = "O endereço de origem e destino precisam ser difrentes.")
                    }
                } else {
                    showButtomSheet(message = "Insira um endereço de destino.")
                }
            } else {
                showButtomSheet(message = "Insira um endereço de origem.")
            }
        } else {
            showButtomSheet(message = "Insira um Código de usuário.")
        }
    }

    private fun getAPIResponse(request: EstimateRequest) {
        val serviceAPI = ApiClient.instance

        lifecycleScope.launch {
            try {

                val response = serviceAPI.estimateRide(request)

                binding.progressBar.isVisible = false

                val action = RequestFragmentDirections.actionRequestFragmentToOptionsFragment(response, binding.etId.text.toString())
                findNavController().navigate(action)

            } catch (e: Exception) {
                binding.progressBar.isVisible = false
                showButtomSheet(message = e.localizedMessage)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}