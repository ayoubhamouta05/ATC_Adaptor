package com.youppix.atcadaptor.presentation.mainApp.scanner

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.youppix.atcadaptor.common.Constant.APP_ENTRY
import com.youppix.atcadaptor.presentation.authentication.AuthActivity
import com.youppix.atcadaptor.presentation.components.NeedToBeLogged


class ScannerScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.getNavigatorScreenModel<ScannerScreenViewModel>()
        val state by viewModel.state

        val appEntry = LocalContext.current.getSharedPreferences(APP_ENTRY, 0)
            .getString(APP_ENTRY, "")

        val context = LocalContext.current

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            if (state.showNeedToBeLogged) {
                NeedToBeLogged(goToLoginScreen = {
                    viewModel.onEvent(ScannerScreenEvent.SaveAppEntry("0"))
                    context.startActivity(Intent(context, AuthActivity::class.java))
                }) {
                    viewModel.onEvent(ScannerScreenEvent.ToggleNeedToBeLoggedBottomSheet)
                }
            }

            Column(modifier = Modifier.padding(innerPadding)) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = {
                        if (appEntry == "3")
                            viewModel.onEvent(ScannerScreenEvent.ToggleNeedToBeLoggedBottomSheet)
                        else
                            viewModel.onEvent(ScannerScreenEvent.SwitchScannerScreen(false))
                    }) { Text("Generate QR") }

                    Button(onClick = {
                        if (appEntry == "3")
                            viewModel.onEvent(ScannerScreenEvent.ToggleNeedToBeLoggedBottomSheet)
                        else
                            viewModel.onEvent(ScannerScreenEvent.SwitchScannerScreen(true))
                    }) { Text("Scan QR") }
                }

                // 🔹 Affichage conditionnel selon l'état du ViewModel
                when (state.isScannerScreen) {
                    false -> QRCodeGeneratorScreen(appEntry)
                    true -> QRCodeScannerScreen(appEntry)
                }
            }
        }
    }
}


@Composable
private fun QRCodeGeneratorScreen(appEntry: String?) {
    var text by remember { mutableStateOf("Hello, QR!") }
    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        if (appEntry != "3") {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Enter text") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Button(
                onClick = { qrBitmap = generateQRCode(text) },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Generate QR Code")
            }

            qrBitmap?.let {
                AndroidView(
                    factory = { context ->
                        ImageView(context).apply { setImageBitmap(it) }
                    },
                    modifier = Modifier
                        .size(250.dp)
                        .padding(16.dp)
                )
            }
        }
    }
}


@Composable
fun QRCodeScannerScreen(appEntry: String?) {
    var scannedText by remember { mutableStateOf("Scan a QR Code") }


    val scannerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val scannedData = result.data?.getStringExtra("SCAN_RESULT")
            scannedText = scannedData ?: "No result"
        }
    }

    val context = LocalContext.current


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        if (appEntry != "3") {
            SelectionContainer {
                Text(scannedText, modifier = Modifier.padding(16.dp))
            }
            Button(onClick = {
                val intent = Intent(context, QRCodeScannerActivity::class.java)
                scannerLauncher.launch(intent)
            }) {
                Text("Scan QR Code")
            }
        }
    }
}


fun generateQRCode(content: String, size: Int = 512): Bitmap {
    val bitMatrix: BitMatrix =
        MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size)
    val width = bitMatrix.width
    val height = bitMatrix.height
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

    for (x in 0 until width) {
        for (y in 0 until height) {
            bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
        }
    }
    return bitmap
}