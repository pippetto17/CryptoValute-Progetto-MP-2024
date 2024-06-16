package it.magi.stonks.models

data class NFTCollection(
    val id: String,
    val contract_address: String,
    val asset_platform_id: String,
    val name: String,
    val symbol: String,
    val image: Image,
    val description: String,
    val native_currency: String,
    val native_currency_symbol: String,
    val floor_price: FloorPrice,
    val market_cap: MarketCap,
    val volume_24h: Volume24h,
    val floorPriceInUsd24hPercentageChange: Double,
    val floorPrice24hPercentageChange: PriceChange,
    val marketCap24hPercentageChange: PriceChange,
    val volume24hPercentageChange: PriceChange,
    val numberOfUniqueAddresses: Int,
    val numberOfUniqueAddresses24hPercentageChange: Double,
    val volumeInUsd24hPercentageChange: Double,
    val totalSupply: Int,
    val oneDaySales: Int,
    val oneDaySales24hPercentageChange: Double,
    val oneDayAverageSalePrice: Double,
    val oneDayAverageSalePrice24hPercentageChange: Double,
    val links: it.magi.stonks.models.Links,
    val floorPrice7dPercentageChange: PriceChange,
    val floorPrice14dPercentageChange: PriceChange,
    val floorPrice30dPercentageChange: PriceChange,
    val floorPrice60dPercentageChange: PriceChange,
    val floorPrice1yPercentageChange: PriceChange,
    val explorers: List<Explorer>
)

data class Image(
    val small: String
)

data class FloorPrice(
    val nativeCurrency: Double,
    val usd: Double
)

data class MarketCap(
    val nativeCurrency: Double,
    val usd: Double
)

data class Volume24h(
    val nativeCurrency: Double,
    val usd: Double
)

data class PriceChange(
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