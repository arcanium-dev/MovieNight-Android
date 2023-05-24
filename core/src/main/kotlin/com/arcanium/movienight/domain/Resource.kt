package com.arcanium.movienight.domain

sealed class Resource<T, M>(
    open val data: T?,
    open val exception: M? = null
) {
    class Success<T, M>(override val data: T) : Resource<T, M>(data = data)
    class Failure<T, M>(override val data: T? = null, override val exception: M?) : Resource<T, M>(
        data = data,
        exception = exception
    )
}
