package com.example.permissions

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.permissions.ui.theme.PermissionsTheme

class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PermissionsTheme {

                val showDialog = mainViewModel.showDialog.collectAsState().value

                val launchAppSettings = mainViewModel.launchAppSettings.collectAsState().value

                val permissionsResultActivityLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { result ->
                        permissions.forEach { permission ->
                            if (result[permission] == false){
                                // If user decline the request :->
                                if (!shouldShowRequestPermissionRationale(permission)) {
                                    mainViewModel.updateLaunchAppSettings(true)
                                }
                                mainViewModel.updateShowDialog(true)
                            }
                        }
                    }
                )

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            permissions.forEach { permission ->
                                val isGranted =
                                    checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

                                // If Permission is not granted :->
                                if (!isGranted) {
                                    if (shouldShowRequestPermissionRationale(permission)) {
                                        // If user decline permission 2 times which case should show when request permission.
                                        // Directly show dialog :->
                                        mainViewModel.updateShowDialog(true)
                                    } else {
                                        // Ask for permission :->
                                        permissionsResultActivityLauncher.launch(permissions)
                                    }
                                }
                            }
                        }
                    ) {
                        Text(text = "Request Permission")
                    }
                }

                if (showDialog) {
                    PermissionDialog(
                        onDismiss = {
                            mainViewModel.updateShowDialog(false)
                        },
                        onConfirm = {
                            mainViewModel.updateShowDialog(false)

                            if (launchAppSettings){
                                // In case you want to ask permission through app settings :->
                                Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", packageName, null)
                                ).also {
                                    startActivity(it)
                                }
                                mainViewModel.updateLaunchAppSettings(false)
                            } else {
                                // In case you want to ask permission through dialog :->
                                permissionsResultActivityLauncher.launch(permissions)
                            }
                        }
                    )
                }

            }
        }
    }
}