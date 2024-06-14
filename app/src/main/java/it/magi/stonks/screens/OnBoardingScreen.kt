package it.magi.stonks.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "next",
                modifier = Modifier
                    .size(80.dp)
                    .clickable {
                        navController.navigate("onboarding2")
                    }
            )
            Spacer(modifier = Modifier.height(70.dp))
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
            Spacer(modifier = Modifier.height(20.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "previous",
                modifier = Modifier
                    .align(Alignment.Start)
                    .size(50.dp)
                    .clickable {
                        navController.navigate("onboarding1")
                    }
            )
            Spacer(modifier = Modifier
                .fillMaxHeight()
                .weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "next",
                modifier = Modifier
                    .size(80.dp)
                    .clickable {
                        navController.navigate("onboarding3")
                    }
            )
            Spacer(modifier = Modifier.height(70.dp))
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
            Spacer(modifier = Modifier.height(20.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "previous",
                modifier = Modifier
                    .align(Alignment.Start)
                    .size(50.dp)
                    .clickable {
                        navController.navigate("onboarding2")
                    }
            )
            Spacer(modifier = Modifier
                .fillMaxHeight()
                .weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "next",
                modifier = Modifier
                    .size(80.dp)
                    .clickable {
                        navController.navigate("onboarding4")
                    }
            )
            Spacer(modifier = Modifier.height(70.dp))
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
            Spacer(modifier = Modifier.height(20.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "previous",
                modifier = Modifier
                    .align(Alignment.Start)
                    .size(50.dp)
                    .clickable {
                        navController.navigate("onboarding3")
                    }
            )
            Spacer(modifier = Modifier
                .fillMaxHeight()
                .weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "next",
                modifier = Modifier
                    .size(80.dp)
                    .clickable {
                        navController.navigate("login")
                    }
            )
            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}