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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import it.magi.stonks.screens.HomeScreen
import it.magi.stonks.screens.RegistrationScreen
import it.magi.stonks.viewmodels.LoginViewModel
import it.magi.stonks.viewmodels.RegistrationViewModel
import it.magi.stonks.ui.theme.StonksTheme
import it.magi.stonks.viewmodels.HomeViewModel

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            LocalContext.current
            StonksTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        startDestination = if (auth.currentUser != null) "home" else "login"
                    ) {
                        composable("login") {
                            LoginViewModel().LoginScreen(navController = navController)
                        }
                        composable("signup") {
                            RegistrationScreen(navController = navController,
                                RegistrationViewModel()
                            )
                        }
                        composable("home") {
                            HomeScreen(navController = navController, HomeViewModel())
                        }
                    }
                }
            }
        }
    }
}