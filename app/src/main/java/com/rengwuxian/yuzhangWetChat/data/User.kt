package com.rengwuxian.yuzhangWetChat.data

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rengwuxian.yuzhangWetChat.R

@Entity(tableName = "users")
class User(
    @PrimaryKey val id: String,
    val name: String,
    @DrawableRes val avatar: Int,
    var password: String
) {
    /*companion object {
        val Me: User = User("dsqrwym", "加油", R.drawable.xiaomao, "12345678")
    }*/
}