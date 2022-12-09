package com.fukuhara.douglas.countrylister.feature.countrylister.business.model

import com.fukuhara.douglas.common.debug.Logger
import com.fukuhara.douglas.common.model.ModelMapper

/*
    To prevent any error that BackEnd may produce when sending the Country List data for us:
    - In VO, every field will be converted to nullable -> Avoid any potential crash
    - In Mapper, all the necessary fields in UI will be declared as non-nullable -> They need to be displayed in UI
    - An approach taken at this time, for the case when a mandatory field is not returned by BackEnd (e.g.: capital) :
        -- During Vo to Model mapping (business layer), we will check the mandatory fields. In case that
           one of those fields is not presented in the response, then we will not include this data into Model
           and we will log this in logcat (we could send it to Crashlytics, Newrelic, Sentry, etc...)
 */

class CountryModelMapper(private val logger: Logger) : ModelMapper<CountriesVo, List<CountryModel>> {
    override fun transform(voData: CountriesVo): List<CountryModel> {
        return voData
            .filter { data -> allRequiredDataIsPresent(data) }
            .map { data ->
                CountryModel(
                    capital = data.capital!!,
                    code = data.code!!,
                    currency = CurrencyModel(
                        code = data.currency?.code,
                        name = data.currency?.name,
                        symbol = data.currency?.symbol),
                    demonym = data.demonym,
                    flag = data.flag,
                    language = LanguageModel(
                        code = data.language?.code,
                        name = data.language?.name,
                        iso6392 = data.language?.iso6392,
                        nativeName = data.language?.nativeName),
                    name = data.name!!,
                    region = data.region!!
                )
        }
    }

    private fun allRequiredDataIsPresent(country: CountryVo) : Boolean {
        return if (country.capital != null && country.code != null && country.name != null && country.region != null) {
            true
        } else {
            logger.d("CountryModelMapper", "Required information missing: " +
                    "country.capital [${country.capital}] | " +
                    "country.code [${country.code}] | " +
                    "country.name [${country.name}] | " +
                    "country.region [${country.region}]")
            false
        }
    }
}