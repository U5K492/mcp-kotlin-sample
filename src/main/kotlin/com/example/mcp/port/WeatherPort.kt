package com.example.mcp.port

import com.example.mcp.domain.Location
import com.example.mcp.domain.Weather

interface WeatherPort {
    fun getCurrentBy(location: Location): Weather
}