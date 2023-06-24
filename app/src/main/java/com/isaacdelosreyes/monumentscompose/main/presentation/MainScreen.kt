package com.isaacdelosreyes.monumentscompose.main.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isaacdelosreyes.monumentscompose.DrawerContent
import com.isaacdelosreyes.monumentscompose.navigation.NavigationHost
import com.isaacdelosreyes.monumentscompose.navigation.TopLevelDestination
import com.isaacdelosreyes.monumentscompose.ui.theme.MonumentsComposeTheme
import com.isaacdelosreyes.monumentscompose.utils.MonumentsState
import com.isaacdelosreyes.monumentscompose.utils.Routes
import com.isaacdelosreyes.monumentscompose.utils.rememberMonumentsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainScreen() {

    val monumentsState: MonumentsState = rememberMonumentsState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    MonumentsComposeTheme {

        ModalNavigationDrawer(
            gesturesEnabled = drawerState.isOpen,
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(
                    topLevelDestinations = TopLevelDestination.values().asList(),
                    drawerState = drawerState,
                    onItemClick = {
                        monumentsState.navController.navigate(it.route)
                    })

            }) {

            Scaffold(floatingActionButton = {
                if (monumentsState.shouldShowFloatingButton) {
                    FloatingActionButton(onClick = {
                        monumentsState.navController.navigate(
                            Routes.Formulary.route
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = ""
                        )
                    }
                }
            }) {
                Column {
                    if (monumentsState.shouldShowAppBar) {
                        val destination = monumentsState.currentTopLevelDestination

                        if (destination != null) {

                            CenterAlignedTopAppBar(title = {
                                Text(
                                    text = stringResource(
                                        id = destination.titleTextId
                                    ),
                                    fontSize = 16.sp
                                )
                            }, navigationIcon = {

                                IconButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            drawerState.open()
                                        }
                                    },
                                    modifier = Modifier.padding(start = 10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = ""
                                    )
                                }
                            })
                        }

                    }
                    
                    NavigationHost(
                        modifier = Modifier.padding(it),
                        navController = monumentsState.navController
                    )
                }
            }
        }
    }
}