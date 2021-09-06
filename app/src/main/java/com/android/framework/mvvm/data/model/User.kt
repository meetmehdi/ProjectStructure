package com.android.framework.mvvm.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "user")
data class User(
    @PrimaryKey @ColumnInfo @Json(name = "id")
    val id: Int = 0,
    @ColumnInfo @Json(name = "name")
    val name: String = "",
    @ColumnInfo @Json(name = "email")
    val email: String = "",
    @ColumnInfo @Json(name = "avatar")
    val avatar: String = ""
)