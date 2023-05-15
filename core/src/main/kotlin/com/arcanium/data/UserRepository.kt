package com.arcanium.data

interface UserRepository {
    suspend fun loginWithEmailAndPassword(email: String, password: String): Resource<Unit, String>
}