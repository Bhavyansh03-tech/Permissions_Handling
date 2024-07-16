# Permissions Handling App

This is a sample Android app that demonstrates how to request camera and microphone permissions using Jetpack Compose.

## Features
- Requests camera and microphone permissions.
- Shows a dialog when permissions are denied.
- Navigates to app settings when permissions are permanently denied.

## Screenshots

<div style="display: flex; justify-content: center; align-items: center;">
    <img src="https://github.com/user-attachments/assets/dc177a68-dc12-4000-a0ae-a4e69e48c6d9" alt="First Screenshot" style="width: 200px; height: auto; margin-right: 10px;">
    <img src="https://github.com/user-attachments/assets/d278ba6c-f707-448e-b587-aae659697858" alt="Second Screenshot" style="width: 200px; height: auto; margin-right: 10px;">
    <img src="https://github.com/user-attachments/assets/9466d7d5-0bbd-45cd-bdfc-6ece181e2bba" alt="Third Screenshot" style="width: 200px; height: auto;">
</div>


## Installation

1. Clone the repository:
```bash
    git clone https://github.com/Bhavyansh03-tech/Permissions_Handling.git
```
2. Open the project in Android Studio.
3. Build and run the app on your Android device or emulator.


## Code Highlights

### Asking Permission If Denied Twice
If the user denies the permission twice, a dialog is shown to explain why the permissions are needed.

```kotlin
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
```

## Showing a Dialog When Permissions Are Denied

The app displays a dialog to inform the user about the need for permissions and provides an option to go to the app settings if the permissions were permanently denied.

```kotlin
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
```


## Contributing

Contributions are welcome! Please fork the repository and submit a pull request for any improvements or bug fixes.

1. Fork the repository.
2. Create your feature branch (`git checkout -b feature/your-feature`).
3. Commit your changes (`git commit -am 'Add some feature'`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Create a new Pull Request.

## Contact

For questions or feedback, please contact [@Bhavyansh03-tech](https://github.com/Bhavyansh03-tech).

---
