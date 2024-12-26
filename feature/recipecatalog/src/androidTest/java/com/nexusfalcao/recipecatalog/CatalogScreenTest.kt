package com.nexusfalcao.recipecatalog

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.nexusfalcao.designsystem.preview.PreviewParameterData
import com.nexusfalcao.designsystem.preview.UtilPreview
import com.nexusfalcao.recipecatalog.state.CatalogUiState
import com.nexusfalcao.recipecatalog.state.FilterState
import com.nexusfalcao.recipecatalog.state.TagFilterEnum
import org.junit.Rule
import org.junit.Test

class CatalogScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun showsEmptyState_whenCatalogUiStateIsEmpty() {
        val emptyTitle = composeTestRule.activity.getString(
            com.nexusfalcao.designsystem.R.string.empty_title
        )

        composeTestRule.setContent {
            CatalogScreen(
                catalogState = CatalogUiState.Success(emptyList()),
                filterUiState = FilterState(TagFilterEnum.ALL),
                windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
            )
        }

        composeTestRule.onNodeWithText(emptyTitle).assertIsDisplayed()
    }

    @Test
    fun showsRecipes_whenCatalogUiStateIsSuccess() {
        val recipes = listOf(PreviewParameterData.recipe.copy(name = "Test Recipe"))
        composeTestRule.setContent {
            CatalogScreen(
                catalogState = CatalogUiState.Success(recipes),
                filterUiState = FilterState(TagFilterEnum.ALL),
                windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
            )
        }

        composeTestRule.onNodeWithText("Test Recipe").assertIsDisplayed()
    }
}