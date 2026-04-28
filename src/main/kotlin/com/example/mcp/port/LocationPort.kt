package com.example.mcp.port

import com.example.mcp.domain.CityName
import com.example.mcp.domain.Location

interface LocationPort {
    fun getByCityName(cityName: CityName): Location?
}