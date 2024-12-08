// uploadApk.gradle.kts

tasks.register("uploadApk") {
    doLast {
        val apkFile = file("${buildDir}/outputs/apk/release/app-release.apk")
        if (apkFile.exists()) {
            val url = "https://example.com/upload" // Replace with your API endpoint
            val apiToken = "your_api_token" // Replace with your API token or authentication method

            val connection = URL(url).openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Authorization", "Bearer $apiToken")
            connection.setRequestProperty("Content-Type", "application/octet-stream")
            connection.doOutput = true

            connection.outputStream.use { output ->
                apkFile.inputStream().use { input ->
                    input.copyTo(output)
                }
            }

            val responseCode = connection.responseCode
            if (responseCode == 200) {
                println("Upload successful!")
            } else {
                println("Failed to upload. Response code: $responseCode")
            }
        } else {
            println("APK file not found.")
        }
    }
}
