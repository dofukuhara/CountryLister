package com.fukuhara.douglas.countrylister.feature.countrylister.business.repository

import com.fukuhara.douglas.common.arch.Either
import com.fukuhara.douglas.common.arch.left
import com.fukuhara.douglas.common.arch.right
import com.fukuhara.douglas.common.exception.NoDataFound
import com.fukuhara.douglas.common.model.ModelMapper
import com.fukuhara.douglas.countrylister.feature.countrylister.business.model.CountriesVo
import com.fukuhara.douglas.countrylister.feature.countrylister.business.model.CountryModel

class CountryRemoteRepository(
    private val api : CountryRemoteApi,
    private val mapper : ModelMapper<CountriesVo, List<CountryModel>>
) : CountryRepository {

    override suspend fun fetchList(): Either<NoDataFound, List<CountryModel>> {
        return try {
            val response = api.fetchList()
            val modelResponse = mapper.transform(response)

            modelResponse.right()
        } catch (throwable: Throwable) {
            NoDataFound(throwable.message ?: "Failed to fetch country list").left()
        }
    }
}