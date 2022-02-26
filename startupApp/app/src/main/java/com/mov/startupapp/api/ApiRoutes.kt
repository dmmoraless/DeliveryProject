package com.mov.startupapp.api

import com.mov.startupapp.routes.CategoriesRoutes
import com.mov.startupapp.routes.UsersRoutes
import retrofit2.Retrofit

class ApiRoutes {

    val API_URL = "http://192.168.0.103:3000/api/"
    val retrofit = RetrofitClient()

    fun getUsersRoutes(): UsersRoutes {
        return retrofit.getClient(API_URL).create(UsersRoutes::class.java)
    }

    fun getUsersRoutesWithToken(token: String): UsersRoutes {
        return retrofit.getClientWithToken(API_URL, token).create(UsersRoutes::class.java)
    }

    fun getCategoriesRoutes(token: String): CategoriesRoutes {
        return retrofit.getClientWithToken(API_URL, token).create(CategoriesRoutes::class.java)
    }

}