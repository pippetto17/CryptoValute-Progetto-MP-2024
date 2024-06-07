package it.magi.stonks.models



data class CoinMarketChart(
    val prices: List<List<Float>>,
    val market_caps: List<List<Float>>,
    val total_volumes: List<List<Float>>
)