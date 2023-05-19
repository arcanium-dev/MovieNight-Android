package com.arcanium.movienight.data.user

import com.arcanium.movienight.domain.NullUserException
import com.arcanium.movienight.domain.Resource
import com.arcanium.movienight.domain.user.model.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class UserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : UserRepository {
    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<User, Exception> = withContext(Dispatchers.IO) {
        return@withContext suspendCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(
                email,
                password
            ).apply {
                addOnSuccessListener {
                    it.user?.email.let { username ->
                        if (username != null) {
                            continuation.resume(Resource.Success(data = User(username = username)))
                        } else {
                            continuation.resume(Resource.Failure(exception = NullUserException()))
                        }
                    }
                }
                addOnFailureListener {
                    continuation.resume(Resource.Failure(exception = it))
                }
            }
        }
    }
}
