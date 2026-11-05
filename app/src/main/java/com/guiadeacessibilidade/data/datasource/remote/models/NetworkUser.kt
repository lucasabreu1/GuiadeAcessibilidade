package com.guiadeacessibilidade.data.datasource.remote.models

import com.guiadeacessibilidade.domain.models.User

data class NetworkUser(
    val uid: String,
    val name: String,
    val email: String
)

fun NetworkUser.ToUser() : User {
    return User(
        this.uid,
        this.name,
        this.email
    )
}