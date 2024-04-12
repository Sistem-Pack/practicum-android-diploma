package ru.practicum.android.diploma.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.domain.models.vacancy.Vacancy
import ru.practicum.android.diploma.presentation.main.MainViewModel
import ru.practicum.android.diploma.ui.main.model.MainFragmentStatus
import ru.practicum.android.diploma.ui.main.vacancy.EmptyItemAdapter
import ru.practicum.android.diploma.ui.main.vacancy.LoadingItemAdapter
import ru.practicum.android.diploma.ui.main.vacancy.VacancyAdapter

class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null
    private var editTextValue = ""
    private val vacancies: ArrayList<Vacancy> = ArrayList()
    private val adapter: VacancyAdapter = VacancyAdapter(vacancies)
    private var loadingItemAdapter = LoadingItemAdapter()
    private var concatAdapter: ConcatAdapter = ConcatAdapter()

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createTextWatcher()

        adapter.itemClickListener = { vacancy ->
            if (viewModel.clickDebounce()) {
                startJobVacancyFragment(vacancy.vacancyId)
            }
        }
        val emptyItemAdapter = EmptyItemAdapter()
        concatAdapter = ConcatAdapter(emptyItemAdapter, adapter, loadingItemAdapter)

        binding!!.rvVacancyList.adapter = concatAdapter
        binding!!.ivSearch.setOnClickListener {
            binding!!.etSearch.setText("")
            hideAllView()
            breakSearch()
        }

        binding!!.ivFilter.setOnClickListener {
            if (viewModel.clickDebounce()) {
                startFilteringSettingsFragment()
            }
        }

        viewModel.listOfVacancies.observe(viewLifecycleOwner) {
            processingSearchStatus(it)
        }
        viewModel.page.observe(viewLifecycleOwner) {
            loadingItemAdapter.visible = viewModel.page.value!! < viewModel.getMaxPages() - 1
        }

        binding!!.rvVacancyList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val pos =
                        (binding!!.rvVacancyList.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = adapter.itemCount
                    if (pos >= itemsCount - 1 && viewModel.scrollDebounce()) {
                        if (viewModel.page.value!! < viewModel.getMaxPages() - 1) {
                            viewModel.installPage(true)
                            viewModel.search()
                        }
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        binding = null
        viewModel.onDestroy()
        super.onDestroyView()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onDestroyView()
        super.onViewStateRestored(savedInstanceState)
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(binding!!.etSearch.windowToken, 0)
    }

    private fun startJobVacancyFragment(vacancyId: String) {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToJobVacancyFragment(vacancyId)
        )
    }

    private fun startFilteringSettingsFragment() {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToFilteringSettingsFragment()
        )
    }

    private fun startSearch() {
        viewModel.installPage(false)
        viewModel.changeRequestText(binding!!.etSearch.text.toString())
        viewModel.searchDebounce()
    }

    private fun createTextWatcher() {
        binding!!.etSearch.doOnTextChanged { s: CharSequence?, start: Int, count: Int, after: Int ->
            editTextValue = binding!!.etSearch.text.toString()
            if (editTextValue.isEmpty()) {
                binding!!.ivSearch.setImageResource(R.drawable.ic_search)
                hideAllView()
                breakSearch()
            } else {
                binding!!.ivSearch.setImageResource(R.drawable.ic_clear)
                binding!!.ivSearchPlaceholder.isVisible = false
                if (editTextValue != viewModel.getRequestText()) {
                    startSearch()
                } else {
                    binding!!.rvVacancyList.isVisible = true
                    binding!!.chip.text = madeTextForChip()
                    binding!!.chip.isVisible = true
                }
            }
        }
    }

    private fun processingSearchStatus(mainFragmentStatus: MainFragmentStatus) {
        hideAllView()
        hideKeyboard()
        when (mainFragmentStatus) {
            is MainFragmentStatus.ListOfVacancies -> {
                showOkStatus(mainFragmentStatus.vacancies)
            }

            is MainFragmentStatus.Loading -> {
                showLoadingStatus()
            }

            is MainFragmentStatus.Default -> {
                showDefaultStatus()
            }

            is MainFragmentStatus.Bad -> {
                showBadStatus()
            }

            is MainFragmentStatus.NoConnection -> {
                showNoConnectionStatus()
            }

            is MainFragmentStatus.showToastOnLoadingTrouble -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_in_loading_process),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun hideAllView() {
        binding!!.ivSearchPlaceholder.isVisible = false
        binding!!.tvServerErrorPlaceholder.isVisible = false
        binding!!.tvNoInternetPlaceholder.isVisible = false
        binding!!.tvFailedRequestPlaceholder.isVisible = false
        binding!!.pbSearch.isVisible = false
    }

    private fun showLoadingStatus() {
        if (viewModel.page.value!! == 0) {
            binding!!.pbSearch.isVisible = true
        }
    }

    private fun showOkStatus(listVacancies: List<Vacancy>) {
        vacancies.clear()
        if (viewModel.page.value!! == 0) {
            if (listVacancies.isNotEmpty()) {
                vacancies.addAll(listVacancies)
                adapter.notifyDataSetChanged()
                binding!!.chip.text = madeTextForChip()
                binding!!.rvVacancyList.isVisible = true
                binding!!.chip.isVisible = true
            } else {
                binding!!.chip.isVisible = true
                binding!!.chip.text = requireContext().getString(R.string.no_vacancy)
                binding!!.rvVacancyList.isVisible = false
                binding!!.tvFailedRequestPlaceholder.isVisible = true
            }
        } else {
            vacancies.addAll(listVacancies)
            adapter.notifyDataSetChanged()
        }
    }

    private fun showDefaultStatus() {
        vacancies.clear()
        binding!!.rvVacancyList.isVisible = false
        binding!!.ivSearchPlaceholder.isVisible = true
    }

    private fun showBadStatus() {
        vacancies.clear()
        binding!!.rvVacancyList.isVisible = false
        binding!!.tvServerErrorPlaceholder.isVisible = true
    }

    private fun showNoConnectionStatus() {
        vacancies.clear()
        binding!!.chip.isVisible = false
        binding!!.rvVacancyList.isVisible = false
        binding!!.tvNoInternetPlaceholder.isVisible = true
    }

    private fun breakSearch() {
        viewModel.onDestroy()
        vacancies.clear()
        binding!!.ivSearchPlaceholder.isVisible = true
        binding!!.pbSearch.isVisible = false
        binding!!.chip.isVisible = false
        binding!!.rvVacancyList.isVisible = false
    }

    private fun madeTextForChip(): String {
        return requireContext().resources.getQuantityString(R.plurals.found, viewModel.getFoundVacancies()) +
            " " + viewModel.getFoundVacancies().toString() + " " +
            requireContext().resources.getQuantityString(R.plurals.vacancy, viewModel.getFoundVacancies())
    }
}
