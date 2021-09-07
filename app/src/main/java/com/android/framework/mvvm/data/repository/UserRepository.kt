package com.android.framework.mvvm.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.framework.mvvm.data.api.ApiService
import com.android.framework.mvvm.data.model.User
import com.android.framework.mvvm.dbHelper.db.AppDatabase
import com.android.framework.mvvm.utilities.DebugHelperUtility
import com.android.framework.mvvm.utils.Status
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val appDatabase: AppDatabase,
    private val compositeDisposable: CompositeDisposable,
    private val apiService: ApiService
) {

    suspend fun getUser() : Response<List<User>> {
        return apiService.getUsers()
    }

    fun insertUser(users: List<User>): MutableLiveData<Status> {

        val insertSuccess: MutableLiveData<Status> = MutableLiveData()

        val completable = appDatabase.userDao().insert(users)
            .andThen(appDatabase.userDao().insert(users))

        val disposable = completable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                insertSuccess.value = Status.SUCCESS
                Log.i("DbInsertion", "Success")
            }, {
                insertSuccess.value = Status.ERROR
                deleteAllData()
            })
        compositeDisposable.add(disposable)

        return insertSuccess
    }

    fun fetchUsers(): MutableLiveData<List<User>> {

        val mutableLiveData: MutableLiveData<List<User>> = MutableLiveData()
        val disposable = appDatabase.userDao().getUserData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ users ->
                mutableLiveData.value = users
                DebugHelperUtility.printInfo("Users select data successfully")

            }, {
                DebugHelperUtility.printError("Users select ", Error(it))
            })

        compositeDisposable.add(disposable)
        return mutableLiveData
    }

    private fun deleteAllData() {

        val disposable = appDatabase.userDao().deleteAll()
            .andThen(appDatabase.userDao().deleteAll())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                DebugHelperUtility.printInfo("dbDeletion", "Success")
            }, {
                DebugHelperUtility.printError("dbDeletion", Error(it))
            })
        compositeDisposable.add(disposable)
    }

}