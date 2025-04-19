package com.youppix.atcadaptor.presentation.mainApp.home

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.youppix.atcadaptor.common.Constant
import com.youppix.atcadaptor.common.Dimens.BottomBarHeight
import com.youppix.atcadaptor.common.Dimens.ExtraSmallPadding2
import com.youppix.atcadaptor.common.Dimens.MediumPadding
import com.youppix.atcadaptor.common.Dimens.SmallPadding
import com.youppix.atcadaptor.common.UserRole
import com.youppix.atcadaptor.presentation.authentication.AuthActivity
import com.youppix.atcadaptor.presentation.components.CustomCircularProgress
import com.youppix.atcadaptor.presentation.components.NeedToBeLogged
import com.youppix.atcadaptor.presentation.components.NotificationPermissionHandler
import com.youppix.atcadaptor.presentation.mainApp.components.CustomSearchBar
import com.youppix.atcadaptor.presentation.mainApp.components.EmptyScreen
import com.youppix.atcadaptor.presentation.mainApp.details.DetailsScreen
import com.youppix.atcadaptor.presentation.mainApp.home.components.HomeSearchingCartItem
import com.youppix.atcadaptor.presentation.mainApp.home.components.WelcomeScreen
import com.youppix.atcadaptor.presentation.mainApp.scanner.ScannerScreenEvent
import com.youppix.atcadaptor.presentation.ui.theme.ATCAdaptorTheme

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.getNavigatorScreenModel<HomeViewModel>()
        val state by viewModel.state
        val context = LocalContext.current
        val searchQuery by viewModel.searchQuery.collectAsState()
        val userType = context.getSharedPreferences(Constant.APP_ENTRY, 0)
            .getString("userType", "0")

        val appEntry =
            context.getSharedPreferences(Constant.APP_ENTRY, 0).getString(Constant.APP_ENTRY, "")


        Log.d("HomeScreen", "App Entry :$appEntry")

        LaunchedEffect(Unit) {
            viewModel.onEvent(HomeEvent.UpdateUserType(userType!!.toInt()))
        }

        NotificationPermissionHandler()

        ATCAdaptorTheme {
            Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                CustomSearchBar(value = searchQuery, modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MediumPadding,
                        end = MediumPadding,
                        top = MediumPadding,
                        bottom = SmallPadding
                    ),
                    hint = stringResource(R.string.search),
                    onTextCleared = {
                    viewModel.onEvent(HomeEvent.UpdateSearchQuery(""))
                }, onSearchClicked = {

                        viewModel.onEvent(HomeEvent.Search(searchQuery))
                }, onTextChange = {
                        viewModel.onEvent(HomeEvent.UpdateSearchQuery(it))
                } ,
                    isEnabled = appEntry !="3" ,
                    onBoxCLicked = {
                        if (appEntry == "3")
                        viewModel.onEvent(HomeEvent.ToggleNeedToBeLoggedBottomSheet)
                    }
                )

            }


            ) { innerPadding ->

                if (state.needToBeLogged) {
                    NeedToBeLogged(goToLoginScreen = {
                        viewModel.onEvent(HomeEvent.SaveAppEntry("0"))
                        context.startActivity(Intent(context, AuthActivity::class.java))
                    }) {
                        viewModel.onEvent(HomeEvent.ToggleNeedToBeLoggedBottomSheet)
                    }
                }

                if (state.isLoading) {
                    CustomCircularProgress(state.isLoading)
                } else {
                    if (state.items.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            contentPadding = PaddingValues(
                                horizontal = SmallPadding, vertical = ExtraSmallPadding2
                            ),
                        ) {


                            items(state.items.size, key = {
                                it
                            }) { index ->
                                HomeSearchingCartItem(
                                    modifier = Modifier.padding(
                                        top = if (index == 0) if (userType == "1") 0.dp else SmallPadding else 0.dp,
                                        bottom = if (index == state.items.size - 1) MediumPadding + BottomBarHeight
                                        else 0.dp
                                    ),
                                    homeSearchItemData = state.items[index],
                                    userType = userType?.toInt() ?: 0
                                ) {
                                    navigator.push(
                                        DetailsScreen(
                                            userRole = if (userType?.toInt() == 1) UserRole.Doctor else UserRole.Patient,
                                            medicationName = state.items[index].medicationName
                                                ?: "",
                                            patientId = state.items[index].patientId
                                        )
                                    )
                                }
                            }
                        }

                    } else if(searchQuery.isEmpty()){
                        WelcomeScreen(modifier = Modifier.padding(innerPadding))
                    }else{
                        EmptyScreen(
                            state.errorMessage,
                            if (userType == "1") "there is no patient or data with this search query" else "there is no data with this search query"
                        )
                    }
                }

            }


        }


    }
}