package com.rengwuxian.yuzhangWetChat.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.rengwuxian.yuzhangWetChat.ui.theme.WeComposeTheme
import javax.inject.Inject

class SharedStateManager @Inject constructor() {

    // 共享状态：当前主题
    private var currentTheme = WeComposeTheme.Theme.Light
    fun getCurrentTheme(): WeComposeTheme.Theme = currentTheme
    fun setCurrentTheme(theme: WeComposeTheme.Theme) {
        currentTheme = theme
    }

    // 共享状态：当前用户
    private var currentUser: User? = null
    fun getCurrentUser(): User? = currentUser
    fun setCurrentUser(user: User?) {
        currentUser = user
    }

    private var currentChat: Chat? = null
    fun getCurrentChat(): Chat? = currentChat
    fun setCurrentChat(chat: Chat?) {
        currentChat = chat
    }
}
