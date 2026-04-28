package com.example.mcp.driver

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Component
class OpenMeteoWeatherDriver {

    private val httpClient = HttpClient.newHttpClient()
    private val mapper = jacksonObjectMapper()

    data class ForecastResult(
        val temperatureCelsius: Double,
        val humidityPercent: Int,
        val windSpeedKmh: Double,
        val weatherCode: Int,
    )

    fun fetch(latitude: Double, longitude: Double): ForecastResult {
        val url = "https://api.open-meteo.com/v1/forecast" +
            "?latitude=$latitude&longitude=$longitude" +
            "&current=temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m" +
            "&timezone=auto"

        val request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build()
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        check(response.statusCode() == 200) { "HTTP ${response.statusCode()}: $url" }

        val body = mapper.readValue<Map<String, Any>>(response.body())

        @Suppress("UNCHECKED_CAST")
        val current = body["current"] as Map<String, Any>

        return ForecastResult(
            temperatureCelsius = (current["temperature_2m"] as Number).toDouble(),
            humidityPercent = (current["relative_humidity_2m"] as Number).toInt(),
            windSpeedKmh = (current["wind_speed_10m"] as Number).toDouble(),
            weatherCode = (current["weather_code"] as Number).toInt(),
        )
    }
}