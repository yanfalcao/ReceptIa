plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.panecatalogdescription"
    compileSdk = 35

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    packaging{
        resources {
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
    testOptions {
        emulatorControl {
            enable = true
        }
    }
}

dependencies {
    implementation(project(":feature:recipecatalog"))
    implementation(project(":feature:description"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
    implementation(project(":core:data"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.androidx.navigation.runtime)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.window.core.android)
    implementation(libs.androidx.adaptive.android)
    implementation(libs.androidx.adaptive.navigation.android)
    implementation(libs.androidx.compose.ui.material3)

    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.androidx.espresso.device)

    ksp(libs.hilt.compiler)

    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso)
    androidTestImplementation(libs.compose.ui.test.junit4)
    androidTestImplementation(libs.mockk)
    androidTestImplementation(libs.mockk.android)

    debugImplementation(libs.compose.ui.test.manifest)

    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
}