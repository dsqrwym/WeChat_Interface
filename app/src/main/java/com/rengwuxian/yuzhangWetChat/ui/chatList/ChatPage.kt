package com.rengwuxian.yuzhangWetChat.ui.chatList

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rengwuxian.yuzhangWetChat.R
import com.rengwuxian.yuzhangWetChat.WeViewModel
import com.rengwuxian.yuzhangWetChat.data.Msg
import com.rengwuxian.yuzhangWetChat.ui.Home
import com.rengwuxian.yuzhangWetChat.ui.TopBar
import com.rengwuxian.yuzhangWetChat.ui.theme.WeComposeTheme
import kotlinx.coroutines.delay

@SuppressLint("UseOfNonLambdaOffsetOverload") // 取消警告，因为我们使用的是非lambda的 offset 方法 // Desactivar la advertencia ya que estamos usando un método de desplazamiento no-lambda
@Composable
fun ChatPage(viewModel: WeViewModel, navHostController: NavHostController) {
    BackHandler {
        navHostController.popBackStack()
        viewModel.endChat()
    }
    //val viewModel: WeViewModel = viewModel() // 获取当前的 ViewModel 实例 // Obtener la instancia actual del ViewModel

    val chat = viewModel.currentChat // 获取当前聊天对象 // Obtener el chat actual

    var shakingTime by remember { mutableIntStateOf(0) } // 震动动画的时间控制器 // Controlador del tiempo de la animación de vibración
    // 重置 shakingTime
    LaunchedEffect(chat) {
        shakingTime = 0 // 重置震动时间
    }

    val listState = rememberLazyListState()  // 添加滚动状态
    val currentSize by rememberUpdatedState(chat?.msgs?.size)
    LaunchedEffect(Unit) {
        var index = 0
        if (currentSize?.minus(1) !! >= 0){
            index = currentSize?.minus(1) ?: 0
        }
        listState.scrollToItem(index)
    }
    LaunchedEffect(currentSize) {
        // 检查是否已经在最后一项，如果不在，则滚动
        val visibleItems = listState.layoutInfo.visibleItemsInfo
        val lastVisibleIndex = visibleItems.lastOrNull()?.index ?: -1
        if (lastVisibleIndex != currentSize?.minus(1)) {
            listState.animateScrollToItem(currentSize?.minus(1) ?: 0)
        }
    }
    if (chat != null) { // 如果有聊天数据，则显示聊天界面 // Si hay datos de chat, mostrar la interfaz de chat
        Column(
            Modifier
                //.offsetPresent(offsetPercentX) // 根据偏移量动画设置偏移位置 // Desplazar el contenido según el valor de desplazamiento en X
                .background(WeComposeTheme.colors.background) // 设置背景色 // Establecer color de fondo
                .fillMaxSize() // 填充整个屏幕大小 // Ocupa el tamaño completo de la pantalla
        ) {
            val keyboardController = LocalSoftwareKeyboardController.current

            TopBar(
                title = chat.friend.name,
                viewModel = viewModel
            ) { // 顶部栏显示好友名字，并提供结束聊天功能 // Barra superior con el nombre del amigo y función para terminar chat
                viewModel.endChat() // 结束当前聊天 // Finalizar chat actual
                keyboardController?.hide()
                navHostController.popBackStack()
            }
            Box(
                Modifier
                    .background(WeComposeTheme.colors.chatPage) // 设置聊天页面背景色 // Establecer color de fondo para la página de chat
                    .weight(1f) // 填充剩余空间 // Ocupa el espacio restante
            ) {
                Box(
                    Modifier
                        .alpha(WeComposeTheme.colors.chatPageBgAlpha) // 设置背景透明度 // Establecer la opacidad del fondo
                        .fillMaxSize() // 填充整个屏幕大小 // Ocupa el tamaño completo de la pantalla
                ) {
                    // 背景图片布局，三个不同的背景图
                    // Imágenes de fondo con diferentes posiciones
                    Image(
                        painterResource(R.drawable.ic_bg_newyear_left), null,
                        Modifier
                            .align(Alignment.CenterStart) // 背景图片位置为左侧居中 // Alinear imagen de fondo al centro de la izquierda
                            .padding(bottom = 100.dp) // 设置底部间距 // Establecer margen inferior de 100dp
                    )
                    Image(
                        painterResource(R.drawable.ic_bg_newyear_top), null,
                        Modifier
                            .align(Alignment.TopEnd) // 背景图片位置为右上角 // Alinear imagen de fondo en la esquina superior derecha
                            .padding(horizontal = 24.dp) // 设置水平间距 // Establecer margen horizontal de 24dp
                    )
                    Image(
                        painterResource(R.drawable.ic_bg_newyear_right), null,
                        Modifier
                            .align(Alignment.BottomEnd) // 背景图片位置为右下角 // Alinear imagen de fondo en la esquina inferior derecha
                            .padding(vertical = 200.dp) // 设置垂直间距 // Establecer margen vertical de 200dp
                    )
                }
                val shakingOffset =
                    remember { Animatable(0f) } // 声明一个可动画化的偏移量，用于震动效果 // Declarar un desplazamiento animable para el efecto de sacudida
                LaunchedEffect(shakingTime) { // 当震动时间变化时启动动画 // Iniciar la animación cuando el tiempo de sacudida cambia
                    if (shakingTime != 0) { // 如果震动时间不为0，执行震动动画 // Si el tiempo de sacudida no es 0, ejecutar la animación de sacudida
                        shakingOffset.animateTo(
                            targetValue = 0f,
                            animationSpec = spring(
                                0.3f,
                                600f
                            ), // 使用弹簧动画（减速效果） // Usar animación tipo resorte (efecto de desaceleración)
                            initialVelocity = -2000f // 初速度设置为负值，模拟震动 // Establecer la velocidad inicial negativa para simular vibración
                        )
                        shakingTime = 0
                    }
                }
                // 聊天消息列表，使用 LazyColumn 按顺序显示消息 // Lista de mensajes de chat, usa LazyColumn para mostrar los mensajes en orden
                LazyColumn(
                    Modifier
                        .fillMaxSize() // 填充整个屏幕大小 // Ocupa el tamaño completo de la pantalla
                        .offset(shakingOffset.value.dp), // 设置偏移量动画的值，应用震动效果 // Aplicar el valor de desplazamiento de la animación
                    state = listState
                ) {
                    items(chat.msgs.size, key = {chat.msgs[it].id}) { index: Int ->
                        val msg = chat.msgs[index] // 获取当前消息 // Obtener el mensaje actual
                        MessageItem(
                            msg,
                            shakingTime,
                            chat.msgs.size - index - 1,
                            viewModel
                        ) // 显示每条消息 // Mostrar cada mensaje
                    }
                }
            }
            ChatBottomBar(
                onBombClicked = { // 底部消息输入栏和爆炸按钮 // Barra inferior de mensajes y botón de explosión
                    viewModel.boom(chat) // 触发爆炸效果 // Activar el efecto de explosión
                    shakingTime++ // 增加震动次数 // Aumentar el número de sacudidas
                },
                onClickSend = {
                    if (it.isNotEmpty()) {
                        viewModel.sendMsg(chat, it)
                        shakingTime = 0
                    }
                }
            )
        }
    }
}


@Composable
fun MessageItem(msg: Msg, shakingTime: Int, shakingLevel: Int, viewModel: WeViewModel) {
    val shakingAngleBubble =
        remember { Animatable(0f) } // 初始化消息气泡的震动角度 // Inicializar el ángulo de sacudida del globo del mensaje
    LaunchedEffect(shakingTime) { // 当震动时间变化时启动动画 // Iniciar animación cuando cambie el tiempo de sacudida
        if (shakingTime != 0) { // 如果震动时间不为0，执行震动动画 // Si el tiempo de sacudida no es 0, ejecutar la animación de sacudida
            delay(shakingLevel.toLong() * 30) // 根据震动级别延迟不同时间 // Retrasar según el nivel de sacudida
            shakingAngleBubble.animateTo(
                targetValue = 0f,
                animationSpec = spring(
                    0.4f,
                    500f
                ), // 弹簧动画，模拟震动效果 // Animación de resorte, simulando un efecto de vibración
                initialVelocity = 1200f / (1 + shakingLevel * 0.4f) // 根据震动级别调整初速度 // Ajustar velocidad inicial según el nivel de sacudida
            )
        }
    }
    if (msg.userId == viewModel.currentUser?.id ) { // 如果消息是从我发出的 // Si el mensaje es enviado por mí
        Row(
            Modifier
                .fillMaxWidth() // 设置行宽度 // Establecer el ancho de la fila
                .padding(8.dp), // 设置内边距 // Establecer margen interno
            horizontalArrangement = Arrangement.End // 水平排列为右对齐 // Alineación horizontal a la derecha
        ) {
            val bubbleColor =
                WeComposeTheme.colors.bubbleMe // 我的消息气泡颜色 // Color del globo de mi mensaje
            Text(
                msg.text, // 显示消息内容 // Mostrar el contenido del mensaje
                Modifier
                    .graphicsLayer(
                        rotationZ = shakingAngleBubble.value, // 震动角度应用到消息气泡 // Aplicar ángulo de sacudida al globo del mensaje
                        transformOrigin = TransformOrigin(
                            1f,
                            0f
                        ) // 设置旋转原点为右上角 // Establecer el origen de la rotación en la esquina superior derecha
                    )
                    .drawBehind {
                        val buble =
                            Path().apply { // 绘制消息气泡形状 // Dibujar la forma del globo de mensaje
                                val rect = RoundRect(
                                    10.dp.toPx(),
                                    0f,
                                    size.width - 10.dp.toPx(),
                                    size.height,
                                    4.dp.toPx(),
                                    4.dp.toPx()
                                )
                                addRoundRect(rect) // 添加圆角矩形路径 // Añadir el camino del rectángulo redondeado
                                moveTo(size.width - 10.dp.toPx(), 15.dp.toPx()) // 绘制气泡尖角路径 // Dibujar la ruta de la punta del globo
                                lineTo(size.width - 5.dp.toPx(), 20.dp.toPx())
                                lineTo(size.width - 10.dp.toPx(), 25.dp.toPx())
                                close() // 关闭路径 // Cerrar el camino
                            }
                        drawPath(buble, bubbleColor) // 绘制气泡 // Dibujar el globo
                    }
                    .padding(20.dp, 10.dp), // 设置内边距 // Establecer margen interno
                color = WeComposeTheme.colors.textPrimaryMe // 设置文本颜色为我的消息文本颜色 // Establecer el color del texto para mi mensaje
            )
            Image(
                painterResource(msg.from?.avatar ?: R.drawable.find_ui), // 显示头像 // Mostrar avatar
                contentDescription = msg.from?.name, // 显示头像描述 // Descripción del avatar
                Modifier
                    .graphicsLayer(
                        rotationZ = shakingAngleBubble.value * 0.6f, // 头像震动角度 // Ángulo de sacudida del avatar
                        transformOrigin = TransformOrigin(
                            1f,
                            0f
                        ) // 设置旋转原点为右上角 // Establecer el origen de rotación en la esquina superior derecha
                    )
                    .size(40.dp) // 设置头像大小 // Establecer tamaño del avatar
                    .clip(RoundedCornerShape(4.dp)) // 设置头像圆角 // Establecer esquina redondeada para el avatar
            )
        }
    } else { // 如果消息是别人发出的 // Si el mensaje es enviado por otra persona
        Row(
            Modifier
                .fillMaxWidth() // 设置行宽度 // Establecer el ancho de la fila
                .padding(8.dp) // 设置内边距 // Establecer margen interno
        ) {

            Image(
                painterResource(msg.from?.avatar ?: R.drawable.find_ui), // 显示头像 // Mostrar avatar
                contentDescription = msg.from?.name, // 显示头像描述 // Descripción del avatar
                Modifier
                    .graphicsLayer(
                        rotationZ = -shakingAngleBubble.value * 0.6f, // 头像震动角度，反向旋转 // Ángulo de sacudida del avatar, rotación inversa
                        transformOrigin = TransformOrigin(
                            0f,
                            0f
                        ) // 设置旋转原点为左上角 // Establecer el origen de rotación en la esquina superior derecha
                    )
                    .size(40.dp) // 设置头像大小 // Establecer tamaño del avatar
                    .clip(RoundedCornerShape(4.dp)) // 设置头像圆角 // Establecer esquina redondeada para el avatar
            )
            val bubbleColor =
                WeComposeTheme.colors.bubbleOthers // 他人的消息气泡颜色 // Color del globo del mensaje de otra persona
            Text(
                msg.text, // 显示消息内容 // Mostrar el contenido del mensaje
                Modifier
                    .graphicsLayer(
                        rotationZ = -shakingAngleBubble.value, // 震动角度应用到消息气泡 // Aplicar ángulo de sacudida al globo del mensaje
                        transformOrigin = TransformOrigin(
                            0f,
                            0f
                        ) // 设置旋转原点为左上角 // Establecer el origen de la rotación en la esquina superior derecha
                    )
                    .drawBehind {
                        val buble =
                            Path().apply { // 绘制消息气泡形状 // Dibujar la forma del globo de mensaje
                                val rect = RoundRect(
                                    10.dp.toPx(),
                                    0f,
                                    size.width - 10.dp.toPx(),
                                    size.height,
                                    4.dp.toPx(),
                                    4.dp.toPx()
                                )
                                addRoundRect(rect) // 添加圆角矩形路径 // Añadir el camino del rectángulo redondeado
                                moveTo(10.dp.toPx(), 15.dp.toPx()) // 绘制气泡尖角路径 // Dibujar la ruta de la punta del globo
                                lineTo(5.dp.toPx(), 20.dp.toPx())
                                lineTo(10.dp.toPx(), 25.dp.toPx())
                            }
                        drawPath(buble, bubbleColor) // 绘制气泡 // Dibujar el globo
                    }
                    .padding(20.dp, 10.dp), // 设置内边距 // Establecer margen interno
                color = WeComposeTheme.colors.textPrimary // 设置文本颜色为他人消息文本颜色 // Establecer el color del texto para mensajes de otras personas
            )
        }
    }
}


@Composable
fun ChatBottomBar(onBombClicked: () -> Unit, onClickSend: (String) -> Unit) {
    var editingText by remember { mutableStateOf("") } // 用于记录输入框的文本内容 // Usado para registrar el contenido del cuadro de texto
    Row(
        Modifier
            .fillMaxWidth() // 填充整个屏幕宽度 // Ocupa todo el ancho de la pantalla
            .background(WeComposeTheme.colors.bottomBar) // 设置底部栏的背景色 // Establecer color de fondo de la barra inferior
            .padding(4.dp, 0.dp) // 设置内边距 // Establecer márgenes internos
    ) {
        Icon(
            painterResource(R.drawable.ic_voice),
            contentDescription = null,
            Modifier
                .align(Alignment.CenterVertically) // 垂直居中对齐图标 // Alinear icono verticalmente en el centro
                .padding(4.dp)
                .size(28.dp), // 设置图标大小 // Establecer tamaño del icono
            tint = WeComposeTheme.colors.icon // 设置图标颜色 // Establecer color del icono
        )
        BasicTextField(
            editingText,
            { editingText = it }, // 绑定文本输入框的文本内容 // Vincular el contenido del cuadro de texto
            Modifier
                .weight(1f) // 占据剩余空间 // Ocupa el espacio restante
                .padding(4.dp, 8.dp) // 设置内边距 // Establecer márgenes internos
                .height(40.dp) // 设置文本框高度 // Establecer la altura del cuadro de texto
                .clip(MaterialTheme.shapes.small) // 设置文本框圆角 // Establecer esquinas redondeadas para el cuadro de texto
                .background(WeComposeTheme.colors.textFieldBackground) // 设置背景色 // Establecer color de fondo
                .padding(start = 8.dp, top = 10.dp, end = 8.dp), // 内部填充 // Relleno interno
            cursorBrush = SolidColor(WeComposeTheme.colors.textPrimary) // 设置光标颜色 // Establecer color del cursor
        )
        Text(
            "\uD83D\uDCA3", // 发送炸弹按钮 // Botón de bomba de envío
            Modifier
                .clickable(onClick = onBombClicked) // 点击按钮触发爆炸效果 // Activar la explosión al hacer clic
                .padding(4.dp)
                .align(Alignment.CenterVertically), // 垂直居中对齐 // Alinear verticalmente en el centro
            fontSize = 24.sp // 设置字体大小 // Establecer tamaño de fuente
        )
        IconButton(onClick = {
            onClickSend(editingText)
            editingText = ""
        }) {
            Icon(
                Icons.AutoMirrored.Filled.Send,
                contentDescription = null,
                Modifier
                    .align(Alignment.CenterVertically) // 垂直居中对齐 // Alinear verticalmente en el centro
                    .padding(10.dp)
                    .size(30.dp), // 设置图标大小 // Establecer tamaño del icono
                tint = WeComposeTheme.colors.icon // 设置图标颜色 // Establecer color del icono
            )
        }
    }
}

/*
fun Modifier.offsetPresent(offsetPercentX: Float = 0f, offsetPercentY: Float = 0f) =
    this.layout { measurable, constraints ->  // 使用自定义布局来应用偏移效果 // Usar un layout personalizado para aplicar un efecto de desplazamiento
        val placeable =
            measurable.measure(constraints) // 根据给定的约束测量元素的大小 // Medir el tamaño del elemento según las restricciones dadas
        layout(
            placeable.width,
            placeable.height
        ) { // 确定布局的宽度和高度，并开始放置元素 // Determinar el ancho y alto del layout, luego colocar el elemento
            // 根据给定的百分比计算 X 和 Y 轴的偏移量 // Calcular los desplazamientos en los ejes X y Y según los porcentajes dados
            val offsetX =
                (-offsetPercentX * placeable.width).roundToInt() // 计算 X 轴的偏移量并四舍五入 // Calcular el desplazamiento en X y redondear
            val offsetY =
                (offsetPercentY * placeable.height).roundToInt() // 计算 Y 轴的偏移量并四舍五入 // Calcular el desplazamiento en Y y redondear
            placeable.placeRelative(
                offsetX,
                offsetY
            ) // 在给定的偏移量位置放置元素 // Colocar el elemento en la posición con el desplazamiento calculado
        }
    }

*/