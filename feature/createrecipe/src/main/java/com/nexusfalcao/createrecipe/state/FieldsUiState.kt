package com.nexusfalcao.createrecipe.state

data class FieldsUiState(
    val favoriteIngredients: ArrayList<String> = arrayListOf(),
    val nonFavoritesIngredients: ArrayList<String> = arrayListOf(),
    val allergicingredients: ArrayList<String> = arrayListOf(),
    val intolerantIngredients: ArrayList<String> = arrayListOf(),
    private var _meal: RadioUiState = RadioUiState.Unselected,
) {
    val meal: RadioUiState
        get() = _meal

    fun getIngredient(state: RecipeFieldState): List<String> {
        return when (state) {
            RecipeFieldState.FAVORITE -> favoriteIngredients
            RecipeFieldState.NON_FAVORITE -> nonFavoritesIngredients
            RecipeFieldState.ALLERGIC -> allergicingredients
            RecipeFieldState.INTOLERANT -> intolerantIngredients
            else -> emptyList()
        }
    }

    fun getListStringSize(state: RecipeFieldState): Int {
        val ingredient = getIngredient(state)
        var listStringSize = 0

        ingredient.forEach {
            listStringSize += it.length
        }

        return listStringSize
    }

    fun addField(state: RecipeFieldState, text: String) {
        when (state) {
            RecipeFieldState.FAVORITE -> {
                favoriteIngredients.add(text)
            }
            RecipeFieldState.NON_FAVORITE -> {
                nonFavoritesIngredients.add(text)
            }
            RecipeFieldState.ALLERGIC -> {
                allergicingredients.add(text)
            }
            RecipeFieldState.INTOLERANT -> {
                intolerantIngredients.add(text)
            }
            RecipeFieldState.MEAL -> {
                _meal = RadioUiState.Selected(text)
            }
        }
    }

    fun removeIngredient(state: RecipeFieldState, ingredient: String) {
        when (state) {
            RecipeFieldState.FAVORITE -> {
                favoriteIngredients.remove(ingredient)
            }
            RecipeFieldState.NON_FAVORITE -> {
                nonFavoritesIngredients.remove(ingredient)
            }
            RecipeFieldState.ALLERGIC -> {
                allergicingredients.remove(ingredient)
            }
            RecipeFieldState.INTOLERANT -> {
                intolerantIngredients.remove(ingredient)
            }
            RecipeFieldState.MEAL -> {
                _meal = RadioUiState.Unselected
            }
        }
    }

    fun copy() = FieldsUiState(
        favoriteIngredients = copyIngredient(favoriteIngredients),
        nonFavoritesIngredients = copyIngredient(nonFavoritesIngredients),
        allergicingredients = copyIngredient(allergicingredients),
        intolerantIngredients = copyIngredient(intolerantIngredients),
        _meal = copyRadioUiState(_meal)
    )

    private fun copyIngredient(ingredient: ArrayList<String>): ArrayList<String> {
        val copy = arrayListOf<String>()
        copy.addAll(ingredient)
        return copy
    }

    private fun copyRadioUiState(radioUiState: RadioUiState): RadioUiState {
        return when (radioUiState) {
            is RadioUiState.Selected -> RadioUiState.Selected(radioUiState.textOption)
            RadioUiState.Unselected -> RadioUiState.Unselected
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other is FieldsUiState) {
            return favoriteIngredients == other.favoriteIngredients &&
                    nonFavoritesIngredients == other.nonFavoritesIngredients &&
                    allergicingredients == other.allergicingredients &&
                    intolerantIngredients == other.intolerantIngredients &&
                    _meal == other._meal
        }
        return false
    }
}
