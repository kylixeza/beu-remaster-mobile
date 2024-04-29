import com.android.builder.internal.aapt.AaptOptions

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.kylix.camera"
    compileSdk = App.compileSdk

    aaptOptions {
        noCompress("tflite", "lite")
    }

    defaultConfig {
        minSdk = App.minSdk

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        mlModelBinding = true
    }
}

dependencies {

    implementation(Libraries.AndroidX.coreKtx)
    implementation(Libraries.AndroidX.fragmentKtx)
    implementation(Libraries.AndroidX.lifecycleViewModelKtx)
    implementation(Libraries.AndroidX.navigationFragment)
    implementation(Libraries.AndroidX.navigationUI)

    implementation(Libraries.CameraX.cameraCore)
    implementation(Libraries.CameraX.camera2)
    implementation(Libraries.CameraX.cameraLifecycle)
    implementation(Libraries.CameraX.cameraView)
    implementation(Libraries.CameraX.cameraExtensions)

    implementation(Libraries.TFLite.tfLite)
    implementation(Libraries.TFLite.tfLiteSupport)
    implementation(Libraries.TFLite.tfLiteGPU)
    implementation(Libraries.TFLite.tfLiteGPUApi)
    implementation(Libraries.TFLite.tfLiteMetadata)

    implementation(Libraries.Lottie.lottie)
    implementation(Libraries.Lottie.dotLottie)

    implementation(Libraries.Koin.koinCore)
    implementation(Libraries.Koin.koinAndroid)

    implementation(platform(Libraries.Arrow.arrowBOM))
    implementation(Libraries.Arrow.arrowCore)

    api(project(Modules.core))
}