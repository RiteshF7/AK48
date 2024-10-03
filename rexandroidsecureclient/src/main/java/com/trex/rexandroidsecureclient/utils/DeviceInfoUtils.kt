import android.Manifest
import android.annotation.SuppressLint
import android.app.admin.DevicePolicyManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat

class DeviceInfoUtil(
    private val context: Context,
) {
    fun getDeviceModel(): String = Build.MODEL

    fun getDeviceName(): String = Settings.Global.getString(context.contentResolver, Settings.Global.DEVICE_NAME) ?: "Unknown"

    fun getAndroidVersion(): String = Build.VERSION.RELEASE

    fun getApiLevel(): Int = Build.VERSION.SDK_INT

    @SuppressLint("HardwareIds")
    fun getDeviceId(): String =
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            ?: "Unknown"

    @SuppressLint("MissingPermission", "HardwareIds")
    fun getIMEI(): String {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_PHONE_STATE,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return "Permission not granted"
        }

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            telephonyManager.imei ?: "Not available"
        } else {
            "Not available on this Android version"
        }
    }

    fun getManufacturer(): String = Build.MANUFACTURER

    fun getBrand(): String = Build.BRAND

    fun getProduct(): String = Build.PRODUCT

    fun isOwnerApp(): Boolean {
        val manager =
            context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        return manager.isDeviceOwnerApp(context.packageName)
    }

    fun getAllDeviceInfo(): Map<String, String> =
        mapOf(
            "Device Model" to getDeviceModel(),
            "Device Name" to getDeviceName(),
            "Android Version" to getAndroidVersion(),
            "API Level" to getApiLevel().toString(),
            "Device ID" to getDeviceId(),
            "IMEI" to getIMEI(),
            "Manufacturer" to getManufacturer(),
            "Brand" to getBrand(),
            "Product" to getProduct(),
        )
}
