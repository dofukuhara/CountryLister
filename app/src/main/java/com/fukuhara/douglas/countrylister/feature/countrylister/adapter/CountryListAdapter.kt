package com.fukuhara.douglas.countrylister.feature.countrylister.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fukuhara.douglas.countrylister.R
import com.fukuhara.douglas.countrylister.feature.countrylister.business.model.CountryModel
import com.fukuhara.douglas.designsystem.databinding.DsCountryCardBinding

class CountryListAdapter(private val dataset: List<CountryModel>) : RecyclerView.Adapter<CountryListAdapter.CountryListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryListViewHolder {
        val binding = DsCountryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CountryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryListViewHolder, position: Int) {
        holder.onBind(dataset[position])
    }

    override fun getItemCount(): Int = dataset.size

    class CountryListViewHolder(private val binding: DsCountryCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(country: CountryModel) {
            /*
                In case that any of the fields used are empty, we are replacing it by "-" (defaultString),
                just to not leave some huge white-spacing in the card.
                It is also possible to log those occurrences to check with BackEnd Team, if needed.
             */
            val defaultString = binding.root.resources.getString(R.string.country_card_country_empty_value)
            val countryCode = country.code.ifEmpty { defaultString }
            val countryCapital = country.capital.ifEmpty { defaultString }
            val countryName = country.name.ifEmpty { defaultString }
            val countryRegion = country.region.ifEmpty { defaultString }

            binding.dsCountryCardCode.text = countryCode
            binding.dsCountryCardCapital.text = countryCapital
            binding.dsCountryCardCountry.text = binding.root.resources.getString(R.string.country_card_country_template, countryName, countryRegion)
        }
    }
}