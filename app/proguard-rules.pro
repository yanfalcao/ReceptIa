# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontwarn org.bouncycastle.jsse.BCSSLParameters*
-dontwarn org.bouncycastle.jsse.BCSSLSocket*
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider*
-dontwarn org.conscrypt.Conscrypt$Version*
-dontwarn org.conscrypt.Conscrypt*
-dontwarn org.conscrypt.ConscryptHostnameVerifier*
-dontwarn org.openjsse.javax.net.ssl.SSLParameters*
-dontwarn org.openjsse.javax.net.ssl.SSLSocket*
-dontwarn org.openjsse.net.ssl.OpenJSSE*

# ReceptIA App
-keep class com.nexusfalcao.receptia.model.** { *; }
-keep class com.nexusfalcao.receptia.persistence.** { *; }
-keep class com.nexusfalcao.receptia.network.** { *; }

# Retrofit2
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

 -keep,allowobfuscation,allowshrinking interface retrofit2.Call
 -keep,allowobfuscation,allowshrinking class retrofit2.Response
 -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# OkHttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

# Okio
-dontwarn okio.**
-keep class okio.** { *; }