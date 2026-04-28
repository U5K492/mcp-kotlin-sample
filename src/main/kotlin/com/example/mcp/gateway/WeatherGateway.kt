package com.example.mcp.gateway

import com.example.mcp.domain.Location
import com.example.mcp.domain.Weather
import com.example.mcp.domain.WeatherCode
import com.example.mcp.driver.OpenMeteoWeatherDriver
import com.example.mcp.port.WeatherPort
import org.springframework.stereotype.Service

@Service
class WeatherGateway(private val driver: OpenMeteoWeatherDriver) : WeatherPort {

    override fun getCurrentBy(location: Location): Weather {
        val result = driver.fetch(location.latitude, location.longitude)
        return Weather(
            weatherCode = WeatherCode(result.weatherCode),
            temperatureCelsius = result.temperatureCelsius,
            humidityPercent = result.humidityPercent,
            windSpeedKmh = result.windSpeedKmh,
        )
    }
}