package it.magi.stonks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import it.magi.stonks.screens.LoginScreen
import it.magi.stonks.screens.SignUpScreen
import it.magi.stonks.ui.theme.ProgettoMP2024Theme

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        enableEdgeToEdge()
        setContent {
            ProgettoMP2024Theme {
                val navController = rememberNavController()
                LocalContext.current
                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    composable("login") {
                        LoginScreen(navController)
                    }
                    composable("signup") {
                        SignUpScreen(navController)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }
}