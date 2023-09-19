package com.example.receptia.persistence.utils

sealed interface DifficultState {
    object Easy : DifficultState
    object Medium : DifficultState
    object Hard : DifficultState
}
