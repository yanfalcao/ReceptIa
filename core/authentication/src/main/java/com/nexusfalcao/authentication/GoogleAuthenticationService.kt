package com.nexusfalcao.authentication

import android.content.Intent
import android.content.IntentSender
import com.nexusfalcao.model.User

interface GoogleAuthenticationService {
    suspend fun initiateGoogleSignIn(): IntentSender?
    suspend fun processGoogleSignInResult(intent: Intent): User
    fun isUserLoggedIn(): Boolean
    suspend fun signOut()
}