package com.arcanium.data.user

import com.arcanium.domain.Resource
import com.arcanium.domain.user.model.User

interface UserRepository {
    suspend fun loginWithEmailAndPassword(email: String, password: String): Resource<User, Exception>
}