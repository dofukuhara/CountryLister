package com.fukuhara.douglas.countrylister.feature.countrylister

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fukuhara.douglas.common.arch.left
import com.fukuhara.douglas.common.arch.right
import com.fukuhara.douglas.common.debug.Logger
import com.fukuhara.douglas.common.exception.NoDataFound
import com.fukuhara.douglas.countrylister.feature.countrylister.business.model.CountriesVo
import com.fukuhara.douglas.countrylister.feature.countrylister.business.model.CountryModelMapper
import com.fukuhara.douglas.countrylister.feature.countrylister.business.repository.CountryRepository
import com.fukuhara.douglas.testutils.MainCoroutineRule
import com.fukuhara.douglas.testutils.Parser
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CountryListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule(testDispatcher)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository: CountryRepository = mockk(relaxed = true)
    private val logger: Logger = mockk(relaxed = true)

    private lateinit var viewModel: CountryListViewModel

    @Before
    fun setup() {
        viewModel = CountryListViewModel(repository, testDispatcher)
    }

    @Test
    fun `given a NoDataFound error response, When fetching country list, Then viewModel should notify an error to the View`() = runTest {
        // Given
        coEvery { repository.fetchList() } returns NoDataFound("Some (un)expected error happened").left()

        // When
        viewModel.getData()
        advanceUntilIdle()

        // Then
        assertThat("isErrorState must be true, indicating that an error happened when fetching country list", viewModel.isErrorState.value, `is`(true))
        assertNull("As a fetching error happened, CountryListModel must be NULL", viewModel.countryListModel.value)
    }

    @Test
    fun `given a response with only one country, When fetching country list, Then countryListModel must hold this content`() = runTest {
        // Given
        val countriesVoRef = Parser.jsonToObject(
            this as Any, "json/country_list_singleitem_fullelement.json", CountriesVo::class.java
        )
        val countryListModelRef = CountryModelMapper(logger).transform(countriesVoRef)
        coEvery { repository.fetchList() } returns countryListModelRef.right()

        // When
        viewModel.getData()
        advanceUntilIdle()

        // Then
        assertThat("isErrorState must be false, indicating that no error happened when fetching country list", viewModel.isErrorState.value, `is`(false))
        assertThat("countryListModel list must be of length 1", viewModel.countryListModel.value?.size, `is`(1))
        // Note: The content of countryListModel was already tested in "CountryListViewModelTest"
    }
}