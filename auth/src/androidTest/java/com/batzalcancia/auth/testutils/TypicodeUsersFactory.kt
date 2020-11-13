package com.batzalcancia.auth.testutils

import com.batzalcancia.auth.data.local.entities.User

class UsersFactory {
    companion object Factory {
        private fun makeUser(id: Int): User {
            return User(
                id = id,
                username = "username$id",
                password = "password",
                country = "Philippines"
            )
        }

        fun makeUserList(count: Int): List<User> {
            val githubUserLocal = mutableListOf<User>()
            repeat(count) {
                githubUserLocal.add(makeUser(it+15))
            }
            return githubUserLocal
        }
    }
}