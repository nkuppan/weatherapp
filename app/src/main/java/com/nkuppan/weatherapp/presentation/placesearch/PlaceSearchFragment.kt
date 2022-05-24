package com.nkuppan.weatherapp.presentation.placesearch

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nkuppan.weatherapp.R
import com.nkuppan.weatherapp.core.extention.clearFocusAndHideKeyboard
import com.nkuppan.weatherapp.core.extention.showSnackBarMessage
import com.nkuppan.weatherapp.core.ui.fragment.BaseBindingFragment
import com.nkuppan.weatherapp.databinding.FragmentPlaceSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlaceSearchFragment : BaseBindingFragment<FragmentPlaceSearchBinding>() {

    private val viewModel: PlaceSearchViewModel by viewModels()

    private val placeListAdapter = PlaceListAdapter { type, city ->
        viewLifecycleOwner.lifecycleScope.launch {
            if (type == 1) {
                viewModel.saveSelectedCity(city)
            } else if (type == 2) {
                viewModel.saveFavoriteCity(city)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeSearchContainer()

        initializeRecyclerView()

        initializeObserver()
    }

    private fun initializeObserver() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.errorMessage.collect {
                        binding.root.showSnackBarMessage(it.asString(requireContext()))
                    }
                }
                launch {
                    viewModel.places.collect {
                        placeListAdapter.submitList(it)
                    }
                }
                launch {
                    viewModel.placeSelected.collectLatest {
                        findNavController().popBackStack()
                    }
                }
            }
        }

        viewModel.searchFavorites()
    }

    private fun initializeSearchContainer() {
        binding.query.apply {
            setOnEditorActionListener { _, actionId, _ ->
                return@setOnEditorActionListener if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    handleSearchAction()
                    true
                } else {
                    false
                }
            }

            setOnKeyListener { _, _, event ->
                if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
                    handleSearchAction()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initializeRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = placeListAdapter
    }

    private fun handleSearchAction() {
        if (viewModel.processQuery()) {
            binding.query.clearFocusAndHideKeyboard()
        } else {
            binding.root.showSnackBarMessage(R.string.enter_valid_query_string)
        }
    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentPlaceSearchBinding {
        return FragmentPlaceSearchBinding.inflate(inflater, container, false)
    }

    override fun bindData(binding: FragmentPlaceSearchBinding) {
        super.bindData(binding)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}