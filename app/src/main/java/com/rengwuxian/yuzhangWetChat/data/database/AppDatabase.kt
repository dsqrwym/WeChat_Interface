package com.rengwuxian.yuzhangWetChat.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rengwuxian.yuzhangWetChat.data.dao.FriendshipDao
import com.rengwuxian.yuzhangWetChat.data.dao.MessageDao
import com.rengwuxian.yuzhangWetChat.data.dao.UserDao
import com.rengwuxian.yuzhangWetChat.data.Msg
import com.rengwuxian.yuzhangWetChat.data.User

@Database(
    entities = [User::class, Msg::class, Friendship::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao
    abstract fun fiendshipDao(): FriendshipDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "chat_database"
                ).addMigrations(MIGRATION_1_2).build().also { instance = it }
            }
        }
    }
}
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // 在这里进行数据库结构的更改，例如添加新字段
        db.execSQL("ALTER TABLE messages ADD COLUMN receiverId TEXT NOT NULL DEFAULT ''")
    }
}

