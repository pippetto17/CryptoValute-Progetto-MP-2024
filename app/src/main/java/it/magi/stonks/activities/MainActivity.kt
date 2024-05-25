package it.magi.stonks.activities

import android.os.Build
import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.firebase.auth.FirebaseAuth
import it.magi.stonks.R
import it.magi.stonks.screens.LoginScreen
import it.magi.stonks.screens.SignUpScreen
import it.magi.stonks.ui.theme.LoginBgColor
import it.magi.stonks.ui.theme.StonksTheme

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        installSplashScreen()

        setContent {
            val navController = rememberNavController()
            StonksTheme {
                LoginScreen(navController = navController)
            }
        }
    }
}

@Composable
fun SplashPage() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LoginBgColor)
    ) {
        LottieAnimation(
            composition = composition,

            )
    }

}