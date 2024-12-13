package com.trex.laxmiemi.data.firebase.firestore

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.trex.rexnetwork.data.NewDevice
import com.trex.rexnetwork.domain.firebasecore.firesstore.DeviceFirestore
import com.trex.rexnetwork.domain.firebasecore.firesstore.Shop
import com.trex.rexnetwork.domain.firebasecore.firesstore.ShopFirestore

class DummyDatabaseScript {
    val db = FirebaseFirestore.getInstance()

    // Create Shop Collection and Sub-collections for Devices
    fun createShop(
        shopId: String,
        ownerName: String,
        shopName: String,
        tokenBalance: Int,
    ) {
        val shopData =
            hashMapOf(
                "ownerName" to ownerName,
                "shopName" to shopName,
                "tokenBalance" to tokenBalance,
                "activeDevicesCount" to 0, // Initialize active devices count
            )

        db
            .collection("shops")
            .document(shopId)
            .set(shopData)
            .addOnSuccessListener {
                Log.d("Firestore", "Shop successfully added!")
            }.addOnFailureListener { e ->
                Log.w("Firestore", "Error adding shop", e)
            }
    }

    // Add Device to Shop (Includes Customer Details Directly)
    fun addDeviceToShop(
        shopId: String,
        imei: String,
        customerName: String,
        emiStatus: String,
        lastPaymentDate: String,
        dateTimeSold: String,
        tokenId: String,
        emiDueDate: String,
        lockStatus: String,
    ) {
        val deviceData =
            hashMapOf(
                "imei" to imei,
                "customerName" to customerName,
                "emiStatus" to emiStatus,
                "lastPaymentDate" to lastPaymentDate,
                "dateTimeSold" to dateTimeSold,
                "status" to "active",
                "tokenId" to tokenId,
                "emiDueDate" to emiDueDate,
                "lockStatus" to lockStatus,
                "lastCommunication" to System.currentTimeMillis(),
            )

        db
            .collection("shops")
            .document(shopId)
            .collection("devices")
            .document()
            .set(deviceData)
            .addOnSuccessListener {
                Log.d("Firestore", "Device successfully added!")
            }.addOnFailureListener { e ->
                Log.w("Firestore", "Error adding device", e)
            }
    }

    // Example Usage
    fun setupFirestoreData() {
        val shopId = "shop1"
        createShop(shopId, "John Doe", "Doe Electronics", 50)

        addDeviceToShop(
            shopId,
            "123456789012345",
            "Jane Smith",
            "on-time",
            "2024-09-01",
            "2024-09-10T12:00:00Z",
            "token123",
            "2024-10-10",
            "unlocked",
        )
    }

    fun testFirestoreOperations() {
        val shopFirestore = ShopFirestore()

        // Create a new shop
        shopFirestore.createOrUpdateShop(
            null,
            Shop( shopName = "Doe's Electronics", tokenBalance = listOf()),
            {
                Log.d("Firestore", "Shop added or updated successfully!")
            },
            {
                Log.e("Firestore", "Failed to add or update shop")
            },
        )

        // Update token balance
        shopFirestore.updateTokenBalance("shop1", 150, {
            Log.d("Firestore", "Token balance updated successfully!")
        }, {
            Log.e("Firestore", "Failed to update token balance")
        })

        // Read token balance (single field)
        shopFirestore.getSingleField("shop1", "tokenBalance", { tokenBalance ->
            Log.d("Firestore", "Token balance: $tokenBalance")
        }, {
            Log.e("Firestore", "Failed to get token balance")
        })

        // Create a new device
        val deviceFirestore = DeviceFirestore("shop1")
//        deviceFirestore.createOrUpdateDevice(
//            null,
//            NewDevice(imeiOne = "123456789", costumerName = "Jane Doe", fcmToken = "", shopId = ""),
//            {
//                Log.d("Firestore", "Device added or updated successfully!")
//            },
//            {
//                Log.e("Firestore", "Failed to add or update device", it)
//            },
//        )

        // Update lock status of a device
//        deviceFirestore.updateLockStatus("RP4OoUi28Sw0GVIcDcp3", "locked", {
//            Log.d("Firestore", "Lock status updated successfully!")
//        }, {
//            Log.e("Firestore", "Failed to update lock status", it)
//        })

        // Delete a device
        deviceFirestore.deleteDevice("device1", {
            Log.d("Firestore", "Device deleted successfully!")
        }, {
            Log.e("Firestore", "Failed to delete device", it)
        })
    }
}
