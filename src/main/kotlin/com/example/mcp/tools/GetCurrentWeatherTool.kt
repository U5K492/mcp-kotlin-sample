package com.example.mcp.tools

import com.example.mcp.domain.CityName
import com.example.mcp.usecase.GetCurrentWeatherUsecase
import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam
import org.springframework.stereotype.Service

@Service
class GetCurrentWeatherTool(private val usecase: GetCurrentWeatherUsecase) {

    @Tool(description = "指定した都市の現在の天気情報を返します。都市名を日本語または英語で指定できます。")
    fun getCurrentWeather(
        @ToolParam(description = "天気を調べたい都市名（例: Tokyo, 大阪, London）") cityName: String
    ): String {
        val result = usecase.execute(CityName(cityName))
            ?: return "都市「$cityName」が見つかりませんでした。"
        return buildString {
            appendLine("場所: ${result.location.name}, ${result.location.country}")
            appendLine("気温: ${result.weather.temperatureCelsius}°C")
            appendLine("湿度: ${result.weather.humidityPercent}%")
            appendLine("風速: ${result.weather.windSpeedKmh} km/h")
            appendLine("天気: ${result.weather.weatherCode.description}")
        }
    }
}