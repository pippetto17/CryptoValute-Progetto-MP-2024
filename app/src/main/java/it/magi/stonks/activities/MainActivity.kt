package it.magi.stonks.activities

import android.graphics.Color
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
import it.magi.stonks.composables.CustomBottomNavBar
import it.magi.stonks.composables.CustomTopAppBar
import it.magi.stonks.navigation.NavigationItem
import it.magi.stonks.screens.HomeScreen
import it.magi.stonks.screens.OtherScreen
import it.magi.stonks.screens.SearchScreen
import it.magi.stonks.screens.WalletScreen
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.ui.theme.StonksTheme
import it.magi.stonks.viewmodels.HomeViewModel
import it.magi.stonks.viewmodels.OtherViewModel
import it.magi.stonks.viewmodels.SearchViewModel
import it.magi.stonks.viewmodels.WalletViewModel

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
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                             CustomTopAppBar()
                    },
                    bottomBar = {
                        CustomBottomNavBar(navController = navController)
                    },
                    containerColor = FormContainerColor
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        startDestination = NavigationItem.Home.route
                    ) {
                        composable(NavigationItem.Home.route) {
                            HomeScreen(navController = navController, viewModel = HomeViewModel(application))
                        }
                        composable(NavigationItem.Wallet.route) {
                            WalletScreen(
                                navController = navController,
                                viewModel = WalletViewModel()
                            )
                        }
                        composable(NavigationItem.Search.route) {
                            SearchScreen(
                                navController = navController,
                                viewModel = SearchViewModel()
                            )
                        }
                        composable(NavigationItem.Other.route) {
                            OtherScreen(
                                navController = navController,
                                viewModel = OtherViewModel()
                            )
                        }
                    }
                }
            }
        }
    }
}