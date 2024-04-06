package ru.practicum.android.diploma.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.domain.models.vacancy.Vacancy
import ru.practicum.android.diploma.presentation.main.MainViewModel

class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null
    private var editTextValue = ""

    // private lateinit var adapter: VacancyAdapter
    private var vacancies = ArrayList<Vacancy>()

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        adapter = VacancyAdapter(vacancies)
//
//        adapter.itemClickListener = {it ->
//            //запустить фрагмент с деталями вакансии и проверить кликдебаунс
//            //startJobVacancyFragment(it.vacancyId)
//        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editTextValue = binding!!.etSearch.text.toString()
                if (editTextValue.isEmpty()) {
                    // viewModel.onDestroy()
                } else {
                    viewModel.changeRequestText(binding!!.etSearch.text.toString())
                    viewModel.searchDebounce()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        binding!!.etSearch.addTextChangedListener(simpleTextWatcher)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun startJobVacancyFragment(vacancyId: String) {
        val bundle = Bundle()
        bundle.putString(VACANCY_ID, vacancyId)
        findNavController().navigate(R.id.action_mainFragment_to_jobVacancyFragment, bundle)
    }

    companion object {
        private const val VACANCY_ID = "vacancy_id"
    }
}
