package com.youppix.atcadaptor.presentation.authentication.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.youppix.atcadaptor.R
import com.youppix.atcadaptor.common.Constant.APP_ENTRY
import com.youppix.atcadaptor.common.Dimens
import com.youppix.atcadaptor.common.Dimens.ExtraSmallPadding
import com.youppix.atcadaptor.common.Dimens.LargePadding
import com.youppix.atcadaptor.common.Dimens.MediumPadding
import com.youppix.atcadaptor.common.Dimens.SmallPadding
import com.youppix.atcadaptor.presentation.components.CustomCircularProgress
import com.youppix.atcadaptor.presentation.components.CustomDialog
import com.youppix.atcadaptor.presentation.components.CustomTextField
import com.youppix.atcadaptor.presentation.mainApp.MainActivity
import kotlinx.coroutines.launch

class LoginScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.getNavigatorScreenModel<LoginViewModel>()
        val loginState = viewModel.loginState.value
        val context = LocalContext.current
        val scope = rememberCoroutineScope()

        LaunchedEffect(
            loginState.loginSuccessful,
            loginState.needUserApprove,
            loginState.loginError
        ) {
            if (loginState.loginSuccessful) {
                loginSuccessful(viewModel, context)
                viewModel.resetState()
            } else if (!loginState.loginError.isNullOrEmpty()) {
                Toast.makeText(context, loginState.loginError, Toast.LENGTH_SHORT).show()
                viewModel.resetState()
            }
        }

        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    title = {
                        Text(
                            text = stringResource(id = R.string.signin),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleSmall.copy(fontSize = 24.sp),
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                )
            }) { innerPadding ->
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .padding(
                        bottom = innerPadding.calculateBottomPadding()
                    )
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(id = R.string.welcomeBack),
                    style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = innerPadding
                                .calculateTopPadding()
                                .plus(
                                    LargePadding
                                )
                        ),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(id = R.string.signInBodyText),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = LargePadding,
                            start = LargePadding,
                            end = LargePadding
                        ),
                    textAlign = TextAlign.Center
                )
                //Email
                CustomTextField(
                    modifier = Modifier
                        .padding(bottom = SmallPadding)
                        .padding(
                            horizontal = Dimens.LargePadding
                        ),
                    value = loginState.email,
                    label = stringResource(id = R.string.email),
                    placeholder = stringResource(id = R.string.enterYourEmail),
                    trailingIcon = Icons.Outlined.Email,
                    onValueChange = { value ->
                        viewModel.updateEmail(value)
                    },
                    isError = !loginState.emailError.isNullOrEmpty(),
                    isPassword = false,
                    errorMessage = loginState.emailError ?: ""
                )

                //Password

                CustomTextField(
                    modifier = Modifier
                        .padding(bottom = SmallPadding)
                        .padding(
                            horizontal = Dimens.LargePadding
                        ),
                    value = loginState.password,
                    onValueChange = { value ->
                        viewModel.updatePassword(value)
                    },
                    label = stringResource(id = R.string.password),
                    placeholder = stringResource(id = R.string.enterYourPassword),
                    trailingIcon = Icons.Outlined.Lock,
                    isError = !loginState.passwordError.isNullOrEmpty(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    isPassword = true,
                    showPassword = loginState.showPassword,
                    onShowPassword = {
                        viewModel.showPassword(it)
                    },
                    errorMessage = loginState.passwordError ?: ""
                )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = LargePadding
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Checkbox(checked = loginState.rememberMe, onCheckedChange = {
                        viewModel.updateRememberMe(it)
                    })

                    Text(
                        text = stringResource(id = R.string.rememberMe),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.offset(-ExtraSmallPadding)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    TextButton(onClick = {
                        Log.d("LoginScreen", navigator.toString())
//                            navigator.push(CheckEmailValidationScreen())
                    }) {
                        Text(
                            text = stringResource(id = R.string.forgotPassword),
                            color = Color.Gray,
                            textDecoration = TextDecoration.Underline,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }


                Button(
                    onClick = {
                        if (viewModel.validateForm(
                                loginState.email,
                                loginState.password,
                                context
                            )
                        ) {
                            scope.launch {
                                viewModel.login(loginState.email, loginState.password)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = LargePadding,
                            vertical = MediumPadding
                        ),
                    shape = RoundedCornerShape(30)
                ) {
                    Text(
                        text = stringResource(id = R.string.continuee),
                        Modifier.padding(vertical = ExtraSmallPadding),
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Spacer(modifier = Modifier.weight(1f))




                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = SmallPadding
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.dontHaveAnAccount),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = Color.Gray,
                        modifier = Modifier.offset(x = SmallPadding)
                    )
                    TextButton(onClick = {
//                            navigator.push(SignUpScreen())
                    }) {
                        Text(
                            text = stringResource(id = R.string.signup),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color.Black
                        )

                    }
                }

                Text(
                    text = stringResource(R.string.or),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = colorResource(R.color.placeholder)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = -SmallPadding),
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = LargePadding,
                        )
                        .offset(y = -SmallPadding * 2),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.continuee),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = Color.Gray,
                        modifier = Modifier
                            .offset(x = ExtraSmallPadding)

                    )
                    TextButton(onClick = {
//                            navigator.push(SignUpScreen())
                    }) {
                        Text(
                            text = stringResource(id = R.string.withoutAnAccount),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color.Black
                        )

                    }
                }

            }

            CustomDialog(
                title = "verify email",
//                    stringResource(id = R.string.verifyEmail),
                message = "verify email body",
//                    stringResource(id = R.string.verifyEmailBody),
                showDialog = loginState.needUserApprove ?: false,
                onConfirmRequest = {
//                        navigator.push(VerificationEmailSignUpScreen(SignUpState(email = loginState.email)))
                    viewModel.resetState()
                },
                onDismissRequest = {
                    viewModel.resetState()
                })

            CustomCircularProgress(isLoading = loginState.isLoading)


        }
    }

    private fun loginSuccessful(viewModel: LoginViewModel, context: Context) {
        viewModel.apply {

            if (viewModel.loginState.value.rememberMe) {
                // save user login state
                saveAppEntry(APP_ENTRY, "2")

            }
            // save user Information
            saveUserInformation()
        }

        context.startActivity(Intent(context, MainActivity::class.java))
        (context as Activity).finish()

    }
}