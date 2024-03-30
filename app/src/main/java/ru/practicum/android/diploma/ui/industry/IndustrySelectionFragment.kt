package ru.practicum.android.diploma.ui.industry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentIndustrySelectionBinding
import ru.practicum.android.diploma.presentation.industry.IndustrySelectionViewModel

class IndustrySelectionFragment : Fragment() {

    private var binding: FragmentIndustrySelectionBinding? = null

    private val viewModel by viewModel<IndustrySelectionViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentIndustrySelectionBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
