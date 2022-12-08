package com.fukuhara.douglas.countrylister.feature.countrylister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fukuhara.douglas.countrylister.feature.countrylister.business.repository.CountryRepository
import kotlinx.coroutines.CoroutineDispatcher

class CountryListViewModelFactory(
    private val repository: CountryRepository,
    private val backgroundDispatcher: CoroutineDispatcher
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CountryListViewModel(repository, backgroundDispatcher) as T
    }
}