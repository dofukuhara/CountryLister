package com.fukuhara.douglas.countrylister.feature.countrylister.business.logic

import android.content.res.Resources
import com.fukuhara.douglas.countrylister.R
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class CountryCardFormatterTest {

    private val resources: Resources = mockk(relaxed = true)

    private companion object {
        const val EMPTY_STRING = ""
        const val DEFAULT_STRING = "-"

        // EMPTY country values
        const val EMPTY_COUNTRY_NAME = EMPTY_STRING
        const val EMPTY_COUNTRY_REGION = EMPTY_STRING
        const val EMPTY_COUNTRY_CODE = EMPTY_STRING
        const val EMPTY_COUNTRY_CAPITAL = EMPTY_STRING

        // VALID country values
        const val VALID_COUNTRY_NAME = "Canada"
        const val VALID_COUNTRY_REGION = "NA"
        const val VALID_COUNTRY_CODE = "CA"
        const val VALID_COUNTRY_CAPITAL = "Ottawa"
    }

    @Before
    fun setUp() {
        every { resources.getString(R.string.country_card_country_empty_value) } returns DEFAULT_STRING

        every { resources.getString(R.string.country_card_country_template, DEFAULT_STRING, DEFAULT_STRING) } returns "-, -"
        every { resources.getString(R.string.country_card_country_template, DEFAULT_STRING, "NA") } returns "-, NA"
        every { resources.getString(R.string.country_card_country_template, "Canada", DEFAULT_STRING) } returns "Canada, -"
        every { resources.getString(R.string.country_card_country_template, "Canada", "NA") } returns "Canada, NA"
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `given that all fields are empty Strings, When formatting to present data to Country Card, Then all fields will be filled with dashes`() {
        // Given
        val countryName = EMPTY_COUNTRY_NAME
        val countryRegion = EMPTY_COUNTRY_REGION
        val countryCode = EMPTY_COUNTRY_CODE
        val countryCapital = EMPTY_COUNTRY_CAPITAL

        // When
        val result = CountryCardFormatter.format(resources, countryName, countryRegion, countryCode, countryCapital)

        // Then
        assertThat("As countryName and countryRegion are empty, then replace by dashes", result.name, `is`("-, -"))
        assertThat("As countryCode is empty, then replace it by a dash", result.code, `is`("-"))
        assertThat("As countryCapital is empty, then replace it by a dash", result.capital, `is`("-"))
    }

    @Test
    fun `given that only countryName is an empty String, When formatting to present data to Country Card, Then only country name part will be replaced by dash`() {
        // Given
        val countryName = EMPTY_COUNTRY_NAME
        val countryRegion = VALID_COUNTRY_REGION
        val countryCode = VALID_COUNTRY_CODE
        val countryCapital = VALID_COUNTRY_CAPITAL

        // When
        val result = CountryCardFormatter.format(resources, countryName, countryRegion, countryCode, countryCapital)

        // Then
        assertThat("As only countryName is empty, then replace countryName by dash", result.name, `is`("-, NA"))
        assertThat("As countryCode is not empty, then include code as is", result.code, `is`("CA"))
        assertThat("As countryCapital is not empty, then include capital as is", result.capital, `is`("Ottawa"))
    }

    @Test
    fun `given that only countryRegion is an empty String, When formatting to present data to Country Card, Then only region part will be replaced by dash`() {
        // Given
        val countryName = VALID_COUNTRY_NAME
        val countryRegion = EMPTY_COUNTRY_REGION
        val countryCode = VALID_COUNTRY_CODE
        val countryCapital = VALID_COUNTRY_CAPITAL

        // When
        val result = CountryCardFormatter.format(resources, countryName, countryRegion, countryCode, countryCapital)

        // Then
        assertThat("As only countryRegion is empty, then replace countryRegion by dash", result.name, `is`("Canada, -"))
        assertThat("As countryCapital is not empty, then include capital as is", result.capital, `is`("Ottawa"))
    }

    @Test
    fun `given that only countryCode is an empty String, When formatting to present data to Country Card, Then only code part will be replaced by dash`() {
        // Given
        val countryName = VALID_COUNTRY_NAME
        val countryRegion = VALID_COUNTRY_REGION
        val countryCode = EMPTY_COUNTRY_CODE
        val countryCapital = VALID_COUNTRY_CAPITAL

        // When
        val result = CountryCardFormatter.format(resources, countryName, countryRegion, countryCode, countryCapital)

        // Then
        assertThat("As countryName and countryRegion are not empty, then place then with proper formatting", result.name, `is`("Canada, NA"))
        assertThat("As countryCode is empty, then replace it by a dash", result.code, `is`("-"))
        assertThat("As countryCapital is not empty, then include capital as is", result.capital, `is`("Ottawa"))
    }

    @Test
    fun `given that only countryCapital is an empty String, When formatting to present data to Country Card, Then only capital part will be replaced by dash`() {
        // Given
        val countryName = VALID_COUNTRY_NAME
        val countryRegion = VALID_COUNTRY_REGION
        val countryCode = VALID_COUNTRY_CODE
        val countryCapital = EMPTY_COUNTRY_CAPITAL

        // When
        val result = CountryCardFormatter.format(resources, countryName, countryRegion, countryCode, countryCapital)

        // Then
        assertThat("As countryName and countryRegion are not empty, then place then with proper formatting", result.name, `is`("Canada, NA"))
        assertThat("As countryCode is not empty, then include code as is", result.code, `is`("CA"))
        assertThat("As countryCapital is empty, then replace it by a dash", result.capital, `is`("-"))
    }

    @Test
    fun `given that all fields are non empty String, When formatting to present data to Country Card, Then no fields will be replaced by dash`() {
        // Given
        val countryName = VALID_COUNTRY_NAME
        val countryRegion = VALID_COUNTRY_REGION
        val countryCode = VALID_COUNTRY_CODE
        val countryCapital = VALID_COUNTRY_CAPITAL

        // When
        val result = CountryCardFormatter.format(resources, countryName, countryRegion, countryCode, countryCapital)

        // Then
        assertThat("As countryName and countryRegion are not empty, then place then with proper formatting", result.name, `is`("Canada, NA"))
        assertThat("As countryCode is not empty, then include code as is", result.code, `is`("CA"))
        assertThat("As countryCapital is not empty, then include capital as is", result.capital, `is`("Ottawa"))
    }
}