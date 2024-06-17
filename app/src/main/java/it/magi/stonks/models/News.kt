package it.magi.stonks.models
import com.google.gson.annotations.SerializedName

data class News(
    val status: String,
    val totalResults: Int,
    val results: List<Article>,
    val nextPage: String?
)

data class Article(
    @SerializedName("article_id") val articleId: String,
    val title: String,
    val link: String,
    val keywords: List<String>?,
    val creator: List<String>?,
    @SerializedName("video_url") val videoUrl: String?,
    val description: String,
    val content: String,
    @SerializedName("pubDate") val pubDate: String,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("source_id") val sourceId: String,
    @SerializedName("source_priority") val sourcePriority: Int?,
    @SerializedName("source_url") val sourceUrl: String,
    @SerializedName("source_icon") val sourceIcon: String?,
    val language: String,
    val country: List<String>,
    val category: List<String>,
    @SerializedName("ai_tag") val aiTag: Any?,
    val sentiment: Any?,
    @SerializedName("sentiment_stats") val sentimentStats: Any?,
    @SerializedName("ai_region") val aiRegion: String?,
    @SerializedName("ai_org") val aiOrg: String?,
    val sponsor: Boolean?
)