package it.magi.stonks.models

data class CoinMarketChart(

    val prices: List<List<Double>>,
    val market_caps: List<List<Double>>,
    val total_volumes: List<List<Double>>
)

data class PriceSnapShot(
    val timestamp: Double,
    val price: Double
)

data class MarketCapSnapShot(
    val timestamp: Double,
    val marketCap: Double
)

data class TotalVolumeSnapShot(
    val timestamp: Double,
    val totalVolume: Double
)