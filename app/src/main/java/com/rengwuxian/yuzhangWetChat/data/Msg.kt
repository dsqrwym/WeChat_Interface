package com.rengwuxian.yuzhangWetChat.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Msg(
    val text: String, // 消息内容
    val time: String, // 时间
    var read: Boolean = false, // 是否已读

    // 引用发送者 User 的 id
    val userId: String,
    val receiverId: String
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0 // 消息的主键
    // 非数据库字段，用来在代码中直接访问 User 对象
    @Ignore
    var from: User? = null
}