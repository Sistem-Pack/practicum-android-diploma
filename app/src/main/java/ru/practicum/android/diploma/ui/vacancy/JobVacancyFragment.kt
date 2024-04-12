package ru.practicum.android.diploma.ui.vacancy

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.util.Log
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
import ru.practicum.android.diploma.domain.models.vacancy.VacancyDetails
import ru.practicum.android.diploma.presentation.vacancy.JobVacancyViewModel

class JobVacancyFragment : Fragment() {

    private var binding: FragmentJobVacancyBinding? = null
    private val viewModel by viewModel<JobVacancyViewModel>()
    private var vacancyId: String? = null
    private val args: JobVacancyFragmentArgs by navArgs()
    private var vacancy: VacancyDetails? = null

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
            if (viewModel.clickDebounce()) {
                viewModel.clickToFavorite(vacancy!!)
            }
        }
        binding?.ivShare?.setOnClickListener {
            viewModel.shareURL(vacancyId!!)
        }
        binding?.tvContactEmailValue?.setOnClickListener {
            viewModel.sendMail(binding?.tvContactEmailValue?.text.toString())
        }
        binding?.tvContactPhoneValue?.setOnClickListener {
            viewModel.makeCall(binding?.tvContactPhoneValue?.text.toString())
        }
        viewModel.observeJobVacancyScreenState().observe(viewLifecycleOwner) {
            when (it) {
                is JobVacancyScreenState.VacancyUploaded -> {
                    showVacancyDetails(it.vacancy)
                    vacancy = it.vacancy
                }
                is JobVacancyScreenState.UploadingProcess -> showProgress()
                is JobVacancyScreenState.NoConnection -> showNoConnectionMessage()
                is JobVacancyScreenState.FailedRequest -> {
                    showErrorMessage()
                    Log.e(ERROR_TAG, "ошибка: ${it.error}")
                }
            }
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
        viewModel.observeJobVacancyScreenState().observe(viewLifecycleOwner) {
            when (it) {
                is JobVacancyScreenState.VacancyUploaded -> {
                    showVacancyDetails(it.vacancy)
                }
                is JobVacancyScreenState.UploadingProcess -> showProgress()
                is JobVacancyScreenState.NoConnection -> showNoConnectionMessage()
                is JobVacancyScreenState.FailedRequest -> {
                    showErrorMessage()
                    Log.e(ERROR_TAG, "ошибка: ${it.error}")
                }
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun showVacancyDetails(vacancyDetails: VacancyDetails?) {
        binding?.apply {
            clearAllPlaceholders()
            ivFavorites.visibility = View.VISIBLE
            ivShare.visibility = View.VISIBLE
            tvVacancyName.text = vacancyDetails?.vacancyName
            tvSalary.text = vacancyDetails?.salary
            showAdress(vacancyDetails)
            tvCompanyName.text = vacancyDetails?.employer
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
            if (vacancyDetails?.isFavorite == true) {
                binding?.ivFavorites?.setImageResource(R.drawable.ic_favorites_on)
            } else {
                binding?.ivFavorites?.setImageResource(R.drawable.ic_favorites_off)
            }
        }
    }

    private fun clearAllPlaceholders() {
        binding?.apply {
            group.visibility = View.VISIBLE
            tvServerErrorVacancyPlaceholder.visibility = View.GONE
            tvNoInternetPlaceholderVacancy.visibility = View.GONE
            pbVacancy.visibility = View.GONE
        }
    }

    private fun showAdress(vacancyDetails: VacancyDetails?) {
        binding?.apply {
            if (vacancyDetails?.areaId == "null") {
                tvCity.text = vacancyDetails.areaRegion
            } else {
                tvCity.text = vacancyDetails?.areaId
            }
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
            pbVacancy.visibility = View.GONE
            tvNoInternetPlaceholderVacancy.visibility = View.GONE
            tvServerErrorVacancyPlaceholder.visibility = View.VISIBLE
        }
    }

    private fun showNoConnectionMessage() {
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
                showPhonesAndComments(vacancyDetails)
            }
        }
    }

    private fun showPhonesAndComments(vacancyDetails: VacancyDetails?) {
        val phones = vacancyDetails?.contactsPhones?.split(";")?.get(0)
        val phone = phones?.split("^")?.get(0)
        val comment = phones?.split("^")?.get(1)?.replace("comment=", "")
        binding?.apply {
            tvContactPhone.visibility = View.VISIBLE
            tvContactPhoneValue.visibility = View.VISIBLE
            tvContactPhoneValue.text = phone
            if (comment!!.isNotEmpty()) {
                tvComment.visibility = View.VISIBLE
                tvCommentValue.visibility = View.VISIBLE
                tvCommentValue.text = comment
            } else {
                tvComment.visibility = View.GONE
                tvCommentValue.visibility = View.GONE
            }
        }
    }

    companion object {
        private const val ERROR_TAG = "VacancyDetailsFragmentError"
    }

}
