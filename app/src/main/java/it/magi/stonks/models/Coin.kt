package it.magi.stonks.models

data class Coin(
    val id: String?,
    val symbol: String?,
    val name: String?,
    val image: String?,
    val current_price: Float?,
    val market_cap: Float?,
    val market_cap_rank: Float?,
    val fully_diluted_valuation: Float?,
    val total_volume: Float?,
    val high_24h: Float?,
    val low_24h: Float?,
    val price_change_24h: Float?,
    val price_change_percentage_24h: Float?,
    val market_cap_change_24h: Float?,
    val market_cap_change_percentage_24h: Float?,
    val circulating_supply: Float?,
    val total_supply: Float?,
    val max_supply: Float?,
    val ath: Float?,
    val ath_change_percentage: Float?,
    val ath_date: String?,
    val atl: Float?,
    val atl_change_percentage: Float?,
    val atl_date: String?,
    val roi: Any?,
    val last_updated: String?,
    val sparkline_in_7d: SparkLine?
)

data class SparkLine(
    val price: List<Float>?
)