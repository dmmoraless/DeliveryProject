package com.mov.startupapp.routes


import com.mov.startupapp.models.ResponseHttp
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ProductsRoutes {

//    @GET("categories/getAll")
//    fun getAll(
//        @Header("Authorization") token: String
//    ): Call<ArrayList<Category>>

    @Multipart
    @POST("products/create")
    fun create(
        @Part image: Array<MultipartBody.Part?>,
        @Part("product") product: RequestBody,
        @Header("Authorization") token: String
    ): Call<ResponseHttp>

}