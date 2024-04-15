package ru.practicum.android.diploma.ui.place

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
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
        viewModel.preloadCountryState()
        viewModel.placeOfWorkState.observe(viewLifecycleOwner) {
            showAllSelectedPlaceData(it)
        }
        setOnClickListeners()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.clearAllSettingsForFragment()
                    findNavController().navigateUp()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun setOnClickListeners() {
        binding!!.ivBack.setOnClickListener {
            viewModel.clearAllSettingsForFragment()
            findNavController().navigateUp()
        }

        binding!!.ivArrowRightCountry.setOnClickListener {
            if (binding!!.tietCountry.text.toString().isNotEmpty()) {
                viewModel.clearCountry()
            } else {
                findNavController().navigate(
                    PlacesOfWorkFragmentDirections.actionPlacesOfWorkFragmentToSelectCountryFragment()
                )
            }
        }

        binding!!.ivArrowRightRegion.setOnClickListener {
            if (binding!!.tietRegion.text.toString().isNotEmpty()) {
                viewModel.clearRegion()
            } else {
                findNavController().navigate(
                    PlacesOfWorkFragmentDirections.actionPlacesOfWorkFragmentToRegionSelectionFragment()
                )
            }
        }

        binding!!.bChoose.setOnClickListener {
            viewModel.setSaveSettings()
            viewModel.clearAllSettingsForFragment()
            findNavController().navigateUp()
        }

    }

    private fun showAllSelectedPlaceData(data: PlacesOfWorkFragmentStatus) {
        when (data) {
            is PlacesOfWorkFragmentStatus.SavedPlacesFilter -> {
                binding!!.tietCountry.setText(data.countryName)
                binding!!.tietRegion.setText(data.regionName)
                if (data.countryName.isNotEmpty()) {
                    binding!!.tilCountry.defaultHintTextColor = ContextCompat.getColorStateList(requireContext(), R.color.black_white)
                    binding!!.bChoose.visibility = View.VISIBLE
                    binding!!.ivArrowRightCountry.setImageResource(R.drawable.ic_clear)
                } else {
                    binding!!.tilCountry.defaultHintTextColor = ContextCompat.getColorStateList(requireContext(), R.color.gray)
                    binding!!.ivArrowRightCountry.setImageResource(R.drawable.ic_arrow_right)
                    binding!!.bChoose.visibility = View.GONE
                }
                if (data.regionName.isNotEmpty()) {
                    binding!!.tilRegion.defaultHintTextColor = ContextCompat.getColorStateList(requireContext(), R.color.black_white)
                    binding!!.ivArrowRightRegion.setImageResource(R.drawable.ic_clear)
                } else {
                    binding!!.tilRegion.defaultHintTextColor = ContextCompat.getColorStateList(requireContext(), R.color.gray)
                    binding!!.ivArrowRightRegion.setImageResource(R.drawable.ic_arrow_right)
                }
            }
        }
    }

}
