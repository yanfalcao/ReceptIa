package com.example.receptia.feature.recipeDescription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receptia.feature.recipeDescription.state.RecipeUiState
import com.example.receptia.feature.recipeDescription.state.ToogleRecipeState
import com.example.receptia.model.Ingredient
import com.example.receptia.model.Recipe
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeDescriptionViewModel : ViewModel() {
    // TODO: Implement logic
    private val _toogleRecipeState = MutableStateFlow<ToogleRecipeState>(ToogleRecipeState.DetailsSelected)
    val toogleRecipeState get() = _toogleRecipeState

    fun selectRecipeToogle() {
        viewModelScope.launch {
            _toogleRecipeState.value = when (_toogleRecipeState.value) {
                ToogleRecipeState.DetailsSelected -> ToogleRecipeState.RecipeSelected
                ToogleRecipeState.RecipeSelected -> ToogleRecipeState.DetailsSelected
            }
        }
    }

    val getRecipe: StateFlow<RecipeUiState> =
        flow<RecipeUiState> {
            delay(300)

            emit(RecipeUiState.Success(recipe = RecipeMock()))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RecipeUiState.Loading,
        )

    private fun RecipeMock(): Recipe {
        return Recipe(
            id = "1",
            name = "Espaguete com Molho de Cogumelos e Bacon",
            description = "Filé de frango suculento temperado com limão e acompanhado de  cogumelos salteados.",
            prepTime = "30 min",
            easeRecipe = "Fácil",
            isFavorite = true,
            amountCalories = "450 kcal",
            amountCarbs = "60g",
            amountProteins = "15g",
            amountPeopleServes = 2,
            ingredients = listOf(
                Ingredient(
                    id = "1",
                    name = "Filé de Frango",
                    measure = "2 unid",
                ),
                Ingredient(
                    id = "1",
                    name = "Linão",
                    measure = "1 unid",
                ),
                Ingredient(
                    id = "1",
                    name = "Cogumelos",
                    measure = "200g",
                ),
                Ingredient(
                    id = "1",
                    name = "Alho",
                    measure = "3 dentes",
                ),
                Ingredient(
                    id = "1",
                    name = "Bacon",
                    measure = "50g",
                ),
            ),
            recipeSteps = "1. Tempere os filés de frango com suco de limão, alho picado, sal e pimenta a gosto." +
                "\\n2. Em uma frigideira, frite o bacon até ficar crocante. Retire o bacon da frigideira e reserve." +
                "\\n3. Na mesma frigideira, adicione os filés de frango temperados e cozinhe em fogo médio até ficarem dourados e cozidos por completo." +
                "\\n4. Retire os filés de frango da frigideira e reserve." +
                "\\n5. Na mesma frigideira, adicione os cogumelos fatiados e cozinhe por alguns minutos até ficarem macios." +
                "\\n6. Adicione o tomate picado, o manjericão e a cebolinha à frigideira e cozinhe por mais alguns minutos." +
                "\\n7. Retorne os filés de frango à frigideira e misture tudo delicadamente." +
                "\\n8. Sirva o filé de frango ao limão com cogumelos acompanhado do bacon crocante por cima.",
        )
    }
}
