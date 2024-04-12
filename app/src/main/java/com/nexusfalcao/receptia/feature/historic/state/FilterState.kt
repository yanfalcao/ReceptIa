package com.nexusfalcao.receptia.feature.historic.state

import com.nexusfalcao.model.Recipe
import com.nexusfalcao.model.state.RecipeDifficult

data class FilterState(
    var tag: TagFilterEnum = TagFilterEnum.ALL,
    var difficult: RecipeDifficult? = null,
    var amountPeopleServes: AmountServesFilterEnum? = null,
    var search: String = "",
) {
    fun filterByTag(recipeList: List<Recipe>): List<Recipe> {
        return when (tag) {
            TagFilterEnum.FAVORITES -> {
                recipeList.filter { recipe ->
                    tag == TagFilterEnum.FAVORITES && recipe.isFavorite
                }.toList()
            }

            TagFilterEnum.ALL -> recipeList.toList()
        }
    }

    fun filterBySearch(recipeList: List<Recipe>): List<Recipe> {
        if (search.isNotEmpty() && search.isNotBlank()) {
            return recipeList.filter { recipe ->
                recipe.name.lowercase().contains(search.lowercase())
            }.toList()
        }
        return recipeList
    }

    fun filterByDifficult(recipeList: List<Recipe>): List<Recipe> {
        if (difficult != null) {
            return recipeList.filter { recipe ->
                recipe.recipeDetails.recipeDifficult == difficult
            }.toList()
        }
        return recipeList
    }

    fun filterByAmountServes(recipeList: List<Recipe>): List<Recipe> {
        return when(amountPeopleServes) {
            null -> recipeList
            AmountServesFilterEnum.ONE -> {
                recipeList.filter { recipe ->
                    recipe.recipeDetails.amountPeopleServes == 1
                }.toList()
            }
            AmountServesFilterEnum.TWO -> {
                recipeList.filter { recipe ->
                    recipe.recipeDetails.amountPeopleServes == 2
                }.toList()
            }
            AmountServesFilterEnum.THREE -> {
                recipeList.filter { recipe ->
                    recipe.recipeDetails.amountPeopleServes == 3
                }.toList()
            }
            AmountServesFilterEnum.FOUR_OR_MORE -> {
                recipeList.filter { recipe ->
                    recipe.recipeDetails.amountPeopleServes >= 4
                }.toList()
            }
        }
    }

    fun isSelected(tag: TagFilterEnum): Boolean {
        return this.tag == tag
    }

    fun isSelected(difficult: RecipeDifficult): Boolean {
        return this.difficult == difficult
    }

    fun isSelected(amount: AmountServesFilterEnum): Boolean {
        return this.amountPeopleServes == amount
    }

    fun hasAnyFilterSelected(): Boolean {
        return this.difficult != null || this.amountPeopleServes != null
    }

    override fun equals(other: Any?): Boolean {
        return false
    }
}
