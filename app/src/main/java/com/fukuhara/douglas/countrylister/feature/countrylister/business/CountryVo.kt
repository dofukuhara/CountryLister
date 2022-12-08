package com.fukuhara.douglas.countrylister.feature.countrylister.business

import com.google.gson.annotations.SerializedName

/*
    VO (Value Object) pojo will only be used to get the value from the Network...
    and
    Model pojo will be a "mapping" from VO.

    - Only Model objects will be used internally;
    - There will be a mapper class, to transform VO into Model;
    - In case of a slight modification is made in the Network response, we would protect the internal code, by
      trying to convert the newly VO into the existing Model
 */

class CountriesVo : ArrayList<CountryVo>()

data class CountryVo(
    @SerializedName("capital") val capital: String?,
    @SerializedName("code") val code: String?,
    @SerializedName("currency") val currency: CurrencyVo?,
    @SerializedName("demonym") val demonym: String?,
    @SerializedName("flag") val flag: String?,
    @SerializedName("language") val language: LanguageVo?,
    @SerializedName("name") val name: String?,
    @SerializedName("region") val region: String?
)

data class CurrencyVo(
    @SerializedName("code") val code: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("symbol") val symbol: String?
)

data class LanguageVo(
    @SerializedName("code") val code: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("iso639_2") val iso6392: String?,
    @SerializedName("nativeName") val nativeName: String?
)