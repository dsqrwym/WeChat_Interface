package com.rengwuxian.yuzhangWetChat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.rengwuxian.yuzhangWetChat.ui.theme.WeChatInterfaceTheme
import com.rengwuxian.yuzhangWetChat.ui.theme.WeComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            //val database = AppDatabase.getInstance(applicationContext)
            //val viewModel: WeViewModel by viewModels()
            val viewModel: WeViewModel = hiltViewModel()
            viewModel.updateThemeFollowingSystem(isSystemInDarkTheme())

            WeChatInterfaceTheme(viewModel.currentTheme) {
                Column(
                    modifier = Modifier
                        .windowInsetsPadding(WindowInsets.statusBars)
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .background(WeComposeTheme.colors.background)
                ) {
                    NavManager()
                }
            }
        }
    }
}
/*
@Preview(showBackground = true, widthDp = 320)
@Composable
fun WeBottomBarPreview() {
    val viewModel: WeViewModel = viewModel()
    BottomBar(viewModel.selectedTab) {
        viewModel.selectedTab = it
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun TabItemPreviewNewYear() {
    WeChatInterfaceTheme(WeComposeTheme.Theme.NewYear) {
        val viewModel: WeViewModel = viewModel()
        Box(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.statusBars)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .background(WeComposeTheme.colors.background)
        ) {
            NavManager(viewModel)
        }
    }
}




*/