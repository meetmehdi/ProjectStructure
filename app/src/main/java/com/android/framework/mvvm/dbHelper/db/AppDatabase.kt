package com.android.framework.mvvm.dbHelper.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.framework.mvvm.data.model.User
import com.android.framework.mvvm.dbHelper.dao.UserDao
import com.android.framework.mvvm.utilities.DATABASE_VERSION


@Database(
    entities = [User::class],
    version = DATABASE_VERSION
)

@TypeConverters(Convertors::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}