package ru.practicum.android.diploma.ui.region

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
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentRegionSelectionBinding
import ru.practicum.android.diploma.domain.models.vacancy.Vacancy
import ru.practicum.android.diploma.presentation.region.RegionSelectionViewModel
import ru.practicum.android.diploma.ui.main.vacancy.VacancyAdapter

class RegionSelectionFragment : Fragment() {

    private var binding: FragmentRegionSelectionBinding? = null
    private val viewModel by viewModel<RegionSelectionViewModel>()
    private var editTextValue = ""
    private val vacancies: ArrayList<Vacancy> = ArrayList()
    private val adapter: VacancyAdapter = VacancyAdapter(vacancies)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegionSelectionBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createTextWatcher()
        binding!!.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        adapter.itemClickListener = { vacancy ->
            if (viewModel.clickDebounce()) {
            }
        }

    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun createTextWatcher() {
        binding!!.etRegion.doOnTextChanged { s: CharSequence?, start: Int, count: Int, after: Int ->
            editTextValue = binding!!.etRegion.text.toString()
            if (editTextValue.isEmpty()) {
                binding!!.ivRegion.setImageResource(R.drawable.ic_search)
                hideAllView()
                breakSearch()
            } else {
                binding!!.ivRegion.setImageResource(R.drawable.ic_clear)
                if (editTextValue != viewModel.getRequestText()) {
                    startSearch()
                } else {
                    binding!!.rvRegion.visibility = View.GONE
                }
            }
        }
    }

    private fun startSearch() {
        viewModel.changeRequestText(binding!!.etRegion.text.toString())
        viewModel.searchDebounce()
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(binding!!.etRegion.windowToken, 0)
    }

    private fun hideAllView() {
        binding!!.tvNotFoundPlaceholder.isVisible = false
        binding!!.tvFailedRequestPlaceholder.isVisible = false
        binding!!.tvNoInternetPlaceholder.isVisible = false
        binding!!.tvFailedRequestPlaceholder.isVisible = false
    }

    private fun breakSearch() {
        viewModel.onDestroy()
        vacancies.clear()
        binding!!.rvRegion.visibility = View.GONE
    }
}

