package it.magi.stonks.models

data class TrendingList(
    val coins: List<CoinItem>,
    val nfts: List<NftItem>,
    val categories: List<CategoryItem>
)

data class CoinItem(
    val item: TrendingCoin
)

data class TrendingCoin(
    val id: String,
    val coin_id: Int,
    val name: String,
    val symbol: String,
    val market_cap_rank: Int,
    val thumb: String,
    val small: String,
    val large: String,
    val slug: String,
    val price_btc: Double,
    val score: Int,
    val data: TrendingCoinData
)

data class TrendingCoinData(
    val price: Double,
    val price_btc: String,
    val price_change_percentage_24h: Map<String, Double>,
    val market_cap: String,
    val market_cap_btc: String,
    val total_volume: String,
    val total_volume_btc: String,
    val sparkline: String,
    val content: Any? // Could be null or another object
)

data class NftItem(
    val id: String,
    val name: String,
    val symbol: String,
    val thumb: String,
    val nft_contract_id: Int,
    val native_currency_symbol: String,
    val floor_price_in_native_currency: Double,
    val floor_price_24h_percentage_change: Double,
    val data: NftData
)

data class NftData(
    val floor_price: String,
    val floor_price_in_usd_24h_percentage_change: String,
    val h24_volume: String,
    val h24_average_sale_price: String,
    val sparkline: String,
    val content: NftContent? // Can be null
)

data class NftContent(
    val title: String,
    val description: String
)

data class CategoryItem(
    val id: Int,
    val name: String,
    val market_cap_1h_change: Double,
    val slug: String,
    val coins_count: Int,
    val data: CategoryData
)

data class CategoryData(
    val market_cap: Double,
    val market_cap_btc: Double,
    val total_volume: Double,
    val total_volume_btc: Double,
    val market_cap_change_percentage_24h: Map<String, Double>,
    val sparkline: String
)