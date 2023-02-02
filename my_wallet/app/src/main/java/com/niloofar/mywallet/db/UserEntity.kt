package com.hossein.mywallet.db

import academy.nouri.s1_room.utils.Constants
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Constants.USER_TABLE)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val userId: Int,
    val Babat: String,
    val Price: Int
)