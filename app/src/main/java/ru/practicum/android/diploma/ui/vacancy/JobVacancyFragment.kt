package ru.practicum.android.diploma.ui.vacancy

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentJobVacancyBinding
import ru.practicum.android.diploma.domain.db.FavoriteVacancyState
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.domain.models.VacancyDetailsResult
import ru.practicum.android.diploma.domain.models.vacancy.VacancyDetails
import ru.practicum.android.diploma.presentation.vacancy.JobVacancyViewModel

class JobVacancyFragment : Fragment() {

    private var binding: FragmentJobVacancyBinding? = null
    private val viewModel by viewModel<JobVacancyViewModel>()
    private var vacancyId: String? = null
    private val args: JobVacancyFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentJobVacancyBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.ivBack?.setOnClickListener {
            findNavController().popBackStack()
        }
        binding?.ivFavorites?.setOnClickListener {
            //viewModel.clickToFavorite(vacancyId)
        }

        viewModel.vacancyDetails.observe(viewLifecycleOwner) {
            observeVacancyDetails(it)
        }

        viewModel.checkIsFavorite.observe(viewLifecycleOwner) {
            observerFavoriteVacancy(it)
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        vacancyId = args.vacancyId
        if (vacancyId == null) {
            findNavController().popBackStack();
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.vacancyDetails.observe(viewLifecycleOwner) {
            observeVacancyDetails(it)
        }
        viewModel.checkIsFavorite.observe(viewLifecycleOwner) {
            observerFavoriteVacancy(it)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun observeVacancyDetails(vacancyDetails: VacancyDetailsResult) {
        when (vacancyDetails.responseStatus) {
            ResponseStatus.OK -> showVacancyDetails(vacancyDetails.results)
            ResponseStatus.LOADING -> showProgress()
            else -> viewModel.checkFavorite(vacancyId!!)
        }
    }

    private fun observerFavoriteVacancy(favoriteState: FavoriteVacancyState) {
        when(favoriteState) {
            is FavoriteVacancyState.SuccessfulRequest -> {
                showVacancyDetails(favoriteState.vacancy)
            }
            is FavoriteVacancyState.FailedRequest -> showErrorMessage()
        }
    }

    private fun showVacancyDetails(vacancyDetails: VacancyDetails?) {
        binding.apply {
            binding?.group?.visibility = View.VISIBLE
            binding?.ivFavorites?.visibility = View.VISIBLE
            binding?.ivFavorites?.visibility = View.VISIBLE
            binding?.pbVacancy?.visibility = View.INVISIBLE
            binding?.tvServerErrorVacancyPlaceholder?.visibility = View.INVISIBLE
            binding?.tvNoInternetPlaceholderVacancy?.visibility = View.INVISIBLE
        }
    }

    private fun showProgress() {
        binding.apply {
            binding?.group?.visibility = View.INVISIBLE
            binding?.ivFavorites?.visibility = View.INVISIBLE
            binding?.ivFavorites?.visibility = View.INVISIBLE
            binding?.pbVacancy?.visibility = View.VISIBLE
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
            /*if (vacancyId.contactsComment.isEmpty()) {
                binding?.tvCommentValue?.visibility = View.GONE
                binding?.tvComment?.visibility = View.GONE
            }*/
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

}
