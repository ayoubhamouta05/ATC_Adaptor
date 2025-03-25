package com.youppix.atcadaptor.presentation.authentication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.transitions.FadeTransition
import cafe.adriel.voyager.transitions.ScaleTransition
import cafe.adriel.voyager.transitions.SlideTransition
import com.youppix.atcadaptor.common.Dimens
import com.youppix.atcadaptor.presentation.authentication.login.LoginScreen
import com.youppix.atcadaptor.presentation.components.LeavingAppDialog
import com.youppix.atcadaptor.presentation.components.StatusBarColor
import com.youppix.atcadaptor.presentation.mainApp.MainActivity
import com.youppix.atcadaptor.presentation.ui.theme.ATCAdaptorTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val viewModel = hiltViewModel<AuthActivityViewModel>()
            ATCAdaptorTheme {
                Log.d("AppEntry", "onCreate: ${viewModel.appEntry.value}")

                StatusBarColor()
                when (viewModel.appEntry.value) {
                    "2" -> { // with account
                        val intent = Intent(this@AuthActivity, MainActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    }

                    "3" -> { // without account (visitor)
                        val intent = Intent(this@AuthActivity, MainActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    }

                    else -> {

                        var backPressedState by remember {
                            mutableStateOf(false)
                        }
                        var showDialog by remember {
                            mutableStateOf(false)
                        }
                        onBackButtonPressed {
                            showDialog = !backPressedState
                            backPressedState
                        }


                        Scaffold(
                            modifier = Modifier
                                .fillMaxSize()
                                .windowInsetsPadding(WindowInsets.systemBars),
                        ) {

                            Navigator(
                                LoginScreen(),
                                disposeBehavior = NavigatorDisposeBehavior(disposeSteps = false),
                            ) { navigator ->
                                FadeTransition(navigator = navigator)
                                backPressedState = navigator.canPop

                            }

                            LeavingAppDialog(
                                showDialog = showDialog,
                                onConfirmRequest = {
                                    finishAffinity()
                                    showDialog = false
                                },
                                onDismissRequest = {
                                    showDialog = false
                                }
                            )

                        }

                    }
                }
            }
        }
    }

    private fun onBackButtonPressed(callback: (() -> Boolean)) {
        onBackPressedDispatcher.addCallback(
            this@AuthActivity,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (callback()) {
                        remove()
                        performBackPress()
                    }
                }
            })
    }

    fun performBackPress() {
        onBackPressedDispatcher.onBackPressed()
    }
}
