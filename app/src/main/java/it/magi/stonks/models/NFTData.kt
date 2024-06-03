package it.magi.stonks.models

data class NFTData (
val id: String,
val contractAddress: String,
val assetPlatformId: String,
val name: String,
val symbol: String,
val image: Image,
val description: String,
val nativeCurrency: String,
val nativeCurrencySymbol: String,
val floorPrice: FloorPrice,
val marketCap: MarketCap,
val volume24h: Volume24h,
val floorPriceInUsd24hPercentageChange: Double,
val floorPrice24hPercentageChange: PercentageChange,
val marketCap24hPercentageChange: PercentageChange,
val volume24hPercentageChange: PercentageChange,
val numberOfUniqueAddresses: Int,
val numberOfUniqueAddresses24hPercentageChange: Double,
val volumeInUsd24hPercentageChange: Double,
val totalSupply: Int,
val oneDaySales: Int,
val oneDaySales24hPercentageChange: Double,
val oneDayAverageSalePrice: Double,
val oneDayAverageSalePrice24hPercentageChange: Double,
val links: Links,
val floorPrice7dPercentageChange: PercentageChange,
val floorPrice14dPercentageChange: PercentageChange,
val floorPrice30dPercentageChange: PercentageChange,
val floorPrice60dPercentageChange: PercentageChange,
val floorPrice1yPercentageChange: PercentageChange,
val explorers: List<Explorer>
)

data class Image(
    val small: String
)

data class FloorPrice(
    val nativeCurrency: Double,
    val usd: Int
)

data class MarketCap(
    val nativeCurrency: Int,
    val usd: Int
)

data class Volume24h(
    val nativeCurrency: Double,
    val usd: Int
)

data class PercentageChange(
    val usd: Double,
    val nativeCurrency: Double
)

data class Links(
    val homepage: String,
    val twitter: String,
    val discord: String
)

data class Explorer(
    val name: String,
    val link: String
)
