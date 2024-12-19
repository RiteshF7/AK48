package com.trex.laxmiemi.utils

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class AppUpdateUtil(

) {


    suspend fun checkForUpdates(): Boolean =
        withContext(Dispatchers.IO) {
            try {
                val url = URL("https://example.com/check-update") // Replace with your URL
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                val responseCode = connection.responseCode
                if (responseCode == 200) {
                    // Parse response and determine if update is available
                    // For simplicity, let's assume it returns true if an update is available
                    true
                } else {
                    false
                }
            } catch (e: Exception) {
                false
            }
        }

    fun showUpdateDialog(
        context: Context,
        onUpdateConfirmed: () -> Unit,
    ) {
        AlertDialog
            .Builder(context)
            .setTitle("Update Available")
            .setMessage("A new version of the app is available. Please update to continue.")
            .setPositiveButton("Update") { _, _ ->
                onUpdateConfirmed()
            }.setNegativeButton("Cancel", null)
            .show()
    }

    suspend fun downloadApk(
        context: Context,
        apkUrl: String,
    ): File? =
        withContext(Dispatchers.IO) {
            try {
                val url = URL(apkUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                val apkFile = File(context.cacheDir, "update.apk")
                connection.inputStream.use { input ->
                    FileOutputStream(apkFile).use { output ->
                        input.copyTo(output)
                    }
                }
                apkFile
            } catch (e: Exception) {
                null
            }
        }

    fun installApk(
        context: Context,
        apkFile: File,
    ) {
        val intent =
            Intent(Intent.ACTION_VIEW).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                val apkUri: Uri =
                    FileProvider.getUriForFile(context, "${context.packageName}.provider", apkFile)
                setDataAndType(apkUri, "application/vnd.android.package-archive")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
        context.startActivity(intent)
    }
}
