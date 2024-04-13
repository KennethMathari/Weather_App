package co.ke.weatherapp.domain


typealias RootError = Error

sealed interface NetworkResult<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data: D) : NetworkResult<D, E>
    data class Error<out D, out E : RootError>(val error: E) : NetworkResult<D, E>
    data object Loading : NetworkResult<Nothing, Nothing>
}