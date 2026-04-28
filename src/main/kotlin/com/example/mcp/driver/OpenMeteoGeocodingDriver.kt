package com.example.mcp.driver

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Component
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets

@Component
class OpenMeteoGeocodingDriver {

    private val httpClient = HttpClient.newHttpClient()
    private val mapper = jacksonObjectMapper()

    data class GeoResult(
        val name: String,
        val country: String,
        val latitude: Double,
        val longitude: Double,
    )

    fun fetch(cityName: String): GeoResult? {
        val encoded = URLEncoder.encode(cityName, StandardCharsets.UTF_8)
        val url = "https://geocoding-api.open-meteo.com/v1/search?name=$encoded&count=1&language=ja&format=json"

        val request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build()
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        check(response.statusCode() == 200) { "HTTP ${response.statusCode()}: $url" }

        val body = mapper.readValue<Map<String, Any>>(response.body())

        @Suppress("UNCHECKED_CAST")
        val first = (body["results"] as? List<*>)?.firstOrNull() as? Map<String, Any> ?: return null

        return GeoResult(
            name = first["name"] as? String ?: cityName,
            country = first["country"] as? String ?: "",
            latitude = first["latitude"] as Double,
            longitude = first["longitude"] as Double,
        )
    }
}
