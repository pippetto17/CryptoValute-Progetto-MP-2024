package it.magi.stonks.viewmodels

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import it.magi.stonks.R
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
    ) {
        FirebaseAuth.getInstance().currentUser?.reload()
        Log.d(
            "Login",
            "current user email is ${auth.currentUser?.email} and is verified ${auth.currentUser?.isEmailVerified}"
        )
        if (email.isNotEmpty() && password.isNotEmpty() && auth.currentUser?.isEmailVerified == true) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Log.d("Login", "Login successful")
                        startActivity(
                            context,
                            Intent(context, MainActivity::class.java),
                            null
                        )
                    } else {
                        Log.d("Login", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            context,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        } else {
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    context,
                    "Please fill in all fields.",
                    Toast.LENGTH_SHORT,
                ).show()
            } else if (auth.currentUser?.isEmailVerified == false) {
                Toast.makeText(
                    context,
                    "Please verify your email.",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }

    @Composable
    fun GoogleLogin() {
        var user by remember { mutableStateOf(Firebase.auth.currentUser) }
        val launcher = rememberFirebaseAuthLauncher(
            onAuthComplete = { result ->
                user = result.user
                Log.d("GoogleAuth", "Auth Complete ${user.toString()}")
            },
            onAuthError = {
                Log.d("GoogleAuth", "ApiException ${it.toString()}")
                user = null
            }
        )
        val token = stringResource(id = R.string.default_web_client_id)
        val context = LocalContext.current
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (user == null) {
                Spacer(modifier = Modifier.height(35.dp))
                Button(
                    onClick = {
                        val gso =
                            GoogleSignInOptions
                                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(token)
                                .requestEmail()
                                .build()
                        val googleSignInClient = GoogleSignIn
                            .getClient(context, gso)
                        launcher
                            .launch(googleSignInClient.signInIntent)

                    },
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_google_logo),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp)
                    )
                    Text(text = "Sign in with Google", modifier = Modifier.padding(6.dp))
                }
            }
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






