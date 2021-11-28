package com.nkuppan.weatherapp.presentation.placesearch

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import com.nkuppan.weatherapp.R
import com.nkuppan.weatherapp.core.extention.EventObserver
import com.nkuppan.weatherapp.core.extention.autoCleared
import com.nkuppan.weatherapp.core.extention.clearFocusAndHideKeyboard
import com.nkuppan.weatherapp.core.extention.showSnackBarMessage
import com.nkuppan.weatherapp.core.ui.fragment.BaseFragment
import com.nkuppan.weatherapp.databinding.FragmentPlaceSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaceSearchFragment : BaseFragment() {

    private var binding: FragmentPlaceSearchBinding by autoCleared()

    private val placeSearchViewModel: PlaceSearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaceSearchBinding.inflate(inflater, container, false)
        binding.viewModel = placeSearchViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        placeSearchViewModel.places.observe(viewLifecycleOwner) {

        }
    }

    private fun handleSearchAction() {
        if (placeSearchViewModel.processQuery()) {
            binding.query.clearFocusAndHideKeyboard()
        } else {
            binding.root.showSnackBarMessage(R.string.enter_valid_query_string)
        }
    }
}