package com.fukuhara.douglas.countrylister.feature.countrylister.business

/*
    VO (Value Object) pojo will only be used to get the value from the Network...
    and
    Model pojo will be a "mapping" from VO.

    - Only Model objects will be used internally;
    - There will be a mapper class, to transform VO into Model;
    - In case of a slight modification is made in the Network response, we would protect the internal code, by
      trying to convert the newly VO into the existing Model
 */

data class CountryModel(
    val capital: String,
    val code: String,
    val currency: CurrencyModel,
    val demonym: String?,
    val flag: String,
    val language: LanguageModel,
    val name: String,
    val region: String
)

data class CurrencyModel(
    val code: String,
    val name: String,
    val symbol: String?
)

data class LanguageModel(
    val code: String?,
    val name: String,
    val iso6392: String?,
    val nativeName: String?
)