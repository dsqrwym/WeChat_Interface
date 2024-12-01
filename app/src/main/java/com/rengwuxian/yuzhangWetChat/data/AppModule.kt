package com.rengwuxian.yuzhangWetChat.data

import android.content.Context
import com.rengwuxian.yuzhangWetChat.data.dao.FriendshipDao
import com.rengwuxian.yuzhangWetChat.data.dao.MessageDao
import com.rengwuxian.yuzhangWetChat.data.dao.UserDao
import com.rengwuxian.yuzhangWetChat.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideSharedStateManager(): SharedStateManager {
        return SharedStateManager()  // 返回 SharedStateManager 实例
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)  // 使用注入的上下文实例
    }

    // 提供 UserDao 实例
    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()  // 获取 UserDao
    }

    // 提供 MessageDao 实例
    @Provides
    @Singleton
    fun provideMessageDao(appDatabase: AppDatabase): MessageDao {
        return appDatabase.messageDao()  // 获取 MessageDao
    }

    // 提供 FriendshipDao 实例
    @Provides
    @Singleton
    fun provideFriendshipDao(appDatabase: AppDatabase): FriendshipDao {
        return appDatabase.fiendshipDao()  // 获取 FriendshipDao
    }
}
