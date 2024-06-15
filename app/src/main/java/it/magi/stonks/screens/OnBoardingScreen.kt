package it.magi.stonks.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import it.magi.stonks.R

@SuppressLint("ResourceType")
@Composable
fun FirstOnBoarding(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.ob1),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier
                .fillMaxHeight()
                .weight(1f))
            Row (
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = {
                    navController.navigate("onboarding2")
                }) {
                    Text(
                        text = stringResource(R.string.onboarding_screen_button_next),
                        color = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun SecondOnBoarding(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.ob2),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier
                .fillMaxHeight()
                .weight(1f))
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = {
                    navController.navigate("onboarding1")
                }) {
                    Text(
                        text = stringResource(R.string.onboarding_screen_button_previous),
                        color = Color(0xFF566286)
                    )
                }
                TextButton(onClick = {
                    navController.navigate("onboarding3")
                }) {
                    Text(
                        text = stringResource(R.string.onboarding_screen_button_next),
                        color = Color(0xFF566286)
                    )
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun ThirdOnBoarding(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.ob3),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier
                .fillMaxHeight()
                .weight(1f))
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = {
                    navController.navigate("onboarding2")
                }) {
                    Text(
                        text = stringResource(R.string.onboarding_screen_button_previous),
                        color = Color(0xFF566286)
                    )
                }
                TextButton(onClick = {
                    navController.navigate("onboarding4")
                }) {
                    Text(
                        text = stringResource(R.string.onboarding_screen_button_next),
                        color = Color(0xFF566286)
                    )
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun FourthOnBoarding(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.ob4),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier
                .fillMaxHeight()
                .weight(1f))
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = {
                    navController.navigate("onboarding3")
                }) {
                    Text(
                        text = stringResource(R.string.onboarding_screen_button_previous),
                        color = Color(0xFF566286)
                    )
                }
                TextButton(onClick = {
                    navController.navigate("onboarding5")
                }) {
                    Text(
                        text = stringResource(R.string.onboarding_screen_button_next),
                        color = Color(0xFF566286)
                    )
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun FifthOnBoarding(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.ob5),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier
                .fillMaxHeight()
                .weight(1f))
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = {
                    navController.navigate("onboarding4")
                }) {
                    Text(
                        text = stringResource(R.string.onboarding_screen_button_previous),
                        color = Color(0xFF566286)
                    )
                }
                TextButton(onClick = {
                    navController.navigate("onboarding6")
                }) {
                    Text(
                        text = stringResource(R.string.onboarding_screen_button_next),
                        color = Color(0xFF566286)
                    )
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun SixthOnBoarding(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.ob6),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier
                .fillMaxHeight()
                .weight(1f))
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = {
                    navController.navigate("onboarding5")
                }) {
                    Text(
                        text = stringResource(R.string.onboarding_screen_button_previous),
                        color = Color(0xFF566286)
                    )
                }
                TextButton(onClick = {
                    navController.navigate("login")
                }) {
                    Text(
                        text = stringResource(R.string.onboarding_screen_button_get_started),
                        color = Color(0xFF566286)
                    )
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}