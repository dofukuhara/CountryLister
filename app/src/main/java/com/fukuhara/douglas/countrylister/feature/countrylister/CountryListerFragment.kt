package com.fukuhara.douglas.countrylister.feature.countrylister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fukuhara.douglas.common.debug.AppLogger
import com.fukuhara.douglas.countrylister.R
import com.fukuhara.douglas.countrylister.databinding.CountryListerFragmentBinding
import com.fukuhara.douglas.countrylister.feature.countrylister.adapter.CountryListAdapter
import com.fukuhara.douglas.countrylister.feature.countrylister.business.model.CountryModelMapper
import com.fukuhara.douglas.countrylister.feature.countrylister.business.repository.CountryRemoteApi
import com.fukuhara.douglas.countrylister.feature.countrylister.business.repository.CountryRemoteRepository
import com.fukuhara.douglas.network.RetrofitClient
import kotlinx.coroutines.Dispatchers

class CountryListerFragment : Fragment(R.layout.country_lister_fragment) {

    private var _binding: CountryListerFragmentBinding? = null
    private val binding get() = _binding!!
    private val logger = AppLogger()

    private val viewModel : CountryListViewModel by viewModels {
        CountryListViewModelFactory(
            repository = CountryRemoteRepository(api = RetrofitClient.getApi(CountryRemoteApi::class.java), mapper = CountryModelMapper(logger)),
            backgroundDispatcher = Dispatchers.IO)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = CountryListerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // We will perform a network fetch for this data when the screen is first loaded, preserving
        // its state when after rotating the device (retaining the dataset and the RecyclerView positioning).
        // Also, depending on the use case, we could cache or persist this data to prevent multiple network calls.
        if (savedInstanceState == null) {
            viewModel.getData()
        }
        setupViewModelObservers()
        setupTryAgainCtaBehaviour() // We will allow the user to refresh (make a new network call) only in case the previous one failed.
    }

    private fun setupViewModelObservers() {
        handleLoadingState()
        handleErrorState()
        handleCountryListData()
    }

    private fun handleLoadingState() {
        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            when (isLoading) {
                true -> {
                    // No other view than ProgressBar should be visible at this point...
                    binding.countryListerRv.visibility = View.INVISIBLE
                    binding.countryListerErrorViewGroup.visibility = View.GONE

                    binding.countryListerPb.show()
                }
                false -> binding.countryListerPb.hide()
            }
        }
    }

    private fun handleErrorState() {
        viewModel.isErrorState.observe(viewLifecycleOwner) { isErrorState ->
            when (isErrorState) {
                true -> binding.countryListerErrorViewGroup.visibility = View.VISIBLE
                false -> binding.countryListerErrorViewGroup.visibility = View.GONE
            }
        }
    }

    private fun handleCountryListData() {
        viewModel.countryListModel.observe(viewLifecycleOwner) { countryListData ->
            val adapter = CountryListAdapter(countryListData)
            binding.countryListerRv.adapter = adapter

            binding.countryListerRv.visibility = View.VISIBLE
        }
    }

    private fun setupTryAgainCtaBehaviour() {
        binding.countryListerTryAgainCta.setOnClickListener {
            viewModel.getData()
        }
    }
}