package com.example.savelogs;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.abhi10jul.savelogs.SaveLogsInStorage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 101;
    private SaveLogsInStorage saveLoggerInstance;
    private static final String DIRECTORY_NAME = "CustomLogger";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check or request permission
        if (hasWritePermission()) {
            initializeLogger();
        } else {
            requestWritePermission();
        }

        // Save logs when the button is clicked
        findViewById(R.id.saveLogButton).setOnClickListener(v -> {
            if (saveLoggerInstance != null) {
                saveLogsToFile();
                verifyLogSaved();
            } else {
                Toast.makeText(this, "Logger not initialized", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Check if WRITE_EXTERNAL_STORAGE permission is granted
    private boolean hasWritePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    // Request WRITE_EXTERNAL_STORAGE permission
    private void requestWritePermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }

    // Handle the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initializeLogger();
        } else {
            Toast.makeText(this, "Permission denied. Unable to save logs.", Toast.LENGTH_LONG).show();
        }
    }

    // Initialize the SaveLogsInStorage instance
    private void initializeLogger() {
        saveLoggerInstance = SaveLogsInStorage.getSaveLoggerInstance(this, DIRECTORY_NAME);
    }

    // Save logs of different levels to storage
    private void saveLogsToFile() {
        Log.i("SaveLogs", "Saving info logs...");
        saveLoggerInstance.saveInfoLogs("SaveLogs", "Info logs saved successfully!");

        Log.d("SaveLogs", "Saving debug logs...");
        saveLoggerInstance.saveDebugLogs("SaveLogs", "Debug logs saved successfully!");

        Log.w("SaveLogs", "Saving warning logs...");
        saveLoggerInstance.saveWarningLogs("SaveLogs", "Warning logs saved successfully!");

        Log.e("SaveLogs", "Saving error logs...");
        saveLoggerInstance.saveErrorLogs("SaveLogs", "Error logs saved successfully!");
    }

    // Verify if logs were saved successfully
    private void verifyLogSaved() {
        File logDirectory = new File(getExternalFilesDir(null), DIRECTORY_NAME);
        File logFile = new File(logDirectory, "info.log");

        if (!logDirectory.exists()) logDirectory.mkdirs(); // Create directory if it doesn't exist
        if (logFile.exists()) logFile.delete(); // Delete old log file if it exists

        // Create and write to a new log file
        if (writeToLogFile(logFile)) {
            Toast.makeText(this, "Logs saved successfully \uD83D\uDE0A !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save logs.", Toast.LENGTH_SHORT).show();
        }
    }

    // Helper method to write content to a log file
    private boolean writeToLogFile(File logFile) {
        try {
            if (logFile.createNewFile()) {
                try (FileWriter writer = new FileWriter(logFile)) {
                    writer.write("Log entry at " + System.currentTimeMillis() + "\n");
                    return true;
                }
            }
        } catch (IOException e) {
            Log.e("SaveLogs", "Error writing to log file", e);
        }
        return false;
    }
}
