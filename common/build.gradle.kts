plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.kylix.common"
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
    implementation(Libraries.AndroidX.appCompat)
    implementation(Libraries.AndroidX.coreKtx)
    implementation(Libraries.AndroidX.material)
    implementation(Libraries.AndroidX.recyclerView)

    implementation(Libraries.Glide.glide)
    kapt(Libraries.Glide.compiler)

    implementation(Libraries.Coroutine.coroutinesCore)
    implementation(Libraries.Coroutine.coroutinesAndroid)

    implementation(Libraries.Retrofit.networkResponseAdapter)
    implementation(Libraries.Retrofit.retrofitGson)

    implementation(Libraries.Koin.koinCore)
    implementation(Libraries.Koin.koinAndroid)

    implementation(platform(Libraries.Arrow.arrowBOM))
    implementation(Libraries.Arrow.arrowCore)

    implementation(Libraries.CustomUI.snackify)
}