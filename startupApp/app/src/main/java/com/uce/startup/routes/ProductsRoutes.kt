package com.uce.startup.routes


import com.uce.startup.models.Product
import com.uce.startup.models.ResponseHttp
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ProductsRoutes {

    @GET("products/findByCategory/{id_category}")
    fun findByCategory(
        @Path("id_category") idCategory: String,
        @Header("Authorization") token: String
    ): Call<ArrayList<Product>>

    @Multipart
    @POST("products/create")
    fun create(
        @Part images: Array<MultipartBody.Part?>,
        @Part("product") product: RequestBody,
        @Header("Authorization") token: String
    ): Call<ResponseHttp>



}