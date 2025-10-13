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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.core.precentation.theme.Blue
import com.example.core.precentation.theme.White
import com.example.core.precentation.theme.WhiteSmoke
import com.example.feature_register.R
import com.example.feature_register.presentation.components.CustomTextField
import com.example.feature_register.presentation.components.ErrorText
import com.example.feature_register.presentation.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(viewModel: RegisterViewModel = viewModel()) {
    val userName by viewModel.userName.collectAsState()
    val login by viewModel.login.collectAsState()
    val password by viewModel.password.collectAsState()

    val userNameError by viewModel.userNameError.collectAsState()
    val loginError by viewModel.loginError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()

    val isRegisterEnabled by viewModel.isRegisterEnabled.collectAsState()

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
            // ВЕРХНЯЯ часть
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.padding_16dp))
            ) {
                Image(
                    modifier = Modifier.heightIn(max = 200.dp),
                    painter = painterResource(R.drawable.books_register_img),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_8dp)))

            // НИЖНЯЯ часть
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
                        .padding(dimensionResource(R.dimen.padding_24dp)),
                    horizontalAlignment = Alignment.Start
                ) {

                    // Кнопка назад
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { }) {
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

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_16dp)))

                    Text(
                        text = stringResource(R.string.signup),
                        style = MaterialTheme.typography.displayLarge
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_16dp)))

                    // Имя
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

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_16dp)))

                    // Логин
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

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_16dp)))

                    // Пароль
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

                    Spacer(modifier = Modifier.height(48.dp))

                    Button(
                        onClick = { /* регистрация */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Blue),
                        enabled = isRegisterEnabled
                    ) {
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




