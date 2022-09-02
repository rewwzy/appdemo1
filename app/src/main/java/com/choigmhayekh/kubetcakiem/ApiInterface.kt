package com.choigmhayekh.kubetcakiem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {
    val hash: String
        get() = "00b7691d86d96aebd21dd9e138f90840"

//    @POST("macros/s/AKfycbz_iU4_3YLlOSFrum6_MLZIprDgmLnP711FurSK4YTsk1S-xFNXn8uiZJ5pk96PeSQl1g/exec")
//    @FormUrlEncoded
//    fun savePost(
//        @Field("phone") phone: String,
//        @Field("type") type: String,
//        @Field("action") action: String
//    ): Call<String>

    @GET("macros/s/AKfycbz_iU4_3YLlOSFrum6_MLZIprDgmLnP711FurSK4YTsk1S-xFNXn8uiZJ5pk96PeSQl1g/exec")
    fun getActive(@Query("hash") hash:String): Call<ResponseBody>

    companion object{
        var BASE_URL="https://script.google.com/"
        fun create():ApiInterface {
            val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(
                BASE_URL).build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}