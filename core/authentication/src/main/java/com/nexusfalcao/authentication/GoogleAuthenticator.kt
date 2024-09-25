package com.nexusfalcao.authentication

import android.app.Application
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Status
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nexusfalcao.authentication.exception.CancelledAuthException
import com.nexusfalcao.authentication.exception.GenericAuthException
import com.nexusfalcao.model.User
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class GoogleAuthenticator @Inject constructor(
    appContext: Application
) : GoogleAuthenticationService {
    private val auth = Firebase.auth
    private val signInClient: SignInClient = Identity.getSignInClient(appContext)

    override suspend fun initiateGoogleSignIn(): IntentSender? {
        val result = try {
            signInClient.beginSignIn(buildSignInRequest()).await()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        return result?.pendingIntent?.intentSender
    }
    override suspend fun processGoogleSignInResult(intent: Intent): User {
        return try {
            val credential = signInClient.getSignInCredentialFromIntent(intent)
            val googleIdToken = credential.googleIdToken
            val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
            val userFirebase = auth.signInWithCredential(googleCredentials).await().user
            val user = userFirebase?.run {
                User(
                    id = uid,
                    name = displayName,
                    isLoggedIn = true,
                )
            }

            user ?: throw GenericAuthException("User not found")
        } catch (e: Exception) {
            when(e) {
                is ApiException -> {
                    if(e.status == Status.RESULT_CANCELED) {
                        throw CancelledAuthException("User cancelled the sign in")
                    } else {
                        throw GenericAuthException(e.message ?: "Error during sign in")
                    }
                }
                else -> throw GenericAuthException(e.message ?: "Error during sign in")
            }
        }
    }

    override fun isUserLoggedIn(): Boolean {
        val user = auth.currentUser
        return user != null
    }

    override suspend fun signOut() {
        try {
            signInClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            throw GenericAuthException(e.message ?: "Error during sign out")
        }
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                    .build()
            ).setAutoSelectEnabled(true)
            .build()
    }
}