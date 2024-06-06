package it.magi.stonks.composables

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import it.magi.stonks.R
import it.magi.stonks.ui.theme.DarkBgColor
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    isHome: Boolean = true,
    navController: NavController,
) {

    var showSearchBar by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        modifier = modifier,
        navigationIcon = {
            if (!isHome) {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        Icons.Rounded.KeyboardArrowLeft,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            } else {
                if (showSearchBar) {
                    FilterBar(viewModel = viewModel)
                }
            }
        },
        title = {
            if (!showSearchBar) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        text = stringResource(R.string.app_name),
                        fontFamily = titleFont(),
                    )
                    Image(
                        modifier = Modifier
                            .size(35.dp)
                            .padding(start = 10.dp),
                        painter = painterResource(R.drawable.app_logo),
                        contentDescription = stringResource(R.string.app_name) + " logo"
                    )
                }
            }

        },
        actions = {
            if (isHome) {
                IconButton(
                    onClick = { showSearchBar = !showSearchBar },
                ) {
                    Icon(
                        modifier = Modifier.size(35.dp),
                        imageVector = if (showSearchBar) Icons.Rounded.Close else Icons.Rounded.Search,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DarkBgColor,
            titleContentColor = Color.White
        )
    )
}