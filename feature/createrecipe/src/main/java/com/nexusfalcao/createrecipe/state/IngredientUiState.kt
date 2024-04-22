package com.nexusfalcao.createrecipe.state

data class IngredientUiState(
    val favoriteIngredients: ArrayList<String> = arrayListOf(),
    val nonFavoritesIngredients: ArrayList<String> = arrayListOf(),
    val allergicingredients: ArrayList<String> = arrayListOf(),
    val intolerantIngredients: ArrayList<String> = arrayListOf(),
) {
    fun getIngredient(state: RecipeFieldState): List<String> {
        return when (state) {
            RecipeFieldState.FAVORITE -> favoriteIngredients
            RecipeFieldState.NON_FAVORITE -> nonFavoritesIngredients
            RecipeFieldState.ALLERGIC -> allergicingredients
            RecipeFieldState.INTOLERANT -> intolerantIngredients
            else -> emptyList()
        }
    }

    fun addIngredient(state: RecipeFieldState, ingredient: String) {
        when (state) {
            RecipeFieldState.FAVORITE -> {
                favoriteIngredients.add(ingredient)
            }
            RecipeFieldState.NON_FAVORITE -> {
                nonFavoritesIngredients.add(ingredient)
            }
            RecipeFieldState.ALLERGIC -> {
                allergicingredients.add(ingredient)
            }
            RecipeFieldState.INTOLERANT -> {
                intolerantIngredients.add(ingredient)
            }
            else -> {}
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
            else -> {}
        }
    }

    fun copy() = IngredientUiState(
        favoriteIngredients = copyIngredient(favoriteIngredients),
        nonFavoritesIngredients = copyIngredient(nonFavoritesIngredients),
        allergicingredients = copyIngredient(allergicingredients),
        intolerantIngredients = copyIngredient(intolerantIngredients),
    )

    private fun copyIngredient(ingredient: ArrayList<String>): ArrayList<String> {
        val copy = arrayListOf<String>()
        copy.addAll(ingredient)
        return copy
    }

    override fun equals(other: Any?): Boolean {
        if (other is IngredientUiState) {
            return favoriteIngredients == other.favoriteIngredients &&
                    nonFavoritesIngredients == other.nonFavoritesIngredients &&
                    allergicingredients == other.allergicingredients &&
                    intolerantIngredients == other.intolerantIngredients
        }
        return false
    }
}
