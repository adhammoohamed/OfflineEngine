plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.protobuf)  // ✅ add this

}

android {
    namespace   = "com.example.offlineengine"
    compileSdk  = 35

    defaultConfig {
        applicationId          = "com.example.offlineengine"
        minSdk                 = 26   //
        targetSdk              = 35
        versionCode            = 1
        versionName            = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = com.android.build.gradle.internal.cxx.configure
            .gradleLocalProperties(rootDir, providers)
        buildConfigField("String", "NEWS_API_KEY", "\"${properties["NEWS_API_KEY"] ?: ""}\"")
        buildConfigField("String", "BASE_URL",     "\"${properties["BASE_URL"]     ?: ""}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled    = true
            isShrinkResources  = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled      = false
            applicationIdSuffix  = ".debug"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs += listOf(
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.paging.ExperimentalPagingApi"
        )
    }

    buildFeatures {
        compose     = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += setOf(
                "META-INF/gradle/incremental.annotation.processors",
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md"
            )
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.26.1"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    // ─── AndroidX Core ────────────────────────────────────────────────────
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // ─── Compose (BOM controls all versions) ──────────────────────────────
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.icons.extended)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // ─── Lifecycle ────────────────────────────────────────────────────────
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.compose)

    // ─── Navigation ───────────────────────────────────────────────────────
    implementation(libs.navigation.compose)

    // ─── Hilt ─────────────────────────────────────────────────────────────
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)             // ✅ ksp only — no kapt

    // ─── Room ─────────────────────────────────────────────────────────────
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)             // ✅ ksp only — no kapt

    // ─── Paging ───────────────────────────────────────────────────────────
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)

    // ─── Network ──────────────────────────────────────────────────────────
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging)  // ✅ 5.x — matches Retrofit 3.x's OkHttp 5 dependency

    // ─── Image ────────────────────────────────────────────────────────────
    implementation(libs.coil.compose)

    // ─── WorkManager + Hilt integration ───────────────────────────────────
    implementation(libs.work.runtime)
    implementation(libs.hilt.work)
    ksp(libs.hilt.work.compiler)        // ✅ ksp only — no kapt


    implementation(libs.datastore.preferences)  // Preferences DataStore
    implementation(libs.datastore.proto)         // Proto DataStore
    implementation(libs.protobuf.javalite)       // Proto runtime


    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("com.google.android.material:material:1.12.0")

    // ─── Testing ──────────────────────────────────────────────────────────
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)


}