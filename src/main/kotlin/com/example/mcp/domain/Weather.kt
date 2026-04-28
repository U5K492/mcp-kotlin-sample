package com.example.mcp.domain

data class Weather(
    val weatherCode: WeatherCode,
    val temperatureCelsius: Double,
    val humidityPercent: Int,
    val windSpeedKmh: Double,
)