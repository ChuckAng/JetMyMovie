package com.example.mymovie.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.mymovie.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun LoginScreen() {
    var loginText by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var isClickLogin by remember { mutableStateOf(false) }
    var isShowPopup by remember { mutableStateOf(false) }
    val modifier = Modifier
        .wrapContentSize()
        .verticalScroll(rememberScrollState())
        .padding(16.dp)

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val painter = painterResource(id = R.drawable.movie_logo)
        Column(modifier = Modifier.align(Alignment.TopEnd)) {
            Image(painter = painter, null, contentScale = ContentScale.Crop)
        }
        Column(
            modifier = if (!isShowPopup) modifier
                .align(Alignment.Center)
                .imePadding() else modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.get_started),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineLarge,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .wrapContentSize()
                    .padding(horizontal = 32.dp, vertical = 8.dp)
            )
            Text(
                text = stringResource(R.string.please_sign_in_to_continue),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineSmall,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .wrapContentSize()
                    .padding(start = 32.dp, end = 32.dp, bottom = 16.dp)
            )
            OutlinedTextField(
                value = loginText,
                enabled = !isShowPopup,
                onValueChange = { newText -> loginText = newText },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null
                    )
                },
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
                enabled = !isShowPopup,
                onValueChange = { newText ->
                    password = newText
                },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        if (isPasswordVisible) {
                            Icon(
                                imageVector = Icons.Default.Visibility,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.VisibilityOff,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
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
                enabled = !isShowPopup,
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
                    .clip(RoundedCornerShape(8.dp))
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
            val interactionSource = remember { MutableInteractionSource() }
            Text(
                text = stringResource(R.string.sign_up_now),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        enabled = !isClickLogin,
                    ) {
                        isShowPopup = true
                    }
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }

    if (isShowPopup) {
        SignUpPopup {
            isShowPopup = false
        }
    }
}

@Composable
fun SignUpPopup(onClickPopup: () -> Unit) {
    var userEmail by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPasword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isClickSignUp by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Popup(
        alignment = Alignment.TopStart,
        properties = PopupProperties(
            dismissOnClickOutside = false,
            focusable = true
        ),
        onDismissRequest = { onClickPopup() },
    ) {
        Column(
            Modifier
                .fillMaxHeight(0.8f)
                .padding(top = 56.dp, start = 32.dp, end = 32.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(MaterialTheme.colorScheme.onBackground, RoundedCornerShape(12.dp))
                .imePadding()
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.sign_up_now),
                    color = MaterialTheme.colorScheme.background,
                    style = MaterialTheme.typography.headlineMedium,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = 32.dp)
                )
                Spacer(Modifier.weight(1f))
                IconButton(
                    onClick = { onClickPopup() },
                    enabled = !isClickSignUp,
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, end = 32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.background
                    )
                }
            }
            OutlinedTextField(
                value = username,
                onValueChange = { newText -> username = newText },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.background
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                label = { Text(text = stringResource(R.string.username)) },
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.username_hint),
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.background,
                    unfocusedTextColor = MaterialTheme.colorScheme.background,
                    focusedContainerColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedContainerColor = MaterialTheme.colorScheme.onBackground,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.secondary,
                    focusedLabelColor = MaterialTheme.colorScheme.background,
                    unfocusedLabelColor = MaterialTheme.colorScheme.background,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, bottom = 8.dp)
            )

            OutlinedTextField(
                value = userEmail,
                onValueChange = { newText ->
                    userEmail = newText
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.background
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                label = { Text(text = stringResource(R.string.email)) },
                maxLines = 1,
                placeholder = { Text(text = stringResource(R.string.email_hint)) },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.background,
                    unfocusedTextColor = MaterialTheme.colorScheme.background,
                    focusedContainerColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedContainerColor = MaterialTheme.colorScheme.onBackground,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.secondary,
                    focusedLabelColor = MaterialTheme.colorScheme.background,
                    unfocusedLabelColor = MaterialTheme.colorScheme.background,
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            )
            OutlinedTextField(
                value = password,
                onValueChange = { newText -> password = newText },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.background
                    )
                },
                trailingIcon = {
                    IconButton(onClick = {
                        isPasswordVisible = !isPasswordVisible
                    }) {
                        if (isPasswordVisible) {
                            Icon(
                                imageVector = Icons.Default.Visibility,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.background
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.VisibilityOff,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                },
                label = { Text(text = stringResource(R.string.password)) },
                placeholder = { Text(text = stringResource(R.string.enter_your_password)) },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.background,
                    unfocusedTextColor = MaterialTheme.colorScheme.background,
                    focusedContainerColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedContainerColor = MaterialTheme.colorScheme.onBackground,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.secondary,
                    focusedLabelColor = MaterialTheme.colorScheme.background,
                    unfocusedLabelColor = MaterialTheme.colorScheme.background,
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 32.dp, bottom = 8.dp)
            )
            OutlinedTextField(
                value = confirmPasword,
                onValueChange = { newText -> confirmPasword = newText },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                label = { Text(text = stringResource(R.string.confirm_password)) },
                singleLine = true,
                placeholder = { Text(text = stringResource(R.string.confirm_your_password)) },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.background,
                    unfocusedTextColor = MaterialTheme.colorScheme.background,
                    focusedContainerColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedContainerColor = MaterialTheme.colorScheme.onBackground,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.secondary,
                    focusedLabelColor = MaterialTheme.colorScheme.background,
                    unfocusedLabelColor = MaterialTheme.colorScheme.background,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            )
            IconButton(
                onClick = {
                    isClickSignUp = true
                    // TODO: api call
                    coroutineScope.launch {
                        delay(3000)
                        isClickSignUp = false
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (!isClickSignUp) MaterialTheme.colorScheme.background else Color.Transparent)
            ) {
                if (isClickSignUp) {
                    CircularProgressIndicator()
                } else {
                    Text(
                        text = stringResource(R.string.create_account),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}