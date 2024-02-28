package co.ke.weatherapp.data.network.utils

/**
 * handle the different types of api responses/states that can occur.
 */
sealed class NetworkResult<out T:Any?>{
    data class Success<out T : Any>(val data: T): NetworkResult<T>()

    data class Error(val errorDetails: Throwable) :
        NetworkResult<Nothing>()

    data object Loading : NetworkResult<Nothing>()
}