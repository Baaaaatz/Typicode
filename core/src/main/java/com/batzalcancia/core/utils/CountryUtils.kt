package com.batzalcancia.core.utils

import com.batzalcancia.core.helpers.entities.Country
import java.text.Collator
import java.util.*

object CountryUtils {
    // A collection to store our country object
    val countries: MutableList<Country> = emptyList<Country>().toMutableList()
        get() {

            field.clear()
            // Get ISO countries, create Country object and
            // store in the collection.
            val isoCountries: Array<String> = Locale.getISOCountries()

            isoCountries.forEach {
                val locale = Locale("en", it)
                val iso = locale.isO3Country
                val code = locale.country
                val name = locale.displayCountry
                if (iso.isNotEmpty() && code.isNotEmpty() && name.isNotEmpty()) {
                    field.add(Country(iso, code, name))
                }
            }
            // Sort the country by their name and then display the content
            // of countries collection object.

            // Sort the country by their name and then display the content
            // of countries collection object.
            Collections.sort(field, CountryComparator())
            return field
        }
}

class CountryComparator : Comparator<Country> {
    private val comparator: Comparator<Any>

    init {
        comparator = Collator.getInstance()
    }

    override fun compare(o1: Country, o2: Country): Int {
        return comparator.compare(o1.name, o2.name)
    }
}