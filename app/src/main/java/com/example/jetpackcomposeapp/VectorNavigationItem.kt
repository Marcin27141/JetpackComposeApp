package com.example.jetpackcomposeapp

import androidx.compose.ui.graphics.vector.ImageVector

data class VectorNavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

data class ResourceNavigationItem(
    val title: String,
    val resourceId: Int,
    val route: String
)