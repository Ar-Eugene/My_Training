package com.example.core.precentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

/**
 * Диалоговое окно перед закрытием экрана если ответили не на все вопросы
 */
@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "иконка диалогового окна")
        },
        title = {
            Text(
                text = dialogTitle,
                style = MaterialTheme.typography.displayLarge,
                fontSize = 24.sp,
            )
        },
        text = {
            Text(
                text = dialogText,
                style = MaterialTheme.typography.labelSmall,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = { onDismissRequest() }) {
                    Text(
                        text = "Нет",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
                TextButton(onClick = { onConfirmation() }) {
                    Text(
                        text = "Да",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
            }
        }
    )
}
