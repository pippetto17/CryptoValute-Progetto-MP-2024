package it.magi.stonks.activities

import android.content.Intent
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
import com.google.firebase.auth.FirebaseAuth
import it.magi.stonks.screens.LoginScreen
import it.magi.stonks.screens.RegistrationScreen
import it.magi.stonks.ui.theme.StonksTheme
import it.magi.stonks.viewmodels.HomeViewModel
import it.magi.stonks.viewmodels.LoginViewModel
import it.magi.stonks.viewmodels.RegistrationViewModel

class StartActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        enableEdgeToEdge()

        if (auth.currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
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
                        composable("registration") {
                            RegistrationScreen(
                                navController = navController,
                                viewModel = RegistrationViewModel()
                            )
                        }
                        composable("login") {
                            LoginScreen(
                                navController = navController,
                                viewModel = LoginViewModel()
                            )
                        }
                    }
                }
            }
        }
    }
}