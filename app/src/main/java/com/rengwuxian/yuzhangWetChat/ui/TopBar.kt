package com.rengwuxian.yuzhangWetChat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.rengwuxian.yuzhangWetChat.R
import com.rengwuxian.yuzhangWetChat.WeViewModel
import com.rengwuxian.yuzhangWetChat.ui.theme.WeComposeTheme

@Composable
fun TopBar(title: String, viewModel: WeViewModel, onBack: (() -> Unit)? = null) {
    Box(
        Modifier
            .background(WeComposeTheme.colors.background)
            .fillMaxWidth()
    ) {
        Row(Modifier.height(48.dp)) {
            if (onBack != null) {
                Icon(
                    painterResource(R.drawable.ic_back),
                    null,
                    Modifier
                        .clickable(onClick = onBack)
                        .align(Alignment.CenterVertically)
                        .size(36.dp)
                        .padding(8.dp),
                    tint = WeComposeTheme.colors.icon
                )
            }
            Spacer(Modifier.weight(1f))
            //val viewModel: WeViewModel = viewModel()
            Icon(
                painterResource(R.drawable.ic_palette),
                contentDescription = "切换主题",
                Modifier
                    .clickable {
                        viewModel.updateTheme(
                            when (viewModel.currentTheme) {
                                WeComposeTheme.Theme.Light -> WeComposeTheme.Theme.Dark
                                WeComposeTheme.Theme.Dark -> WeComposeTheme.Theme.NewYear
                                WeComposeTheme.Theme.NewYear -> WeComposeTheme.Theme.Light
                            }
                        )
                    }
                    .align(Alignment.CenterVertically)
                    .size(36.dp)
                    .padding(8.dp),
                tint = WeComposeTheme.colors.icon
            )
        }

        Text(title, Modifier.align(Alignment.Center), WeComposeTheme.colors.textPrimary)
    }
}