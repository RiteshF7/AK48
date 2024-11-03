package com.trex.laxmiemi.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast

object GoogleMapUtils {
    /**
     * Opens Google Maps with a specific location
     * @param context Android Context
     * @param latitude Location latitude
     * @param longitude Location longitude
     * @param label Optional label for the location
     */
    fun openGoogleMap(
        context: Context,
        latitude: Double,
        longitude: Double,
        label: String? = null,
    ) {
        try {
            // Create the location URI
            val gmmIntentUri =
                if (label != null) {
                    // URI with label
                    Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude($label)")
                } else {
                    // URI without label
                    Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude")
                }

            // Create intent with the location URI
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")

            // Check if Google Maps is installed
            if (mapIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(mapIntent)
            } else {
                // If Google Maps isn't installed, open in browser
                val browserIntent =
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps?q=$latitude,$longitude"),
                    )
                context.startActivity(browserIntent)
            }
        } catch (e: Exception) {
            Toast
                .makeText(
                    context,
                    "Error opening maps: ${e.localizedMessage}",
                    Toast.LENGTH_SHORT,
                ).show()
        }
    }

    /**
     * Opens Google Maps with a specific location URL
     * @param context Android Context
     * @param url Google Maps URL
     */
    fun openGoogleMapUrl(
        context: Context,
        url: String,
    ) {
        try {
            // Create intent with the URL
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            intent.setPackage("com.google.android.apps.maps")

            // Check if Google Maps is installed
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                // If Google Maps isn't installed, open in browser
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(browserIntent)
            }
        } catch (e: Exception) {
            Log.e("error map", "openGoogleMapUrl: ${e.localizedMessage}")
            Toast
                .makeText(
                    context,
                    "Error opening maps: ${e.localizedMessage}",
                    Toast.LENGTH_SHORT,
                ).show()
        }
    }

    /**
     * Opens Google Maps directions from current location to destination
     * @param context Android Context
     * @param destinationLat Destination latitude
     * @param destinationLng Destination longitude
     */
    fun openGoogleMapDirections(
        context: Context,
        destinationLat: Double,
        destinationLng: Double,
    ) {
        try {
            val uri =
                Uri.parse(
                    "google.navigation:q=$destinationLat,$destinationLng&mode=d",
                )
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")

            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                // Fallback to browser
                val browserIntent =
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(
                            "https://www.google.com/maps/dir/?api=1&destination=$destinationLat,$destinationLng",
                        ),
                    )
                context.startActivity(browserIntent)
            }
        } catch (e: Exception) {
            Toast
                .makeText(
                    context,
                    "Error opening directions: ${e.localizedMessage}",
                    Toast.LENGTH_SHORT,
                ).show()
        }
    }
}
