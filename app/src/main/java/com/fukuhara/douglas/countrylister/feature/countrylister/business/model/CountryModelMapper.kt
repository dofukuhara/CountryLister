package com.fukuhara.douglas.countrylister.feature.countrylister.business.model

import com.fukuhara.douglas.common.debug.Logger
import com.fukuhara.douglas.common.model.ModelMapper

/*
    To prevent any error that BackEnd may produce when sending the Country List data for us:
    - In VO, every field will be converted to nullable -> Avoid any potential crash
    - In Mapper, all the necessary fields in UI will be declared as non-nullable -> They need to be displayed in UI
    - An approach taken at this time, for the case when a mandatory field is not returned by BackEnd (e.g.: capital) :
        -- During Vo to Model mapping (business layer), we will replace null by empty string
        -- We will log this in logcat (we could send it to Crashlytics, Newrelic, Sentry, etc...), so
           that we can analyze it in the future and communicate this with BackEnd Team
 */

class CountryModelMapper(private val logger: Logger) : ModelMapper<CountriesVo, List<CountryModel>> {
    override fun transform(voData: CountriesVo): List<CountryModel> {
        return voData
            .map { data ->
                logIfMandatoryDataForUiIsMissing(data)
                CountryModel(
                    capital = data.capital ?: "",
                    code = data.code ?: "",
                    name = data.name ?: "",
                    region = data.region ?: ""
                )
        }
    }

    private fun logIfMandatoryDataForUiIsMissing(country: CountryVo)  {
        if (country.capital == null || country.code == null || country.name == null || country.region == null) {
            logger.d("CountryModelMapper", "Required information missing: " +
                    "country.capital [${country.capital}] | " +
                    "country.code [${country.code}] | " +
                    "country.name [${country.name}] | " +
                    "country.region [${country.region}]")
        }
    }
}