package com.youppix.atcadaptor.presentation.mainApp.alert

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.youppix.atcadaptor.R
import com.youppix.atcadaptor.common.Constant.APP_ENTRY
import com.youppix.atcadaptor.common.Dimens.BottomBarHeight
import com.youppix.atcadaptor.common.Dimens.ExtraSmallPadding
import com.youppix.atcadaptor.common.Dimens.ExtraSmallPadding2
import com.youppix.atcadaptor.common.Dimens.MediumPadding
import com.youppix.atcadaptor.common.Dimens.SmallPadding
import com.youppix.atcadaptor.presentation.components.CustomCircularProgress
import com.youppix.atcadaptor.presentation.components.CustomTopAppBar
import com.youppix.atcadaptor.presentation.mainApp.alert.components.NotificationDetailsScreen
import com.youppix.atcadaptor.presentation.mainApp.alert.components.NotificationsListItem
import com.youppix.atcadaptor.presentation.mainApp.components.EmptyContent
import com.youppix.atcadaptor.presentation.mainApp.home.HomeScreen

class AlertScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val viewModel = navigator.getNavigatorScreenModel<AlertViewModel>()
        val state by viewModel.state
        val userId = context.getSharedPreferences(APP_ENTRY, 0).getString("userId", "")
        LaunchedEffect(Unit) {
            userId?.let {
                viewModel.getNotifications(userId = it.toInt())
            }
        }
        LaunchedEffect(state.notifications) {
            userId?.let {
                viewModel.updateNotifications(userId = it.toInt())
            }
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CustomTopAppBar(
                    title = "Alerts",
                    isArabic = false
                ) {
                    if (navigator.canPop) {
                        navigator.pop()
                    } else {
                        navigator.replace(HomeScreen())
                    }
                }
            }
        ) { innerPadding ->
            if (state.isLoading) {
                CustomCircularProgress(state.isLoading)
            } else if (state.notifications.isEmpty()) {
                EmptyContent(
                    alphaAnim = 1f,
                    message = "No Alerts Yet.",
                    R.drawable.ic_search_document
                ) { }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(MediumPadding)
                ) {
                    items(state.notifications.size, key = { state.notifications[it].id }) {
                        NotificationsListItem(
                            modifier = Modifier
                                .padding(
                                    vertical = ExtraSmallPadding
                                )
                                .padding(bottom = if (it == state.notifications.size - 1) BottomBarHeight + SmallPadding else 0.dp),
                            notification = state.notifications[it]
                        ) { notification ->
                            navigator.push(NotificationDetailsScreen(notification))
                        }
                    }

                }
            }

        }

    }
}