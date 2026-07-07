package com.example.feature_register.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.precentation.theme.Blue
import com.example.core.precentation.theme.White
import com.example.core.precentation.theme.WhiteSmoke
import com.example.feature_register.R
import com.example.feature_register.presentation.components.CustomTextField
import com.example.feature_register.presentation.components.ErrorText
import com.example.feature_register.presentation.viewmodel.RegisterUiEvent
import com.example.feature_register.presentation.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onRegisterSuccess: () -> Unit,
    onBackClick: () -> Unit,
) {
    val userName by viewModel.userName.collectAsState()
    val login by viewModel.login.collectAsState()
    val password by viewModel.password.collectAsState()

    val userNameError by viewModel.userNameError.collectAsState()
    val loginError by viewModel.loginError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val authError by viewModel.authError.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val isRegisterEnabled by viewModel.isRegisterEnabled.collectAsState()

    var showExitDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                RegisterUiEvent.NavigateToAuthorization -> onRegisterSuccess()
            }
        }
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text(stringResource(R.string.exit_dialog_title)) },
            text = { Text(stringResource(R.string.exit_dialog_message)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showExitDialog = false
                        onBackClick()
                    }
                ) {
                    Text(stringResource(R.string.yes))
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text(stringResource(R.string.no))
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(com.example.core.R.dimen.padding_16dp))
            ) {
                Image(
                    modifier = Modifier.heightIn(max = 200.dp),
                    painter = painterResource(R.drawable.books_register_img),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(com.example.core.R.dimen.padding_8dp)))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(WhiteSmoke)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(com.example.core.R.dimen.padding_24dp)),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                if (viewModel.hasUnsavedData()) {
                                    showExitDialog = true
                                } else {
                                    onBackClick()
                                }
                            }
                        ) {
                            Icon(
                                painter = painterResource(com.example.core.R.drawable.arrow_back_ic),
                                contentDescription = "Назад"
                            )
                        }
                        Text(
                            text = stringResource(R.string.back),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(dimensionResource(com.example.core.R.dimen.padding_16dp)))

                    Text(
                        text = stringResource(R.string.signup),
                        style = MaterialTheme.typography.displayLarge
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(com.example.core.R.dimen.padding_16dp)))

                    CustomTextField(
                        value = userName,
                        onValueChange = { viewModel.stateUserName(it) },
                        placeholder = stringResource(R.string.enter_name),
                        icon = Icons.Default.AccountBox,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (userNameError != null) {
                        ErrorText(text = userNameError!!)
                    }

                    Spacer(modifier = Modifier.height(dimensionResource(com.example.core.R.dimen.padding_16dp)))

                    CustomTextField(
                        value = login,
                        onValueChange = { viewModel.stateLogin(it) },
                        placeholder = stringResource(R.string.enter_login),
                        icon = Icons.Default.Person,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (loginError != null) {
                        ErrorText(text = loginError!!)
                    }

                    Spacer(modifier = Modifier.height(dimensionResource(com.example.core.R.dimen.padding_16dp)))

                    CustomTextField(
                        value = password,
                        onValueChange = { viewModel.statePassword(it) },
                        placeholder = stringResource(R.string.enter_password),
                        icon = Icons.Default.Lock,
                        modifier = Modifier.fillMaxWidth(),
                        isPassword = true
                    )
                    if (passwordError != null) {
                        ErrorText(passwordError!!)
                    }
                    if (authError != null) {
                        ErrorText(authError!!)
                    }

                    Spacer(modifier = Modifier.height(48.dp))

                    Button(
                        onClick = { viewModel.register() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Blue,
                            disabledContainerColor = Color(0xFFBDBDBD),
                            contentColor = White,
                            disabledContentColor = White,
                        ),
                        enabled = isRegisterEnabled
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = White,
                                modifier = Modifier.height(24.dp)
                            )
                        } else {
                            Text(
                                stringResource(R.string.register),
                                style = MaterialTheme.typography.displayMedium.copy(
                                    color = White,
                                    fontSize = 21.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
