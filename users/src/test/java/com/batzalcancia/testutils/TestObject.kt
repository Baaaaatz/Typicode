package com.batzalcancia.testutils

import com.batzalcancia.users.data.entities.Address
import com.batzalcancia.users.data.entities.Company
import com.batzalcancia.users.data.entities.Geo
import com.batzalcancia.users.data.entities.User


/**
 * Copyright 2020, White Cloak Technologies, Inc., All rights reserved.
 *
 * @author Batz Alcancia
 * @since 10/25/20
 */

val users = mutableListOf<User>().apply {
    repeat(10) {
        add(
            User(
                id = it,
                name = "Name$it",
                username = "username$it",
                email = "email$it",
                address = Address(
                    "street$it",
                    "suite$it",
                    "city$it",
                    "zipcode$it",
                    Geo(it.toDouble(), it.toDouble())
                ),
                phone = "phone$it",
                website = "website$it",
                company = Company(
                    "name$it",
                    "catchPhrase$it",
                    "bs$it"
                )
            )
        )
    }
}