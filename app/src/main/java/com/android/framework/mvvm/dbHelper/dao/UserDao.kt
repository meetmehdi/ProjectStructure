package com.android.framework.mvvm.dbHelper.dao

import androidx.room.*
import com.android.framework.mvvm.data.model.User
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<User>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User): Completable

    @Update
    fun update(user: User): Completable

    @Query("delete from user")
    fun deleteAll(): Completable

    @Query("Select * from user")
    fun getUserData(): Single<List<User>>
}