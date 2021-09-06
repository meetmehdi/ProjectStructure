package com.android.framework.mvvm.data.repository.db

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.framework.mvvm.data.model.User
import com.android.framework.mvvm.utilities.DebugHelperUtility
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DbHelper(val context: Context) {
    private val appDatabase: AppDatabase? = AppDatabase.getInstance(context)
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun insertUser(
        users: List<User>,
        insertSuccess: MutableLiveData<Int>
    ) {
        if (appDatabase != null) {
            var completable = appDatabase.userDao().insert(users)
                .andThen(appDatabase.userDao().insert(users))

            val disposable = completable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    insertSuccess.value = 1
                    Log.i("DbInsertion", "Success")
                }, {
                    insertSuccess.value = 0
                    deleteAllData()
                })
            compositeDisposable.add(disposable)
        }
    }

    fun fetchUsers(
    ): MutableLiveData<List<User>> {
        val mutableLiveData: MutableLiveData<List<User>> = MutableLiveData()
        if (appDatabase != null) {
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
        }
        return mutableLiveData
    }

    private fun deleteAllData() {
        if (appDatabase != null) {
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


}