package com.rengwuxian.yuzhangWetChat.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class Chat(
    var friend: User,
    var msgs: SnapshotStateList<Msg> = mutableStateListOf()
)