package it.magi.stonks.objects

import android.content.ContentValues.TAG
import android.credentials.GetCredentialException
import android.credentials.GetCredentialRequest
import android.credentials.GetCredentialResponse
import android.os.Build
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import it.magi.stonks.R
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

/*
@Composable
fun GoogleSignInButton() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val rawNonce = UUID.randomUUID().toString()
    val bytes = rawNonce.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    val hasedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }
    val onClick: () -> Unit = {
        println("pspspspspspsp2")
        val credentialManager = CredentialManager.create(context)
        val googleIdOption: GetGoogleIdOption =
            GetGoogleIdOption.Builder().setFilterByAuthorizedAccounts(false)
                .setServerClientId("407431367773-nlg80561qd2gecmiqjul5js5v4bp6ptk.apps.googleusercontent.com")
                .setNonce(hasedNonce).build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder().
                .build()

        coroutineScope.launch {
            println("pspspspspspsps")
            try {
                val result = credentialManager.getCredential(
                    request = request, context = context
                )
                val credential = result.credential
                val googleTokenIdCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val googleTokenId = googleTokenIdCredential.idToken
                Log.i(TAG, "GoogleSignInButton: $googleTokenId")
                Toast.makeText(context, "Sei loggato con Google", Toast.LENGTH_SHORT).show()
            } catch (e: GetCredentialException) {
                Log.i(TAG, "Exception: ${e.message}")
            } catch (e: GoogleIdTokenParsingException) {
                Log.i(TAG, "Exception: ${e.message}")
            }
            catch (e: Exception){
                Log.i(TAG, "Exception: ${e.message}")
            }
        }
    }
    Button(
        onClick = {
                  onClick()
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
    fun handleSignIn(result: GetCredentialResponse) {
        // Handle the successfully returned credential.
        val credential = result.credential

        when (credential) {

            // Passkey credential
            is PublicKeyCredential -> {
                // Share responseJson such as a GetCredentialResponse on your server to
                // validate and authenticate
                val responseJson = credential.authenticationResponseJson
            }

            // Password credential
            is PasswordCredential -> {
                // Send ID and password to your server to validate and authenticate.
                val username = credential.id
                val password = credential.password
            }

            // GoogleIdToken credential
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // Use googleIdTokenCredential and extract id to validate and
                        // authenticate on your server.
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e(TAG, "Received an invalid google id token response", e)
                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    Log.e(TAG, "Unexpected type of credential")
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                Log.e(TAG, "Unexpected type of credential")
            }
        }
    }
}
*/