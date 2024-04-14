package ru.practicum.android.diploma.ui.country

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentSelectCountryBinding
import ru.practicum.android.diploma.domain.models.areas.AreaCountry
import ru.practicum.android.diploma.presentation.country.SelectCountryViewModel
import ru.practicum.android.diploma.ui.country.adapter.CountryAdapter
import ru.practicum.android.diploma.ui.country.model.CountryFragmentStatus

class SelectCountryFragment : Fragment() {

    private var binding: FragmentSelectCountryBinding? = null
    private val viewModel by viewModel<SelectCountryViewModel>()
    private var countries: ArrayList<AreaCountry> = ArrayList()
    private val countryAdapter: CountryAdapter = CountryAdapter(countries)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSelectCountryBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        countryAdapter.itemClickListener = { areaCountry ->
            viewModel.setFilters(areaCountry)
            findNavController().navigateUp()
        }

        binding!!.rvCountry.adapter = countryAdapter
        viewModel.showAreas()
        viewModel.countryStateData.observe(viewLifecycleOwner) {
            showArea(it)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun hideAllPlaceholders() {
        binding!!.tvNoInternetPlaceholder.visibility = View.GONE
        binding!!.tvNotFoundPlaceholder.visibility = View.GONE
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showArea(countryScreenStatus: CountryFragmentStatus) {
        when (countryScreenStatus) {
            is CountryFragmentStatus.ListOfCountries -> {
                countries.clear()
                countries.addAll(countryScreenStatus.countries)
                binding!!.rvCountry.visibility = View.VISIBLE
                hideAllPlaceholders()
                countryAdapter.notifyDataSetChanged()
            }

            is CountryFragmentStatus.NoConnection -> {
                binding!!.tvNoInternetPlaceholder.visibility = View.VISIBLE
                binding!!.tvNotFoundPlaceholder.visibility = View.GONE
                binding!!.rvCountry.visibility = View.GONE
            }

            is CountryFragmentStatus.Bad, CountryFragmentStatus.NoLoaded -> {
                binding!!.tvNotFoundPlaceholder.visibility = View.VISIBLE
                binding!!.tvNoInternetPlaceholder.visibility = View.GONE
                binding!!.rvCountry.visibility = View.GONE
            }
        }
    }
}
