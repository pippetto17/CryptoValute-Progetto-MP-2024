package it.magi.stonks.models

data class CoinHistory(

    val prices: List<PriceData>,
    val marketCaps: List<MarketCapData>,
    val totalVolumes: List<TotalVolumeData>
)

data class PriceData(
    val timestamp: Long,
    val price: Double
)

data class MarketCapData(
    val timestamp: Long,
    val marketCap: Double
)

data class TotalVolumeData(
    val timestamp: Long,
    val totalVolume: Double
)