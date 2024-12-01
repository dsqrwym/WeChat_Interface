package com.rengwuxian.yuzhangWetChat.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.rengwuxian.yuzhangWetChat.NavManager
import com.rengwuxian.yuzhangWetChat.R
import com.rengwuxian.yuzhangWetChat.WeViewModel
import com.rengwuxian.yuzhangWetChat.ui.theme.WeChatInterfaceTheme
import com.rengwuxian.yuzhangWetChat.ui.theme.WeComposeTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Login(navController: NavHostController, viewModel: WeViewModel) {
    var userid by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    // 修复: Scaffold content 参数接收 PaddingValues
    Scaffold(
        contentColor = WeComposeTheme.colors.background,
        content = { paddingValues ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(WeComposeTheme.colors.background), // 使用传递的 PaddingValues
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    painter = painterResource(R.drawable.login_nero_ai_photo_face),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .size(250.dp)
                )
                Card(
                    Modifier
                        .weight(2f)
                        .padding(8.dp),
                    shape = RoundedCornerShape(32.dp)
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(32.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.login_welcome),
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .weight(5f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Spacer(Modifier.height(60.dp))
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = userid,
                                onValueChange = { userid = it },
                                label = { Text(stringResource(R.string.login_userid)) },
                                singleLine = true,
                                trailingIcon = {
                                    if (userid.isNotEmpty()) {
                                        IconButton(onClick = { userid = "" }) {
                                            Icon(
                                                imageVector = Icons.Filled.Clear,
                                                contentDescription = null
                                            )
                                        }
                                    }
                                }
                            )
                            Spacer(Modifier.height(8.dp))
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = password,
                                onValueChange = { password = it },
                                label = { Text(text = stringResource(R.string.login_password)) },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Password,
                                    imeAction = ImeAction.Done
                                ),
                                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(
                                    stringResource(R.string.login_password_mask)[0]
                                ),
                                trailingIcon = {
                                    IconButton(onClick = {
                                        isPasswordVisible = !isPasswordVisible
                                    }) {
                                        Icon(
                                            imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                            contentDescription = null,
                                            tint = WeComposeTheme.colors.badge
                                        )
                                    }
                                }
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        Button(
                            onClick = {
                                viewModel.login(userid, password) {
                                    if (it) {
                                        navController.navigate("home")
                                        Log.d("Login", "${it}")
                                    } else {
                                        userid = ""
                                        password = ""
                                    }
                                }
                            },
                            enabled = userid.isNotBlank() && password.length >= 6,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(stringResource(R.string.login), fontSize = 20.sp)
                        }
                        Spacer(Modifier.weight(1f))
                        Row(
                            Modifier.fillMaxWidth(),
                            Arrangement.SpaceBetween
                        ) {
                            TextButton(onClick = {
                                // 注册操作
                            }) {
                                Text(stringResource(R.string.register))
                            }
                            TextButton(onClick = {
                                // 忘记密码操作
                            }) {
                                Text(stringResource(R.string.login_password_forget))
                            }
                        }
                    }
                }
            }
        }
    )
}
/*
@Preview(showBackground = true)
@Composable
fun Login() {
    val viewModel: WeViewModel = viewModel()
    WeChatInterfaceTheme(WeComposeTheme.Theme.Light) {
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

