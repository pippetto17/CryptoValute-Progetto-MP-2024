package it.magi.stonks.viewmodels

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import it.magi.stonks.activities.MainActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {
    fun userLogin(
        auth: FirebaseAuth,
        email: String,
        password: String,
        navController: NavController,
        context: android.content.Context
    ): Int {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (auth.currentUser?.isEmailVerified == false) {
                            Toast.makeText(
                                context,
                                "Please verify your email.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Log.d("Login", "Login successful")

                            val intent = Intent(context, MainActivity::class.java).apply {
                                flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            }
                            context.startActivity(intent)

                        }
                    } else {
                        Log.d("Login", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            context,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            return 1
        } else {
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    context,
                    "Please fill in all fields.",
                    Toast.LENGTH_SHORT,
                ).show()
                return 1
            } else if (auth.currentUser?.isEmailVerified == false) {
                return 0
            }
        }
        return 1
    }

    fun retrieveCredentials(auth: FirebaseAuth, email: String, context: Context) {
        if (email.isNotEmpty()) {
            auth.sendPasswordResetEmail(email)
            Toast.makeText(
                context,
                "Password reset email sent.",
                Toast.LENGTH_SHORT,
            ).show()
        } else {
            Toast.makeText(
                context,
                "Please enter your email.",
                Toast.LENGTH_SHORT,
            ).show()
        }
    }

    @Composable
    fun rememberFirebaseAuthLauncher(
        onAuthComplete: (AuthResult) -> Unit,
        onAuthError: (ApiException) -> Unit
    ): ManagedActivityResultLauncher<Intent, ActivityResult> {
        val scope = rememberCoroutineScope()
        return rememberLauncherForActivityResult(
            ActivityResultContracts
                .StartActivityForResult()
        ) { result ->
            val task = GoogleSignIn
                .getSignedInAccountFromIntent(result.data)
            try {
                val account = task
                    .getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider
                    .getCredential(account.idToken!!, null)
                scope.launch {
                    val authResult = Firebase
                        .auth.signInWithCredential(credential).await()
                    onAuthComplete(authResult)
                }
            } catch (e: ApiException) {
                Log.d("GoogleAuth", e.toString())
                onAuthError(e)
            }
        }
    }
}






