package com.fukuhara.douglas.countrylister.feature.countrylister.business

import com.fukuhara.douglas.common.model.ModelMapper

class CountryModelMapper : ModelMapper<List<CountryVo>, List<CountryModel>> {
    override fun transform(voListData: List<CountryVo>): List<CountryModel> {
        return voListData.map { voData ->
            CountryModel(
                capital = voData.capital,
                code = voData.code,
                currency = CurrencyModel(code = voData.currency.code, name = voData.currency.name, symbol = voData.currency.symbol),
                flag = voData.flag,
                language = LanguageModel(code = voData.language.code, name = voData.language.name),
                name = voData.name,
                region = voData.region
            )
        }
    }
}