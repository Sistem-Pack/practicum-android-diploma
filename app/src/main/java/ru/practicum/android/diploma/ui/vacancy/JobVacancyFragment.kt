package ru.practicum.android.diploma.ui.vacancy

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentJobVacancyBinding
import ru.practicum.android.diploma.domain.db.FavoriteVacanciesIdState
import ru.practicum.android.diploma.domain.models.vacancy.VacancyDetails
import ru.practicum.android.diploma.presentation.vacancy.JobVacancyViewModel

class JobVacancyFragment : Fragment() {

    private var binding: FragmentJobVacancyBinding? = null

    private val viewModel by viewModel<JobVacancyViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentJobVacancyBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.showDetailVacancy(KEY_VACANCY)
        observeViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel.showDetailVacancy(KEY_VACANCY)
        binding?.group?.visibility = View.GONE
        //observeViewModel()
        binding?.ivBack?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }


    private fun observeViewModel() {
        viewModel. .observe(viewLifecycleOwner) { state ->
            when (state) {
                is FavoriteVacanciesIdState.SuccessfulRequest -> showDetails(it.vacancyId)
                is FavoriteVacanciesIdState.FailedRequest -> showErrorMessage()
            }
        }
    }

    private fun showErrorMessage() {
        binding.apply {
            binding?.group?.visibility = View.INVISIBLE
            binding?.tvServerErrorVacancyPlaceholder?.visibility = View.VISIBLE
        }
    }


    private fun hideContactsIfEmpty(vacancyId: VacancyDetails) {
            if (vacancyId.contactsName.isEmpty()) {
                binding?.tvContactPersonValue?.visibility = View.GONE
                binding?.tvContactPerson?.visibility = View.GONE
            }
            if (vacancyId.contactsEmail.isEmpty()) {
                binding?.tvContactEmailValue?.visibility = View.GONE
                binding?.tvContactEmail?.visibility = View.GONE
            }
            if (vacancyId.contactsPhones.isEmpty()) {
                binding?.tvContactPhoneValue?.visibility = View.GONE
                binding?.tvContactPhone?.visibility = View.GONE
            }
        //у нас в модели отсутствует поле комментария к вакансии!! Потерялось, надо добавить
            if (vacancyId.contactsComment.isEmpty()) {
                binding?.tvCommentValue?.visibility = View.GONE
                binding?.tvComment?.visibility = View.GONE
            }
            if (vacancyId.contactsName.isEmpty() &&
                vacancyId.contactsEmail.isEmpty() &&
                vacancyId.contactsPhones.isEmpty()
            ) {
                binding?.ContactBox?.visibility = View.GONE
            }
    }

    private fun showKeySkills(vacancyId: VacancyDetails) {
            if (vacancyId.keySkills.isEmpty()) {
                binding?.tvKeySkills?.visibility = View.GONE
                binding?.tvKeySkillsDescription?.visibility = View.GONE
            } else {
                binding?.tvKeySkillsDescription?.text = vacancyId.keySkills
            }
    }

    private fun favoriteClickInit(vacancyId: VacancyDetails) {
        binding?.ivFavorites?.setOnClickListener {
            viewModel.clickToFavorite(vacancyId)
        }
    }

    companion object {
        const val KEY_VACANCY = "vacancyId"
        fun createArgs(vacancyId: String): Bundle? {
            val bundle = Bundle()
            bundle.putString(KEY_VACANCY, vacancyId)
            return bundle
        }
    }

}
