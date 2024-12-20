package com.rengwuxian.yuzhangWetChat.data.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.rengwuxian.yuzhangWetChat.data.User

@Entity(
    tableName = "friendships",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["userId"]),
        ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["friendId"])
    ],
    indices = [
        Index(value = ["userId"]),  // 为 userId 列添加索引
        Index(value = ["friendId"]) // 为 friendId 列添加索引
    ],
    primaryKeys = ["userId", "friendId"] // 设置复合主键
)
data class Friendship(
    val userId: String,    // 当前用户 ID
    val friendId: String   // 好友 ID
)
