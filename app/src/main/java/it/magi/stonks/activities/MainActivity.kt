package it.magi.stonks.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import it.magi.stonks.composables.CustomBottomNavBar
import it.magi.stonks.navigation.NavigationItem
import it.magi.stonks.screens.CoinScreen
import it.magi.stonks.screens.HomeScreen
import it.magi.stonks.screens.OtherScreen
import it.magi.stonks.screens.SearchScreen
import it.magi.stonks.screens.WalletScreen
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.ui.theme.StonksTheme
import it.magi.stonks.viewmodels.HomeViewModel
import it.magi.stonks.viewmodels.OtherViewModel
import it.magi.stonks.viewmodels.WalletViewModel

const val apiKey="CG-Xag5m7fAKyT7rBF6biSrs1GF"

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
                    bottomBar = {
                        CustomBottomNavBar(navController = navController)
                    },
                    containerColor = FormContainerColor,
                    contentWindowInsets = WindowInsets(0, 0, 0, 0),
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        startDestination = NavigationItem.Home.route
                    ) {
                        composable(NavigationItem.Home.route) {
                            HomeScreen(navController, viewModel = HomeViewModel(application))
                        }
                        composable(NavigationItem.Wallet.route) {
                            WalletScreen(
                                navController = navController,
                                viewModel = WalletViewModel()
                            )
                        }
                        composable(NavigationItem.Search.route) {
                            SearchScreen(navController = navController)
                        }
                        composable(NavigationItem.Other.route) {
                            OtherScreen(
                                navController = navController,
                                viewModel = OtherViewModel()
                            )
                        }
                        composable(
                            "coin/{coinId}",
                            arguments = listOf(
                                navArgument("coinId") {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->
                            val coinId = backStackEntry.arguments?.getString("coinId")
                            if (coinId != null) {
                                CoinScreen(
                                    navController = navController,
                                    viewModel = HomeViewModel(application),
                                    apiKey = apiKey,
                                    coinId = coinId,
                                    currency = HomeViewModel(application).getCurrencyPreference(application),
                                    application = application
                                )

                            }
                        }
                    }
                }
            }
        }
    }
}