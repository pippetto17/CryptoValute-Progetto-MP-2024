package it.magi.stonks.screens

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import it.magi.stonks.composables.NewsItem
import it.magi.stonks.composables.OtherTopAppBar
import it.magi.stonks.models.News
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.viewmodels.NewsViewModel
import it.magi.stonks.viewmodels.newsApiKey

@Composable
fun NewsScreen(navController: NavController, viewModel: NewsViewModel) {
    val url = "https://www.coingecko.com/it/news"

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            OtherTopAppBar(
                navController = navController
            )
        },
        containerColor = FormContainerColor,
        contentWindowInsets = WindowInsets(left = 0.dp, top = 0.dp, right = 0.dp, bottom = 0.dp),
    ) { innerPadding ->

        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            factory = { context ->
                WebView(context).apply {
                    try {
                        webViewClient = WebViewClient()
                        loadUrl(url)
                    } catch (e: Exception) {
                        Log.e("WebView", e.message.toString())
                    }
                }
            }
        )
    }
}