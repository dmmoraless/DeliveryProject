package com.mov.startupapp.routes


import com.mov.startupapp.models.Category
import com.mov.startupapp.models.ResponseHttp
import com.mov.startupapp.models.User
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