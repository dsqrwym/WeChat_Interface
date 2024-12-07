package com.rengwuxian.yuzhangWetChat

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rengwuxian.yuzhangWetChat.ui.Home
import com.rengwuxian.yuzhangWetChat.ui.Login
import com.rengwuxian.yuzhangWetChat.ui.chatList.ChatPage

//import com.rengwuxian.yuzhangWetChat.ui.chatList.offsetPresent

@Composable
fun NavManager() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login",

        ) {
        composable("home") {
            val isLoaded = remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                isLoaded.value = true
            }
            if (isLoaded.value) {
                Box {
                    Home(navController)
                }
            }
        }

        composable("login") {
            Login(navController)
        }

        composable(
            route = "chatpage",
            enterTransition = {
                scaleIn(
                    initialScale = 0.8f,
                    animationSpec = tween(300)
                ) + fadeIn(
                    animationSpec = tween(500, easing = LinearEasing)
                ) + slideIntoContainer(
                    animationSpec = tween(500, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                scaleOut(
                    targetScale = 0.8f,
                    animationSpec = tween(300)
                ) + slideOutOfContainer(
                    animationSpec = tween(500, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            /*val offsetPercentX by animateFloatAsState(
                if (viewModel.chatting) 0f else 1f,
                label = "",
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy, // 控制弹簧的弹性
                    stiffness = Spring.StiffnessHigh // 控制弹簧的刚性，影响动画的速度
                )
            ) // 根据聊天状态决定偏移量（X轴）动画 // Determinar la animación del desplazamiento en el eje X según el estado de chat
            {
                ChatPage(viewModel, navController)
            }*/
            val isLoaded = remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                isLoaded.value = true
            }
            if (isLoaded.value) {
                ChatPage(navController)
            }
        }
    }
}

/*spring() 参数的作用：
dampingRatio：控制弹簧的阻尼效果。它影响动画的弹性：

Spring.DampingRatioNoBouncy：无弹性，动画没有回弹。
Spring.DampingRatioLowBouncy：低阻尼，会产生明显的弹跳效果。
Spring.DampingRatioMediumBouncy：中等阻尼，产生适中的弹跳效果。
Spring.DampingRatioHighBouncy：高阻尼，弹跳效果较少，动画更平滑。
stiffness：控制弹簧的刚性，影响动画的速度：

Spring.StiffnessLow：低刚性，弹簧慢，动画持续时间长。
Spring.StiffnessMedium：中等刚性，动画速度适中。
Spring.StiffnessHigh：高刚性，弹簧快，动画速度较快。*/