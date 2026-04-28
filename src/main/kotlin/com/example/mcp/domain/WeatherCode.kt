package com.example.mcp.domain

@JvmInline
value class WeatherCode(val rawCode: Int) {
    val description: String get() = when (rawCode) {
        0 -> "快晴"
        1 -> "晴れ"
        2 -> "一部曇り"
        3 -> "曇り"
        45, 48 -> "霧"
        51, 53, 55 -> "霧雨"
        61, 63, 65 -> "雨"
        71, 73, 75 -> "雪"
        80, 81, 82 -> "にわか雨"
        95 -> "雷雨"
        96, 99 -> "雹を伴う雷雨"
        else -> "不明 (コード: $rawCode)"
    }
}
