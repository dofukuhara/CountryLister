package com.fukuhara.douglas.countrylister.feature.countrylister.business

import com.fukuhara.douglas.common.model.ModelMapper

class CountryModelMapper : ModelMapper<CountriesVo, List<CountryModel>> {
    override fun transform(voListData: CountriesVo): List<CountryModel> {
        return voListData.map { voData ->
            CountryModel(
                capital = voData.capital,
                code = voData.code,
                currency = CurrencyModel(
                    code = voData.currency.code,
                    name = voData.currency.name,
                    symbol = voData.currency.symbol),
                demonym = voData.demonym,
                flag = voData.flag,
                language = LanguageModel(
                    code = voData.language.code,
                    name = voData.language.name,
                    iso6392 = voData.language.iso6392,
                    nativeName = voData.language.nativeName),
                name = voData.name,
                region = voData.region
            )
        }
    }
}