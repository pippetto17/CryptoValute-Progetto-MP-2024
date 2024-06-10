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
import it.magi.stonks.screens.BuyCoinScreen
import it.magi.stonks.screens.CoinScreen
import it.magi.stonks.screens.HomeScreen
import it.magi.stonks.screens.NewsScreen
import it.magi.stonks.screens.OtherScreen
import it.magi.stonks.screens.ProfileSettingsScreen
import it.magi.stonks.screens.WalletScreen
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.ui.theme.StonksTheme
import it.magi.stonks.utilities.Utilities
import it.magi.stonks.viewmodels.SettingsViewModel
import it.magi.stonks.viewmodels.StonksViewModel
import it.magi.stonks.viewmodels.WalletViewModel

const val apiKey = "CG-Xag5m7fAKyT7rBF6biSrs1GF"


class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            LocalContext.current
            val prefCurrency = Utilities().getCurrencyPreference().uppercase()
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
                        startDestination = NavigationItem.Stonks.route
                    ) {
                        composable(NavigationItem.Stonks.route) {
                            HomeScreen(
                                navController,
                                viewModel = StonksViewModel(application),
                                prefCurrency = prefCurrency
                            )
                        }
                        composable(NavigationItem.Wallet.route) {
                            WalletScreen(
                                navController = navController,
                                viewModel = WalletViewModel(application)
                            )
                        }
                        composable(NavigationItem.News.route) {
                            NewsScreen(navController = navController)
                        }
                        composable(NavigationItem.Other.route) {
                            OtherScreen(
                                navController = navController,
                                viewModel = SettingsViewModel(
                                    application,
                                    prefCurrency
                                )
                            )
                        }
                        composable("profile") {
                            ProfileSettingsScreen(
                                navController = navController,
                                viewModel = SettingsViewModel(application, prefCurrency)
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
                                    viewModel = StonksViewModel(application),
                                    apiKey = apiKey,
                                    coinId = coinId,
                                    currency = prefCurrency
                                )
                            }
                        }
                        composable(
                            "buycoin/{coinId}",
                            arguments = listOf(
                                navArgument("coinId") {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->
                            val coinId = backStackEntry.arguments?.getString("coinId")
                            if (coinId != null) {
                                BuyCoinScreen(
                                    navController = navController,
                                    viewModel = WalletViewModel(application),
                                    coinId = coinId,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}