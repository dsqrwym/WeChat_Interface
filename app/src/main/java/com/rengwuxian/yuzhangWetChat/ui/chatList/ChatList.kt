package com.rengwuxian.yuzhangWetChat.ui.chatList

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rengwuxian.yuzhangWetChat.R
import com.rengwuxian.yuzhangWetChat.WeViewModel
import com.rengwuxian.yuzhangWetChat.data.Chat
import com.rengwuxian.yuzhangWetChat.ui.TopBar
import com.rengwuxian.yuzhangWetChat.ui.theme.WeComposeTheme

// 显示聊天列表的主界面
// Interfaz principal que muestra la lista de chats
@Composable
fun ChatList(navHostController: NavHostController) {
    val viewModel: WeViewModel = hiltViewModel(LocalContext.current as ComponentActivity)// 获取当前的 ViewModel 实例 // Obtener la instancia actual del ViewModel


    val chats = viewModel.chats
    Log.d("WeViewModel ChatList", "chats.size: ${chats.size}")
    Column(
        Modifier
            .background(WeComposeTheme.colors.background) // 设置背景色 // Establecer el color de fondo
            .fillMaxSize() // 填充整个屏幕 // Ocupa toda la pantalla
    ) {
        TopBar(title = stringResource(R.string.app_name)) // 顶部标题栏 // Barra superior con título
        LazyColumn(Modifier.background(WeComposeTheme.colors.listItem)) { // 可滚动的聊天列表 // Lista de chats desplazable
            itemsIndexed(chats) { index, chat ->
                ChatListItem(chat, viewModel, navHostController) // 每一项显示聊天数据 // Cada ítem muestra los datos del chat
                if (index < chats.lastIndex) { // 如果不是最后一项，显示分隔线 // Si no es el último ítem, mostrar separador
                    HorizontalDivider(
                        Modifier.padding(start = 68.dp), // 左侧偏移 68dp // Desplazamiento izquierdo de 68dp
                        thickness = 0.8.dp, // 分隔线粗细 // Grosor del separador
                        color = WeComposeTheme.colors.chatListDivider // 分隔线颜色 // Color del separador
                    )
                }
            }
        }
    }
}

// 聊天列表中的单项布局
// Composición de cada ítem en la lista de chats
@Composable
private fun ChatListItem(chat: Chat, viewModel: WeViewModel, navHostController: NavHostController) {
   // val viewModel: WeViewModel = viewModel() // 绑定 ViewModel // Asociar con ViewModel
    Row(
        Modifier
            .clickable { // 点击事件，启动聊天详情页面
                // Al hacer clic, iniciar la pantalla de detalles del chat
                viewModel.startChat(chat)
                navHostController.navigate("chatpage")
            }
            .fillMaxWidth() // 填满宽度 // Ocupa todo el ancho
    ) {
        // 好友头像
        // Avatar del amigo
        Image(
            painterResource(chat.friend.avatar), // 加载头像资源 // Cargar recurso de avatar
            chat.friend.name, // 内容描述（可访问性） // Descripción (para accesibilidad)
            Modifier
                .padding(4.dp) // 设置边距 // Establecer márgenes
                .size(48.dp) // 设置图片大小为 48dp // Tamaño de la imagen a 48dp
                .unread(
                    ((!chat.msgs.isEmpty()) && (chat.msgs.last().receiverId == viewModel.currentUser?.id) && (!chat.msgs.last().read)),
                    WeComposeTheme.colors.badge
                ) // 显示未读红点 // Mostrar punto rojo si hay mensajes sin leer
                .clip(RoundedCornerShape(4.dp)) // 圆角处理 // Bordes redondeados
        )
        // 聊天的主要信息（好友名称和最后一条消息）
        // Información principal del chat (nombre del amigo y último mensaje)
        Column(
            Modifier
                .weight(1f) // 填满剩余空间 // Ocupa el espacio restante
                .align(Alignment.CenterVertically) // 垂直居中 // Centrado vertical
        ) {
            Text(
                chat.friend.name, // 显示好友名称 // Mostrar el nombre del amigo
                fontSize = 17.sp, // 设置字体大小 // Tamaño de fuente
                color = WeComposeTheme.colors.textPrimary // 字体颜色 // Color de texto
            )
            Text(
                chat.msgs.lastOrNull()?.text ?: "", // 显示最后一条消息内容 // Mostrar el contenido del último mensaje
                fontSize = 14.sp, // 字体大小 // Tamaño de fuente
                color = WeComposeTheme.colors.textPrimary // 字体颜色 // Color de texto
            )
        }
        // 显示消息时间
        // Mostrar la hora del mensaje
        Text(
            chat.msgs.lastOrNull()?.time ?: "", // 最后一条消息时间 // Hora del último mensaje
            Modifier.padding(8.dp, 8.dp, 12.dp, 8.dp), // 设置内边距 // Margen interno
            fontSize = 11.sp, // 字体大小 // Tamaño de fuente
            color = WeComposeTheme.colors.textSecondary // 字体颜色 // Color de texto
        )
    }
}

// 自定义修饰符，用于显示未读红点
// Modificador personalizado para mostrar punto rojo de mensajes sin leer
fun Modifier.unread(show: Boolean, color: Color): Modifier = this.drawWithContent {
    drawContent() // 先绘制原始内容 // Primero dibujar el contenido original
    if (show) { // 如果需要显示未读红点 // Si es necesario mostrar el punto rojo
        drawCircle(
            color, // 红点颜色 // Color del punto rojo
            5.dp.toPx(), // 红点直径为 5dp // Diámetro del punto en 5dp
            Offset(
                size.width - 1.dp.toPx(),
                1.dp.toPx()
            ) // 红点位置（右上角） // Posición del punto (esquina superior derecha)
        )
    }
}
