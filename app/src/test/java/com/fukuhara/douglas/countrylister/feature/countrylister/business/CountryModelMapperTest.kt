package com.fukuhara.douglas.countrylister.feature.countrylister.business

import com.fukuhara.douglas.common.model.ModelMapper
import com.fukuhara.douglas.testutils.Parser
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class CountryModelMapperTest {
    private lateinit var countryModelMapper: ModelMapper<CountriesVo, List<CountryModel>>

    @Before
    fun setUp() {
        countryModelMapper = CountryModelMapper()
    }

    @Test
    fun `given a json with empty data, When mapping VO into Model, Then Model must be empty too`() {
        val countriesVoRef = Parser.jsonToObject(
            this as Any, "json/country_list_empty.json", CountriesVo::class.java
        )
        val countriesModel = countryModelMapper.transform(countriesVoRef)

        assertThat("The length of Model must be the same as from VO", countriesModel.size, `is`(0))
    }

    @Test
    fun `given a json with a single full country data, When mapping VO into Model, Then VO and Model must hold the same content`() {
        val countriesVoRef = Parser.jsonToObject(
            this as Any, "json/country_list_singleitem_fullelement.json", CountriesVo::class.java
        )
        val countriesModel = countryModelMapper.transform(countriesVoRef)

        assertThat("The length of Model must be the same as from VO", countriesModel.size, `is`(1))
        assertThat("CAPITAL value from Model should the same as from VO", countriesModel.firstOrNull()?.capital, `is`("Kabul"))
        assertThat("CODE value from Model should the same as from VO", countriesModel.firstOrNull()?.code, `is`("AF"))
        assertThat("CURRENCY.CODE value from Model should the same as from VO", countriesModel.firstOrNull()?.currency?.code, `is`("AFN"))
        assertThat("CURRENCY.NAME value from Model should the same as from VO", countriesModel.firstOrNull()?.currency?.name, `is`("Afghan afghani"))
        assertThat("CURRENCY.SYMBOL value from Model should the same as from VO", countriesModel.firstOrNull()?.currency?.symbol, `is`("؋"))
        assertThat("DEMONYM value from Model should the same as from VO", countriesModel.firstOrNull()?.demonym, `is`("Caymanian"))
        assertThat("FLAG value from Model should the same as from VO", countriesModel.firstOrNull()?.flag, `is`("https://restcountries.eu/data/afg.svg"))
        assertThat("LANGUAGE.CODE value from Model should the same as from VO", countriesModel.firstOrNull()?.language?.code, `is`("ps"))
        assertThat("LANGUAGE.NAME value from Model should the same as from VO", countriesModel.firstOrNull()?.language?.name, `is`("Pashto"))
        assertThat("LANGUAGE.ISO639_2 value from Model should the same as from VO", countriesModel.firstOrNull()?.language?.iso6392, `is`("eng"))
        assertThat("LANGUAGE.NATIVENAME value from Model should the same as from VO", countriesModel.firstOrNull()?.language?.nativeName, `is`("English"))
        assertThat("NAME value from Model should the same as from VO", countriesModel.firstOrNull()?.name, `is`("Afghanistan"))
        assertThat("REGION value from Model should the same as from VO", countriesModel.firstOrNull()?.region, `is`("AS"))
    }

    @Test
    fun `given a json with five data, When mapping VO into Model, Then Model must have the same amount of elements`() {
        val countriesVoRef = Parser.jsonToObject(
            this as Any, "json/country_list_fiveitems.json", CountriesVo::class.java
        )
        val countriesModel = countryModelMapper.transform(countriesVoRef)

        assertThat("The length of Model must be the same as from VO", countriesModel.size, `is`(5))
    }

    @Test
    fun `given a json with five data, When mapping VO into Model, Then items in Model must be in same order as in VO`() {
        val countriesVoRef = Parser.jsonToObject(
            this as Any, "json/country_list_fiveitems.json", CountriesVo::class.java
        )
        val countriesModel = countryModelMapper.transform(countriesVoRef)

        assertThat("CAPITAL value from Model should the same as from VO", countriesModel[0].capital, `is`("Kabul"))
        assertThat("CAPITAL value from Model should the same as from VO", countriesModel[1].capital, `is`("Mariehamn"))
        assertThat("CAPITAL value from Model should the same as from VO", countriesModel[2].capital, `is`("Tirana"))
        assertThat("CAPITAL value from Model should the same as from VO", countriesModel[3].capital, `is`("Algiers"))
        assertThat("CAPITAL value from Model should the same as from VO", countriesModel[4].capital, `is`("Pago Pago"))
    }
}

