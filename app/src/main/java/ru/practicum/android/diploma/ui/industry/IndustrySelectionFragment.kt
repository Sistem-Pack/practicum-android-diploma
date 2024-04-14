package ru.practicum.android.diploma.ui.industry

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentIndustrySelectionBinding
import ru.practicum.android.diploma.domain.models.industry.Industry
import ru.practicum.android.diploma.presentation.industry.IndustrySelectionViewModel

class IndustrySelectionFragment : Fragment() {

    private var _binding: FragmentIndustrySelectionBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<IndustrySelectionViewModel>()
    private val adapter = IndustriesAdapter {
        viewModel.saveSelectedIndustry(it)
        inputMethodManager?.hideSoftInputFromWindow(binding.etIndustry.windowToken, 0)
    }
    private var inputMethodManager: InputMethodManager? = null
    private var editTextValue = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentIndustrySelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getIndustries()

        viewModel.observeIndustrySelectionScreenState().observe(viewLifecycleOwner) {
            hideAll()
            when (it) {
                is IndustrySelectionScreenState.UploadingProcess -> showProgress()
                is IndustrySelectionScreenState.NoConnection -> showNoConnectionMessage()
                is IndustrySelectionScreenState.FailedRequest -> showErrorMessage()
                is IndustrySelectionScreenState.IndustriesUploaded -> {
                    showIndustries(it.industries, it.selectedIndustryId)
                }
            }
        }

        inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager

        binding.rvIndustryList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvIndustryList.adapter = adapter

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.bChoose.setOnClickListener {
            viewModel.putSelectedIndustryInSharedPrefs()
            findNavController().navigateUp()
        }

        binding.ivIndustry.setOnClickListener {
            binding.etIndustry.setText("")
            inputMethodManager?.hideSoftInputFromWindow(binding.etIndustry.windowToken, 0)
        }

        createTextWatcher()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun showIndustries(industries: ArrayList<Industry>, selectedIndustryId: String) {
        adapter.industries = industries
        adapter.selectedIndustryId = selectedIndustryId
        adapter.notifyDataSetChanged()
        binding.rvIndustryList.isVisible = true
        if (selectedIndustryId.isNotEmpty()) {
            binding.bChoose.isVisible = true
        }
    }

    private fun showProgress() {
        binding.pbLoading.isVisible = true
    }

    private fun showNoConnectionMessage() {
        binding.tvNoInternetPlaceholder.isVisible = true
    }

    private fun showErrorMessage() {
        binding.tvFailedRequestPlaceholder.isVisible = true
    }

    private fun hideAll() {
        binding.tvNoInternetPlaceholder.isVisible = false
        binding.tvNotFoundPlaceholder.isVisible = false
        binding.tvFailedRequestPlaceholder.isVisible = false
        binding.bChoose.isVisible = false
        binding.pbLoading.isVisible = false
        binding.rvIndustryList.isVisible = false
    }

    private fun createTextWatcher() {
        binding.etIndustry.doOnTextChanged { s: CharSequence?, start: Int, count: Int, after: Int ->
            editTextValue = binding.etIndustry.text.toString()
            if (editTextValue.isEmpty()) {
                binding.ivIndustry.setImageResource(R.drawable.ic_search)
            } else {
                binding.ivIndustry.setImageResource(R.drawable.ic_clear)
            }
            viewModel.industrySearch(editTextValue)
        }
    }

}
