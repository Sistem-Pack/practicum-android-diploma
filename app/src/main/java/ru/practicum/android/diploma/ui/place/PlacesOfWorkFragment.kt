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

class PlacesOfWorkFragment : Fragment() {

    private var binding: FragmentPlacesOfWorkBinding? = null
    private val viewModel by viewModel<PlacesOfWorkViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPlacesOfWorkBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.ivArrowRightCountry.setOnClickListener {
            findNavController().navigate(
                PlacesOfWorkFragmentDirections.actionPlacesOfWorkFragmentToSelectCountryFragment()
            )
        }

        binding!!.ivArrowRightRegion.setOnClickListener {
            findNavController().navigate(
                PlacesOfWorkFragmentDirections.actionPlacesOfWorkFragmentToRegionSelectionFragment()
            )
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
