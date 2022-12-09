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

    private val viewModel : CountryListViewModel by viewModels {
        CountryListViewModelFactory(
            repository = CountryRemoteRepository(api = RetrofitClient.getApi(CountryRemoteApi::class.java), mapper = CountryModelMapper(AppLogger())),
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

        // As there is no versioning or timestamp of the result data, we will perform a reload of
        // this data whenever the screen is reloaded.
        // Depending on the use case, we could "cache" this data to prevent multiple network calls.
        viewModel.getData()
        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        handleLoadingState()
        handleErrorState()
        handleCountryListData()
    }

    private fun handleLoadingState() {
        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            when (isLoading) {
                true -> binding.countryListerPb.show()
                false -> binding.countryListerPb.hide()
            }
        }
    }

    private fun handleErrorState() {
        viewModel.isErrorState.observe(viewLifecycleOwner) { isErrorState ->
            when (isErrorState) {
                true -> Unit // Handle Error case
                false -> Unit // Dismiss any error view or message from this fragment
            }
        }
    }

    private fun handleCountryListData() {
        viewModel.countryListModel.observe(viewLifecycleOwner) { countryListData ->
            val adapter = CountryListAdapter(countryListData)
            binding.countryListerRv.adapter = adapter
        }
    }
}