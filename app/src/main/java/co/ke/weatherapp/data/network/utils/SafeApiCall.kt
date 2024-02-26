package co.ke.weatherapp.data.network.utils

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import java.io.IOException

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
suspend fun <T : Any> safeApiCall(apiCall: suspend () -> T): NetworkResult<T> {
    return try {
        NetworkResult.Success(apiCall())
    } catch (e: IOException) {
        NetworkResult.Error(e) // Handle IO exceptions
    } catch (e: HttpException) {
        NetworkResult.Error(e) // Handle HTTP exceptions
    } catch (e: Exception) {
        NetworkResult.Error(e) // Catch other exceptions
    }
}