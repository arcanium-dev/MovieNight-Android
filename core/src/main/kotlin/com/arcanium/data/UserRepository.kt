package com.arcanium.data

import com.arcanium.domain.model.User

interface UserRepository {
    suspend fun loginWithEmailAndPassword(email: String, password: String): Resource<User, Exception>
}