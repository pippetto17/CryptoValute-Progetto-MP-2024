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
    val trust_score: Int,
    val trust_score_rank: Int,
    val trade_volume_24h_btc: Float,
    val trade_volume_24h_btc_normalized: Float
)