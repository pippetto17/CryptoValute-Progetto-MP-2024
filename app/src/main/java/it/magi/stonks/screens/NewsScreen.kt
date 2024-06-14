package it.magi.stonks.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import it.magi.stonks.composables.PieChart

@Composable
fun NewsScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Search Screen")
        PieChart(
            data = mapOf(
                Pair("Sample-1", 150f),
                Pair("Sample-2", 120f),
                Pair("Sample-3", 110f),
                Pair("Sample-4", 170f),
                Pair("Sample-5", 120f),
            )
        )
    }
}