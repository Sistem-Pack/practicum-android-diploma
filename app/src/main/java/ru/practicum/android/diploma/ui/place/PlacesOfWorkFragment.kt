package ru.practicum.android.diploma.ui.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentPlacesOfWorkBinding
import ru.practicum.android.diploma.presentation.place.PlacesOfWorkViewModel
import ru.practicum.android.diploma.ui.place.model.PlacesOfWorkFragmentStatus

class PlacesOfWorkFragment : Fragment() {

    private var binding: FragmentPlacesOfWorkBinding? = null
    private val viewModel by viewModel<PlacesOfWorkViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPlacesOfWorkBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding!!.ivArrowRightCountry.setOnClickListener {
            findNavController().navigate(
                PlacesOfWorkFragmentDirections.actionPlacesOfWorkFragmentToSelectCountryFragment()
            )
        }

        binding!!.ivArrowRightRegion.setOnClickListener {
            if (binding!!.tietCountry.text!!.isNotEmpty()) {
                findNavController().navigate(
                    PlacesOfWorkFragmentDirections.actionPlacesOfWorkFragmentToRegionSelectionFragment()
                )
            }
        }

        viewModel.preloadCountryState()

        viewModel.placeOfWorkState.observe(viewLifecycleOwner) {
            showAllSelectedPlaceData(it)
        }

        binding!!.bChoose.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun showAllSelectedPlaceData(data: PlacesOfWorkFragmentStatus) {
        when (data) {
            is PlacesOfWorkFragmentStatus.SavedPlacesFilter -> {
                binding!!.tietCountry.setText(data.countryName)
                binding!!.tietRegion.setText(data.regionName)
                if (data.countryName.isNotEmpty()) {
                    binding!!.bChoose.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
