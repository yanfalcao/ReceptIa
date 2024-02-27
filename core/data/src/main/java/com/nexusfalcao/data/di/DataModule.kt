package com.nexusfalcao.data.di

import android.app.Application
import com.nexusfalcao.data.repository.DefaultIngredientRepository
import com.nexusfalcao.data.repository.DefaultRecipeRepository
import com.nexusfalcao.data.repository.DefaultUserRepository
import com.nexusfalcao.data.repository.IngredientRepository
import com.nexusfalcao.data.repository.RecipeRepository
import com.nexusfalcao.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun providesUserRepository(appContext: Application): UserRepository {
        return DefaultUserRepository(appContext)
    }

    @Provides
    @Singleton
    fun providesRecipeRepository(): RecipeRepository {
        return DefaultRecipeRepository()
    }

    @Provides
    @Singleton
    fun providesIngredientRepository(): IngredientRepository {
        return DefaultIngredientRepository()
    }
}