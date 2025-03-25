package com.youppix.atcadaptor.domain.model.bottomBar

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
data class BottomBar(
    val title : String,
    val selectedIcon  : Int,
    val unselectedIcon : Int,
    val screen : Int
)
