package it.magi.stonks.models

data class NFT (
    val id: String,
    val contractAddress: String,
    val name: String,
    val assetPlatformId: String,
    val symbol: String
)