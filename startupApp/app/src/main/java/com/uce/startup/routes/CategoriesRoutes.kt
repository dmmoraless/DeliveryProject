package com.uce.startup.routes


import com.uce.startup.models.Category
import com.uce.startup.models.ResponseHttp
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface CategoriesRoutes {

    @GET("categories/getAll")
    fun getAll(
        @Header("Authorization") token: String
    ): Call<ArrayList<Category>>

    @Multipart
    @POST("categories/create")
    fun create(
        @Part image: MultipartBody.Part,
        @Part("category") category: RequestBody,
        @Header("Authorization") token: String
    ): Call<ResponseHttp>



}