Android Log Saver
This Android project demonstrates how to save logs (Info, Debug, Warning, and Error logs) to external storage in an Android app using Java and Android Studio. The app requests necessary storage permissions at runtime and uses the SaveLogsInStorage library to manage log file creation and storage.

Features:
Requests WRITE_EXTERNAL_STORAGE permission at runtime (for API 23 and above).
Saves logs of various levels: Info, Debug, Warning, and Error.
Creates log files in the external storage directory.
Verifies log saving success and provides feedback via toast messages.
Requirements:
Android API 23 (Marshmallow) or higher.
Android Studio for development.
Required permissions: WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE (for API 33 and below).
Setup:
Clone the repository.
Open the project in Android Studio.
Build and run the app on a physical device (external storage required).
