package it.magi.stonks.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import it.magi.stonks.R
import it.magi.stonks.composables.OtherTopAppBar
import it.magi.stonks.composables.SignDivisor
import it.magi.stonks.ui.theme.DarkBgColor
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.utilities.Utilities
import it.magi.stonks.viewmodels.SettingsViewModel
import kotlinx.coroutines.launch

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun SettingsScreen(
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

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            OtherTopAppBar(
                navController = navController,
            )
        },
        containerColor = FormContainerColor,
        contentWindowInsets = WindowInsets(left = 0.dp, top = 0.dp, right = 0.dp, bottom = 0.dp),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = DarkBgColor
                    ),
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                ) {
                    TextButton(onClick = {
                        navController.navigate("profile")
                    }) {
                        Text(
                            text = "Profile Settings",
                            color = Color.White,
                            fontFamily = titleFont(),
                            fontSize = 15.sp
                        )
                    }
                    SignDivisor()
                    TextButton(onClick = {
                        viewModel.logOut(context)
                    }) {
                        Text(
                            text = "Logout",
                            color = Color.White,
                            fontFamily = titleFont(),
                            fontSize = 15.sp
                        )
                    }
                }
                Spacer(modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f))
                Card(
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = DarkBgColor
                    ),
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(20.dp)
                        .fillMaxWidth()
                        .clickable {
                            showDeleteDialog = true
                        },
                ) {
                    Text(
                        text = "Delete Account",
                        color = Color.Red,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .wrapContentSize(),
                        textAlign = TextAlign.Center,
                        fontFamily = titleFont(),
                        fontSize = 25.sp
                    )
                }
            }
        }
    }
}