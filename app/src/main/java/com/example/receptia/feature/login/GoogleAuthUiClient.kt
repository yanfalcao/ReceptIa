package com.example.receptia.feature.login

import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.receptia.BuildConfig
import com.example.receptia.model.SignInError
import com.example.receptia.model.SignInErrorStatus
import com.example.receptia.model.SignInResult
import com.example.receptia.persistence.User
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Status
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class GoogleAuthUiClient(
    private val oneTapClient: SignInClient
) {
    private val auth = Firebase.auth

    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent: Intent): SignInResult {
        return try {
            val credential = oneTapClient.getSignInCredentialFromIntent(intent)
            val googleIdToken = credential.googleIdToken
            val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
            val userFirebase = auth.signInWithCredential(googleCredentials).await().user
            val user = userFirebase?.run {
                User().apply {
                    id = uid
                    name = displayName
                }
            }
            SignInResult(
                data = user,
                error = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            val status: SignInErrorStatus = when(e) {
                is ApiException -> {
                    if(e.status == Status.RESULT_CANCELED) {
                        SignInErrorStatus.CANCELLED
                    } else {
                        SignInErrorStatus.GENERIC
                    }
                }
                else -> SignInErrorStatus.GENERIC
            }
            SignInResult(
                data = null,
                error = SignInError(
                    message = e.message,
                    status = status
                )
            )
        }
    }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getSignedInUser(): User? = auth.currentUser?.run {
        User().apply {
            id = uid
            name = displayName
        }
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                    .build()
            ).setAutoSelectEnabled(true)
            .build()
    }
}