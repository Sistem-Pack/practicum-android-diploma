package ru.practicum.android.diploma.ui.filter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFilteringSettingsBinding
import ru.practicum.android.diploma.presentation.filter.FilteringSettingsViewModel

class FilteringSettingsFragment : Fragment() {

    private var binding: FragmentFilteringSettingsBinding? = null

    private val viewModel by viewModel<FilteringSettingsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFilteringSettingsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createClickListeners()
        createTextWatcher()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun createClickListeners() {
        binding!!.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding!!.ivArrowRightJobPlace.setOnClickListener {
            findNavController().navigate(
                FilteringSettingsFragmentDirections.actionFilteringSettingsFragmentToPlacesOfWorkFragment()
            )
        }
        binding!!.ivArrowRightIndustry.setOnClickListener {
            findNavController().navigate(
                FilteringSettingsFragmentDirections.actionFilteringSettingsFragmentToIndustrySelectionFragment()
            )
        }
        binding!!.ivSalaryClear.setOnClickListener {
            binding!!.tietSalary.text!!.clear()
        }
    }

    private fun createTextWatcher() {
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
                binding!!.ivSalaryClear.isVisible = !p0.isNullOrEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        binding!!.tietSalary.addTextChangedListener(simpleTextWatcher)
    }
}

