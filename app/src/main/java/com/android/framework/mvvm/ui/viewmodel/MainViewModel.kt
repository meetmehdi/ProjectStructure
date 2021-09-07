package com.android.framework.mvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.framework.mvvm.data.api.ApiService
import com.android.framework.mvvm.data.model.User
import com.android.framework.mvvm.data.repository.UserRepository
import com.android.framework.mvvm.utils.NetworkHelper
import com.android.framework.mvvm.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService,
    private val networkHelper: NetworkHelper,
    private val userRepository: UserRepository) : ViewModel() {

    private val _users = MutableLiveData<Resource<List<User>>>()
    val users: LiveData<Resource<List<User>>>
        get() = _users

    init {
        initUsers()
    }

    private fun initUsers() {

        viewModelScope.launch {
            _users.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                userRepository.getUser().let {
                    if (it.isSuccessful) {
                        _users.postValue(Resource.success(it.body()))
                    } else _users.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _users.postValue(Resource.error("No internet connection", null))

        }
    }

    fun insertUsers(users: List<User>) = userRepository.insertUser(users)

    fun fetchUsers() = userRepository.fetchUsers()

}