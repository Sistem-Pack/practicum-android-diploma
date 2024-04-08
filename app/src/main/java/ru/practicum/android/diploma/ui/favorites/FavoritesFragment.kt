package ru.practicum.android.diploma.ui.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.domain.models.vacancy.Vacancy
import ru.practicum.android.diploma.presentation.favorites.FavoritesViewModel

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FavoritesViewModel>()

    private val adapter = VacancyAdapter {
        if (viewModel.clickDebounce()) {
            startJobVacancy(it.vacancyId)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFavoriteVacanciesId()

        viewModel.observeFavoritesScreenState().observe(viewLifecycleOwner) {
            when (it) {
                is FavoritesScreenState.VacanciesUploaded -> {
                    if (it.vacancies.size == 0) {
                        viewModel.getFavoriteVacancies()
                    } else {
                        showVacanciesList(it.vacancies)
                    }
                }

                is FavoritesScreenState.VacanciesIdUploaded -> {
                    if (it.vacanciesId.size == 0) {
                        showPlaceholderEmptyList()
                    } else {
                        viewModel.getFavoriteVacancies()
                    }
                }

                is FavoritesScreenState.UploadingProcess -> showLoading()
                is FavoritesScreenState.NoFavoritesVacancies -> showPlaceholderEmptyList()
                is FavoritesScreenState.FailedRequest -> {
                    showPlaceholderFailedRequest()
                    Log.e("AAA", "ошибка: ${it.error}")
                }
            }
        }

        binding.rvRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvRecyclerView.adapter = adapter

        binding.rvRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val pos =
                        (binding.rvRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = adapter.itemCount
                    if (pos >= itemsCount - 1) {
                        viewModel.getFavoriteVacancies()
                    }
                }
            }
        })

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun showPlaceholderFailedRequest() {
        binding.ivPlaceholderImage.setImageResource(R.drawable.placeholder_failed_request)
        binding.tvPlaceholderMessage.text = getString(R.string.failed_request)
        adapter.vacancies.clear()
        adapter.notifyDataSetChanged()
        binding.pbLoading.isVisible = false
        binding.ivPlaceholderImage.isVisible = true
        binding.tvPlaceholderMessage.isVisible = true
    }

    private fun showPlaceholderEmptyList() {
        binding.ivPlaceholderImage.setImageResource(R.drawable.ic_empty_list)
        binding.tvPlaceholderMessage.text = getString(R.string.empty_list)
        adapter.vacancies.clear()
        adapter.notifyDataSetChanged()
        binding.pbLoading.isVisible = false
        binding.ivPlaceholderImage.isVisible = true
        binding.tvPlaceholderMessage.isVisible = true
    }

    private fun showVacanciesList(vacancies: ArrayList<Vacancy>) {
        binding.ivPlaceholderImage.isVisible = false
        binding.tvPlaceholderMessage.isVisible = false
        binding.pbLoading.isVisible = false
        adapter.vacancies = vacancies
        adapter.notifyDataSetChanged()
    }

    private fun showLoading() {
        binding.ivPlaceholderImage.isVisible = false
        binding.tvPlaceholderMessage.isVisible = false
        binding.pbLoading.isVisible = true
    }

    private fun startJobVacancy(vacancyId: String) {
        findNavController().navigate(
            FavoritesFragmentDirections.actionFavoritesFragmentToJobVacancyFragment(vacancyId)
        )
    }

}
