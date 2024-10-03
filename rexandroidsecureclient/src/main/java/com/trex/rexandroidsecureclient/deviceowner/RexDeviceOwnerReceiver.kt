package com.trex.rexandroidsecureclient.deviceowner

import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.trex.rexandroidsecureclient.MainActivity
import com.trex.rexandroidsecureclient.R

class RexDeviceOwnerReceiver : DeviceAdminReceiver() {
    override fun onEnabled(
        context: Context,
        intent: Intent,
    ) {
        super.onEnabled(context, intent)
        Toast.makeText(context, "Device Admin Enabled", Toast.LENGTH_SHORT).show()
    }

    override fun onDisabled(
        context: Context,
        intent: Intent,
    ) {
        super.onDisabled(context, intent)
        Toast.makeText(context, "Device Admin Disabled", Toast.LENGTH_SHORT).show()
    }

    override fun onProfileProvisioningComplete(
        context: Context,
        intent: Intent,
    ) {
        super.onProfileProvisioningComplete(context, intent)

        val manager = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val componentName =
            ComponentName(context.applicationContext, RexDeviceOwnerReceiver::class.java)

        // Set the profile name
        manager.setProfileName(componentName, context.getString(R.string.profile_name))

        // Start the main activity
        val launchIntent = Intent(context, MainActivity::class.java)
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(launchIntent)

        // Optionally, you can perform additional setup here
        Toast.makeText(context, "Profile Provisioning Completed", Toast.LENGTH_LONG).show()
    }

    fun requestDeviceAdmin(context: Context) {
        val componentName = ComponentName(context, RexDeviceOwnerReceiver::class.java)
        val intent =
            Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
                putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName)
                putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Enable admin to manage device")
            }
        context.startActivity(intent)
    }
}
