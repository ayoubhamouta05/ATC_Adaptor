package com.youppix.atcadaptor.presentation.mainApp.scanner

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.youppix.atcadaptor.R
import com.youppix.atcadaptor.common.Constant.APP_ENTRY
import com.youppix.atcadaptor.common.Dimens.MediumPadding
import com.youppix.atcadaptor.common.Dimens.SmallPadding
import com.youppix.atcadaptor.presentation.authentication.AuthActivity
import com.youppix.atcadaptor.presentation.components.CustomCircularProgress
import com.youppix.atcadaptor.presentation.components.CustomTopAppBar
import com.youppix.atcadaptor.presentation.components.NeedToBeLogged
import com.youppix.atcadaptor.presentation.mainApp.calculation.CalculationEvent
import com.youppix.atcadaptor.presentation.mainApp.home.HomeScreen
import com.youppix.atcadaptor.presentation.mainApp.patientDetails.PatientDetailsScreen


class ScannerScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.getNavigatorScreenModel<ScannerScreenViewModel>()
        val state by viewModel.state

        val appEntry = LocalContext.current.getSharedPreferences(APP_ENTRY, 0)
            .getString(APP_ENTRY, "")

        val context = LocalContext.current

        val scannerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val scannedData = result.data?.getStringExtra("SCAN_RESULT")
                scannedData?.let {
                    val list = it.split("|")
                    Log.d("ScannerResult" , list.toString())
                    viewModel.onEvent(ScannerScreenEvent.GetPatientData(list[0],
                        list[1],
                        list[2]))
                }
            }
        }

        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                CustomTopAppBar(
                    title = "Scanner", isArabic = false
                ) {
                    if (navigator.canPop) {
                        navigator.pop()
                    } else {
                        navigator.replace(HomeScreen())
                    }
                }
            }) { innerPadding ->
            if (state.showNeedToBeLogged) {
                NeedToBeLogged(goToLoginScreen = {
                    viewModel.onEvent(ScannerScreenEvent.SaveAppEntry("0"))
                    context.startActivity(Intent(context, AuthActivity::class.java))
                }) {
                    viewModel.onEvent(ScannerScreenEvent.ToggleNeedToBeLoggedBottomSheet)
                }
            }
            if (state.isLoading) {
                CustomCircularProgress(state.isLoading)
            } else
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp)
                        .wrapContentSize()
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    state.patientData?.let { patient ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(SmallPadding)
                                .clip(RoundedCornerShape(SmallPadding))
                                .clickable {
                                    Log.d("PatientDetails" , "patiend id : ${patient.patientId}")
                                    navigator.push(PatientDetailsScreen(patient.patientId))
                                }
                                ,
                            shape = RoundedCornerShape(SmallPadding),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White)
                        ) {

                            ListItem(headlineContent = {
                                Text(
                                    patient.userName,
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }, supportingContent = {
                                Column {
                                    Text(
                                        "ID: ${patient.userId} | ${patient.patientId}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        "Email: ${patient.userEmail} ",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        "Phone: ${patient.userPhone} ",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }


                            }, colors = ListItemDefaults.colors(
                                containerColor = androidx.compose.ui.graphics.Color.Transparent
                            )
                            )
                        }
                    }

                    Spacer(Modifier.height(MediumPadding))

                    Text(
                        text = "Find Patient With Qr Code",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    if (appEntry != "3") {

                        Button(onClick = {
                            val intent = Intent(context, QRCodeScannerActivity::class.java)
                            scannerLauncher.launch(intent)
                        }) {
                            Text("Scan QR Code")
                        }
                    }
                }



        }
    }
}