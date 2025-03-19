package com.youppix.atcadaptor.presentation.mainApp.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.youppix.atcadaptor.common.Constant
import com.youppix.atcadaptor.common.Dimens.BottomBarHeight
import com.youppix.atcadaptor.common.Dimens.ExtraSmallPadding2
import com.youppix.atcadaptor.common.Dimens.LargePadding
import com.youppix.atcadaptor.common.Dimens.MediumPadding
import com.youppix.atcadaptor.common.Dimens.SmallPadding
import com.youppix.atcadaptor.common.UserRole
import com.youppix.atcadaptor.presentation.components.CustomCircularProgress
import com.youppix.atcadaptor.presentation.mainApp.components.CustomSearchBar
import com.youppix.atcadaptor.presentation.mainApp.details.DetailsScreen
import com.youppix.atcadaptor.presentation.mainApp.home.components.HomeSearchingCartItem
import com.youppix.atcadaptor.presentation.ui.theme.ATCAdaptorTheme

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.getNavigatorScreenModel<HomeViewModel>()
        val state by viewModel.state
        val searchQuery by viewModel.searchQuery.collectAsState()
        val userType = LocalContext.current.getSharedPreferences(Constant.APP_ENTRY, 0)
            .getString("userType", "0")

        LaunchedEffect(Unit) {
            viewModel.onEvent(HomeEvent.UpdateUserType(userType!!.toInt()))
        }

        ATCAdaptorTheme {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    ,
                topBar = {
                    CustomSearchBar(value = searchQuery,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = MediumPadding , end = MediumPadding , top = MediumPadding )
                            ,
                        onTextCleared = {
                            viewModel.onEvent(HomeEvent.UpdateSearchQuery(""))
                        },
                        onSearchClicked = {
                            viewModel.onEvent(HomeEvent.Search(searchQuery))
                        },
                        onTextChange = {
                            viewModel.onEvent(HomeEvent.UpdateSearchQuery(it))
                        })

                }


            ) { innerPadding ->

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(
                        horizontal = SmallPadding,
                        vertical = ExtraSmallPadding2
                    ),
                ) {
                    items(state.items) { item ->
                        HomeSearchingCartItem(
                            modifier = Modifier.padding(
                                bottom = if (item == state.items.last())
                                    MediumPadding + BottomBarHeight
                                else 0.dp
                            ),
                            homeSearchItemData = item,
                            userType = userType?.toInt() ?: 0
                        ) {
                            navigator.push(
                                DetailsScreen(
                                    userRole = if (userType?.toInt() == 1)
                                        UserRole.Doctor else UserRole.Patient ,
                                    medicationName = item.medicationName?:"" ,
                                    patientId = item.patientId
                                )
                            )
                        }
                    }
                }

            }

            CustomCircularProgress(state.isLoading)

        }


    }
}