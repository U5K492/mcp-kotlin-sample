package com.example.mcp.usecase

import com.example.mcp.domain.CityName
import com.example.mcp.domain.Location
import com.example.mcp.domain.Weather
import com.example.mcp.port.LocationPort
import com.example.mcp.port.WeatherPort
import org.springframework.stereotype.Service

data class CurrentWeatherResult(
    val location: Location,
    val weather: Weather,
)

@Service
class GetCurrentWeatherUsecase(
    private val locationPort: LocationPort,
    private val weatherPort: WeatherPort,
) {
    fun execute(cityName: CityName): CurrentWeatherResult? {
        val location = locationPort.getByCityName(cityName) ?: return null
        val weather = weatherPort.getCurrentBy(location)
        return CurrentWeatherResult(location, weather)
    }
}