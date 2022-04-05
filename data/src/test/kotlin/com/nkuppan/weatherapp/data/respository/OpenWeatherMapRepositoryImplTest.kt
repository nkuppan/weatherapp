package com.nkuppan.weatherapp.data.respository

import com.google.common.truth.Truth.assertThat
import com.nkuppan.weatherapp.data.mapper.CurrentWeatherDtoMapper
import com.nkuppan.weatherapp.data.mapper.DailyWeatherDtoMapper
import com.nkuppan.weatherapp.data.mapper.HourlyWeatherDtoMapper
import com.nkuppan.weatherapp.data.network.OpenWeatherMapApiService
import com.nkuppan.weatherapp.domain.model.Resource
import com.nkuppan.weatherapp.domain.model.WeatherType
import com.nkuppan.weatherapp.domain.respository.WeatherRepository
import com.nkuppan.weatherapp.domain.utils.AppCoroutineDispatchers
import com.nkuppan.weatherapp.utils.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class OpenWeatherMapRepositoryImplTest : BaseCoroutineTest() {

    private lateinit var mockWebServer: MockWebServer

    private lateinit var weatherRepository: WeatherRepository

    override fun onCreate() {
        super.onCreate()

        mockWebServer = MockWebServer()
        mockWebServer.start(8080)

        val openWeatherMapApiService: OpenWeatherMapApiService = Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://127.0.0.1:8080")
            .build()
            .create()

        weatherRepository = OpenWeatherMapRepositoryImpl(
            openWeatherMapApiService,
            CurrentWeatherDtoMapper(),
            HourlyWeatherDtoMapper(),
            DailyWeatherDtoMapper(),
            AppCoroutineDispatchers(
                testCoroutineDispatcher,
                testCoroutineDispatcher,
                testCoroutineDispatcher
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        mockWebServer.shutdown()
    }

    private fun setupMockResponse(response: MockResponse) {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return response
            }
        }
    }

    @Test
    fun `read weather success json response file`() {
        val reader = FakeResponseFileReader(SUCCESS_RESPONSE_FILE_NAME)
        assertThat(reader.content).isNotNull()
    }

    @Test
    fun `Search weather information with empty result`() =
        runBlocking {

            setupMockResponse(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(FakeResponseFileReader(SUCCESS_EMPTY_RESPONSE_FILE_NAME).content)
            )

            val availableWeatherInfo = weatherRepository.getAllWeatherForecast(
                FAKE_CITY, NUMBER_OF_HOURS, NUMBER_OF_DAYS
            )

            assertThat(availableWeatherInfo).isInstanceOf(Resource.Error::class.java)

            val errorResponse = availableWeatherInfo as Resource.Error
            assertThat(errorResponse).isNotNull()
            assertThat(errorResponse.exception).isNotNull()
        }

    @Test
    fun `Search weather information with valid result`(): Unit =
        runBlocking {

            setupMockResponse(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(FakeResponseFileReader(SUCCESS_RESPONSE_FILE_NAME).content)
            )

            val availableWeatherInfo = weatherRepository.getAllWeatherForecast(
                FAKE_CITY, NUMBER_OF_HOURS, NUMBER_OF_DAYS
            )
            assertThat(availableWeatherInfo).isInstanceOf(Resource.Success::class.java)

            val successResponse = availableWeatherInfo as Resource.Success
            assertThat(successResponse).isNotNull()
            assertThat(successResponse.data).isNotEmpty()

            val weatherInfo = successResponse.data[WeatherType.CURRENT]
            assertThat(weatherInfo).isNotNull()
            assertThat(weatherInfo).isNotEmpty()
            val currentWeather = weatherInfo!![0]
            assertThat(currentWeather).isNotNull()
            assertThat(currentWeather.weatherTitle).isNotNull()
            assertThat(currentWeather.date).isNotEqualTo(0)
        }

    @Test
    fun `Search weather information with error result`() =
        runBlocking {

            setupMockResponse(
                MockResponse()
                    .setResponseCode(400)
                    .setBody(FakeResponseFileReader(ERROR_RESPONSE_FILE_NAME).content)
            )

            val availableWeatherInfo = weatherRepository.getAllWeatherForecast(
                FAKE_CITY, NUMBER_OF_HOURS, NUMBER_OF_DAYS
            )
            assertThat(availableWeatherInfo).isInstanceOf(Resource.Error::class.java)
            val error = availableWeatherInfo as Resource.Error
            assertThat(error).isNotNull()
            assertThat(error.exception).isNotNull()
        }

    companion object {
        private const val SUCCESS_RESPONSE_FILE_NAME: String = "valid_response.json"
        private const val SUCCESS_EMPTY_RESPONSE_FILE_NAME: String = "valid_empty_response.json"
        private const val ERROR_RESPONSE_FILE_NAME: String = "valid_error_response.json"
    }
}