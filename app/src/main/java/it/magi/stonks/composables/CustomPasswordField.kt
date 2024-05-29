package it.magi.stonks.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import it.magi.stonks.R

@Composable
fun CustomPasswordField(
    value: String,
    isError: Boolean = false,
    labelId: Int,
    onValueChange: (String) -> Unit,
    passwordErrors: List<Int> = ArrayList()
) {
    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    Column {
        TextField(
            modifier = Modifier
                .align(alignment = androidx.compose.ui.Alignment.CenterHorizontally),
            value = value,
            shape = RoundedCornerShape(15.dp),
            onValueChange = onValueChange,
            label = {
                Text(
                    stringResource(id = labelId),
                    color = Color.White
                )
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                errorTextColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent
            ),
            singleLine = true,
            trailingIcon = {
                IconButton(
                    modifier = Modifier.size(25.dp),
                    onClick = { passwordVisible = !passwordVisible },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = if (passwordVisible)
                            ImageVector.vectorResource(R.drawable.ic_not_visible)
                        else ImageVector.vectorResource(R.drawable.ic_visible),
                        contentDescription = ""
                    )
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password",
                    tint = Color.LightGray
                )
            },
        )
        if (isError && value.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
            ) {
                for (error in passwordErrors) {
                    when (error) {
                        1 -> Text(
                            text = stringResource(id = R.string.signup_password_error_code_1_length),
                            modifier = Modifier.padding(start = 10.dp),
                            color = Color.DarkGray
                        )

                        2 -> Text(
                            text = stringResource(id = R.string.signup_password_error_code_2_special_character),
                            modifier = Modifier.padding(start = 10.dp),
                            color = Color.DarkGray
                        )

                        3 -> Text(
                            text = stringResource(id = R.string.signup_password_error_code_3_number),
                            modifier = Modifier.padding(start = 10.dp),
                            color = Color.DarkGray
                        )

                        4 -> Text(
                            text = stringResource(id = R.string.signup_password_error_code_4_uppercase),
                            modifier = Modifier.padding(start = 10.dp),
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}