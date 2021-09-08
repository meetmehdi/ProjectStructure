package com.android.framework.mvvm.data.api

import com.android.framework.mvvm.data.model.User
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    @GET("users")
    fun getUsersList(): Observable<Response<List<User>>> //Single
}