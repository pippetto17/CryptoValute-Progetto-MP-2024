package it.magi.stonks.models

data class Exchange (
    val id: String,
    val name: String,
    val yearEstablished: Int,
    val country: String,
    val description: String,
    val url: String,
    val image: String,
    val hasTradingIncentive: Boolean,
    val trustScore: Float,
    val trustScoreRank: Float,
    val tradeVolume24hBtc: Float,
    val tradeVolume24hBtcNormalized: Float
)