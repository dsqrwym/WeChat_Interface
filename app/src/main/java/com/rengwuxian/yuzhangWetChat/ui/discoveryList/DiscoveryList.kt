package com.rengwuxian.yuzhangWetChat.ui.discoveryList

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import com.rengwuxian.yuzhangWetChat.ui.TopBar
import com.rengwuxian.yuzhangWetChat.ui.chatList.unread
import com.rengwuxian.yuzhangWetChat.ui.theme.WeComposeTheme

@Composable
fun DiscoveryListTopBar(viewModel: WeViewModel) {
    TopBar(title = stringResource(R.string.tab_item_discovery), viewModel)
}

@Composable
fun DiscoveryListItem(
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
fun DiscoveryList(viewModel: WeViewModel) {
    Column(Modifier.fillMaxSize()) {
        DiscoveryListTopBar(viewModel)
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
                DiscoveryListItem(R.drawable.ic_moments, stringResource(R.string.discover_moments), badge = {
                    Box(
                        Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(50))
                            .size(21.dp)
                            .background(WeComposeTheme.colors.badge)
                    ) {
                        Text(
                            "3",
                            Modifier.align(Alignment.Center),
                            fontSize = 12.sp,
                            color = WeComposeTheme.colors.onBadge
                        )
                    }
                }, endBadge = {
                    Image(
                        painterResource(R.drawable.avatar_gaolaoshi), stringResource(R.string.chat_avatar), Modifier
                            .padding(8.dp, 0.dp)
                            .size(32.dp)
                            .unread(false, WeComposeTheme.colors.badge)
                            .clip(RoundedCornerShape(4.dp))
                    )
                })
                Spacer(
                    Modifier
                        .background(WeComposeTheme.colors.background)
                        .fillMaxWidth()
                        .height(8.dp)
                )
                DiscoveryListItem(R.drawable.ic_channels, stringResource(R.string.discover_channels), endBadge = {
                    Image(
                        painterResource(R.drawable.avatar_diuwuxian), "avatar", Modifier
                            .padding(8.dp, 0.dp)
                            .size(32.dp)
                            .unread(false, WeComposeTheme.colors.badge)
                            .clip(RoundedCornerShape(4.dp))
                    )
                    Text(
                        "赞过", Modifier.padding(0.dp, 0.dp, 4.dp, 0.dp),
                        fontSize = 14.sp, color = WeComposeTheme.colors.textSecondary
                    )
                })
                Spacer(
                    Modifier
                        .background(WeComposeTheme.colors.background)
                        .fillMaxWidth()
                        .height(8.dp)
                )
                DiscoveryListItem(R.drawable.ic_ilook, stringResource(R.string.discover_looks))
                HorizontalDivider(
                    Modifier.padding(start = 56.dp),
                    color = WeComposeTheme.colors.chatListDivider,
                    thickness = 0.8f.dp
                )
                DiscoveryListItem(R.drawable.ic_isearch, stringResource(R.string.discover_search))
                Spacer(
                    Modifier
                        .background(WeComposeTheme.colors.background)
                        .fillMaxWidth()
                        .height(8.dp)
                )
                DiscoveryListItem(R.drawable.ic_nearby, stringResource(R.string.discover_nearby))
            }
        }
    }
}