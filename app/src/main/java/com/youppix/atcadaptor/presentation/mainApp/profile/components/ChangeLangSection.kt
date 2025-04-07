package com.youppix.atcadaptor.presentation.mainApp.profile.components

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.youppix.atcadaptor.R
import com.youppix.atcadaptor.common.Constant
import com.youppix.atcadaptor.common.Dimens
import com.youppix.atcadaptor.presentation.mainApp.MainActivity
import com.youppix.atcadaptor.presentation.mainApp.profile.ProfileEvent
import java.util.Locale

@Composable
fun ChangeLangSection(isArabic : Boolean , dropLanguageMenu : Boolean ,onDismissRequest : () -> Unit, onCLick: () -> Unit ,
                      event: (ProfileEvent)-> Unit) {

    val context = LocalContext.current
    Column(
        Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center
    ) {
        ProfileItem(
            modifier = Modifier.padding(vertical = Dimens.ExtraSmallPadding2),
            isArabic = isArabic,
            painter = painterResource(id = R.drawable.ic_language),
            title = stringResource(id = R.string.language)
        ) {
            onCLick()
        }
//        Box(modifier = Modifier.align(Alignment.End)){
//            DropdownMenu(
//                modifier = Modifier.background(MaterialTheme.colorScheme.background),
//                expanded = dropLanguageMenu,
//                onDismissRequest = {
//                   onDismissRequest()
//                }
//            ) {
//                Constant.lANG_LIST.forEach { lang ->
//                    DropdownMenuItem(
//                        modifier = Modifier.align(Alignment.End),
//                        text = {
//                            Text(
//                                text = stringResource(id = lang.first),
//                                style = MaterialTheme.typography.bodySmall.copy(
//                                    fontWeight = FontWeight.Bold
//                                )
//                            )
//                        },
//                        onClick = {
//                            if(Locale.getDefault().language != lang.second) {
//                                event(ProfileEvent.SaveAppLanguage(lang.second))
//                                context.startActivity(
//                                    Intent(
//                                        context,
//                                        MainActivity::class.java
//                                    )
//                                )
//                            }
//                          onDismissRequest()
//                        })
//
//                }
//
//
//            }
        }

        Spacer(
            modifier = Modifier
                .height(0.5.dp)
                .fillMaxWidth()
                .padding(horizontal = Dimens.SmallPadding)
                .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
        )
    }
