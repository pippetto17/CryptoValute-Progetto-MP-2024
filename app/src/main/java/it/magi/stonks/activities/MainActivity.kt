package it.magi.stonks.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import it.magi.stonks.objects.BottomNavigationBar
import it.magi.stonks.objects.NavigationItem
import it.magi.stonks.screens.HomeScreen
import it.magi.stonks.screens.OtherScreen
import it.magi.stonks.screens.SearchScreen
import it.magi.stonks.screens.WalletScreen
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
                    },
                    bottomBar = {
                        BottomAppBar{
                            BottomNavigationBar(navController = navController)
                        }
                    },
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        startDestination = NavigationItem.Home.route
                    ) {
                        composable(NavigationItem.Home.route) {
                            HomeScreen(navController = navController, viewModel = HomeViewModel())
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