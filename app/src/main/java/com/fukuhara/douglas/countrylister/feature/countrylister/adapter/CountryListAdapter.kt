package com.fukuhara.douglas.countrylister.feature.countrylister.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fukuhara.douglas.countrylister.feature.countrylister.business.logic.CountryCardFormatter
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
                Delegating the logic to format the data to "CountryCardFormatter", so we can remove the logic
                from the view, as well as allowing us to Unit Test this formatting behaviour
             */
            val countryCardContent = CountryCardFormatter.format(
                resources = binding.root.resources,
                countryCode = country.code,
                countryCapital = country.capital,
                countryName = country.name,
                countryRegion = country.region
            )

            binding.dsCountryCardCode.text = countryCardContent.code
            binding.dsCountryCardCapital.text = countryCardContent.capital
            binding.dsCountryCardCountry.text = countryCardContent.name
        }
    }
}