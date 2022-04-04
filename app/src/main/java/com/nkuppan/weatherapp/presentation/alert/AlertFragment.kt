package com.nkuppan.weatherapp.presentation.alert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.nkuppan.weatherapp.core.extention.autoCleared
import com.nkuppan.weatherapp.core.ui.fragment.BaseFragment
import com.nkuppan.weatherapp.databinding.FragmentAlertBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlertFragment : BaseFragment() {

    private val navArgs: AlertFragmentArgs by navArgs()

    private var binding: FragmentAlertBinding by autoCleared()

    private var alertListAdapter: AlertListAdapter by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        alertListAdapter = AlertListAdapter()

        initializeRecyclerView()

        initializeObserver()
    }

    private fun initializeObserver() {
        alertListAdapter.submitList(navArgs.alerts.toMutableList())
    }

    private fun initializeRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = alertListAdapter
    }
}