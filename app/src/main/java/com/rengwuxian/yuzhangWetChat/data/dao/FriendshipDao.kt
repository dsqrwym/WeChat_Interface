package com.rengwuxian.yuzhangWetChat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rengwuxian.yuzhangWetChat.data.User
import com.rengwuxian.yuzhangWetChat.data.database.Friendship

@Dao
interface FriendshipDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriendship(friendship: Friendship)

    @Query("SELECT * FROM users WHERE id IN (SELECT friendId FROM friendships WHERE userId = :userId)")
    suspend fun getFriendsOfUser(userId: String): List<User>

    @Query("DELETE FROM friendships WHERE userId = :userId AND friendId = :friendId")
    suspend fun deleteFriendship(userId: String, friendId: String)
}