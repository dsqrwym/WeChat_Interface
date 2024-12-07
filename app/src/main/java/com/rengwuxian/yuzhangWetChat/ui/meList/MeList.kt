package com.rengwuxian.yuzhangWetChat.ui.meList

import androidx.activity.ComponentActivity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rengwuxian.yuzhangWetChat.R
import com.rengwuxian.yuzhangWetChat.WeViewModel
import com.rengwuxian.yuzhangWetChat.ui.theme.WeComposeTheme

@Composable
fun MeListTopBar(viewModel: WeViewModel) {
    Row(
        Modifier
            .background(WeComposeTheme.colors.listItem)
            .fillMaxWidth()
            .height(138.dp)
    ) {
        Image(
            painterResource(id = viewModel.currentUser?.avatar ?: R.drawable.find_ui), contentDescription = "Me",
            Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 24.dp)
                .clip(RoundedCornerShape(6.dp))
                .size(64.dp)
        )
        Column(
            Modifier
                .weight(1f)
                .padding(start = 12.dp)
        ) {
            Text(
                viewModel.currentUser?.name ?: "none",
                Modifier.padding(top = 36.dp),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = WeComposeTheme.colors.textPrimary
            )
            Text(
                "${stringResource(R.string.login_userid)}：${viewModel.currentUser?.id ?: ""}",
                Modifier.padding(top = 10.dp),
                fontSize = 12.sp,
                color = WeComposeTheme.colors.textSecondary
            )
            Text(
                stringResource(R.string.me_add_status),
                Modifier
                    .padding(top = 10.dp)
                    .border(1.dp, WeComposeTheme.colors.onBackground, RoundedCornerShape(56))
                    .padding(8.dp, 2.dp),
                fontSize = 13.sp,
                color = WeComposeTheme.colors.onBackground
            )
        }
        Icon(
            painterResource(id = R.drawable.ic_qrcode), contentDescription = stringResource(R.string.me_qrcode),
            Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 20.dp)
                .size(13.dp),
            tint = WeComposeTheme.colors.onBackground
        )
        Icon(
            painterResource(R.drawable.ic_arrow_more), contentDescription = stringResource(R.string.icon_more),
            Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 16.dp)
                .size(16.dp),
            tint = WeComposeTheme.colors.more
        )
    }
}

@Composable
fun MeListItem(
    @DrawableRes icon: Int,
    title: String,
    badge: @Composable (() -> Unit)? = null,
    endBadge: @Composable (() -> Unit)? = null
) {
    Row(
        Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(icon), stringResource(R.string.icon), Modifier
                .padding(12.dp, 8.dp, 8.dp, 8.dp)
                .size(36.dp)
                .padding(8.dp)
        )
        Text(
            title,
            fontSize = 17.sp,
            color = WeComposeTheme.colors.textPrimary
        )
        badge?.invoke()
        Spacer(Modifier.weight(1f))
        endBadge?.invoke()
        Icon(
            painterResource(R.drawable.ic_arrow_more), contentDescription = stringResource(R.string.icon_more),
            Modifier
                .padding(0.dp, 0.dp, 12.dp, 0.dp)
                .size(16.dp),
            tint = WeComposeTheme.colors.more
        )
    }
}

@Composable
fun MeList() {
    val viewModel: WeViewModel = hiltViewModel(LocalContext.current as ComponentActivity)// 获取当前的 ViewModel 实例 // Obtener la instancia actual del ViewModel

    Box(
        Modifier
            .background(WeComposeTheme.colors.background)
            .fillMaxSize()
    ) {
        Column(
            Modifier
                .background(WeComposeTheme.colors.listItem)
                .fillMaxWidth()
        ) {
            MeListTopBar(viewModel)
            Spacer(
                Modifier
                    .background(WeComposeTheme.colors.background)
                    .fillMaxWidth()
                    .height(8.dp)
            )
            MeListItem(R.drawable.ic_pay, stringResource(R.string.me_pay))
            Spacer(
                Modifier
                    .background(WeComposeTheme.colors.background)
                    .fillMaxWidth()
                    .height(8.dp)
            )
            MeListItem(R.drawable.ic_collections, stringResource(R.string.me_favorites))
            HorizontalDivider(Modifier.padding(start = 56.dp), color = WeComposeTheme.colors.chatListDivider, thickness = 0.8f.dp)
            MeListItem(R.drawable.ic_photos, stringResource(R.string.discover_moments))
            HorizontalDivider(Modifier.padding(start = 56.dp), color = WeComposeTheme.colors.chatListDivider, thickness = 0.8f.dp)
            MeListItem(R.drawable.ic_cards, stringResource(R.string.me_cards))
            HorizontalDivider(Modifier.padding(start = 56.dp), color = WeComposeTheme.colors.chatListDivider, thickness = 0.8f.dp)
            MeListItem(R.drawable.ic_stickers, stringResource(R.string.me_stickers))
            Spacer(
                Modifier
                    .background(WeComposeTheme.colors.background)
                    .fillMaxWidth()
                    .height(8.dp)
            )
            MeListItem(R.drawable.ic_settings, stringResource(R.string.me_settings))
        }
    }
}/*
@Composable
@Preview
fun MelistPreview(){
    val viewModel: WeViewModel = viewModel()
    MeList(viewModel = viewModel)
}*/