# mcp-kotlin-sample

[日本語](README.ja.md) | English

A sample MCP (Model Context Protocol) server built with Kotlin and Spring Boot.

It provides a `get_current_weather` tool that returns current weather information for a given city using the free [Open-Meteo](https://open-meteo.com/) API.

## Tools

| Tool | Description |
|---|---|
| `get_current_weather` | Returns current temperature, humidity, wind speed, and weather condition for a city (supports both English and Japanese city names) |

## Tech Stack

| | Version |
|---|---|
| Kotlin | 2.1.20 |
| Spring Boot | 3.5.14 |
| Spring AI | 1.1.5 |
| Java | 25 |
| MCP Protocol | HTTP Streamable |

## Architecture

The project follows a clean architecture style with the following layers:

```
tools/         MCP tool definitions (@Tool annotations)
usecase/       Business logic
port/          Interfaces for external dependencies
gateway/       Implementations of ports (calls drivers)
driver/        External API clients (Open-Meteo)
domain/        Value objects (CityName, Location, Weather, WeatherCode)
```

## Prerequisites

- JDK 21 or later
- or Docker

## Getting Started

### Run with Maven

```bash
./mvnw spring-boot:run
```

The MCP server starts at `http://localhost:8080`.

### Run with Docker

```bash
# Build the image
docker build -t mcp-kotlin-sample .

# Start the container
docker run -p 8080:8080 mcp-kotlin-sample
```

## Verification

### Test with curl

Send requests to the MCP Streamable HTTP endpoint at `/mcp`.

**1. List available tools**

```bash
curl -X POST http://localhost:8080/mcp \
  -H "Content-Type: application/json" \
  -H "Accept: application/json, text/event-stream" \
  -d '{
    "jsonrpc": "2.0",
    "id": 1,
    "method": "tools/list",
    "params": {}
  }'
```

**2. Get current weather (e.g. Tokyo)**

```bash
curl -X POST http://localhost:8080/mcp \
  -H "Content-Type: application/json" \
  -H "Accept: application/json, text/event-stream" \
  -d '{
    "jsonrpc": "2.0",
    "id": 2,
    "method": "tools/call",
    "params": {
      "name": "getCurrentWeather",
      "arguments": {
        "cityName": "Tokyo"
      }
    }
  }'
```

Example response:

```
場所: 東京都, 日本
気温: 22.5°C
湿度: 60%
風速: 12.3 km/h
天気: 晴れ
```

### Connect with Claude Desktop

Add the following to your Claude Desktop config file (`claude_desktop_config.json`):

**Config file location:**
- macOS: `~/Library/Application Support/Claude/claude_desktop_config.json`
- Windows: `%APPDATA%\Claude\claude_desktop_config.json`

```json
{
  "mcpServers": {
    "mcp-kotlin-sample": {
      "url": "http://localhost:8080/mcp"
    }
  }
}
```

Restart Claude Desktop and try asking: *"What's the weather in Tokyo?"* — the MCP tool will be invoked automatically.

## External APIs

This project uses the following free APIs (no API key required):

- [Open-Meteo Geocoding API](https://open-meteo.com/en/docs/geocoding-api) — resolves city name to coordinates
- [Open-Meteo Forecast API](https://open-meteo.com/en/docs) — fetches current weather by coordinates
