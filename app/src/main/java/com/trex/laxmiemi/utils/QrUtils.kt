package com.trex.laxmiemi.utils

import android.app.admin.DevicePolicyManager
import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.trex.rexnetwork.Constants
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class QrUtils {
    //    private val apkUrl = "${Constants.BASE_URL}/file"
    private val apkUrl = "https://tmpfiles.org/dl/16237425/app-debug.apk"
    private val apkChecksum = "ICLZ4euu328IyILyzBDkcMi-qwKfRz8RmAeVg_fKo9s"

    fun getQrBitmap(shopId: String): Bitmap {
        val qrJson = getQrJson(shopId)
        return generateQRCodeBitmap(qrJson.toString())
    }

    private fun getQrJson(shopId: String): JsonObject {
        val clientPackageName = "com.trex.rexandroidsecureclient"
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
                    buildJsonObject {
                        put(Constants.ADMIN_SHOP_ID, shopId)
                    },
                )
            }
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
