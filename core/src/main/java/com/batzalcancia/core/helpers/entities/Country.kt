package com.batzalcancia.core.helpers.entities

data class Country(
    var iso: String,
    var code: String,
    var name: String
) {
    override fun toString() = name
}