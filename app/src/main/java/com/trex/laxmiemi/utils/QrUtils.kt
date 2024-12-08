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

        // Find the bounding box of the QR code within the bit matrix
        var minX = bitMatrix.width
        var minY = bitMatrix.height
        var maxX = 0
        var maxY = 0

        for (x in 0 until bitMatrix.width) {
            for (y in 0 until bitMatrix.height) {
                if (bitMatrix[x, y]) {
                    if (x < minX) minX = x
                    if (x > maxX) maxX = x
                    if (y < minY) minY = y
                    if (y > maxY) maxY = y
                }
            }
        }

        // Calculate the dimensions of the QR code
        val qrWidth = maxX - minX + 1
        val qrHeight = maxY - minY + 1

        // Create the bitmap with the dimensions of the QR code
        val bitmap = Bitmap.createBitmap(qrWidth, qrHeight, Bitmap.Config.RGB_565)

        for (x in 0 until qrWidth) {
            for (y in 0 until qrHeight) {
                bitmap.setPixel(
                    x,
                    y,
                    if (bitMatrix[x + minX, y + minY]) android.graphics.Color.BLACK else android.graphics.Color.WHITE,
                )
            }
        }

        return bitmap
    }
}
