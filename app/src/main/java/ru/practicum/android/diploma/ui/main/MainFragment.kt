package ru.practicum.android.diploma.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.domain.models.vacancy.Vacancy
import ru.practicum.android.diploma.presentation.main.MainViewModel
import ru.practicum.android.diploma.ui.main.model.MainFragmentStatus
import ru.practicum.android.diploma.ui.main.vacancy.VacancyAdapter

class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null
    private var editTextValue = ""
    private val vacancies: ArrayList<Vacancy> = ArrayList()
    private val adapter: VacancyAdapter = VacancyAdapter(vacancies)

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

        binding!!.rvVacancyList.adapter = adapter
        binding!!.ivSearch.setOnClickListener {
            binding!!.etSearch.setText("")
            hideAllView()
            breakSearch()
        }

        viewModel.listOfVacancies.observe(viewLifecycleOwner) {
            processingSearchStatus(it)
        }

        binding!!.rvVacancyList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val pos = (binding!!.rvVacancyList.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = adapter.itemCount
                    if (pos >= itemsCount-1) {
                        if (viewModel.scrollDebounce()) {
                            viewModel.installPage(true)
                            viewModel.search()
                            Log.d("BABAYLOV", "${vacancies.size}")
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

    private fun hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(binding!!.etSearch.windowToken, 0)
    }

    private fun startJobVacancyFragment(vacancyId: String) {

    }

    private fun startSearch() {
        changeViewsVisibility(true)
        viewModel.installPage(false)
        viewModel.changeRequestText(binding!!.etSearch.text.toString())
        viewModel.searchDebounce()
    }

    private fun changeViewsVisibility(action: Boolean) {
        binding!!.ivSearchPlaceholder.isVisible = !action
    }

    private fun createTextWatcher() {
        binding!!.etSearch.doOnTextChanged { s: CharSequence?, start: Int, count: Int, after: Int ->
            editTextValue = binding!!.etSearch.text.toString()
            if (editTextValue.isEmpty()) {
                binding!!.ivSearch.setImageResource(R.drawable.ic_search)
                changeViewsVisibility(false)
                breakSearch()
            } else {
                binding!!.ivSearch.setImageResource(R.drawable.ic_clear)
                startSearch()
            }
        }
    }

    private fun processingSearchStatus(mainFragmentStatus: MainFragmentStatus) {
        vacancies.clear()
        hideAllView()
        hideKeyboard()
        when (mainFragmentStatus) {
            is MainFragmentStatus.ListOfVacancies -> {
                showOkStatus(mainFragmentStatus.vacancies, mainFragmentStatus.found)
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
        }
    }

    private fun hideAllView() {
        binding!!.ivSearchPlaceholder.isVisible = false
        binding!!.tvServerErrorPlaceholder.isVisible = false
        binding!!.tvNoInternetPlaceholder.isVisible = false
        binding!!.tvFailedRequestPlaceholder.isVisible = false
        binding!!.pbSearch.isVisible = false
        binding!!.pbLoading.isVisible = false
        binding!!.vBackGroundForPBLoading.isVisible = false
    }

    private fun showLoadingStatus() {
        if (viewModel.getPage() == 0) {
            binding!!.pbSearch.isVisible = true
        } else {
            binding!!.pbLoading.isVisible = true
            binding!!.vBackGroundForPBLoading.isVisible = true
        }

    }

    private fun showOkStatus(listVacancies: List<Vacancy>, vacanciesFound: Int) {
        if (viewModel.getPage() == 0) {
            if (listVacancies.isNotEmpty()) {
                vacancies.addAll(listVacancies)
                adapter.notifyDataSetChanged()
                binding!!.chip.text =
                    requireContext().resources.getQuantityString(R.plurals.found, vacanciesFound) +
                        " " + vacanciesFound.toString() + " " +
                        requireContext().resources.getQuantityString(R.plurals.vacancy, vacanciesFound)
                binding!!.rvVacancyList.isVisible = true
                binding!!.chip.isVisible = true
            } else {
                binding!!.chip.isVisible = true
                binding!!.chip.text = requireContext().getString(R.string.no_vacancy)
                binding!!.tvFailedRequestPlaceholder.isVisible = true
            }
        } else {
            vacancies.addAll(listVacancies)
            adapter.notifyDataSetChanged()
        }
    }

    private fun showDefaultStatus() {
        binding!!.rvVacancyList.isVisible = false
        binding!!.ivSearchPlaceholder.isVisible = true
    }

    private fun showBadStatus() {
        binding!!.rvVacancyList.isVisible = false
        binding!!.tvServerErrorPlaceholder.isVisible = true
    }

    private fun showNoConnectionStatus() {
        binding!!.rvVacancyList.isVisible = false
        binding!!.tvNoInternetPlaceholder.isVisible = true
    }

    private fun breakSearch() {
        viewModel.onDestroy()
        binding!!.ivSearchPlaceholder.isVisible = true
        binding!!.pbSearch.isVisible = false
        binding!!.chip.isVisible = false
        binding!!.rvVacancyList.isVisible = false
    }
}
