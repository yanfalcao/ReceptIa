package com.example.receptia.feature.historic.state

import com.example.receptia.persistence.Recipe
import com.example.receptia.persistence.utils.DifficultState

data class FilterState(
    var tag: TagFilterEnum = TagFilterEnum.ALL,
    var difficult: DifficultState? = null,
    var amountPeopleServes: AmountServesFilterEnum = AmountServesFilterEnum.NONE,
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
                recipe.difficultState == difficult
            }.toList()
        }
        return recipeList
    }

    fun filterByAmountServes(recipeList: List<Recipe>): List<Recipe> {
        return when(amountPeopleServes) {
            AmountServesFilterEnum.NONE -> recipeList
            AmountServesFilterEnum.ONE -> {
                recipeList.filter { recipe ->
                    recipe.amountPeopleServes == 1
                }.toList()
            }
            AmountServesFilterEnum.TWO -> {
                recipeList.filter { recipe ->
                    recipe.amountPeopleServes == 2
                }.toList()
            }
            AmountServesFilterEnum.THREE -> {
                recipeList.filter { recipe ->
                    recipe.amountPeopleServes == 3
                }.toList()
            }
            AmountServesFilterEnum.FOUR_OR_MORE -> {
                recipeList.filter { recipe ->
                    recipe.amountPeopleServes >= 4
                }.toList()
            }
        }
    }

    fun isSelected(tag: TagFilterEnum): Boolean {
        return this.tag == tag
    }

    override fun equals(other: Any?): Boolean {
        return false
    }
}
