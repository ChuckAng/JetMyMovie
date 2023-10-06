package com.example.mymovie.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymovie.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginScreen() {
    var loginText by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val visibilityIcon = if (isPasswordVisible) {
        Icon(imageVector = Icons.Default.Visibility, contentDescription = null)
    } else {
        Icon(imageVector = Icons.Default.VisibilityOff, contentDescription = null)
    }
    var isClickLogin by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = loginText,
            onValueChange = { newText ->
                loginText = newText
            },
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            label = { Text(text = stringResource(R.string.email)) },
            singleLine = true,
            placeholder = { Text(text = stringResource(R.string.email_hint)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { newText ->
                password = newText
            },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) { visibilityIcon }
            },
            label = { Text(text = stringResource(R.string.password)) },
            singleLine = true,
            placeholder = { Text(text = stringResource(R.string.enter_your_password)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )
        Text(
            text = stringResource(R.string.forgot_password),
            fontSize = 12.sp,
            textAlign = TextAlign.End,
            modifier = Modifier
                .clickable {
                }
                .align(Alignment.End)
                .wrapContentWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp)
        )
        IconButton(
            onClick = {
                isClickLogin = true
                // TODO: api call
                coroutineScope.launch {
                    delay(3000)
                    isClickLogin = false
                }
            },
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth(0.8f)
                .clip(RoundedCornerShape(16.dp))
                .background(if (!isClickLogin) MaterialTheme.colorScheme.onBackground else Color.Transparent)
        ) {
            if (isClickLogin) {
                CircularProgressIndicator()
            } else {
                Icon(
                    imageVector = Icons.Default.Login,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background
                )
            }
        }
        Text(
            text = stringResource(R.string.sign_up_now),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clickable {
                    // TODO: api call
                }
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}