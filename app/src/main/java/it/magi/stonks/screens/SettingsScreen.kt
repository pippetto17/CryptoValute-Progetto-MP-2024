package it.magi.stonks.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import it.magi.stonks.R
import it.magi.stonks.composables.SignDivisor
import it.magi.stonks.utilities.Utilities
import it.magi.stonks.viewmodels.SettingsViewModel
import kotlinx.coroutines.launch

@Composable
fun OtherScreen(
    navController: NavController,
    viewModel: SettingsViewModel,
) {
    val context = LocalContext.current
    val apiKey = stringResource(R.string.api_key)
    viewModel.getSupportedCurrencies(apiKey)

    var showDeleteDialog by rememberSaveable {
        mutableStateOf(false)
    }
    if (showDeleteDialog) {
        Utilities().DeleteAccountDialog(
            onDismiss = {
                showDeleteDialog = false
            },
            onConfirmButton = {
                showDeleteDialog = false
                viewModel.viewModelScope.launch { viewModel.deleteFirebaseUser(context)}
            },
            onDismissButton = {
                showDeleteDialog = false
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        TextButton(onClick = {
            navController.navigate("profile")
        }) {
            Text(text = "Profile Settings", color = Color.White)
        }
        SignDivisor()
        TextButton(onClick = {
            showDeleteDialog = true
        }) {
            Text(text = "Delete Account", color = Color.White)
        }
        SignDivisor()
        TextButton(onClick = {
            viewModel.logOut(context)
        }) {
            Text(text = "Logout", color = Color.White)
        }
    }
}