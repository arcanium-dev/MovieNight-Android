package com.arcanium.data

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : UserRepository {
    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<Unit, String> = withContext(Dispatchers.IO) {
        return@withContext suspendCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(
                email,
                password
            ).apply {
                addOnSuccessListener {
                    continuation.resume(Resource.Success(data = Unit))
                }
                addOnFailureListener {
                    continuation.resume(Resource.Failure(exception = it.localizedMessage))
                }
            }
        }
    }
}
