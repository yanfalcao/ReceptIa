package com.nexusfalcao.data.di

import android.app.Application
import com.nexusfalcao.data.repository.DefaultRecipeRepository
import com.nexusfalcao.data.repository.DefaultUserRepository
import com.nexusfalcao.data.repository.RecipeRepository
import com.nexusfalcao.data.repository.UserRepository
import com.nexusfalcao.database.ReceptIaDatabase
import com.nexusfalcao.network.retrofit.RetrofitNetwork
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
        return DefaultUserRepository(
            userDao = ReceptIaDatabase.getInstance(appContext)?.userDao(),
        )
    }

    @Provides
    @Singleton
    fun providesRecipeRepository(appContext: Application): RecipeRepository {
        return DefaultRecipeRepository(
            recipeDao = ReceptIaDatabase.getInstance(appContext)?.recipeDao(),
            ingredientDao = ReceptIaDatabase.getInstance(appContext)?.ingredientDao(),
            stepDao = ReceptIaDatabase.getInstance(appContext)?.stepDao(),
            chatgptNetworkApi = RetrofitNetwork.gptService(),
        )
    }
}
