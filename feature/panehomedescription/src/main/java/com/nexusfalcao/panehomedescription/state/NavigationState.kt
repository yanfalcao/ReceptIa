package com.nexusfalcao.panehomedescription.state

import com.nexusfalcao.model.User

data class NavigationState(
    val navigateToNewRecipe: () -> Unit = {},
    val navigateToCatalog: () -> Unit = {},
    val navigateToAvatar: () -> Unit = {},
    val navigateToHome: () -> Unit = {},
    val signOut: () -> Unit = {},
    val user: User?,
)
