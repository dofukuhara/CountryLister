package com.fukuhara.douglas.countrylister.feature.countrylister.business.repository

import com.fukuhara.douglas.countrylister.feature.countrylister.business.model.CountriesVo
import retrofit2.http.GET

interface CountryRemoteApi {

    @GET("countries.json")
    suspend fun fetchList() : CountriesVo
}