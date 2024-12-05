package com.trex.laxmiemi.utils

import NewDeviceIds
import android.app.admin.DevicePolicyManager
import android.graphics.Bitmap
import android.util.Log
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.trex.rexnetwork.Constants
import com.trex.rexnetwork.domain.firebasecore.firesstore.FirestoreExtraData
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class QrUtils(
    extraData: FirestoreExtraData,
) {
    //    private val apkUrl = extraData.url
    private val apkChecksum = extraData.checksum
    private val apkUrl = "${Constants.BASE_URL}/api/apk/url?version=v1"

    fun getQrBitmap(deviceIds: NewDeviceIds): Bitmap {
        Log.i("Current url and checksum", "getQrBitmap: $apkChecksum :: $apkUrl")
        val qrJson = getQrJson(deviceIds)
        return generateQRCodeBitmap(qrJson.toString())
    }

    private fun getQrJson(newDeviceIds: NewDeviceIds): JsonObject {
        val clientPackageName = "com.trex.rexandroidsecureclient"
        // First, create and verify admin extras bundle
        val adminExtrasBundle =
            buildJsonObject {
                put(Constants.ADMIN_SHOP_ID, newDeviceIds.shopId)
                put(Constants.ADMIN_DEVICE_ID, newDeviceIds.deviceId)
            }

        // Debug log to verify admin extras content
        Log.d("QRDebug", "Admin Extras Bundle: $adminExtrasBundle")

        val qrJson =
            buildJsonObject {
                put(
                    DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME,
                    "$clientPackageName/$clientPackageName.DeviceAdminReceiver",
                )
                put(
                    DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_CHECKSUM,
                    apkChecksum,
                )
                put(
                    DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_LOCATION,
                    apkUrl,
                )

                put(
                    DevicePolicyManager.EXTRA_PROVISIONING_ADMIN_EXTRAS_BUNDLE,
                    adminExtrasBundle,
                )
            }
        Log.i("TAG", "getQrJson: $$qrJson")
        return qrJson
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
