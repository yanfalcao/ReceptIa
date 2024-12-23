package com.example.panecatalogdescription

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.device.DeviceInteraction.Companion.setScreenOrientation
import androidx.test.espresso.device.EspressoDevice.Companion.onDevice
import androidx.test.espresso.device.action.ScreenOrientation
import androidx.test.espresso.device.rules.ScreenOrientationRule
import com.example.panecatalogdescription.state.DetailPaneState
import com.example.panecatalogdescription.state.ListPaneState
import com.example.panecatalogdescription.state.NavigationState
import com.nexusfalcao.description.state.RecipeUiState
import com.nexusfalcao.description.state.ToogleRecipeState
import com.nexusfalcao.designsystem.preview.UtilPreview
import com.nexusfalcao.model.Ingredient
import com.nexusfalcao.model.Recipe
import com.nexusfalcao.model.RecipeDetails
import com.nexusfalcao.model.Step
import com.nexusfalcao.recipecatalog.state.CatalogUiState
import com.nexusfalcao.recipecatalog.state.FilterState
import com.nexusfalcao.recipecatalog.state.TagFilterEnum
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date
import java.util.UUID

class PaneCatalogDescriptonScreenTest {

    lateinit var recipe: Recipe

    @Before
    fun setUp() {
        recipe = Recipe(
            id = UUID.randomUUID().toString(),
            name = "Espaguete com Molho de Cogumelos e Bacon",
            description = "Filé de frango suculento temperado com limão e acompanhado de  cogumelos salteados.",
            isFavorite = true,
            recipeDetails = RecipeDetails(
                preparationTime = "30 min",
                difficult = "Fácil",
                amountCalories = "450 kcal",
                amountCarbs = "60g",
                amountProteins = "15g",
                amountPeopleServes = 2,
                difficultLevel = 1
            ),
            ingredients = listOf(
                Ingredient(
                    id = UUID.randomUUID().toString(),
                    name = "Filé de Frango",
                    measure = "2 unid"
                ),
                Ingredient(
                    id = UUID.randomUUID().toString(),
                    name = "Fresh fruits (e.g. berries, banana, man",
                    measure = "1 cup, chopped"
                ),
                Ingredient(
                    id = UUID.randomUUID().toString(),
                    name = "Cogumelos",
                    measure = "200g"
                ),
                Ingredient(
                    id = UUID.randomUUID().toString(),
                    name = "Alho",
                    measure = "3 dentes"
                ),
                Ingredient(
                    id = UUID.randomUUID().toString(),
                    name = "Bacon",
                    measure = "50g"
                )
            ),
            recipeSteps = listOf(
                Step(
                    id = UUID.randomUUID().toString(),
                    position = 1,
                    description = "Tempere os filés de frango com suco de limão, alho picado, sal e pimenta a gosto."
                ),
                Step(
                    id = UUID.randomUUID().toString(),
                    position = 2,
                    description = "Em uma frigideira, frite o bacon até ficar crocante. Retire o bacon da frigideira e reserve."
                ),
                Step(
                    id = UUID.randomUUID().toString(),
                    position = 3,
                    description = "Na mesma frigideira, adicione os filés de frango temperados e cozinhe em fogo médio até ficarem dourados e cozidos por completo."
                ),
                Step(
                    id = UUID.randomUUID().toString(),
                    position = 4,
                    description = "Retire os filés de frango da frigideira e reserve."
                ),
                Step(
                    id = UUID.randomUUID().toString(),
                    position = 5,
                    description = "Na mesma frigideira, adicione os cogumelos fatiados e cozinhe por alguns minutos até ficarem macios."
                ),
                Step(
                    id = UUID.randomUUID().toString(),
                    position = 6,
                    description = "Adicione o tomate picado, o manjericão e a cebolinha à frigideira e cozinhe por mais alguns minutos."
                ),
                Step(
                    id = UUID.randomUUID().toString(),
                    position = 7,
                    description = "Retorne os filés de frango à frigideira e misture tudo delicadamente."
                ),
                Step(
                    id = UUID.randomUUID().toString(),
                    position = 8,
                    description = "Sirva o filé de frango ao limão com cogumelos acompanhado do bacon crocante por cima."
                )
            ),
            createdAt = Date()
        )
    }

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @get:Rule
    val screenOrientationRule: ScreenOrientationRule = ScreenOrientationRule(ScreenOrientation.LANDSCAPE)

    @Test
    fun showsEmptyPaneWidget_whenNoContentInDetailPane() {
        onDevice().setScreenOrientation(ScreenOrientation.LANDSCAPE)

        composeTestRule.setContent {
            PaneCatalogDescriptionScreen(
                listPaneState = ListPaneState(
                    catalogUiState = CatalogUiState.Loading,
                    filterState = FilterState(
                        search = "",
                        tag = TagFilterEnum.ALL,
                    ),
                ),
                detailPaneState = DetailPaneState(
                    toogleState = ToogleRecipeState.DetailsSelected,
                    recipeUiState = RecipeUiState.Loading,
                    refreshPane = { recipeId -> },
                ),
                navigationState = NavigationState(
                    user = null,
                    refreshPane = { recipeId -> },
                ),
                windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
            )
        }

        composeTestRule.onNodeWithText("Area reserved for your recipe").assertIsDisplayed()
    }

    @Test
    fun showsRecipeDescription_whenContentIsAvailable() {
        composeTestRule.setContent {
            PaneCatalogDescriptionScreen(
                listPaneState = ListPaneState(
                    catalogUiState = CatalogUiState.Success(listOf(recipe)),
                    filterState = FilterState(
                        search = "",
                        tag = TagFilterEnum.ALL,
                    ),
                ),
                detailPaneState = DetailPaneState(
                    toogleState = ToogleRecipeState.DetailsSelected,
                    recipeUiState = RecipeUiState.Success(recipe),
                    refreshPane = { recipeId -> },
                ),
                navigationState = NavigationState(
                    user = null,
                    refreshPane = { recipeId -> },
                ),
                windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
            )
        }

        composeTestRule.onNodeWithText(recipe.name).performClick()
        composeTestRule.onAllNodesWithText(recipe.name).assertCountEquals(expectedSize = 2)
    }

    @Test
    fun showsFilteredRecipes_whenFilterIsApplied() {
        val filteredRecipe = recipe.copy(name = "Filtered Recipe")

        composeTestRule.setContent {
            PaneCatalogDescriptionScreen(
                listPaneState = ListPaneState(
                    catalogUiState = CatalogUiState.Success(listOf(filteredRecipe)),
                    filterState = FilterState(
                        search = "Filtered",
                        tag = TagFilterEnum.ALL,
                    ),
                ),
                detailPaneState = DetailPaneState(
                    toogleState = ToogleRecipeState.DetailsSelected,
                    recipeUiState = RecipeUiState.Loading,
                    refreshPane = { recipeId -> },
                ),
                navigationState = NavigationState(
                    user = null,
                    refreshPane = { recipeId -> },
                ),
                windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
            )
        }

        composeTestRule.onNodeWithText("Filtered Recipe").assertIsDisplayed()
    }
}