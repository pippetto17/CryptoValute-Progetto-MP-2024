package it.magi.stonks.models

data class News(
    val status: String,
    val totalResults: Int,
    val results: List<Article>,
    val nextPage: String?
)

data class Article(
    val article_id: String,
    val title: String,
    val link: String,
    val keywords: List<String>?,
    val creator: List<String>?,
    val video_url: String?,
    val description: String,
    val content: String,
    val pubDate: String,
    val image_url: String?,
    val source_id: String,
    val source_priority: Int,
    val source_url: String,
    val source_icon: String,
    val language: String,
    val country: List<String>,
    val category: List<String>,
    val ai_tag: String,
    val sentiment: String,
    val sentiment_stats: String,
    val ai_region: String,
    val ai_org: String
)