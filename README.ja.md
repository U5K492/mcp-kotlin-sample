# mcp-kotlin-sample

日本語 | [English](README.md)

Kotlin + Spring Boot で実装した MCP (Model Context Protocol) サーバーのサンプルプロジェクトです。

都市名を指定すると [Open-Meteo](https://open-meteo.com/) の無料 API を使って現在の天気情報を返す `get_current_weather` ツールを提供します。

## 機能

| ツール名 | 説明 |
|---|---|
| `get_current_weather` | 都市名（日本語・英語どちらも可）から現在の気温・湿度・風速・天気を返す |

## 技術スタック

| 種別 | バージョン |
|---|---|
| Kotlin | 2.1.20 |
| Spring Boot | 3.5.14 |
| Spring AI | 1.1.5 |
| Java | 25 |
| MCP プロトコル | HTTP Streamable |

## アーキテクチャ

クリーンアーキテクチャ風のレイヤー構成になっています。

```
tools/         MCP ツール定義（@Tool アノテーション）
usecase/       ビジネスロジック
port/          外部依存のインターフェース
gateway/       port の実装（driver を呼ぶ）
driver/        外部 API クライアント（Open-Meteo）
domain/        値オブジェクト（CityName, Location, Weather, WeatherCode）
```

## 事前準備

- Java 21 以上（JDK）
- または Docker

## 起動方法

### Maven でローカル起動

```bash
./mvnw spring-boot:run
```

起動すると `http://localhost:8080` で MCP サーバーが立ち上がります。

### Docker で起動

```bash
# イメージのビルド
docker build -t mcp-kotlin-sample .

# コンテナの起動
docker run -p 8080:8080 mcp-kotlin-sample
```

## 動作確認

### curl で直接確認する

MCP の Streamable HTTP エンドポイント (`/mcp`) に対してリクエストを送ります。

**1. ツール一覧の取得**

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

**2. 天気を取得する（例: 東京）**

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

レスポンス例:

```
場所: 東京都, 日本
気温: 22.5°C
湿度: 60%
風速: 12.3 km/h
天気: 晴れ
```

### Claude Desktop に接続して確認する

Claude Desktop の設定ファイル（`claude_desktop_config.json`）に以下を追加します。

**設定ファイルの場所:**
- macOS: `~/Library/Application Support/Claude/claude_desktop_config.json`
- Windows: `%APPDATA%\Claude\claude_desktop_config.json`

**設定内容:**

```json
{
  "mcpServers": {
    "mcp-kotlin-sample": {
      "url": "http://localhost:8080/mcp"
    }
  }
}
```

設定後に Claude Desktop を再起動すると、チャットで「東京の天気を教えて」などと聞くと MCP ツールが呼び出されます。

## 外部 API

このプロジェクトは以下の無料 API を使用しています（API キー不要）。

- [Open-Meteo Geocoding API](https://open-meteo.com/en/docs/geocoding-api) — 都市名から緯度経度を取得
- [Open-Meteo Forecast API](https://open-meteo.com/en/docs) — 緯度経度から現在の天気を取得
