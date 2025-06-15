package com.asemlab.simpleorder.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = CircleShape.copy(CornerSize(50)),
    medium = RoundedCornerShape(CornerSize(8.dp)),
    large = RoundedCornerShape(CornerSize(16.dp)),
    extraLarge = RoundedCornerShape(CornerSize(32.dp)),
)