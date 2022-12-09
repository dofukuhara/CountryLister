package com.fukuhara.douglas.countrylister.feature.countrylister.business.logic

import android.content.res.Resources
import com.fukuhara.douglas.countrylister.R

object CountryCardFormatter {
    /*
        In case that any of the fields used are empty, we are replacing it by "-" (defaultString),
        just to not leave some huge white-spacing in the card.
        It is also possible to log those occurrences to check with BackEnd Team, if needed.
     */
    fun format(resources: Resources, countryName: String, countryRegion: String, countryCode: String, countryCapital: String) : CountryCardData {
        val defaultString = resources.getString(R.string.country_card_country_empty_value)

        return CountryCardData(
            name = resources.getString(R.string.country_card_country_template,
                countryName.ifEmpty { defaultString }, countryRegion.ifEmpty { defaultString }),
            code = countryCode.ifEmpty { defaultString },
            capital = countryCapital.ifEmpty { defaultString }
        )
    }
}

data class CountryCardData(
    val name: String, val code: String, val capital: String
)