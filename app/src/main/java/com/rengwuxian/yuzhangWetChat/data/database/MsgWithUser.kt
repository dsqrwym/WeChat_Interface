package com.rengwuxian.yuzhangWetChat.data.database

import androidx.room.Embedded
import androidx.room.Relation
import com.rengwuxian.yuzhangWetChat.data.Msg
import com.rengwuxian.yuzhangWetChat.data.User

data class MsgWithUser(
    @Embedded val msg: Msg, // 嵌套消息表数据
    @Relation(
        parentColumn = "userId", // Msg 中的外键
        entityColumn = "id" // User 表中的主键
    )
    val user: User // 关联的 User 对象
)
