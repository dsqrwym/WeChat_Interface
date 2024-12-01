package com.rengwuxian.yuzhangWetChat.ui.contactList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rengwuxian.yuzhangWetChat.R
import com.rengwuxian.yuzhangWetChat.WeViewModel
import com.rengwuxian.yuzhangWetChat.data.User
import com.rengwuxian.yuzhangWetChat.ui.TopBar
import com.rengwuxian.yuzhangWetChat.ui.theme.WeComposeTheme


@Composable
fun ContactListTopBar(viewModel: WeViewModel){
    TopBar(title = stringResource(R.string.tab_item_contacts), viewModel = viewModel) // 顶部栏显示“联系人”标题 / Barra superior que muestra el título "Contactos"
}

@Composable
fun ContactListItem(
    contact: User
){
    Row(
        Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(contact.avatar), // 显示联系人头像 / Muestra la imagen de avatar del contacto
            contentDescription = stringResource(R.string.chat_avatar), // 头像的内容描述 / Descripción del contenido de la imagen (avatar)
            Modifier
                .padding(12.dp, 8.dp, 8.dp, 8.dp) // 设置图片的内边距 / Establece el relleno de la imagen
                .size(36.dp) // 设置头像的尺寸 / Establece el tamaño de la imagen
                .clip(RoundedCornerShape(4.dp)) // 设置头像的圆角 / Establece los bordes redondeados en la imagen
        )
        Text(
            text = contact.name, // 显示联系人姓名 / Muestra el nombre del contacto
            Modifier
                .weight(1f) // 设置文本的权重，让文本填充可用空间 / Establece el peso para que el texto ocupe todo el espacio disponible
                .align(Alignment.CenterVertically), // 垂直居中对齐 / Alineación vertical centrada
            fontSize = 17.sp, // 设置字体大小 / Establece el tamaño de la fuente
            color = WeComposeTheme.colors.textPrimary // 设置文本颜色 / Establece el color del texto
        )
    }
}

@Composable
fun ContactList(viewModel: WeViewModel) {
    Column(Modifier.fillMaxSize()) {
        ContactListTopBar(viewModel) // 显示顶部栏 / Muestra la barra superior
        Box(
            Modifier
                .background(WeComposeTheme.colors.background) // 设置背景颜色 / Establece el color de fondo
                .fillMaxSize() // 填充整个屏幕 / Llena toda la pantalla
        ) {
            ContactList(viewModel.contacts) // 显示联系人列表 / Muestra la lista de contactos
        }
    }
}

@Composable
fun ContactList(contacts: List<User>){
    val contactAdd = stringResource(R.string.contact_add) // "添加联系人" 字符串 / Cadena de texto "Añadir contacto"
    val contactChat = stringResource(R.string.contact_chat) // "联系人聊天" 字符串 / Cadena de texto "Chat de contacto"
    val contactGroup = stringResource(R.string.contact_group) // "联系人分组" 字符串 / Cadena de texto "Grupo de contactos"
    val contactTag = stringResource(R.string.contact_tag) // "联系人标签" 字符串 / Cadena de texto "Etiqueta de contacto"
    val contactOfficial = stringResource(R.string.contact_official) // "官方联系人" 字符串 / Cadena de texto "Contacto oficial"

    LazyColumn(
        Modifier
            .background(WeComposeTheme.colors.listItem) // 设置列表项的背景颜色 / Establece el color de fondo para los elementos de la lista
            .fillMaxWidth() // 设置宽度为最大 / Establece el ancho a máximo
    ){
        // 好友列表上面的部分 / Parte superior de la lista de contactos
        // Datos de los botones en la parte superior de la lista de contactos
        val buttons = listOf(
            User("contact_add", contactAdd, R.drawable.ic_contact_add, ""), // 添加联系人按钮 / Botón para añadir contacto
            User("contact_chat", contactChat, R.drawable.ic_contact_chat, ""), // 聊天按钮 / Botón de chat
            User("contact_group", contactGroup, R.drawable.ic_contact_group, ""), // 分组按钮 / Botón de grupo
            User("contact_tag", contactTag, R.drawable.ic_contact_tag, ""), // 标签按钮 / Botón de etiqueta
            User("contact_official", contactOfficial, R.drawable.ic_contact_official, "") // 官方按钮 / Botón oficial
        )
        itemsIndexed(buttons) { index, contact ->
            ContactListItem(contact = contact) // 显示每个按钮项 / Muestra cada elemento del botón
            if (index < buttons.size - 1){
                HorizontalDivider(
                    Modifier.padding(start = 56.dp), // 设置分割线的内边距 / Establece el relleno de la línea divisoria
                    color = WeComposeTheme.colors.chatListDivider, // 设置分割线颜色 / Establece el color de la línea divisoria
                    thickness = 0.8f.dp // 设置分割线的厚度 / Establece el grosor de la línea divisoria
                )
            }
        }
        item {
            Text(
                text = stringResource(R.string.contact_friend), // "好友" 标题 / Título "Amigos"
                Modifier
                    .background(WeComposeTheme.colors.background) // 设置背景颜色 / Establece el color de fondo
                    .fillMaxWidth() // 填充宽度 / Llena el ancho
                    .padding(12.dp, 8.dp), // 设置内边距 / Establece el relleno
                fontSize = 14.sp, // 设置字体大小 / Establece el tamaño de la fuente
                color = WeComposeTheme.colors.onBackground // 设置字体颜色 / Establece el color del texto
            )
        }

        // 显示联系人列表 / Muestra la lista de contactos
        itemsIndexed(contacts) { index, contact ->
            ContactListItem(contact) // 显示联系人项 / Muestra el elemento de contacto
            if (index < contacts.size - 1){
                HorizontalDivider(
                    Modifier.padding(start = 56.dp), // 设置分割线的内边距 / Establece el relleno de la línea divisoria
                    color = WeComposeTheme.colors.chatListDivider, // 设置分割线颜色 / Establece el color de la línea divisoria
                    thickness = 0.8f.dp // 设置分割线的厚度 / Establece el grosor de la línea divisoria
                )
            }
        }
    }
}
