package ru.practicum.android.diploma.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.domain.models.vacancy.Vacancy
import ru.practicum.android.diploma.presentation.main.MainViewModel
import ru.practicum.android.diploma.ui.main.vacancy.VacancyAdapter

class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null
    private var editTextValue = ""
    private lateinit var vacancies: ArrayList<Vacancy>
    private lateinit var adapter: VacancyAdapter
    private lateinit var simpleTextWatcher: TextWatcher

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vacancies = ArrayList<Vacancy>()
        adapter = VacancyAdapter(vacancies)
        createTextWatcher()

        adapter.itemClickListener = { vacancy ->
            startJobVacancyFragment(vacancy.vacancyId)
        }

        binding!!.etSearch.addTextChangedListener(simpleTextWatcher)
        binding!!.rvVacancyList.adapter = adapter
        binding!!.ivSearch.setOnClickListener {
            binding!!.etSearch.setText("")
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun startJobVacancyFragment(vacancyId: String) {

    }

    private fun startSearch(){
        changeViewsVisibility(true)
    }

    private fun changeViewsVisibility(action: Boolean){
        binding!!.ivSearchPlaceholder.isVisible = !action
        binding!!.rvVacancyList.isVisible = action
    }

    private fun createTextWatcher(){
        simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editTextValue = binding!!.etSearch.text.toString()
                if (editTextValue.isEmpty()) {
                    binding!!.ivSearch.setImageResource(R.drawable.ic_search)
                    changeViewsVisibility(false)
                } else {
                    binding!!.ivSearch.setImageResource(R.drawable.ic_clear)
                    startSearch()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
    }
}
