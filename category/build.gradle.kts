plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.kylix.category"
    compileSdk = App.compileSdk

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
    }
}

dependencies {

    implementation(Libraries.AndroidX.coreKtx)
    implementation(Libraries.AndroidX.fragmentKtx)
    implementation(Libraries.AndroidX.material)
    implementation(Libraries.AndroidX.constraintLayout)
    implementation(Libraries.AndroidX.lifecycleViewModelKtx)
    implementation(Libraries.CustomUI.sneaker)
    implementation(Libraries.Glide.glide)

    implementation(Libraries.Koin.koinCore)
    implementation(Libraries.Koin.koinAndroid)

    implementation(platform(Libraries.Arrow.arrowBOM))
    implementation(Libraries.Arrow.arrowCore)

    implementation(project(Modules.common))
    implementation(project(Modules.core))
}