package com.rengwuxian.yuzhangWetChat

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rengwuxian.yuzhangWetChat.data.Chat
import com.rengwuxian.yuzhangWetChat.data.Msg
import com.rengwuxian.yuzhangWetChat.data.SharedStateManager
import com.rengwuxian.yuzhangWetChat.data.User
import com.rengwuxian.yuzhangWetChat.data.dao.FriendshipDao
import com.rengwuxian.yuzhangWetChat.data.dao.MessageDao
import com.rengwuxian.yuzhangWetChat.data.dao.UserDao
import com.rengwuxian.yuzhangWetChat.data.database.Friendship
import com.rengwuxian.yuzhangWetChat.ui.theme.WeComposeTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class WeViewModel @Inject constructor(
    private val sharedStateManager: SharedStateManager,
    private val userDao: UserDao,
    private val messageDao: MessageDao,
    private val friendshipDao: FriendshipDao
) : ViewModel() {

    //var chats by mutableStateOf(emptyList<Chat>())
    var chats by mutableStateOf(emptyList<Chat>())
    var contacts by mutableStateOf(emptyList<User>())

    var selectedTab by mutableIntStateOf(0)

    var currentTheme by mutableStateOf(sharedStateManager.getCurrentTheme())
    var currentUser by mutableStateOf(sharedStateManager.getCurrentUser())

    var currentChat: Chat? by mutableStateOf(sharedStateManager.getCurrentChat())

    init {
        //loadMockData()//Esto es para añadir dados de prueba
        loadContacts()
        loadChats()
    }

    private fun loadMockData() {
        viewModelScope.launch {
            // 插入联系人数据
            val mockContacts = mockContacts()
            userDao.insertUser(mockContacts)

            // 插入聊天记录数据
            val mockChats = mockChats()
            mockChats.forEach { chat ->
                chat.msgs.forEach { msg ->
                    messageDao.insertMessage(msg)
                }
            }

            val mockFriendships = mockFriendships()
            mockFriendships.forEach { friendship ->
                friendshipDao.insertFriendship(friendship)
            }

            loadContacts()
            loadChats()
        }
    }

    private fun mockFriendships(): List<Friendship> {
        val me = User("dsqrwym", "加油", R.drawable.xiaomao, "12345678")
        val gaolaoshi = User("gaolaoshi", "高老师", R.drawable.avatar_gaolaoshi, "66666666")
        val zhangtianshi = User("zhangtianshi", "张天师", R.drawable.avatar_diuwuxian, "88888888")

        return listOf(
            Friendship(userId = me.id, friendId = gaolaoshi.id),  // 加油 和 高老师 是好友
            Friendship(userId = me.id, friendId = zhangtianshi.id), // 加油 和 张天师 是好友
            Friendship(userId = gaolaoshi.id, friendId = me.id),  // 加油 和 高老师 是好友
            Friendship(userId = zhangtianshi.id, friendId = me.id), // 加油 和 张天师 是好友
        )
    }

    private fun mockContacts(): List<User> {
        return listOf(
            User("gaolaoshi", "高老师", R.drawable.avatar_gaolaoshi, "66666666"),
            User("zhangtianshi", "张天师", R.drawable.avatar_diuwuxian, "88888888"),
            User("dsqrwym", "加油", R.drawable.xiaomao, "12345678")
        )
    }

    private fun mockChats(): List<Chat> {
        val me = User("dsqrwym", "加油", R.drawable.xiaomao, "12345678")
        val gaolaoshi = User("gaolaoshi", "高老师", R.drawable.avatar_gaolaoshi, "66666666")
        val zhangtianshi = User("zhangtianshi", "张天师", R.drawable.avatar_diuwuxian, "88888888")

        return listOf(
            Chat(
                friend = gaolaoshi,
                msgs = mutableStateListOf(
                    Msg(text = "锄禾日当午", time = "14:20", read = true, userId = gaolaoshi.id, receiverId = me.id),
                    Msg("汗滴禾下土", "14:20", read = true, me.id, gaolaoshi.id),
                    Msg("谁知盘中餐", "14:20", true, gaolaoshi.id, me.id),
                    Msg("粒粒皆辛苦", "14:20", true, me.id, gaolaoshi.id),
                    Msg("唧唧复唧唧，木兰当户织。不闻机杼声，惟闻女叹息。", "14:20", true, gaolaoshi.id, me.id),
                    Msg("双兔傍地走，安能辨我是雄雌？", "14:20", true, me.id, gaolaoshi.id),
                    Msg("床前明月光，疑是地上霜。", "14:20", true, gaolaoshi.id, me.id),
                    Msg("吃饭吧？", "14:20", true, me.id, gaolaoshi.id),
                )
            ),
            Chat(
                friend = zhangtianshi,
                msgs = mutableStateListOf(
                    Msg("哈哈哈", "13:48", true, zhangtianshi.id, me.id),
                    Msg("哈哈昂", "13:48", true, me.id, zhangtianshi.id),
                    Msg("你笑个屁呀", "13:48", false, zhangtianshi.id, me.id)
                )
            )
        )
    }


    fun updateTheme(newTheme: WeComposeTheme.Theme) {
        sharedStateManager.setCurrentTheme(newTheme)  // 使用 ThemeManager 设置新主题
        currentTheme = newTheme  // 更新 ViewModel 中的 currentTheme
    }

    // 获取当前时间的帮助函数
    private fun getCurrentTime(): String {
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    }

    fun startChat(chat: Chat) {
        //chatting = true
        viewModelScope.launch {
            val index = chat.msgs.lastOrNull()?.id
            if(index != null) {
                val newMessage = messageDao.getMessageById(index)
                if (newMessage != null) {
                    newMessage.read = false
                    messageDao.updateMessage(newMessage)
                }
            }
        }
        sharedStateManager.setCurrentChat(chat)
        currentChat = chat
        chat.msgs.lastOrNull()?.read = true
    }


    fun endChat() {
        /*if (chatting) {
            chatting = false
            currentChat = null
        }
        return chatting*/
        currentChat = null
        sharedStateManager.setCurrentChat(null)
    }

    fun sendMsg(chat: Chat, text: String) {
        viewModelScope.launch {
            currentUser?.let { me ->
                val newMsg = Msg(
                    text = text,
                    time = getCurrentTime(),
                    userId = me.id, // 当前用户 ID
                    receiverId = chat.friend.id
                    )

                messageDao.insertMessage(newMsg)
                // 填充消息的 `from` 字段为当前用户
                newMsg.from = me
                val insertedId = messageDao.insertMessage(newMsg) // 返回新插入的 ID
                newMsg.id = insertedId.toInt() // 将新的 id 设置到消息对象中
                chat.msgs.add(newMsg)
            }
            //chat.msgs.add(Msg(User.Me, text, getCurrentTime()).apply { read = false })
        }
    }

    private fun loadContacts() {
        viewModelScope.launch {
            currentUser?.let { user ->
                contacts = friendshipDao.getFriendsOfUser(user.id) // 仅加载好友
                Log.d("WeViewModel", "Contacts loaded: ${contacts.size}")
            }
        }
    }

    private fun loadChats() {
        viewModelScope.launch {
            currentUser?.let {
                val messagesWithUser = messageDao.getAllMessagesWithUser()
                // 将 Msg 转换为带有 from 字段的对象
                val messages = messagesWithUser.map { msgWithUser ->
                    msgWithUser.msg.apply {
                        from = msgWithUser.user // 手动填充 from 字段
                    }
                }

                chats = contacts.map { contact ->
                    val userMessages = messages.filter {
                        (it.userId == currentUser?.id && it.receiverId == contact.id || it.userId == contact.id && it.receiverId == currentUser?.id)
                    }
                    Chat(contact, userMessages.toMutableStateList())
                }
            }
        }
    }

    fun addFriend(friendId: String) {
        viewModelScope.launch {
            currentUser?.let { user ->
                val friend = userDao.getUserById(friendId)
                if (friend != null) {
                    friendshipDao
                        .insertFriendship(Friendship(userId = user.id, friendId = friend.id))
                    loadContacts() // 重新加载好友列表
                }
            }
        }
    }

    fun removeFriend(friendId: String) {
        viewModelScope.launch {
            currentUser?.let { user ->
                friendshipDao.deleteFriendship(user.id, friendId)
                loadContacts() // 重新加载好友列表
            }
        }
    }

    fun login(userId: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = userDao.getUserById(userId)
            val loginSuccess = (user != null && user.password == password)
            if (loginSuccess) {
                sharedStateManager.setCurrentUser(user)
                currentUser = user
                Log.d("WeViewModel", "currentUser: ${currentUser != null}")
                loadContacts()
                loadChats()
            }
            callback(loginSuccess)
        }
    }

    fun boom(chat: Chat) {
        viewModelScope.launch {
            sendMsg(chat, "\uD83D\uDCA3")
        }
        //chat.msgs.add(Msg(User.Me, "\uD83D\uDCA3", getCurrentTime()).apply { read = true })
    }
}