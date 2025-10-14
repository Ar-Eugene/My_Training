package com.example.feature_register.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.domain.models.ExamType
import com.example.feature_register.presentation.viewmodel.SelectExamViewModel

@Composable
fun SelectExamScreen(
    onOgeClick: () -> Unit,
    onEgeClick: () -> Unit,
    viewModel: SelectExamViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                viewModel.saveExamType(ExamType.OGE)
                onOgeClick()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("ОГЭ")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.saveExamType(ExamType.EGE)
                onEgeClick()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("ЕГЭ")
        }
    }
}