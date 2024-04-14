package ru.practicum.android.diploma.ui.region

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentRegionSelectionBinding
import ru.practicum.android.diploma.domain.models.areas.AreaSubject
import ru.practicum.android.diploma.presentation.region.RegionSelectionViewModel
import ru.practicum.android.diploma.ui.region.adapter.RegionAdapter
import ru.practicum.android.diploma.ui.region.model.RegionFragmentStatus

class RegionSelectionFragment : Fragment() {

    private var binding: FragmentRegionSelectionBinding? = null
    private val viewModel by viewModel<RegionSelectionViewModel>()
    private var editTextValue = ""
    private val regions: ArrayList<AreaSubject> = ArrayList()
    private val regionAdapter: RegionAdapter = RegionAdapter(regions)

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

        binding!!.ivRegion.setOnClickListener {
            binding!!.etRegion.setText("")
        }

        regionAdapter.itemClickListener = { regionSubjects ->
            viewModel.setFilters(regionSubjects)
            findNavController().navigateUp()
        }

        binding!!.rvRegion.adapter = regionAdapter
        viewModel.regionStateData.observe(viewLifecycleOwner) {
            showRegion(it)
        }

        viewModel.getRegions()

    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showRegion(regionScreenStatus: RegionFragmentStatus) {
        hideKeyboard()
        when (regionScreenStatus) {
            is RegionFragmentStatus.ListOfRegions -> {
                regions.clear()
                regions.addAll(regionScreenStatus.regions)
                binding!!.rvRegion.visibility = View.VISIBLE
                hideAllPlaceholders()
                regionAdapter.notifyDataSetChanged()
            }

            is RegionFragmentStatus.NoConnection -> {
                binding!!.tvNoInternetPlaceholder.visibility = View.VISIBLE
                binding!!.tvNotFoundPlaceholder.visibility = View.GONE
                binding!!.tvFailedRequestPlaceholder.visibility = View.GONE
                binding!!.rvRegion.visibility = View.GONE
            }

            is RegionFragmentStatus.Bad -> {
                binding!!.tvFailedRequestPlaceholder.visibility = View.GONE
                binding!!.tvNotFoundPlaceholder.visibility = View.VISIBLE
                binding!!.tvNoInternetPlaceholder.visibility = View.GONE
                binding!!.rvRegion.visibility = View.GONE
            }

            is RegionFragmentStatus.NoLoaded -> {
                binding!!.tvFailedRequestPlaceholder.visibility = View.VISIBLE
                binding!!.tvNotFoundPlaceholder.visibility = View.GONE
                binding!!.tvNoInternetPlaceholder.visibility = View.GONE
                binding!!.rvRegion.visibility = View.GONE
            }
        }
    }

    private fun hideAllPlaceholders() {
        binding!!.tvNoInternetPlaceholder.visibility = View.GONE
        binding!!.tvNotFoundPlaceholder.visibility = View.GONE
        binding!!.tvFailedRequestPlaceholder.visibility = View.GONE
    }

    private fun createTextWatcher() {
        binding!!.etRegion.doOnTextChanged { s: CharSequence?, start: Int, count: Int, after: Int ->
            editTextValue = binding!!.etRegion.text.toString()
            if (editTextValue.isEmpty()) {
                binding!!.ivRegion.setImageResource(R.drawable.ic_search)
                hideAllView()
                breakSearch()
                viewModel.getAllRegions()
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
        binding!!.tvNotFoundPlaceholder.visibility = View.GONE
        binding!!.tvFailedRequestPlaceholder.visibility = View.GONE
        binding!!.tvNoInternetPlaceholder.visibility = View.GONE
        binding!!.tvFailedRequestPlaceholder.visibility = View.GONE
    }

    private fun breakSearch() {
        viewModel.onDestroy()
        binding!!.rvRegion.visibility = View.GONE
    }
}

