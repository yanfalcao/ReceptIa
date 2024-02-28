package com.nexusfalcao.model

data class Step(
    val id: String,
    val position: Int,
    val description: String,
) {
    override fun toString(): String {
        return "${position}. $description"
    }
}
