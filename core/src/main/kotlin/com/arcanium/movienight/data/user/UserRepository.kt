package com.arcanium.movienight.data.user

import com.arcanium.movienight.domain.Resource
import com.arcanium.movienight.domain.user.model.User

interface UserRepository {
    suspend fun loginWithEmailAndPassword(email: String, password: String): Resource<User, Exception>
}