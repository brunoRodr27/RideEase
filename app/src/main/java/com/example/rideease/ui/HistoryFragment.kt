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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rideease.data.ApiClient
import com.example.rideease.data.RideHistoryRequest
import com.example.rideease.databinding.FragmentHistoryBinding
import com.example.rideease.ui.adapter.HistoricoAdapter
import com.example.rideease.util.showButtomSheet
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var historyAdapter: HistoricoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iniciarClicks()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        historyAdapter = HistoricoAdapter()

        binding.rvHistorico.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistorico.setHasFixedSize(true)
        binding.rvHistorico.adapter = historyAdapter
    }

    private fun iniciarClicks() {
        binding.btVoltar.setOnClickListener { findNavController().popBackStack() }

        binding.btHistorico.setOnClickListener { buscaHistorico() }
    }

    private fun buscaHistorico() {
        val idUsuario = binding.etId.text.toString()
        val idMotorista = binding.etIddriver.text.toString()

        if (idUsuario.isNotEmpty()) {
            if (idMotorista.isNotEmpty()) {
                binding.progressBar.isVisible = true

                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)

                val request = RideHistoryRequest(idUsuario, idMotorista.toInt())
                getAPIHistorico(request)
            } else {
                showButtomSheet(message = "Insira um número de Morista.")
            }
        } else {
            showButtomSheet(message = "Insira um Código de usuário.")
        }
    }

    private fun getAPIHistorico(request: RideHistoryRequest) {
        val serviceAPI = ApiClient.instance

        lifecycleScope.launch {
            try {
                val response = serviceAPI.getRideHistory(request.customer_id, request.rides)
                historyAdapter.submitList(response.rides)

                binding.progressBar.isVisible = false

            } catch (e: Exception) {
                showButtomSheet(message = e.localizedMessage)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}