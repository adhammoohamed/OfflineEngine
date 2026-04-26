// ✅ Added hilt and ksp plugins here — they MUST be declared at root level
// even if apply = false. Without this, the app module plugin alias will fail.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android)      apply false
    alias(libs.plugins.kotlin.compose)      apply false
    alias(libs.plugins.kotlin.ksp)          apply false
    alias(libs.plugins.hilt)                apply false
    alias(libs.plugins.protobuf)            apply false
}