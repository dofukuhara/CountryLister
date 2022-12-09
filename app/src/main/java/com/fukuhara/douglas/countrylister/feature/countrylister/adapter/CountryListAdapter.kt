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
            binding.dsCountryCardCode.text = country.code
            binding.dsCountryCardCapital.text = country.capital
            binding.dsCountryCardCountry.text = binding.root.resources.getString(R.string.country_card_country_template, country.name, country.region)
        }
    }
}