package com.rengwuxian.yuzhangWetChat.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.rengwuxian.yuzhangWetChat.WeViewModel
import com.rengwuxian.yuzhangWetChat.ui.chatList.ChatList
import com.rengwuxian.yuzhangWetChat.ui.contactList.ContactList
import com.rengwuxian.yuzhangWetChat.ui.discoveryList.DiscoveryList
import com.rengwuxian.yuzhangWetChat.ui.meList.MeList
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalPagerApi::class)
fun Home() {
    // 创建 PagerState 并绑定 ViewModel 的选中状态
    // Crear PagerState y enlazarlo con el estado seleccionado de ViewModel
    val viewModel: WeViewModel = hiltViewModel(LocalContext.current as ComponentActivity)// 获取当前的 ViewModel 实例 // Obtener la instancia actual del ViewModel
    val pagerState = rememberPagerState(viewModel.selectedTab)
    val coroutineScope = rememberCoroutineScope() // 用于执行异步任务 // Para ejecutar tareas asincrónicas

    // 使用垂直布局容器组合顶部内容和底部导航栏
    // Usar un contenedor de diseño vertical para combinar contenido superior y barra inferior
    Column {
        // 创建支持滑动的分页布局，支持左右 Swipe（滑动）切换页面
        // Crear un diseño de paginación con soporte para Swipe (deslizar) horizontal
        HorizontalPager(
            count = 4, // 总共有 4 页内容 // Total de 4 páginas
            Modifier.weight(1f), // 分配剩余空间给分页组件 // Asignar espacio restante al componente de paginación
            state = pagerState // 绑定 PagerState 状态，用于处理页面切换逻辑 // Asociar el estado PagerState para manejar la lógica de cambio de páginas
        ) { page: Int ->
            when (page) {
                0 -> ChatList() // 显示聊天列表页 // Mostrar la página de lista de chats
                1 -> ContactList() // 显示联系人列表页 // Mostrar la página de lista de contactos
                2 -> DiscoveryList() // 朋友圈 // Mostrar la página de descubrimientos (por ejemplo, Momentos o contenido popular)
                3 -> MeList() // 显示“我”的个人页面，包含用户信息和设置 // Mostrar la página "Yo", que incluye información del usuario y configuración
            }
        }

        // 底部导航栏，点击时切换到对应的页面
        // Barra de navegación inferior, cambiar a la página correspondiente al hacer clic
        BottomBar(viewModel.selectedTab) {
            viewModel.selectedTab = it // 更新 ViewModel 的选中状态 // Actualizar el estado seleccionado en ViewModel
            coroutineScope.launch {
                // 平滑滚动到选中的页面（实现 Swipe 效果的编程式导航）
                // Desplazarse suavemente a la página seleccionada (navegación programática para Swipe)
                pagerState.animateScrollToPage(it)
            }
        }
    }

    // 页面滑动时，动态更新底部导航栏的选中状态（反向同步 Swipe 和导航栏状态）
    // Al deslizar las páginas, actualizar dinámicamente el estado seleccionado en la barra inferior (sincronización inversa entre Swipe y barra de navegación)
    LaunchedEffect(pagerState.currentPage) {
        viewModel.selectedTab = pagerState.currentPage
    }
}
