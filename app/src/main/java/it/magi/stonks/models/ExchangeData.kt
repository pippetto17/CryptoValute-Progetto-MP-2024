package it.magi.stonks.models

data class ExchangeData(
    val name: String,
    val yearEstablished: Int,
    val country: String,
    val description: String,
    val url: String,
    val image: String,
    val facebookUrl: String,
    val redditUrl: String,
    val telegramUrl: String,
    val slackUrl: String?,
    val otherUrl1: String,
    val otherUrl2: String?,
    val twitterHandle: String,
    val hasTradingIncentive: Boolean,
    val centralized: Boolean,
    val publicNotice: String?,
    val alertNotice: String?,
    val trustScore: Int,
    val trustScoreRank: Int,
    val tradeVolume24hBtc: Double,
    val tradeVolume24hBtcNormalized: Double,
    val tickers: List<Ticker>
)

data class Ticker(
    val base: String,
    val target: String,
    val market: Market,
    val last: Double,
    val volume: Double,
    val convertedLast: Map<String, Double>,
    val convertedVolume: Map<String, Double>,
    val trustScore: String,
    val bidAskSpreadPercentage: Double,
    val timestamp: String,
    val lastTradedAt: String,
    val lastFetchAt: String,
    val isAnomaly: Boolean,
    val isStale: Boolean,
    val tradeUrl: String,
    val tokenInfoUrl: String?,
    val coinId: String,
    val targetCoinId: String
)

data class Market(
    val name: String,
    val identifier: String,
    val hasTradingIncentive: Boolean
)