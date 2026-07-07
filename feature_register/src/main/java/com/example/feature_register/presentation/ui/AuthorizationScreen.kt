package com.example.feature_register.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.feature_register.presentation.viewmodel.AuthorizationUiEvent
import com.example.feature_register.presentation.viewmodel.AuthorizationViewModel

@Composable
fun AuthorizationScreen(
    viewModel: AuthorizationViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
) {
    val login by viewModel.login.collectAsState()
    val password by viewModel.password.collectAsState()
    val loginError by viewModel.loginError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val authError by viewModel.authError.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isLoginEnabled by viewModel.isLoginEnabled.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                AuthorizationUiEvent.LoginSuccess -> onLoginSuccess()
            }
        }
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
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(R.drawable.school_objects_img),
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.hello),
                    style = MaterialTheme.typography.displayLarge.copy(color = White)
                )
                Text(
                    text = stringResource(R.string.welcom_to_progect),
                    style = MaterialTheme.typography.displayMedium.copy(color = White)
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
                    Text(
                        text = stringResource(R.string.enter),
                        style = MaterialTheme.typography.displayLarge
                    )
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

                    Spacer(modifier = Modifier.height(dimensionResource(com.example.core.R.dimen.padding_24dp)))

                    CustomTextField(
                        value = password,
                        onValueChange = { viewModel.statePassword(it) },
                        placeholder = stringResource(R.string.enter_password),
                        icon = Icons.Default.Lock,
                        modifier = Modifier.fillMaxWidth(),
                        isPassword = true
                    )
                    if (passwordError != null) {
                        ErrorText(text = passwordError!!)
                    }
                    if (authError != null) {
                        ErrorText(authError!!)
                    }

                    Spacer(modifier = Modifier.height(48.dp))

                    Button(
                        onClick = { viewModel.login() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Blue,
                            disabledContainerColor = Color(0xFFBDBDBD),
                            contentColor = White,
                            disabledContentColor = White,
                        ),
                        enabled = isLoginEnabled
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = White,
                                modifier = Modifier.height(24.dp)
                            )
                        } else {
                            Text(
                                stringResource(R.string.login),
                                style = MaterialTheme.typography.displayMedium.copy(
                                    color = White,
                                    fontSize = 21.sp
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.dont_have_account),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = stringResource(R.string.register),
                            modifier = Modifier
                                .clickable { onRegisterClick() }
                                .padding(start = 6.dp),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }
            }
        }
    }
}
