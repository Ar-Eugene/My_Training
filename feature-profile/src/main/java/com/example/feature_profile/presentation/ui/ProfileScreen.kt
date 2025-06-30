package com.example.feature_profile.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.ui.theme.MyTrainingTheme
import com.example.feature_profile.R

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(top = dimensionResource(R.dimen.padding_24dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            contentDescription = null,
            modifier = Modifier
                .size(dimensionResource(R.dimen.image_size))
                .padding(dimensionResource(R.dimen.padding_8dp)),
            painter = painterResource(R.drawable.study_test)
        )
        Text(
            text = stringResource(R.string.user)
        )
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    MyTrainingTheme {
        ProfileScreen(modifier = Modifier)
    }
}