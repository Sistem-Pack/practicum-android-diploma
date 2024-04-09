package ru.practicum.android.diploma.ui.vacancy

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentJobVacancyBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.ivBack?.setOnClickListener {
            findNavController().popBackStack()
        }
        binding?.ivFavorites?.setOnClickListener {
            viewModel.clickToFavorite()
        }
        binding?.ivShare?.setOnClickListener {
            viewModel.shareURL(vacancyId!!)
        }
        binding?.tvContactEmail?.setOnClickListener {
            viewModel.sendMail(binding?.tvContactEmail?.text.toString())
        }
        binding?.tvContactPhone?.setOnClickListener {
            viewModel.makeCall(binding?.tvContactPhone?.text.toString())
        }
        viewModel.vacancyDetails.observe(viewLifecycleOwner) {
            observeVacancyDetails(it)
        }

        viewModel.checkIsFavorite.observe(viewLifecycleOwner) {
            observerFavoriteVacancy(it)
        }
        viewModel.showDetailVacancy(vacancyId!!)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        vacancyId = args.vacancyId
        if (vacancyId!!.isEmpty()) {
            findNavController().popBackStack()
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
            ResponseStatus.NO_CONNECTION -> showNoInternetConnection()
            else -> viewModel.checkFavorite(vacancyId!!)
        }
    }

    private fun observerFavoriteVacancy(favoriteState: FavoriteVacancyState) {
        when (favoriteState) {
            is FavoriteVacancyState.SuccessfulRequest -> {
                showVacancyDetails(favoriteState.vacancy)
            }

            is FavoriteVacancyState.FailedRequest -> showErrorMessage()
        }
    }

    private fun showVacancyDetails(vacancyDetails: VacancyDetails?) {
        binding?.apply {
            group.visibility = View.VISIBLE
            ivFavorites.visibility = View.VISIBLE
            ivShare.visibility = View.VISIBLE
            tvVacancyName.text = vacancyDetails?.vacancyName
            tvSalary.text = vacancyDetails?.salary
            tvCompanyName.text = vacancyDetails?.employer
            if (vacancyDetails?.areaId == "null") {
                tvCity.text = vacancyDetails.areaRegion
            } else {
                tvCity.text = vacancyDetails?.areaId
            }
            tvExperienceField.text = vacancyDetails?.experienceName
            tvTypeOfEmployment.text = vacancyDetails?.employmentType
            tvResponsibilitiesValue.text = Html.fromHtml(
                vacancyDetails?.description,
                Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM
            )
            if (vacancyDetails?.keySkills?.isEmpty() == true) {
                tvKeySkills.visibility = View.GONE
                tvKeySkillsDescription.visibility = View.GONE
            } else {
                tvKeySkills.visibility = View.VISIBLE
                binding?.tvKeySkillsDescription?.text = getKeySkills(vacancyDetails?.keySkills)
            }
            if (vacancyDetails?.contactsEmail != "null" && vacancyDetails?.contactsName != "null" &&
                vacancyDetails?.contactsPhones != ""
            ) {
                showContacts(vacancyDetails)
            } else {
                binding?.ContactBox?.visibility = View.GONE
            }
            showLogo(vacancyDetails?.artworkUrl)
            tvServerErrorVacancyPlaceholder.visibility = View.GONE
            tvNoInternetPlaceholderVacancy.visibility = View.GONE
            pbVacancy.visibility = View.GONE
        }
    }

    private fun showLogo(artWorkUrl: String?) {
        binding?.apply {
            Glide.with(ivLogoPlug)
                .load(artWorkUrl)
                .placeholder(R.drawable.logo_plug)
                .centerCrop()
                .transform(RoundedCorners(ivLogoPlug.resources.getDimensionPixelSize(R.dimen.three_space)))
                .into(ivLogoPlug)
        }
    }

    private fun showProgress() {
        binding?.apply {
            group.visibility = View.GONE
            ivFavorites.visibility = View.GONE
            ivShare.visibility = View.GONE
            pbVacancy.visibility = View.VISIBLE
        }
    }

    private fun showErrorMessage() {
        binding?.apply {
            group.visibility = View.GONE
            tvServerErrorVacancyPlaceholder.visibility = View.VISIBLE
        }
    }

    private fun showNoInternetConnection() {
        binding?.apply {
            group.visibility = View.GONE
            tvNoInternetPlaceholderVacancy.visibility = View.VISIBLE
        }
    }

    private fun getKeySkills(keySkills: String?): String {
        var keySkillsText = ""
        if (keySkills != null) {
            keySkillsText = "  \u2022   " + keySkills.substring(0, keySkills.length - 1).replace(";", "\n  \u2022   ")
        }
        return keySkillsText
    }

    private fun showContacts(vacancyDetails: VacancyDetails?) {
        binding?.apply {
            if (vacancyDetails?.contactsName == "null") {
                tvContactPerson.visibility = View.GONE
                tvContactPersonValue.visibility = View.GONE
            } else {
                tvContactPerson.visibility = View.VISIBLE
                tvContactPersonValue.visibility = View.VISIBLE
                tvContactPersonValue.text = vacancyDetails?.contactsName
            }
            if (vacancyDetails?.contactsEmail == "null") {
                tvContactEmail.visibility = View.GONE
                tvContactEmailValue.visibility = View.GONE
            } else {
                tvContactEmail.visibility = View.VISIBLE
                tvContactEmailValue.visibility = View.VISIBLE
                tvContactEmailValue.text = vacancyDetails?.contactsEmail
            }
            if (vacancyDetails?.contactsPhones == "") {
                tvContactPhone.visibility = View.GONE
                tvContactPhoneValue.visibility = View.GONE
                tvCommentValue.visibility = View.GONE
                tvComment.visibility = View.GONE
            } else {
                val phones = vacancyDetails?.contactsPhones?.split(";")?.get(0)
                val phone = phones?.split("^")?.get(0)
                val comment = phones?.split("^")?.get(1)?.replace("comment=", "")
                tvContactPhone.visibility = View.VISIBLE
                tvContactPhoneValue.visibility = View.VISIBLE
                tvContactPhoneValue.text = phone
                tvComment.visibility = View.VISIBLE
                tvCommentValue.visibility = View.VISIBLE
                tvCommentValue.text = comment
            }
        }
    }

}
