package it.magi.stonks.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import it.magi.stonks.screens.LoginScreen
import it.magi.stonks.ui.theme.StonksTheme

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        setContent {
            val navController = rememberNavController()
            StonksTheme {
                LoginScreen().Login(navController = navController)
            }
        }
    }
}

/*@Composable
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

}*/