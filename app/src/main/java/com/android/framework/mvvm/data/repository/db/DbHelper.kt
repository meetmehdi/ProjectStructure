package com.android.framework.mvvm.data.repository.db

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.framework.mvvm.data.model.User
import com.android.framework.mvvm.utilities.DebugHelperUtility
import com.android.framework.mvvm.utils.Status
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DbHelper(
    private val context: Context,
    private val appDatabase: AppDatabase,
    private val compositeDisposable: CompositeDisposable
) {


    fun insertUser(users: List<User>): MutableLiveData<Status> {

        val insertSuccess: MutableLiveData<Status> = MutableLiveData()
//
//        CoroutineScope(Dispatchers.IO).launch{
//            appDatabase.userDao().insert(users)
//        }

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