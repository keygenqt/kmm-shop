package com.keygenqt.shop.android.features.contact.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.keygenqt.shop.android.R
import com.keygenqt.shop.android.components.texts.AppText

@Composable
fun ContactMessageBlock(
    onSendMessage: () -> Unit
) {
    val dark = isSystemInDarkTheme()

    Card(
        backgroundColor = if (dark) Color(0xFF2B8B67) else Color(0xFFDAF7EC),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Box(
                    modifier = Modifier
                        .size(75.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.7f))
                ) {
                    Image(
                        modifier = Modifier
                            .requiredSize(45.dp)
                            .offset(0.dp, 3.dp)
                            .align(Alignment.Center),
                        contentDescription = null,
                        painter = painterResource(id = R.drawable.contact_comment),
                    )
                }

                Spacer(modifier = Modifier.size(16.dp))

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    AppText(text = "Напишите нам!")

                    Spacer(modifier = Modifier.size(4.dp))

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onSendMessage,
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {

                            Icon(
                                modifier = Modifier.height(18.dp),
                                imageVector = Icons.Outlined.Send,
                                contentDescription = null,
                                tint = Color.White
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                AppText(
                                    color = Color.White,
                                    text = "Отправить сообщение"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
