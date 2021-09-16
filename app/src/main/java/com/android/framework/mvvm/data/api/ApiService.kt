package com.android.framework.mvvm.data.api

import com.android.framework.mvvm.data.model.User
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import javax.inject.Inject

interface ApiService {
    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    @GET("users")
    fun getUsersList(): Observable<Response<List<User>>> //Single

    @GET("users")
    fun getUsersList(@HeaderMap headerMap: HashMap<String, String>): Observable<Response<List<User>>>
}