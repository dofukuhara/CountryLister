package com.fukuhara.douglas.countrylister.feature.countrylister

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fukuhara.douglas.common.arch.Either
import com.fukuhara.douglas.countrylister.feature.countrylister.business.model.CountryModel
import com.fukuhara.douglas.countrylister.feature.countrylister.business.repository.CountryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountryListViewModel(
    private val repository: CountryRepository,
    private val backgroundDispatcher: CoroutineDispatcher
) : ViewModel() {

    // Handle Loading/Progress State
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState

    // Flag to notify if the repository call failed
    private val _isErrorState = MutableLiveData<Boolean>()
    val isErrorState: LiveData<Boolean> = _isErrorState

    // LiveData that will hold the Country List model received from repository
    private val _countryListModel = MutableLiveData<List<CountryModel>>()
    val countryListModel: LiveData<List<CountryModel>> = _countryListModel

    fun getData() {
        viewModelScope.launch {
            _loadingState.value = true

            // As it is a heavy call, let's run it in another dispatcher
            val countryListResult = withContext(backgroundDispatcher) {
                repository.fetchList()
            }

            _loadingState.value = false

            when (countryListResult) {
                is Either.Left -> {
                    _isErrorState.value = true
                }
                is Either.Right -> {
                    _isErrorState.value = false
                    _countryListModel.value = countryListResult.data
                }
            }
        }
    }
}