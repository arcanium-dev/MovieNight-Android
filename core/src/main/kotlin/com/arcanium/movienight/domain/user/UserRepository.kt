package com.arcanium.movienight.domain.user

import com.arcanium.movienight.domain.Resource
import com.arcanium.movienight.domain.user.model.User

interface UserRepository {
    val currentLoggedInUser: User?
    suspend fun loginWithEmailAndPassword(email: String, password: String): Resource<User, Exception>
}