package co.ke.weatherapp.data.repository.co.ke.weatherapp.data.repository

import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import co.ke.weatherapp.data.network.dto.CurrentWeather
import co.ke.weatherapp.data.network.services.WeatherApi
import co.ke.weatherapp.data.network.utils.NetworkResult
import co.ke.weatherapp.data.network.utils.NetworkResult.*
import co.ke.weatherapp.data.repository.WeatherRepository
import co.ke.weatherapp.data.repository.WeatherRepositoryImpl
import co.ke.weatherapp.domain.model.CurrentWeatherDomain
import co.ke.weatherapp.utils.WeatherRepositoryTestData
import co.ke.weatherapp.utils.WeatherRepositoryTestData.apiKey
import co.ke.weatherapp.utils.WeatherRepositoryTestData.latitude
import co.ke.weatherapp.utils.WeatherRepositoryTestData.longitude
import com.google.common.truth.Truth.assertThat
import com.hannesdorfmann.instantiator.instance
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryImplTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()
    private var weatherApi = mockk<WeatherApi>(relaxed = true)
    private val weatherRepository: WeatherRepository = WeatherRepositoryImpl(weatherApi,dispatcher)

    private val currentWeatherDomain = instance<CurrentWeatherDomain>()
    private val currentWeather = instance<CurrentWeather>()


    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `getCurrentWeather returns NetworkResult_Success`() = runTest {
        coEvery {
            weatherApi.getCurrentWeather(latitude, longitude, apiKey)
        } returns currentWeather

        coEvery {
            weatherRepository.getCurrentWeather(latitude, longitude, apiKey)
        } returns flowOf(Success(currentWeather))

        val response = weatherRepository.getCurrentWeather(latitude, longitude, apiKey).first()

        val successResponse = response as? Success

        assert(successResponse is Success)
    }
}