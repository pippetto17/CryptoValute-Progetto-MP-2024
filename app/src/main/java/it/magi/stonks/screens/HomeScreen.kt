package it.magi.stonks.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import it.magi.stonks.viewmodels.HomeViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {
    val context = LocalContext.current
    LazyColumn {

    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Home Screen")
        Button(onClick = {
            viewModel.logOut(context)
            navController.navigate("login")
        }) {
            Text(text = "Logout")
        }
    }
}