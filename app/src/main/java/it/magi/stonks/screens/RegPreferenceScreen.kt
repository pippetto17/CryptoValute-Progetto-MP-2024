package it.magi.stonks.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import it.magi.stonks.R
import it.magi.stonks.ui.theme.TitleColor
import it.magi.stonks.ui.theme.TitleFontSize
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.utilities.Utilities
import it.magi.stonks.viewmodels.RegistrationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDown() {
    val list = listOf("EUR", "USD", "GBP", "JPY")

    var isExpanded by remember {
        mutableStateOf(false)
    }

    var selectedText by remember { mutableStateOf(list[0]) }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = {isExpanded = !isExpanded}
        ) {
            TextField(
                modifier = Modifier.menuAnchor(),
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)}
            )
            
            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
                list.forEachIndexed{ index, text ->
                    DropdownMenuItem(
                        text = { Text(text = text) },
                        onClick = {
                            selectedText = list[index]
                            isExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
        Text(text = "Currently selected: $selectedText")
    }
}

@Composable
fun RegPreference(navController: NavController, viewModel: RegistrationViewModel) {
    val nameState = viewModel.name.collectAsState()
    val surnameState = viewModel.surname.collectAsState()

    val formFilter = "^[a-zA-Z\\s]+$".toRegex()

    Box(modifier = Modifier.fillMaxSize())
    {
        Image(
            painter = painterResource(id = R.drawable.login_background_design2),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.app_logo),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(120.dp)
            )
            Column {
                Text(
                    text = stringResource(id = R.string.app_name).uppercase(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    fontFamily = titleFont(),
                    fontSize = TitleFontSize,
                    color = TitleColor
                )
            }
            OutlinedTextField(label = {
                Text(
                    stringResource(id = (R.string.signup_name_label)), color = Color.White
                )
            }, value = nameState.value, colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White, unfocusedTextColor = Color.White
            ), onValueChange = {
                if (it.isEmpty() || it.matches(formFilter)) {
                    viewModel._name.value = it
                }
            })
            OutlinedTextField(label = {
                Text(
                    stringResource(id = (R.string.signup_surname_label)), color = Color.White
                )
            }, value = surnameState.value, colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White, unfocusedTextColor = Color.White
            ), onValueChange = {
                if (it.isEmpty() || it.matches(formFilter)) {
                    viewModel._surname.value = it
                }
            })
            DropDown()
            Button(
                onClick = { navController.navigate("registration") })
            {
                Text(text = "Next", color = Color.Black)
            }
        }
    }
}
