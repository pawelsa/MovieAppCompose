package com.example.movieappcompose.screens.scanner

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.movieappcompose.widgets.MovieAppBar
import com.example.movieappcompose.widgets.Page
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.camera.CameraSettings

@Composable
fun ScannerScreen(showBottomBar:Boolean) {
    Page(showBottomBar) {
        Column {
            MovieAppBar(
                title = { Text(text = "SCANNER") },
            )
            ScannerView()
        }
    }
}

@Composable
fun ScannerView(
) {
    val context = LocalContext.current
    val barcodeView = remember {
        DecoratedBarcodeView(context)
    }

    AndroidView({ barcodeView }, modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)) {

        it.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult) {
                try {
                    when (result.barcodeFormat) {
                        BarcodeFormat.DATA_MATRIX -> Toast
                                .makeText(
                                    context,
                                    result.text,
                                    Toast.LENGTH_LONG
                                )
                                .show()
                        else -> Toast
                                .makeText(context, result.text, Toast.LENGTH_LONG)
                                .show()
                    }
                } catch (ex: Exception) {
//                    Timber.e("ScannerScreen", "Parse Error $ex")
                }
            }

            override fun possibleResultPoints(p0: MutableList<ResultPoint>?) = Unit
        })

        it.barcodeView.decoderFactory.createDecoder(
            mapOf(
                com.google.zxing.DecodeHintType.TRY_HARDER to false,
                com.google.zxing.DecodeHintType.ASSUME_GS1 to true,
                com.google.zxing.DecodeHintType.POSSIBLE_FORMATS to listOf(BarcodeFormat.DATA_MATRIX)
            )
        )

        it.changeSettings {
            focusMode = CameraSettings.FocusMode.MACRO
            isMeteringEnabled = true
            isBarcodeSceneModeEnabled = true
            isAutoFocusEnabled = true
            isContinuousFocusEnabled = false
        }
    }
/*
    LaunchedEffect{
        barcodeView.resume()
    }


    onDispose {
        barcodeView.pause()
    }*/

}

private fun DecoratedBarcodeView.changeSettings(change: CameraSettings.() -> Unit) {
    val settings = barcodeView.cameraSettings
    settings.change()
    barcodeView.cameraSettings = settings
}