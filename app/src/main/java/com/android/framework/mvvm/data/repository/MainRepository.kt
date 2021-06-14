package com.android.framework.mvvm.data.repository

import com.android.framework.mvvm.data.api.ApiService
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getUsers() =  apiService.getUsers()
}