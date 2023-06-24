package com.isaacdelosreyes.monumentscompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.isaacdelosreyes.monumentscompose.navigation.TopLevelDestination
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(
    drawerState: DrawerState,
    topLevelDestinations: List<TopLevelDestination>,
    onItemClick: (TopLevelDestination) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    ModalDrawerSheet() {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentScale = ContentScale.Crop,
            contentDescription = "Main app icon",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(topLevelDestinations) { topLevelDestination ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            onItemClick(topLevelDestination)
                        }
                ) {
                    Icon(
                        imageVector = topLevelDestination.selectedIcon,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = stringResource(id = topLevelDestination.titleTextId),
                        modifier = Modifier.weight(1f)
                    )
                }

            }
        }
    }
}