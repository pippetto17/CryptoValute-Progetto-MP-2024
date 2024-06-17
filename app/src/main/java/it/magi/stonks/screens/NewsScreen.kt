package it.magi.stonks.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import it.magi.stonks.composables.NewsItem
import it.magi.stonks.composables.OtherTopAppBar
import it.magi.stonks.composables.PieChart
import it.magi.stonks.models.News
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.viewmodels.NewsViewModel
import it.magi.stonks.viewmodels.newsApiKey

@Composable
fun NewsScreen(navController: NavController, viewModel: NewsViewModel) {
    var newsList by remember { mutableStateOf<News?>(null) }
    var loadingNews by remember { mutableStateOf(false) }
    val articleList = newsList?.results

    viewModel.getLatestNewsApiRequest(newsApiKey) {
        Log.d("NewsScreen", "NewsList: $it")
        newsList = it
        loadingNews = false
    }

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LazyColumn {
                items(articleList ?: emptyList()) { article ->
                    NewsItem(
                        title = article.title,
                        onClick = {}
                    )
                }

            }
        }
    }
}