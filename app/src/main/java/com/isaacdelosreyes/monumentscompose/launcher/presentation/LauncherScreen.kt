package com.isaacdelosreyes.monumentscompose.launcher.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isaacdelosreyes.monumentscompose.R
import kotlinx.coroutines.delay

@Preview
@Composable
fun LauncherScreen(navigateToHome: () -> Unit = {}) {

    val permission = android.Manifest.permission.CAMERA

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            navigateToHome()
        }
    }

    LaunchedEffect(Unit) {
        delay(3000)
        launcher.launch(permission)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.monument_launcher
            ),
            contentDescription = stringResource(
                id = R.string.launcher_cover_content_description
            ),
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
        )
    }
}