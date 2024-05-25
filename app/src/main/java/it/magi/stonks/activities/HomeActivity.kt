package it.magi.stonks.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import it.magi.stonks.screens.LoginViewModel
import it.magi.stonks.screens.RegistrationScreen
import it.magi.stonks.ui.theme.StonksTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            LocalContext.current
            StonksTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        startDestination = "login"
                    ) {
                        composable("login") {
                            LoginViewModel()
                        }
                        composable("signup") {
                            RegistrationScreen(navController)
                        }
                    }
                }
            }
        }
    }
}