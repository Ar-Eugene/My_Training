package com.example.core.precentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Отвечает за поле ввода ответа при TEXT_ANSWER
 */
@Composable
fun AnswerTextField(
    userInput: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    placeholder: String = "Введите ответ",
) {
    TextField(
        value = userInput,
        onValueChange = { if (enabled) onValueChange(it) },
        placeholder = { Text(placeholder) },
        textStyle = MaterialTheme.typography.labelSmall,
        maxLines = 1,
        singleLine = true,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            errorContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        )
    )
}
