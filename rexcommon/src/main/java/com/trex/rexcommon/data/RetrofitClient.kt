package com.trex.rexcommon.data

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.POST

interface RexKtorServer {
    @POST("/send")
    suspend fun sendMessage(
        @Body body: SendMessageDto,
    )

    @POST("/regdevice")
    suspend fun registerNewDevice(
        @Body body: NewDevice,
    ): Response<Unit>
}

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"
//    private const val BASE_URL = "http://192.168.0.165:8080/"

    val builder: RexKtorServer =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
}
