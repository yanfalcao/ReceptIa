package com.nexusfalcao.authentication.fake

import android.content.Intent
import android.content.IntentSender
import com.nexusfalcao.authentication.GoogleAuthenticationService
import com.nexusfalcao.model.User

class FakeGoogleAuthenticator : GoogleAuthenticationService {
    override suspend fun initiateGoogleSignIn(): IntentSender? {
        TODO("Not yet implemented")
    }

    override suspend fun processGoogleSignInResult(intent: Intent): User {
        TODO("Not yet implemented")
    }

    override fun isUserLoggedIn(): Boolean {
        return true
    }

    override suspend fun signOut() {}
}
