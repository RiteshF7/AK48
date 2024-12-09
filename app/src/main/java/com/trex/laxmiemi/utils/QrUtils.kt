package com.trex.laxmiemi.utils

import NewDeviceIds
import android.app.admin.DevicePolicyManager
import android.graphics.Bitmap
import android.util.Log
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.trex.rexnetwork.Constants
import com.trex.rexnetwork.domain.firebasecore.firesstore.FirestoreExtraData
import com.trex.rexnetwork.domain.repositories.FileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class QrUtils(
    extraData: FirestoreExtraData,
) {
    private val checksum = extraData.checksum

    suspend fun getQrBitmap(deviceIds: NewDeviceIds): Bitmap? {
        // Retrieve the latest APK URL
        val updatedApkUrl =
            withContext(Dispatchers.IO) {
                val result = FileRepository().getRascFileUrl()
                if (result.isSuccessful) {
                    result.body()?.url ?: return@withContext null
                } else {
                    Log.e("QrUtils", "Failed to fetch APK URL: ${result.errorBody()?.string()}")
                    return@withContext null
                }
            }

        return if (updatedApkUrl != null) {
            // Generate QR JSON and QR Code Bitmap
            val qrJson =
                withContext(Dispatchers.Default) {
                    getQrJson(deviceIds, updatedApkUrl)
                }
            withContext(Dispatchers.Default) {
                generateQRCodeBitmap(qrJson.toString())
            }
        } else {
            null
        }
    }

    private fun getQrJson(
        newDeviceIds: NewDeviceIds,
        url: String,
    ): JsonObject {
        val clientPackageName = "com.trex.rexandroidsecureclient"
        val adminExtrasBundle =
            buildJsonObject {
                put(Constants.ADMIN_SHOP_ID, newDeviceIds.shopId)
                put(Constants.ADMIN_DEVICE_ID, newDeviceIds.deviceId)
            }

        // Debug log to verify admin extras content
        Log.d("QRDebug", "Admin Extras Bundle: $adminExtrasBundle")

        return buildJsonObject {
            put(
                DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME,
                "$clientPackageName/$clientPackageName.DeviceAdminReceiver",
            )
            put(
                DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_CHECKSUM,
                checksum,
            )
            put(
                DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_LOCATION,
                url,
            )
            put(
                DevicePolicyManager.EXTRA_PROVISIONING_ADMIN_EXTRAS_BUNDLE,
                adminExtrasBundle,
            )
        }
    }

    private fun generateQRCodeBitmap(content: String): Bitmap {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(
                    x,
                    y,
                    if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE,
                )
            }
        }

        return bitmap
    }
}
