package com.android.framework.mvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.framework.mvvm.baseClass.BaseViewModel
import com.android.framework.mvvm.data.model.User
import com.android.framework.mvvm.data.repository.UserRepository
import com.android.framework.mvvm.interfaces.ViewNavigation
import com.android.framework.mvvm.utils.LiveMessageEvent
import com.android.framework.mvvm.utils.NetworkHelper
import com.android.framework.mvvm.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val userRepository: UserRepository
) : BaseViewModel() {

    val navigationEvent = LiveMessageEvent<ViewNavigation>()
    private val userList = MutableLiveData<Resource<List<User>>>()
    val users: LiveData<Resource<List<User>>>
        get() = userList

    init {
        initUsers()
//        fetchAllUsers()
    }

    private fun initUsers() {
        launchIO {
            try {
                userList.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    userRepository.getUser().let {
                        if (it.isSuccessful) {
                            userList.postValue(Resource.success(it.body()))
                        } else userList.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                } else userList.postValue(Resource.error("No internet connection", null))
            } catch (e: Exception) {
                userList.postValue(Resource.error(e.message.toString(), null))
            }
        }
    }

    private fun fetchAllUsers() = userRepository.getUserList(userList)

    fun insertUsers(users: List<User>) = userRepository.insertUser(users)

    fun fetchUsers() = userRepository.fetchUsers()
}