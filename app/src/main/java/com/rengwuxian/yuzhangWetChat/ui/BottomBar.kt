package com.rengwuxian.yuzhangWetChat.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rengwuxian.yuzhangWetChat.R
import com.rengwuxian.yuzhangWetChat.ui.theme.WeChatInterfaceTheme
import com.rengwuxian.yuzhangWetChat.ui.theme.WeComposeTheme

@Composable
fun BottomBar(selected: Int, onSelectedChange: (Int) -> Unit) {
    Row(Modifier.background(WeComposeTheme.colors.bottomBar)) {
        TabItem(
            if (selected == 0) R.drawable.ic_chat_filled else R.drawable.ic_chat_outlined,
            R.string.tab_item_chat,
            if (selected == 0) WeComposeTheme.colors.iconCurrent else WeComposeTheme.colors.icon,
            Modifier
                .weight(1f)
                .clickable {
                    onSelectedChange(0)
                }
        )
        TabItem(
            if (selected == 1) R.drawable.ic_contacts_filled else R.drawable.ic_contacts_outlined,
            R.string.tab_item_contacts,
            if (selected == 1) WeComposeTheme.colors.iconCurrent else WeComposeTheme.colors.icon,
            Modifier
                .weight(1f)
                .clickable {
                    onSelectedChange(1)
                }
        )
        TabItem(
            if (selected == 2) R.drawable.ic_discovery_filled else R.drawable.ic_discovery_outlined,
            R.string.tab_item_discovery,
            if (selected == 2) WeComposeTheme.colors.iconCurrent else WeComposeTheme.colors.icon,
            Modifier
                .weight(1f)
                .clickable {
                    onSelectedChange(2)
                }
        )
        TabItem(
            if (selected == 3) R.drawable.ic_me_filled else R.drawable.ic_me_outlined,
            R.string.tab_item_me,
            if (selected == 3) WeComposeTheme.colors.iconCurrent else WeComposeTheme.colors.icon,
            Modifier
                .weight(1f)
                .clickable {
                    onSelectedChange(3)
                }
        )
    }
}

@Composable
fun TabItem(
    @DrawableRes iconId: Int,
    @StringRes stringId: Int,
    tint: Color,
    modifier: Modifier
) {
    Column(
        modifier.padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(painterResource(iconId), stringResource(stringId), Modifier.size(24.dp), tint = tint)
        Text(stringResource(stringId), fontSize = 11.sp, color = tint)
    }
}


@Preview(showBackground = true, widthDp = 320)
@Composable
fun WeBottomBarPreview() {
    var selectedTab by remember { mutableIntStateOf(0) }
    BottomBar(0){
        selectedTab = it
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun TabItemPreviewNewYear() {
    WeChatInterfaceTheme(WeComposeTheme.Theme.NewYear) {
        var selectedTab by remember { mutableIntStateOf(0) }
        BottomBar(2){
            selectedTab = it
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun WeBottomBarPreviewDark() {
    WeChatInterfaceTheme(WeComposeTheme.Theme.Dark) {
        var selectedTab by remember { mutableIntStateOf(0) }
        BottomBar(3){
            selectedTab = it
        }
    }
}
