package it.magi.stonks.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FilterBar(filter: MutableState<String>, onclick: (filter: String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = filter.value,
        onValueChange = {
            filter.value = it
            onclick(it)
        },
        shape = RoundedCornerShape(30.dp),
        label = {
            Text(
                text = "Filter",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold

            )
        },
        textStyle = TextStyle(
            color = Color.White
        ),
        colors = OutlinedTextFieldDefaults.colors(
        ),
        modifier = Modifier
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            keyboardController?.hide()
            focusManager.clearFocus()
            onclick(filter.value)
        }),
        trailingIcon = {
            Icon(
                Icons.Rounded.Search,
                contentDescription = null,
                modifier = Modifier
                    .size(15.dp)
                    .clickable {
                        onclick(filter.value)
                        keyboardController?.hide()
                    })
        }
    )
}