package it.magi.stonks.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import it.magi.stonks.composables.PieChart
import it.magi.stonks.models.News
import it.magi.stonks.viewmodels.NewsViewModel
import it.magi.stonks.viewmodels.newsApiKey

@Composable
fun NewsScreen(navController: NavController, viewModel: NewsViewModel) {
    var newsList by remember { mutableStateOf<News?>(null) }
    var loadingNews by remember { mutableStateOf(false) }
    viewModel.getLatestNewsApiRequest(newsApiKey) {
        Log.d("NewsScreen", "NewsList: $it")
        newsList = it
        loadingNews = false
    }
    if (loadingNews) {
        CircularProgressIndicator()
    } else if(newsList!=null){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Search Screen")

        }
    }else{
        Text(text = "Nothing to show")
    }
}