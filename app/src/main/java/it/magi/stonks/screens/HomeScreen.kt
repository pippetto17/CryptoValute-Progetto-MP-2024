package it.magi.stonks.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import it.magi.stonks.ui.theme.LoginBgColor
import it.magi.stonks.viewmodels.HomeViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LoginBgColor)
    ) {
        Text(text = "Homescreen", color = Color.White)
        Button(onClick = {
            viewModel.logOut()
            navController.navigate("login")
        }) {
            Text(text = "Logout", color = Color.White)
        }
    }
}