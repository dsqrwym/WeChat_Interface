package com.rengwuxian.yuzhangWetChat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.rengwuxian.yuzhangWetChat.data.Msg
import com.rengwuxian.yuzhangWetChat.data.database.MsgWithUser

@Dao
interface MessageDao {
    @Transaction // 必须添加 @Transaction，因为这是多表查询
    @Query("SELECT * FROM messages WHERE userId = :userId")
    suspend fun getMessagesWithUserByUserId(userId: String): List<MsgWithUser>

    @Transaction
    @Query("SELECT * FROM messages")
    suspend fun getAllMessagesWithUser(): List<MsgWithUser>

    @Query("SELECT * FROM messages WHERE id = :msgId LIMIT 1")
    suspend fun getMessageById(msgId: Int): Msg?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(msg: Msg)

    @Update
    suspend fun updateMessage(msg: Msg)
}
