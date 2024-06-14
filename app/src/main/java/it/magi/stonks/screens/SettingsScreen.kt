package it.magi.stonks.screens

import android.annotation.SuppressLint
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.ImageLoader
import it.magi.stonks.R
import it.magi.stonks.composables.OtherTopAppBar
import it.magi.stonks.composables.SignButton
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
                viewModel.viewModelScope.launch { viewModel.deleteFirebaseUser(context) }
            },
            onDismissButton = {
                showDeleteDialog = false
            }
        )
    }

    Scaffold(
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
            Column(
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
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                )
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "Powered By",
                        color = Color.White,
                        fontSize = 15.sp
                    )
                    Text(
                        text = "I MAGI",
                        color = Color.White,
                        fontFamily = titleFont(),
                        fontSize = 15.sp,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                    Image(
                        painter = painterResource(R.drawable.magi_logo),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(25.dp)
                    )
                }

                SignButton(
                    onclick = { showDeleteDialog = true },
                    text = "Delete Account",
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkBgColor,
                        contentColor = Color.Red
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, end = 40.dp, top = 20.dp, bottom = 20.dp)
                )
            }
        }
    }
}