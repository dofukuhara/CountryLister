package com.fukuhara.douglas.countrylister.feature.countrylister.business.repository

import com.fukuhara.douglas.common.arch.Either
import com.fukuhara.douglas.common.exception.NoDataFound
import com.fukuhara.douglas.countrylister.feature.countrylister.business.model.CountryModel

interface CountryRepository {
    suspend fun fetchList(): Either<NoDataFound, List<CountryModel>>
}