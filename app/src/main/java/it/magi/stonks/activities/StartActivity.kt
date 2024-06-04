package it.magi.stonks.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.credentials.GetCredentialRequest
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import it.magi.stonks.screens.LoginScreen
import it.magi.stonks.screens.RegistrationScreen
import it.magi.stonks.ui.theme.StonksTheme
import it.magi.stonks.utilities.Utilities
import it.magi.stonks.viewmodels.LoginViewModel
import it.magi.stonks.viewmodels.RegistrationViewModel
import kotlinx.coroutines.launch

private const val WEB_CLIENT_ID =
    "407431367773-nlg80561qd2gecmiqjul5js5v4bp6ptk.apps.googleusercontent.com"

class StartActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        enableEdgeToEdge()
        auth = FirebaseAuth.getInstance()
        auth.currentUser?.reload()
        if (auth.currentUser != null && auth.currentUser?.isEmailVerified == true) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current
            StonksTheme {
                val scope = rememberCoroutineScope()
                val CredentialManager = androidx.credentials.CredentialManager.create(context)
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        startDestination = "login"
                    ) {
                        composable("registration") {
                            RegistrationScreen(
                                navController = navController,
                                viewModel = RegistrationViewModel(application)
                            )
                        }
                        composable("login") {
                            LoginScreen(
                                onSignInClick = {
                                    val googleIdOption = GetGoogleIdOption
                                        .Builder()
                                        .setFilterByAuthorizedAccounts(true)
                                        .setServerClientId(WEB_CLIENT_ID)
                                        .setNonce("")
                                        .build()

                                    val request = GetCredentialRequest
                                        .Builder()
                                        .addCredentialOption(googleIdOption)
                                        .build()
                                    scope.launch {
                                        try {
                                            val result =
                                                CredentialManager.getCredential(context, request)
                                            val credential = result.credential
                                            val googleIdTokenCredential =
                                                GoogleIdTokenCredential.createFrom(credential.data)
                                            val googleIdToken = googleIdTokenCredential.idToken

                                            val firebaseCredential =
                                                GoogleAuthProvider.getCredential(
                                                    googleIdToken,
                                                    null
                                                )

                                            auth.signInWithCredential(firebaseCredential)
                                                .addOnCompleteListener { task ->
                                                    if (task.isSuccessful) {
                                                        ContextCompat.startActivity(
                                                            context,
                                                            Intent(
                                                                context,
                                                                MainActivity::class.java
                                                            ),
                                                            null
                                                        )

                                                        val database =
                                                            FirebaseDatabase.getInstance("https://criptovalute-b1e06-default-rtdb.europe-west1.firebasedatabase.app/")
                                                        val email = auth.currentUser?.email
                                                        if (email != null) {
                                                            val myRef = database.getReference()
                                                                .child("users")
                                                                .child(
                                                                    Utilities().convertDotsToCommas(
                                                                        email
                                                                    ).lowercase()
                                                                )
                                                        } else {
                                                            Log.d(
                                                                "RealTimeDatabase",
                                                                "signInWithGoogle:failure email is null"
                                                            )
                                                        }
                                                    }
                                                }
                                                
                                        } catch (e: Exception) {
                                            Log.d("Login", "signInWithGoogle:failure $e")
                                            Toast.makeText(
                                                context,
                                                "Authentication failed.",
                                                Toast.LENGTH_SHORT,
                                            ).show()
                                        }
                                    }
                                },
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