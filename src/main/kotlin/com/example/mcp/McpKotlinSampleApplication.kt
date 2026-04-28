package com.example.mcp

import org.springframework.ai.tool.ToolCallbackProvider
import org.springframework.ai.tool.method.MethodToolCallbackProvider
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.example.mcp.tools.GetCurrentWeatherTool

@SpringBootApplication
class McpKotlinSampleApplication

fun main(args: Array<String>) {
    runApplication<McpKotlinSampleApplication>(*args)
}

@Configuration
class McpToolConfig {
    @Bean
    fun currentWeatherProvider(tool: GetCurrentWeatherTool): ToolCallbackProvider =
        MethodToolCallbackProvider.builder().toolObjects(tool).build()
}
