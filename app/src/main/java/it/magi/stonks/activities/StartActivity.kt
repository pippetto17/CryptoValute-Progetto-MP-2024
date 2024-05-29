package it.magi.stonks.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import it.magi.stonks.objects.GoogleAuthUiClient
import it.magi.stonks.screens.LoginScreen
import it.magi.stonks.screens.RegistrationScreen
import it.magi.stonks.ui.theme.StonksTheme
import it.magi.stonks.viewmodels.LoginViewModel
import it.magi.stonks.viewmodels.RegistrationViewModel
import com.google.android.gms.auth.api.identity.Identity
import it.magi.stonks.screens.SignInScreen
import it.magi.stonks.viewmodels.SignInViewModel
import kotlinx.coroutines.launch
import androidx.activity.result.IntentSenderRequest
import it.magi.stonks.screens.ProfileScreen


class StartActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        auth.currentUser?.reload()
        enableEdgeToEdge()

        if (auth.currentUser != null&& auth.currentUser?.isEmailVerified == true){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "sign_in") {
                composable("sign_in") {
                    val viewModel = viewModel<SignInViewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()

                    LaunchedEffect(key1 = Unit) {
                        if(googleAuthUiClient.getSignedInUser() != null) {
                            navController.navigate("profile")
                        }
                    }

                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartIntentSenderForResult(),
                        onResult = { result ->
                            if(result.resultCode == RESULT_OK) {
                                lifecycleScope.launch {
                                    val signInResult = googleAuthUiClient.signInWithIntent(
                                        intent = result.data ?: return@launch
                                    )
                                    viewModel.onSignInResult(signInResult)
                                }
                            }
                        }
                    )
                    
                    LaunchedEffect(key1 = state.isSignInSuccessful) {
                        if(state.isSignInSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "Sign in successful",
                                Toast.LENGTH_LONG
                            ).show()

                            navController.navigate("profile")
                            viewModel.resetState()
                        }
                    }

                    SignInScreen(
                        state = state,
                        onSignInClick = {
                            lifecycleScope.launch {
                                val signInIntentSender = googleAuthUiClient.signIn()
                                launcher.launch(
                                    IntentSenderRequest.Builder(
                                        signInIntentSender ?: return@launch
                                ).build()
                                )
                            }
                        }
                    )
                }

                composable("profile") {
                    ProfileScreen(
                        userData = googleAuthUiClient.getSignedInUser(),
                        onSignOut = {
                            lifecycleScope.launch {
                                googleAuthUiClient.signOut()
                                Toast.makeText(
                                    applicationContext,
                                    "Signed out",
                                    Toast.LENGTH_LONG
                                ).show()

                                navController.popBackStack()
                            }
                        }
                    )
                }
            }

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