package com.trex.laxmiemi.utils

import android.app.admin.DevicePolicyManager
import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class QrUtils {
    private val apkUrl = "https://tmpfiles.org/dl/14482678/ddd.apk"
    private val apkChecksum = "uEfSdZTvepuqMPQvjLoZysR3cyhigaEvdEhT8WVDYjk"

    fun getQrBitmap(): Bitmap {
        val qrJson = getQrJson()
        return generateQRCodeBitmap(qrJson.toString())
    }

    private fun getQrJson(): JsonObject {
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
                        put(CommonConstants.KEY_SHOP_ID, CommonConstants.shodId)
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
