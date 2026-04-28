package com.example.mcp.gateway

import com.example.mcp.domain.CityName
import com.example.mcp.domain.Location
import com.example.mcp.driver.OpenMeteoGeocodingDriver
import com.example.mcp.port.LocationPort
import org.springframework.stereotype.Service

@Service
class LocationGateway(private val driver: OpenMeteoGeocodingDriver) : LocationPort {

    override fun getByCityName(cityName: CityName): Location? {
        val result = driver.fetch(cityName.value) ?: return null
        return Location(
            name = result.name,
            country = result.country,
            latitude = result.latitude,
            longitude = result.longitude,
        )
    }
}
